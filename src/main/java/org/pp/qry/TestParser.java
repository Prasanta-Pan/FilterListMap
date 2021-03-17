package org.pp.qry;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.pp.qry.generated.QryBaseVisitor;
import org.pp.qry.generated.QryLexer;
import org.pp.qry.generated.QryParser;

public class TestParser extends QryBaseVisitor<Object> {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String qry = "a + b * 5";
		parse(qry);
		
		qry = "a + b * 5 + c";
		parse(qry);
		
		qry = "a * b / 5 + c";
		parse(qry);
		
		qry = "a + b  > 5 + c";
		parse(qry);
		
		qry = "5 in";
		parse(qry);
	}
	
	public static void parse (String qry) {
		long time = System.currentTimeMillis();
		/** Start Parsing */
		ANTLRInputStream input = new ANTLRInputStream(qry);
		QryLexer lexer = new QryLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		QryParser qryParser = new QryParser(tokens);
		ParseTree pt = qryParser.expr();
		/** Start parse tree walking */
		TestParser prsr = new TestParser();
		prsr.visit(pt);
		System.out.println("Time taken..." + (System.currentTimeMillis() - time));
	}
	// process operand
	@Override public Object visitOprnd(QryParser.OprndContext ctx) { 
		visit(ctx.operand()); 
		if (ctx.operator() != null)
		  visit(ctx.operator()); 
		return null;
	}
	
	// Process arithmetic expression
	@Override public Object visitArithLow(QryParser.ArithLowContext ctx) { 
		//visit(ctx.operand());
		
		return null;
	}
	// Process arithmetic expression
	@Override public Object visitArithHigh(QryParser.ArithHighContext ctx) { 
			//visit(ctx.operand());
			
			return null;
	}
	// Process relational operator
	@Override public Object visitRelopr(QryParser.ReloprContext ctx) { 
		
		//return visitChildren(ctx); 
		return null;
	}
	// Process conditional operator
	@Override public Object visitCond(QryParser.CondContext ctx) { 
		
		//return visitChildren(ctx);
		return null;
	}
	// Process between operator
	@Override public Object visitBet(QryParser.BetContext ctx) { 
		
		return null; 
	}
	// process in operator
	@Override public Object visitIn(QryParser.InContext ctx) { 
		System.out.println("In operator");
		return visitChildren(ctx); 
	}
	// Process parentheses
	@Override public Object visitParan(QryParser.ParanContext ctx) { 
		
		return visitChildren(ctx); 
	}
	
}
