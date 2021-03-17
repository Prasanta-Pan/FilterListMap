package org.pp.qry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.pp.qry.generated.QryBaseVisitor;
import org.pp.qry.generated.QryLexer;
import org.pp.qry.generated.QryParser;
import org.pp.qry.generated.QryParser.OperandContext;

public class QryProcessor extends QryBaseVisitor<Object> {
	/** Root Node of expression tree */
	private Node root = null;
	/** User parameters */
	private Object[] params = null;
	/** Parameter Index */
	private int prmIndx = 0;
	/** current selected map */
	private Map<String,Object> m = null;
	/** To help build tree (expression) */
	private Deque<Node> stk = new LinkedList<>();
	private static Map<Integer, Action> actionMap = null;
	
	static {
		// Initialise operator map
		actionMap = new HashMap<>();
		// Initialise action map first
		actionMap.put(QryParser.ADD, new AddAction());
		actionMap.put(QryParser.SUB, new SubAction());
		actionMap.put(QryParser.MUL, new MulAction());
		actionMap.put(QryParser.DIV, new DivAction());
		actionMap.put(QryParser.MOD, new ModAction());
		// For equal and not equal action
		actionMap.put(QryParser.CON_EQ, new EqAction());
		actionMap.put(QryParser.CON_NEQ, new NqAction());
		// Conditional operators
		actionMap.put(QryParser.CON_GT, new GtAction());
		actionMap.put(QryParser.CON_GTE, new GeAction());
		actionMap.put(QryParser.CON_LT, new LtAction());
		actionMap.put(QryParser.CON_LTE, new LeAction());
		// In/between operators
		actionMap.put(QryParser.IN, new InAction());
		actionMap.put(QryParser.NIN, new NinAction());
		actionMap.put(QryParser.BET, new BetAction());
		actionMap.put(QryParser.NBET, new NbetAction());
		// relational operators
		actionMap.put(QryParser.REL_AND, new AndAction());
		actionMap.put(QryParser.REL_OR, new OrAction());
	}
	
	private QryProcessor() { }
	private QryProcessor(Object[] params) { this.params = params; }
	// Start the ball rolling
	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
		
		String qry = "email = 'pan.prasanta@gmail.com' && units > ?";
		print(getFilteredList(list,qry, 10));
		
		qry = "email != 'pan.prasanta@gmail.com' && price > ?";
		print(getFilteredList(list,qry, 10));
		// 
		qry = "email = 'pan.prasanta@gmail.com' && ( price > ? && units < 20 )";
		print(getFilteredList(list,qry, 10));
		
		qry = "email = 'pan.prasanta@gmail.com' && units not between 12 and 15";
		print(getFilteredList(list,qry));
		
		qry = "email = 'pan.prasanta@gmail.com' && units * 5 <= ?";
		print(getFilteredList(list,qry, 20));
		
