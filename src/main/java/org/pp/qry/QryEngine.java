package org.pp.qry;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.concurrent.TimeUnit;

final class QryEngine {
	/** declare operator constant */
	private static final int OP_MUL 	= 0;
	private static final int OP_DIV 	= 1;
	private static final int OP_MOD 	= 2;
	private static final int OP_ADD 	= 3;
	private static final int OP_SUB 	= 4;
	private static final int OP_LT 		= 5;
	private static final int OP_LTE 	= 6;
	private static final int OP_GTE 	= 7;
	private static final int OP_GT 		= 8;
	private static final int OP_EQ 		= 9;
	private static final int OP_NEQ 	= 10;
	private static final int OP_AND 	= 11;
	private static final int OP_OR 		= 12;
	private static final int OP_BET 	= 13;
	private static final int OP_NBET 	= 14;
	private static final int OP_IN 		= 15;
	private static final int OP_NIN 	= 16;
	private static final int OP_PRN 	= 17;
	
	/** Declares operator's priority */
	private static final int PRI_PARAN 		= 99; // '(' expression ')'
	private static final int PRI_MATH_HIGH 	= 95; // '*', '/' , '%'
	private static final int PRI_MATH_LOW 	= 80; // '+' , '-'
	private static final int PRI_COND_OP 	= 75; // '>', '>=', '<', '<=', '=' , '!=', 'in', 'between'
	private static final int PRI_AND 		= 70; // '&&'
	private static final int PRI_OR 		= 65; // '||'

	/** Declare value type */
	private static final int TYPE_OP 	= 6; // Type operator
	private static final int TYPE_ID 	= 5; // Value type is ID
	private static final int TYPE_STR 	= 4; // Value type is String value
	private static final int TYPE_NUM 	= 3; // Value type is number
	private static final int TYPE_BOOL 	= 2; // Value type is boolean
	private static final int TYPE_NULL 	= 1; // Value type is null
	private static final int TYPE_PRN 	= 0;

	/** Constant for debugging purpose */
	private static final String STR_ADD 	= "+";
	private static final String STR_SUB 	= "-";
	private static final String STR_MUL 	= "*";
	private static final String STR_DIV 	= "/";
	private static final String STR_MOD 	= "%";
	private static final String STR_GT 		= ">";
	private static final String STR_GTE 	= ">=";
	private static final String STR_LT 		= "<";
	private static final String STR_LTE 	= "<=";
	private static final String STR_EQ 		= "=";
	private static final String STR_NEQ 	= "!=";
	private static final String STR_AND 	= "&&";
	private static final String STR_OR 		= "||";
	private static final String STR_IN 		= "in";
	private static final String STR_NOT 	= "not";
	private static final String STR_NIN 	= "nin";
	private static final String STR_BET 	= "between";
	private static final String STR_NBET 	= "nbet";
	private static final String STR_PRN 	= "(";
	private static final String STR_PRN_END = ")";

	/** Root Node of expression tree */
	private Node root = null;
	/** User parameters */
	private Object[] params = null;
	/** Parameter Index */
	private int prmIndx = 0;
	/** Query context */
	private QryContext qCtx = null;
	/** SQL query to parse*/
	private String sql = null;
	/** Hold current char pointer as well as number of operations */
	private int pos = 0;
	/** sequence number when the load occurred */
	private int loadSeqNo = -1;
	/** Load indicator */
	private boolean loaded = false;
	/** If the data loading is done via PK */
	private boolean seekByPk = false;
	/** To build token */
	private StringBuilder sbldr = new StringBuilder();	
	/** To help build tree (expression) */
	private Deque<Node> stk = new LinkedList<>();
			
	private QryEngine() { }
	private QryEngine(QryContext ctx, String qryStr, Object[] params) { 
		this.params = params;
		this.sql = qryStr;
		this.qCtx = ctx;
	}
	// Get a Filtered list
	static List<Map<String,Object>> getFilteredList(QryContext ctx, String qryStr, Object[] params) {
		// Compile query
		QryEngine qry = new QryEngine(ctx, qryStr, params);
		qry.expr();
		// Stake size
		if (qry.stk.size() != 1)
			tRex("Something seriously went wrong");
		// assign it too root node
		qry.root = qry.stk.pop();
		if (qry.root.priority > PRI_COND_OP)
			tRex("Invalid expression, expression must be evaluated to binray value");
		// Execute
		return qry.getFilteredList(ctx, -1); 
	}
	 // 
	List<Map<String,Object>> getFilteredList(QryContext ctx, int pageSz) {
		Node tmp = null; int c = 0; this.qCtx = ctx;
		List<Map<String, Object>> retList = new ArrayList<>();
		do {
        	pos = -1;
        	tmp = evaluate(root); 
        	if ((boolean) tmp.val && loaded)
        	   retList.add(ctx.currentMap());
        	c++;
        } while (ctx.hasNextMap() && (pageSz < 0 || pageSz > 0 && c < pageSz));
        //System.out.println("Counter: " + c);
		return retList;
	}
	// decide how to load based on PK
	@SuppressWarnings("unchecked")
	private void loadByPk(Object val, int op) {
		// =, >, >=, <, <=, between
		if (val == null)
			throw new RuntimeException("PK value can not be null");
		else if (op == OP_GT)
			val = qCtx.higherValue(val);
		else if (op == OP_BET)
			val = ((List<Node>)val).get(0).val;
		else if (op < OP_GTE)
			val = null;
		// do seek to right record
		qCtx.seek(val);
		// update seek indicator
		seekByPk = true;		
	}
	// Evaluate tree recursively
	private Node evaluate(Node node) {
		if (node.type != TYPE_OP)
			return node;
		// visit left subtree first
		Node left = evaluate(node.left);
		if (left == null)
			return null;
		// visit right subtree
		Node right = evaluate(node.right);
		if (right == null)
			return null;
		// Perform action
		/** Increment operation counter */
		pos++; int op = (int) node.val;
		Node res = null;
		
		// Load records first if not loaded yet
		if (!loaded && (left.type == TYPE_ID || right.type == TYPE_ID)) {
			// check if root is || conditioned
			if (root.priority == PRI_OR)
				qCtx.seek(null);
			// for arithmetic operators, !=, &&, ||, in(not), not between.
			else if (op < OP_LT || (op > OP_EQ && op < OP_BET) || op > OP_BET)
				qCtx.seek(null);
			// if both type are id we will be loading since beginning
			else if (left.type == TYPE_ID && right.type == TYPE_ID)
				qCtx.seek(null);
			// primary key conditions
			else if (left.type == TYPE_ID && qCtx.isPk((String)left.val))
				loadByPk(right.val,op);
			else if (right.type == TYPE_ID && qCtx.isPk((String)right.val))
				loadByPk(left.val,op);
			else
				qCtx.seek(null);	
			
			// check if any record exist after loading
			if (qCtx.currentMap() == null)
				return null;
			// record load operation number
			loadSeqNo = pos;
			// mark as loaded
			loaded = true;			
		}
		
		// get value node
		Node l = idToNode(left);
		Node r = idToNode(right);
		
		// If arithmetic operator '+', '-' , '/' , '*' , '%'
		if (op < OP_LT) {
			// validate type
			if (l.type != r.type || l.type != TYPE_NUM)
				tRex("Type must be number for both operand '" + left.val + " " + op + " " + right.val + "'");
			// Now get numbers
			MyNumber mnl = (MyNumber) l.val;
			MyNumber mnr = (MyNumber) r.val;
            //
			switch (op) {
				case OP_ADD:
					res = new Node(mnl.add(mnr), TYPE_NUM);
					break;
				case OP_SUB:
					res = new Node(mnl.sub(mnr), TYPE_NUM);
					break;
				case OP_MUL:
					res = new Node(mnl.mul(mnr), TYPE_NUM);
					break;
				case OP_DIV:
					res = new Node(mnl.div(mnr), TYPE_NUM);
					break;
				case OP_MOD:
					res = new Node(mnl.remainder(mnr), TYPE_NUM);
					break;
				default:
					tRex("Unknown operation type");
			}			
		} else {
			// Return value must be boolean condition
			res = new Node(false, TYPE_BOOL);
			// For condition operator >, >=, <, <=
			if (op < OP_EQ) {
				// validate type
				if (l.type != r.type || l.val == null)
					tRex("Type mismatch or null value '" + left.val + " " + op + " " + right.val + "'");
				// do action
				switch (op) {
					case OP_GT:
						res.val = (l.compareTo(r) > 0) ? true : false;
						break; 
					case OP_GTE:
						res.val = (l.compareTo(r) >= 0) ? true : false;
						break;
					case OP_LT:
						res.val = (l.compareTo(r) < 0) ? true : false;
						break;
					case OP_LTE:
						res.val = (l.compareTo(r) <= 0) ? true : false;
						break;
					default:
						tRex("Unknown operation type");	
				}				
			}
			// For Conditional operator = , !=
			else if (op < OP_AND) {
				if (l.val != null && r.val != null) {
					// check type
					if (l.type != r.type)
						tRex("Type mismatch '" + left.val + " " + op + " " + right.val + "'");
					// check
					int eq = l.compareTo(r);
					if (eq != 0 && op == OP_NEQ)
						res.val = true;
					else if (eq == 0 && op == OP_EQ)
						res.val = true;
				} 
				else if (l.val == null && r.val == null)
					res.val = op == OP_EQ ? true : false;
				else if (op == OP_NEQ)
					res.val = true;				
			}
			// '||' or '&&' operator
			else if (op < OP_BET) {
				if (l.type != r.type || l.type != TYPE_BOOL)
					tRex("Type mismatch/must be of boolean type '" + left.val + " " 
							+ op + " " + right.val + "'");
				if (op == OP_OR && ((boolean)l.val || (boolean)r.val))
					res.val = true;
				else if (op == OP_AND && ((boolean)l.val && (boolean)r.val))
					res.val = true;					
			}
			// 'between' or 'not between'
			else if (op < OP_IN) {
				// check type
				if (l.type != r.type)
					tRex("Type mismatch '" + left.val + " " + op + " " + right.val + "'");
				@SuppressWarnings("unchecked")
				List<Node> list = (List<Node>) r.val;
				if (op == OP_BET && (l.compareTo(list.get(0)) >= 0 && l.compareTo(list.get(1)) <= 0)) 
					res.val = true;
				else if (op == OP_NBET && (l.compareTo(list.get(0)) < 0 || l.compareTo(list.get(1)) > 0))
					res.val = true;
			}
			// 'in' or 'not in' operator
			else {
				// check type
				if (l.type != r.type)
				  tRex("Type mismatch '" + left.val + " " + op + " " + right.val + "'");
				@SuppressWarnings("unchecked")
				Set<Node> nSet = (Set<Node>) r.val;
				if (op == OP_IN && nSet.contains(l))
					res.val = true;
				else if (op == OP_NIN && !nSet.contains(l))
					res.val = true;
			}
			// quite if result was false and primary key
			if (!(boolean) res.val && pos == loadSeqNo && seekByPk)
				qCtx.quite();	
		}		
		return res;				
	}
	