		//qry = "";
		//print(getFilteredList(list,qry));
				
	}
	
	private static Map<String,Object> getMap(Object...vals) {
		Map<String,Object> m = new HashMap<>();
		m.put("name", vals[0]);
		m.put("email", vals[1]);
		m.put("mobile", vals[2]);
		m.put("price", vals[3]);
		m.put("units", vals[4]);
		m.put("address", vals[5]);		
		return m;
	}
	
	private static void print(List<Map<String, Object>> fList) {
		StringBuilder sbldr = new StringBuilder();
		for (Map<String, Object> m: fList) {
			for (Object o : m.values())
				sbldr.append(o.toString() + ",");
			
			sbldr.append("\n");
		}
		System.out.println(sbldr);
	}
	
	static QryProcessor compile(String qryStr, Object[] params) {
		long time = System.currentTimeMillis();
		/** Start Parsing */
		ANTLRInputStream input = new ANTLRInputStream(qryStr);
		QryLexer lexer = new QryLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		QryParser qryParser = new QryParser(tokens);
		ParseTree pt = qryParser.expr();
		/** Start parse tree walking */
		QryProcessor qry = new QryProcessor(params);
		qry.visit(pt);
		System.out.println("Time taken..." + (System.currentTimeMillis() - time));
		return qry;
	}
	
	public static List<Map<String,Object>> getFilteredList(
		List<Map<String, Object>> list, String qryStr, Object... params) {
		// Compile
		QryProcessor qry = compile(qryStr, params);
		if (qry.stk.size() != 1)
			throw new RuntimeException("Something seriously went wrong");
		// assign it too root node
		qry.root = qry.stk.pop();
		// Execute
		return qry.getFilteredList(list);
	}

	public List<Map<String,Object>> getFilteredList(List<Map<String,Object>> list) {
		Node tmp = null;	
		List<Map<String, Object>> retList = new ArrayList<>();
        for (Map<String, Object> m : list) {
        	this.m = m;
        	tmp = evaluate(root);
        	if (tmp.type != Node.TYPE_BOOL)
        	  throw new RuntimeException("Something seriously went wrong");
        	if ((boolean)tmp.val)
        		retList.add(m);
        }
		return retList;
	}
	// Evaluate tree recursively
	private Node evaluate(Node node) {
		if (node.type != Node.TYPE_OP)
			return node;
		// visit left subtree first
		Node l = evaluate(node.left);
		// visit right subtree
		Node r = evaluate(node.right);
		// perform action now
		if (l.type == Node.TYPE_ID)
			l = idToNode(l);
		if (r.type == Node.TYPE_ID)	
		    r = idToNode(r);
		return actionMap.get(node.val).execute(l, r, this.m);		
	}
	// convert id to value node
	private Node idToNode(Node n) {
		if (!m.containsKey(n.val))
		   throw new RuntimeException("ID '" + n.val + "' not found in the map");
		Object val = m.get(n.val);
		return Node.objToNode(val);			
	}
    //
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
	// process operand
	@Override
	public Object visitOprnd(QryParser.OprndContext ctx) {
		// visit operand first
		visit(ctx.operand());
		// visit operator		
		if (ctx.operator() != null)
			visit(ctx.operator());
		return null;
	}
    
	@Override public Object visitOperand(QryParser.OperandContext ctx) { 
		Node vNode = null;
		// if identifier value
		if (ctx.Identifier() != null)
			vNode = new Node(ctx.getText(), Node.TYPE_ID);
		// if null value
		else if (ctx.NULL() != null)
			vNode = new Node(null, Node.TYPE_NULL);
		// If number value
		else if (ctx.Number() != null) {
			// Convert text to BigDecimal
			BigDecimal bdVal = new BigDecimal(ctx.getText());
			vNode = new Node(bdVal, Node.TYPE_NUM);
		}
		// for String value
		else if (ctx.String() != null) {
			// Extract field omitting single quote
			String val = ctx.getText().substring(1, ctx.getText().length() - 1);
			// create string node
			vNode = new Node(val, Node.TYPE_STR);
		}
		// if boolean literal
		else if (ctx.TRUE() != null || ctx.FALSE() != null) {
			// Convert text to boolean
			Boolean val = new Boolean(ctx.getText());
			vNode = new Node(val, Node.TYPE_STR);
		}
		// Must be placeholder
		else {
			// Get user parameter
			Object obj = params[prmIndx++];
			vNode = Node.objToNode(obj);
		}
		// if In/bet operator no need to build tree
		if (stk.isEmpty() || ((int) stk.peek().val) >= QryParser.IN)
			stk.push(vNode);
		else
			buildTree(vNode);

		return null;
	}
	
	// Process arithmetic expression (Low priority)
	@Override
	public Object visitArithLow(QryParser.ArithLowContext ctx) {
		return commonExp(ctx.expr(),Node.PRI_MATH_LOW, ctx.op.getType());
	}
	// Process arithmetic expression (High priority)
	@Override
	public Object visitArithHigh(QryParser.ArithHighContext ctx) {
		return commonExp(ctx.expr(), Node.PRI_MATH_HIGH, ctx.op.getType());
	}	
	// common method to process relational operator
	private Object commonRelOpr(int op,QryParser.ExprContext expr) {
		int pr = op == QryParser.REL_AND ? Node.PRI_AND : Node.PRI_OR;
		return commonExp(expr, pr, op);
	}
	// Process relational operator
	@Override
	public Object visitRelopr(QryParser.ReloprContext ctx) {
		return commonRelOpr(ctx.op.getType(), ctx.expr());
	}
	
	// Process conditional operator
	@Override
	public Object visitCond(QryParser.CondContext ctx) {
		return commonExp(ctx.expr(), Node.PRI_COND_OP, ctx.op.getType());
	}
	// Common method to process all expression
	private Object commonExp(QryParser.ExprContext expr,int priority, int op) {
		// Push Operator now
		stk.push(new Node(op, Node.TYPE_OP, priority));
		// Visit right expression
		visit(expr);
		return null;
	}

	// Process between operator
	@Override
	public Object visitBet(QryParser.BetContext ctx) {
		// get the operator code
		int op = QryParser.BET;
		if (ctx.NOT() != null)
		   op = QryParser.NBET;
		// Now push the operator to the stack
		stk.push(new Node(op, Node.TYPE_OP, Node.PRI_COND_OP));
		// get consolidated right operand
	    Node node = oprndCheck(ctx.operand(), false);
		// Build tree now
		buildTree(node);
		// Check if expression is present
		if (ctx.expr() != null) 
		   commonRelOpr(ctx.op.getType(), ctx.expr());
		return null;
	}

	// process in operator
	@Override
	public Object visitIn(QryParser.InContext ctx) {
		// get the operator
		int op = QryParser.IN;
		if (ctx.NOT() != null)
		    op = QryParser.NIN;
		// Now push the operator to the stack
		stk.push(new Node(op, Node.TYPE_OP, Node.PRI_COND_OP));
		// get list of right operands
		Node node = oprndCheck(ctx.operand(), true);
		// Build tree now
		buildTree(node);		
		// Check if expression is present
		if (ctx.expr() != null) 
		   commonRelOpr(ctx.op.getType(), ctx.expr());
		return null;
	} 
	// common method for IN/between
	private Node oprndCheck(List<OperandContext> list, boolean in) {
		Node node = null; int type = -1;
		Collection<Node> rList = in ? new HashSet<>() : new LinkedList<>();
		for (OperandContext oprnd: list) {
			// Visit operand
			visit(oprnd); 
			// get operand node
			node = stk.pop();
			if (node.type == Node.TYPE_ID)
				throw new RuntimeException("Identifier not allowed in IN/BET");
			else if (node.type == Node.TYPE_NULL)
				throw new RuntimeException("null not allowed in IN/BET");
			else if (type < 0)	
				type = node.type;
			else if (type != node.type)
				throw new RuntimeException("Non uniform data type for IN/BET");
			rList.add(node);
		}
		// validate between operator operands
		if (!in) { 
			List<Node> l = (List<Node>) rList;
			if (l.get(0).compareTo(l.get(1)) >= 0)
				throw new RuntimeException("Invalid between operands");
		}
		return new Node(rList, type);
	}
	// Process parentheses
	@Override
	public Object visitParan(QryParser.ParanContext ctx) {
		// Push parentheses to stack 
        stk.push(new Node(QryParser.PRN, Node.TYPE_PRN, Node.PRI_PARAN));
        // Now Evaluate  expression
        visit(ctx.expr());
        // get top node
        Node top = stk.pop();
        // Remove parentheses from the stack
        stk.pop();
        // make it a top priority
        top.priority = Node.PRI_PARAN;
        // push again to stack
        if (!stk.isEmpty())
        	buildTree(top);
        else 
        	stk.push(top);
        // Check if expression to be continued
        if (ctx.operator() != null)
        	visit(ctx.operator());
		return null;
	}

	/**
	 * Expression tree node
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class Node {
		/** Declares operator's priority */
		private static final int PRI_PARAN = 99; // '(' expression ')'
		private static final int PRI_MATH_HIGH = 95; // '*', '/' , '%'
		private static final int PRI_MATH_LOW = 80; // '+' , '-'
		private static final int PRI_COND_OP = 75; // '>', '>=', '<', '<=', '=' , '!=', 'in', 'between'
		private static final int PRI_AND = 70; // '&&'
		private static final int PRI_OR = 65; // '||'
		/** Declare value type */
		private static final int TYPE_OP = 6;
		private static final int TYPE_ID = 5; // Value type is ID
		private static final int TYPE_STR = 4; // Value type is String value
		private static final int TYPE_NUM = 3; // Value type is number
		private static final int TYPE_BOOL = 2; // Value type is boolean
		private static final int TYPE_NULL = 1; // Value type is null
		private static final int TYPE_PRN = 0;

		private Object val;
		private int priority = -1, type;
		private Node left, right;

		private Node(Object val, int type) {
			this.val = val;
			this.type = type;
		}
        
		private Node(Object val, int type, int priority) {
			this(val, type);
			this.priority = priority;
		}
		 
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
			if (val == null)
				throw new NullPointerException("Null value can not be cmpared");
			Comparable<Object> l = (Comparable<Object>) val;
			Comparable<Object> r = (Comparable<Object>) othr.val;
			return l.compareTo(r);			
		}
		// Object to node mapping
		private static Node objToNode(Object obj) {
			Node vNode = null;
			if (obj == null)
				vNode = new Node(obj, Node.TYPE_NULL);
			else if (obj instanceof String)
				vNode = new Node(obj, Node.TYPE_STR);
			else if (obj instanceof Integer)
				vNode = new Node(new BigDecimal((int) obj), Node.TYPE_NUM);
			else if (obj instanceof Long)
				vNode = new Node(new BigDecimal((long) obj), Node.TYPE_NUM);
			else if (obj instanceof Float)
				vNode = new Node(new BigDecimal((float) obj), Node.TYPE_NUM);
			else if (obj instanceof Double)
				vNode = new Node(new BigDecimal((double) obj), Node.TYPE_NUM);
			else if (obj instanceof Boolean)
				vNode = new Node(obj, Node.TYPE_BOOL);
			else
				throw new RuntimeException("Unknown/Unsupported object type");
			return vNode;
		}
		
		@Override
		public String toString() {
			if (val == null)
				return "null";
			else if (type == TYPE_OP)
				return actionMap.get(val).opString();
			else 
				return val.toString() + ":" + -1;			
		}
		
		@Override
		public int hashCode() { return val.hashCode(); }
		
		@Override
		public boolean equals(Object othr) {
			Node n = (Node) othr;
			return val.equals(n.val);
		}
	}

	/**
	 * Operator action
	 * 
	 * @email pan.prasanta@gmail.com
	 */
	private static abstract class Action {
		// Do action
		public abstract Node execute(Node left, Node right, Map<String, Object> m);
		// return operator String value
		public abstract String opString();
		
		public void numberCheck(Node left, Node right, String op) {
			// Verify type mismatch
			if (left.type != right.type || left.type != Node.TYPE_NUM)
			  throw new RuntimeException("Either both operand type doesn't match or not number type, operator: " + op);
		}
		
		public void condTypeCheck(Node left, Node right, String op) {
			if (left.type != right.type)
				throw new RuntimeException("Both operand type doesn't match, operator: " + op);
		}
		
		public void booleanCheck(Node left, Node right, String op) {
			// Verify type mismatch
		    if (left.type != right.type || left.type != Node.TYPE_BOOL)
			  throw new RuntimeException("Either both operand type doesn't match or not boolean type, operator: " + op);
		}
	}

	/**
	 * Add action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class AddAction extends Action {
		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			numberCheck(left, right, "+");
			BigDecimal l = (BigDecimal) left.val;
			BigDecimal r = (BigDecimal) right.val;
			// create return value type
			return new Node(l.add(r), Node.TYPE_NUM);
		}

		@Override
		public String opString() { return "+ : " + Node.PRI_MATH_LOW; }

	}

	/**
	 * Subtract action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class SubAction extends Action {
		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			numberCheck(left, right, "-");
			BigDecimal l = (BigDecimal) left.val;
			BigDecimal r = (BigDecimal) right.val;
			// create return value type
			return new Node(l.subtract(r), Node.TYPE_NUM);
		}
		
		@Override
		public String opString() { return "- : " + Node.PRI_MATH_LOW; }

	}

	/**
	 * Multiplication action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class MulAction extends Action {
		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			numberCheck(left, right, "*");
			BigDecimal l = (BigDecimal) left.val;
			BigDecimal r = (BigDecimal) right.val;
			// create return value type
			return new Node(l.multiply(r), Node.TYPE_NUM);
		}
		
		@Override
		public String opString() { return "* : " + Node.PRI_MATH_HIGH;}

	}

	/**
	 * Divide action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class DivAction extends Action {
		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			// Verify type mismatch
			numberCheck(left, right, "/");
			BigDecimal l = (BigDecimal) left.val;
			BigDecimal r = (BigDecimal) right.val;
			// create return value type
			return new Node(l.divide(r), Node.TYPE_NUM);
		}
		
		@Override
		public String opString() { return "/ : " + Node.PRI_MATH_HIGH; }

	}

	/**
	 * Mod action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class ModAction extends Action {
		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			numberCheck(left, right, "%");
			BigDecimal l = (BigDecimal) left.val;
			BigDecimal r = (BigDecimal) right.val;
			// create return value type
			return new Node(l.remainder(r), Node.TYPE_NUM);
		}
		
		@Override
		public String opString() { return "% : " + Node.PRI_MATH_HIGH; }

	}

	/**
	 * Equal action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class EqAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			// create return value type
	        Node vNode = new Node(false, Node.TYPE_BOOL);
	        if (left.val != null && right.val != null && left.compareTo(right) == 0) 
	        	vNode.val = true;
	        else if (left.val == null && right.val == null)
	        	vNode.val = true;
	        
	        return vNode;
		}
		
		@Override
		public String opString() { return "= : " + Node.PRI_COND_OP; }

	}

	/**
	 * Not equal to action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class NqAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			// create return value type
	        Node vNode = new Node(false, Node.TYPE_BOOL);
	        if (left.val != null && right.val != null && left.compareTo(right) != 0) 
	        	vNode.val = true;
	        else if (left.val != null && right.val == null)
	        	vNode.val = true;
	        else if (left.val == null && right.val != null)
	        	vNode.val = true;	        
			return vNode;
		}
		
		@Override
		public String opString() { return "!= : " + Node.PRI_COND_OP; }

	}

	/**
	 * Greater than action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class GtAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, ">");
	        Node vNode = new Node(false, Node.TYPE_BOOL);
	        if (left.compareTo(right) > 0)
	        	vNode.val = true;
			
			return vNode;
		}
		
		@Override
		public String opString() { return "> : " + Node.PRI_COND_OP; }

	}

	/**
	 * Greater than and equal action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class GeAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, ">=");
			// create return value type
	        Node vNode = new Node(false, Node.TYPE_BOOL);
	        if (left.compareTo(right) >= 0)
	        	vNode.val = true;
			
			return vNode;
		}
		
		@Override
		public String opString() { return ">= : " + Node.PRI_COND_OP; }

	}

	/**
	 * Less than action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class LtAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, "<");
			// create return value type
	        Node vNode = new Node(false, Node.TYPE_BOOL);
	        if (left.compareTo(right) < 0)
	        	vNode.val = true;
			
			return vNode;
		}
		
		@Override
		public String opString() { return "< : " + Node.PRI_COND_OP; }

	}

	/**
	 * Less than equal to action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class LeAction extends Action {
		
		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, "<=");
			// create return value type
	        Node vNode = new Node(false, Node.TYPE_BOOL);
	        if (left.compareTo(right) <= 0)
	        	vNode.val = true;
			
			return vNode;
		}
		
		@Override
		public String opString() { return "<= : " + Node.PRI_COND_OP; }

	}

	/**
	 * In operator action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class InAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, "in");
			// create return value type
			Node vNode = new Node(true, Node.TYPE_BOOL);
			@SuppressWarnings("unchecked")
			Set<Node> set = (Set<Node>) right.val;
			if (!set.contains(left))
				vNode.val = false;
			return vNode;	
		}
		
		@Override
		public String opString() { return "in : " + Node.PRI_COND_OP; }

	}

	/**
	 * not in action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class NinAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, "'not in'");
			// create return value type
			Node vNode = new Node(false, Node.TYPE_BOOL);
			@SuppressWarnings("unchecked")
			Set<Node> set = (Set<Node>) right.val;
			if (!set.contains(left))
				vNode.val = true;
			return vNode;			
		}
		
		@Override
		public String opString() { return "not in : " + Node.PRI_COND_OP; }

	}

	/**
	 * Between action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class BetAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, "'between'");
			// create return value type
			Node vNode = new Node(false, Node.TYPE_BOOL);
			@SuppressWarnings("unchecked")
			List<Node> l = (List<Node>) right.val;
			if (left.compareTo(l.get(0)) >= 0 && left.compareTo(l.get(1)) <= 0)
				vNode.val = true;
			return vNode;
		}
		
		@Override
		public String opString() { return "between : " + Node.PRI_COND_OP; }

	}

	/**
	 * Not between action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class NbetAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			condTypeCheck(left, right, "'not between'");
			// create return value type
			Node vNode = new Node(true, Node.TYPE_BOOL);
			@SuppressWarnings("unchecked")
			List<Node> l = (List<Node>) right.val;
			if (left.compareTo(l.get(0)) >= 0 && left.compareTo(l.get(1)) <= 0)
				vNode.val = false;
			return vNode;
		}
		
		@Override
		public String opString() { return "not between : " + Node.PRI_COND_OP; }

	}

	/**
	 * And operator action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class AndAction extends Action {
		
		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			booleanCheck(left, right, "&&");
			// create return value type
			Node vNode = new Node(false, Node.TYPE_BOOL);
			if ((boolean) left.val && (boolean) right.val)
				vNode.val = true;
			return vNode;
		}
		
		@Override
		public String opString() { return "&& : " + Node.PRI_AND;  }

	}

	/**
	 * Or operator action
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class OrAction extends Action {

		@Override
		public Node execute(Node left, Node right, Map<String, Object> m) {
			booleanCheck(left, right, "||");
			// create return value type
			Node vNode = new Node(false, Node.TYPE_BOOL);
			if ((boolean) left.val || (boolean) right.val)
				vNode.val = true;
			return vNode;
		}
		
		@Override
		public String opString() { return "|| : " + Node.PRI_OR ;}

	}

}