	// Convert ID to Value node
	private Node idToNode(Node n) {
		if (n.type == TYPE_ID) {
			Object v = qCtx.currentMap().get((String) n.val);
			n = Node.objToNode(v);
		}
		return n;
	}
	// start parsing expression
	private void expr() {
		char uPoint = (char) checkNextChar();
		// if parentheses start
		if (uPoint != '(') {
			pos--;
			operand();// get operand
		} else {
			stk.push(new Node(OP_PRN, TYPE_PRN, PRI_PARAN));
			// call expression recursively
			expr();
			expectToken(STR_PRN_END);
			// process parentheses
			processParan();
		}
		// if operator
		operator();
	}
	// process parameter 
	private void processParan() {
		// remove right operand
		Node r = stk.pop();
		if (r.type == OP_PRN)
			return;
		// remove '('
		stk.pop();
		// Make it high priority
		r.priority = PRI_PARAN;
		if (!stk.isEmpty()) 
		  buildTree(r);		
	}
	// scan next operator
	private void operator() {
		// get basic operator
		int cPos = pos;
		// get token
		String op = nextToken();
		if (op.length() > 0) {
			// Possible operator of one length
			if (op.length() == 1) {
				switch (op) {
					case STR_GT:
						stk.push(new Node(OP_GT, TYPE_OP, PRI_COND_OP));
						break;
					case STR_LT:
						stk.push(new Node(OP_LT, TYPE_OP, PRI_COND_OP));
						break;
					case STR_EQ:
						stk.push(new Node(OP_EQ, TYPE_OP, PRI_COND_OP));
						break;
					case STR_ADD:
						stk.push(new Node(OP_ADD, TYPE_OP, PRI_MATH_LOW));
						break;
					case STR_SUB:
						stk.push(new Node(OP_SUB, TYPE_OP, PRI_MATH_LOW));
						break;
					case STR_MUL:
						stk.push(new Node(OP_MUL, TYPE_OP, PRI_MATH_HIGH));
						break;
					case STR_DIV:
						stk.push(new Node(OP_DIV, TYPE_OP, PRI_MATH_HIGH));
						break;
					case STR_MOD:
						stk.push(new Node(OP_MOD, TYPE_OP, PRI_MATH_HIGH));
						break;
					case STR_PRN_END: 
						pos = cPos;
						return;
					default:
						tRexOp(cPos);
				}
			}
			// possible operator of length 2
			else if (op.length() == 2) {
				switch (op) {
					case STR_GTE:
						stk.push(new Node(OP_GTE, TYPE_OP, PRI_COND_OP));
						break;
					case STR_LTE:
						stk.push(new Node(OP_LTE, TYPE_OP, PRI_COND_OP));
						break;
					case STR_NEQ:
						stk.push(new Node(OP_NEQ, TYPE_OP, PRI_COND_OP));
						break;
					case STR_OR:
						stk.push(new Node(OP_OR, TYPE_OP, PRI_OR));
						break;
					case STR_AND:
						stk.push(new Node(OP_AND, TYPE_OP, PRI_AND));
						break;
					case STR_IN:
						processSplOp(OP_IN);
						return;
					default:
						tRexOp(cPos);
				}
			}
			// ... not between or in
			else {
				switch (op) {
					case STR_NOT:
						// Check if between or in provided or not
						cPos = pos;
						op = nextToken();
						switch (op) {
							case STR_BET:
								processSplOp(OP_NBET);
								return;
							case STR_IN:
								processSplOp(OP_NIN);
								return;
							default:
								tRex("Expecting 'between' or 'in' operator @" + cPos);
						}
						break;
					case STR_BET:
						processSplOp(OP_BET);
						return;
					case STR_NBET :
						processSplOp(OP_NBET);
						return;
					case STR_NIN :
						processSplOp(OP_NIN);
						return;
					default:
						tRexOp(cPos);
				}
			}
			// now call expression
			expr();
		}
	}
	// scan next token
	private String nextToken() {
		sbldr.setLength(0);
		for (int uPoint = -1; pos < sql.length();) {
			uPoint = sql.codePointAt(pos++);
			if (!Character.isWhitespace(uPoint))
				sbldr.appendCodePoint(uPoint);
			else if (sbldr.length() == 0)
				continue;
			else
				break;
		}
		return sbldr.toString();
	}
	// expect a provided token, throw exception if not
	private boolean expectToken(String exTkn) {
		int cPos = pos;
		String tkn = nextToken();
		if (!tkn.equals(exTkn))
			tRex("Excpecting '" + exTkn + "' @" + cPos);
		return true;
	}
	// check next token, move position if it was asked so
	private int checkNextChar() {
		for (int uPoint = -1; pos < sql.length();) {
			uPoint = sql.codePointAt(pos++);
			if (!Character.isWhitespace(uPoint))
				return uPoint;
		}
		return -1;
	}
	// Process Special operator between/in, not between/nbet, not in/nin
	private void processSplOp(int op) {
		java.util.Collection<Node> coll = op < OP_IN ? new ArrayList<>(2) : new HashSet<>();
		stk.push(new Node(op, TYPE_OP, PRI_COND_OP));
		// Check parentheses start
		int cPos = pos;
		char ch = (char) checkNextChar();
		if (ch != '(')
			tRex("Expecting '(' @" + cPos);
		// Start getting operand
		Node r = null, l = null; 
		while (true) {
			// get next operand
			cPos = pos;
			operand();
			r = stk.pop();
			if (r.type == TYPE_ID)
				tRex("Identifier is not allowed for in/between operator @" + cPos);
			else if (l == null)
				l = r;
			else if (l.type != r.type || l.val == null)
				tRex("Either type mismatch or null for in/between operator @" + cPos);
			// for between operator
			else if (op < OP_IN && l.compareTo(r) >= 0) 
				tRex("Right operands of (not) between operator is not valid @" + cPos);
			// add to set
			coll.add(r);
			// get ',' or ')'
			cPos = pos;
			ch = (char) checkNextChar();
			if (ch == ',')
				continue;
			else if (ch == ')')
				break;
			else
				tRex("Expecting ',' or ')' @" + cPos);
		}
		// check if multiple operands were provided
		if (op < OP_IN && coll.size() > 2)
			tRex("More than two operands for (not) between operator @" + cPos);
		// Set of Node
		r = new Node(coll, l.type);
		buildTree(r);
		// check for relational operator's presence
		checkRelOp();
	}	
	// common for between/in processing
	private void checkRelOp() {
		int cPos = pos;
		nextToken();		
		if (sbldr.length() > 0) {
			String relOp = sbldr.toString();
			switch (relOp) {
			    case "&&" :
			    	stk.push(new Node(OP_AND, TYPE_OP, PRI_AND));
			    	break;
			    case "||" :
			    	stk.push(new Node(OP_OR, TYPE_OP, PRI_OR));
			    	break;
			    default :
			    	tRex("Expecting '&&' or '||' @" + cPos);
			}
			expr();
		}
	}
	// scan next number / string or integer....
	private void operand() {
		// reset token builder
		sbldr.setLength(0); 
		// get current position
		int cPos = pos, state = -1;	
		// continue recognising operand
		for (int uPoint = -1; pos < sql.length();) {
			uPoint = sql.codePointAt(pos++);
			// initial state
			if (state < 0) {
				if (Character.isWhitespace(uPoint))
					continue;
				else if (uPoint == '\'') // Start of String operand
					state = 4;
				// If start of number operand
				else if (isNumber(uPoint)) {
					state = uPoint == '.' ? 2 : 0;	
					pos--;
				} 
				// or ID, boolean, null, ?
				else { state = 6; pos--; }
				cPos = pos;
			}
			// Number state Integer=0, Long=1, Float=2, Double=3,
			else if (state < 4) {
				// continue with current state and digits
				if (!isNumber(uPoint)) {
					if (uPoint == 'd') state = 3;
					else if (uPoint == 'l') state = 1;
					else pos--;
					break;
				} 
				else
					sbldr.appendCodePoint(uPoint);				
			}
			// String state
			else if (state < 5) {
				if (uPoint != '\'')  sbldr.appendCodePoint(uPoint);
				else  { state = 5; break; }
			}
			// must be id
			else {
				// Check if characters are acceptable
				if (!isAlphaNumeric(uPoint)) { pos--; break; }
				else 
					sbldr.appendCodePoint(uPoint);					
			}			
		}
		// check if operand found or not
		// Operand node
		Node oprnd = null;
		// check current state
		switch (state) {
			// ID, boolean, null, ?
			case 6:
				String id = sbldr.toString().trim();
				if ("true".equals(id))
					oprnd = new Node(true, TYPE_BOOL);
				else if ("false".equals(id))
					oprnd = new Node(false, TYPE_BOOL);
				else if ("null".equals(id))
					oprnd = new Node(null, TYPE_NULL);
				else if ("?".equals(id)) {
					Object obj = params[prmIndx++];
					oprnd = Node.objToNode(obj);
				} else
					oprnd = new Node(id, TYPE_ID);
				break;
			// String type
			case 5:
				oprnd = new Node(sbldr.toString(), TYPE_STR);
				break;
			// Process number type
			case 0:
			case 1:
			case 2:
			case 3:
				MyNumber mn = null;
				try { mn = new MyNumber(sbldr.toString(), state); } 
				catch (NumberFormatException ne) { tRexOprnd(cPos); }
				oprnd = new Node(mn, TYPE_NUM);
				break;
			// Any other state, throw exception
			default:
				tRexOprnd(cPos);
		}
		// stack is empty or its between/in (not) operator
		if (stk.isEmpty() || ((int) stk.peek().val) > OP_OR) {
			stk.push(oprnd);
			return;
		}
		// Now build tree if possible
	    buildTree(oprnd);		
	}
	// Check if it is number
	private boolean isNumber(int uPoint) {
	  return uPoint == '.' || 
			 (uPoint > 47 && uPoint < 58) || 
			 uPoint == '+' || uPoint == '-';
	}
	// is alpha numeric and special acceptable characters
	private boolean isAlphaNumeric(int uPoint) {
		return (uPoint > 62 && uPoint < 91)		|| // Upper case...
			   (uPoint > 96 && uPoint < 123)	|| // Lower case...
			   (uPoint > 47 && uPoint < 58)		|| // Digits
			   (uPoint == 35 || uPoint == 36)	|| // #,$
			   (uPoint == 45 || uPoint == 46)	|| // -, .
			   (uPoint == 95);					   // _	(under score)
	}
	// build tree
	private void buildTree(Node r) {
		// Pop operator
		Node op = stk.pop();
		// Pop left operand or subtree
		Node l = stk.pop();

		Node tmp = l;
		while (tmp.right != null && op.priority > tmp.priority)
			tmp = tmp.right;

		Node nl = tmp.copy();
		tmp.reset(op.val, op.type, op.priority, nl, r);
		// Push resultant tree
		stk.push(l);
	}
	/**
	 * Expression tree node
	 * 
	 * @author pan.prasanta@gmail.com
	 *
	 */
	private static final class Node {
		/** terminal or non terminal value */
		private Object val;
		/** operator priority */
		private int priority = -1;
		/** node type value */
		private int type = -1;
		/** Left/right sub tree pointer */
		private Node left, right;
		// constructors
		private Node(Object val, int type) { this.val = val; this.type = type; }
		private Node(Object val, int type, int priority) { this(val, type); this.priority = priority; }

		// redefine node
		private void reset(Object val, int type, int priority, Node l, Node r) {
			this.left = l;
			this.right = r;
			this.val = val;
			this.type = type;
			this.priority = priority;
		}
        // clone Node
		private Node copy() {
			Node node = new Node(this.val, this.type, this.priority);
			node.left = this.left;
			node.right = this.right;
			return node;
		}
		// 
		@SuppressWarnings("unchecked")
		private int compareTo(Node othr) {
			Comparable<Object> l = (Comparable<Object>) val;
			Comparable<Object> r = (Comparable<Object>) othr.val;
			return l.compareTo(r);			
		}
		// Object to node mapping
		private static Node objToNode(Object obj) {
			Node vNode = null;
			if (obj == null)
				vNode = new Node(obj, TYPE_NULL);
			else if (obj instanceof String)
				vNode = new Node(obj, TYPE_STR);
			else if (obj instanceof Integer)
				vNode = new Node(new MyNumber(MyNumber.INT, obj), TYPE_NUM);
			else if (obj instanceof Long)
				vNode = new Node(new MyNumber(MyNumber.LONG, obj), TYPE_NUM);
			else if (obj instanceof Float)
				vNode = new Node(new MyNumber(MyNumber.FLT, obj), TYPE_NUM);
			else if (obj instanceof Double)
				vNode = new Node(new MyNumber(MyNumber.DBL, obj), TYPE_NUM);
			else if (obj instanceof Boolean)
				vNode = new Node(obj, TYPE_BOOL);
			else
				throw new RuntimeException("Unknown/Unsupported object type");
			return vNode;
		}		
		@Override
		public String toString() {
			if (val == null)
				return "null";
			else if (type != TYPE_OP)
				return val.toString();
			else {
				int v = (int) val;
				switch (v) {
				case OP_GT:
					return STR_GT;
				case OP_LT:
					return STR_LT;
				case OP_GTE:
					return STR_GTE;
				case OP_LTE:
					return STR_LTE;
				case OP_EQ:
					return STR_EQ;
				case OP_NEQ:
					return STR_NEQ;
				case OP_AND:
					return STR_AND;
				case OP_OR:
					return STR_OR;
				case OP_ADD:
					return STR_ADD;
				case OP_SUB:
					return STR_SUB;
				case OP_MUL:
					return STR_MUL;
				case OP_DIV:
					return STR_DIV;
				case OP_MOD:
					return STR_MOD;
				case OP_IN:
					return STR_IN;
				case OP_BET:
					return STR_BET;
				case OP_PRN:
					return STR_PRN;
				case OP_NIN:
					return STR_NOT + " " + STR_IN;
				case OP_NBET:
					return STR_NOT + " " + STR_BET;
				default:
					tRex("Unknown operator");
				}
			}
			return null;				
		}		
		@Override
		public int hashCode() { return val.hashCode(); }
		@Override
		public boolean equals(Object othr) {
			Node n = (Node) othr;
			return val.equals(n.val);
		}
	}

	// To handle number related operations
	private static final class MyNumber implements Comparable<MyNumber> {
		// number type int=0, long = 1, float = 2, double = 3
		private int type = -1;
		// Java number object
		private Number numObj = null;
		// define data type
		private static final int INT 	= 0;
		private static final int LONG 	= 1;
		private static final int FLT 	= 2;
		private static final int DBL 	= 3;

		// constructors
		private MyNumber(int t, Object num) { this.numObj = (Number) num; this.type = t; }
		private MyNumber(String numStr, int t) {
			type = t;
			switch (t) {
				case 0: numObj = Integer.parseInt(numStr); break;
				case 1: numObj = Long.parseLong(numStr); break;
				case 2: numObj = Float.parseFloat(numStr); break;
				case 3: numObj = Double.parseDouble(numStr); break;
			}
		}
		@Override
		public int hashCode() { return numObj.hashCode(); }
		@Override
		public boolean equals(Object othr) { 
			MyNumber o = (MyNumber) othr; 
			return compareTo(o) == 0 ? true : false;
		}
		// addition
		public MyNumber add(MyNumber o) {
			int t = type >= o.type ? type : o.type;
			// INT or long
			if (t < FLT) {
				if (type == LONG || o.type == LONG) {
					long ll = numObj.longValue();
					long rl = o.numObj.longValue();
					return new MyNumber(LONG, ll + rl);
				}
				// continue with INT otherwise
				int li = numObj.intValue();
				int ri = o.numObj.intValue();
				return new MyNumber(INT, li + ri);			
			} else if (t < DBL) { // Could INT, long or float
				// if long raise to double
				if (type == LONG || o.type == LONG) {
					double ld = numObj.doubleValue();
					double rd = o.numObj.doubleValue();
					return new MyNumber(DBL, ld + rd);
				}
				// ...either float
				float lf = numObj.floatValue();
				float rf = o.numObj.floatValue();
				return new MyNumber(FLT, lf + rf);
			} else { // raise all to double
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld + rd);
			}
		}

		// subtraction
		public MyNumber sub(MyNumber o) {
			int t = type >= o.type ? type : o.type;
			// INT or long
			if (t < FLT) {
				if (type == LONG || o.type == LONG) {
					long ll = numObj.longValue();
					long rl = o.numObj.longValue();
					return new MyNumber(LONG, ll - rl);
				}
				// continue with INT otherwise
				int li = numObj.intValue();
				int ri = o.numObj.intValue();
				return new MyNumber(INT, li - ri);			
			} else if (t < DBL) { // Could INT, long or float
				// if long raise to double
				if (type == LONG || o.type == LONG) {
					double ld = numObj.doubleValue();
					double rd = o.numObj.doubleValue();
					return new MyNumber(DBL, ld - rd);
				}
				// ...either float
				float lf = numObj.floatValue();
				float rf = o.numObj.floatValue();
				return new MyNumber(FLT, lf - rf);
			} else { // raise all to double
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld - rd);
			}
		}

		// multiplication
		public MyNumber mul(MyNumber o) {
			int t = type >= o.type ? type : o.type;
			// INT or long
			if (t < FLT) {
				if (type == LONG || o.type == LONG) {
					long ll = numObj.longValue();
					long rl = o.numObj.longValue();
					return new MyNumber(LONG, ll * rl);
				}
				// continue with INT otherwise
				int li = numObj.intValue();
				int ri = o.numObj.intValue();
				return new MyNumber(INT, li * ri);			
			} else if (t < DBL) { // Could INT, long or float
				// if long raise to double
				if (type == LONG || o.type == LONG) {
					double ld = numObj.doubleValue();
					double rd = o.numObj.doubleValue();
					return new MyNumber(DBL, ld * rd);
				}
				// ...either float
				float lf = numObj.floatValue();
				float rf = o.numObj.floatValue();
				return new MyNumber(FLT, lf * rf);
			} else { // raise all to double
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld * rd);
			}
		}

		// divide
		public MyNumber div(MyNumber o) {
			int t = type >= o.type ? type : o.type;
			// INT or long
			if (t < FLT) {
				if (type == LONG || o.type == LONG) {
					long ll = numObj.longValue();
					long rl = o.numObj.longValue();
					return new MyNumber(LONG, ll / rl);
				}
				// continue with INT otherwise
				int li = numObj.intValue();
				int ri = o.numObj.intValue();
				return new MyNumber(INT, li / ri);			
			} else if (t < DBL) { // Could INT, long or float
				// if long raise to double
				if (type == LONG || o.type == LONG) {
					double ld = numObj.doubleValue();
					double rd = o.numObj.doubleValue();
					return new MyNumber(DBL, ld / rd);
				}
				// ...either float
				float lf = numObj.floatValue();
				float rf = o.numObj.floatValue();
				return new MyNumber(FLT, lf / rf);
			} else { // raise all to double
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld / rd);
			}
		}

		// remainder
		public MyNumber remainder(MyNumber o) {
			int t = type >= o.type ? type : o.type;
			// INT or long
			if (t < FLT) {
				if (type == LONG || o.type == LONG) {
					long ll = numObj.longValue();
					long rl = o.numObj.longValue();
					return new MyNumber(LONG, ll % rl);
				}
				// continue with INT otherwise
				int li = numObj.intValue();
				int ri = o.numObj.intValue();
				return new MyNumber(INT, li % ri);			
			} else if (t < DBL) { // Could INT, long or float
				// if long raise to double
				if (type == LONG || o.type == LONG) {
					double ld = numObj.doubleValue();
					double rd = o.numObj.doubleValue();
					return new MyNumber(DBL, ld % rd);
				}
				// ...either float
				float lf = numObj.floatValue();
				float rf = o.numObj.floatValue();
				return new MyNumber(FLT, lf % rf);
			} else { // raise all to double
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld % rd);
			}
		}

		@Override
		public int compareTo(MyNumber o) {
			// TODO Auto-generated method stub
			int t = type >= o.type ? type : o.type;
			// INT and long
			if (t < FLT) {
				// if one of the type is LONG
				if (type == LONG || o.type == LONG) {
					long ll = numObj.longValue();
					long rl = o.numObj.longValue();
					return (ll < rl) ? -1 : ((ll == rl) ? 0 : 1);
				}
				//...or INT if not
				int li = numObj.intValue();
				int ri = o.numObj.intValue();
				return (li < ri) ? -1 : ((li == ri) ? 0 : 1);
			}
			// FLoat
			else if (t < DBL) {
				// if long raise to double
				if (type == LONG || o.type == LONG) {
					double ld = numObj.doubleValue();
					double rd = o.numObj.doubleValue();
					return (ld < rd) ? -1 : ((ld == rd) ? 0 : 1);
				}
				// ...either float
				float lf = numObj.floatValue();
				float rf = o.numObj.floatValue();
				return (lf < rf) ? -1 : ((lf == rf) ? 0 : 1);
			}
			// double
			else {
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return (ld < rd) ? -1 : ((ld == rd) ? 0 : 1);
			}
		}
	}
	
	private static void tRex(String msg) { throw new RuntimeException(msg); }
	private static void tRexOprnd(int curPos) {
		throw new RuntimeException("Expecting number/identifier/string"
				+ "/'null'/'true'/'false'/'?' @" + curPos);
	}
	private static void tRexOp(int curPos) {
		throw new RuntimeException(
				"Expecting operators (e.g. >, <, >=, <= ...etc) @" + curPos);
	}	
}
