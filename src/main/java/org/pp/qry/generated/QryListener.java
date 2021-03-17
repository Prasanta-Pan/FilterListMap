// Generated from Qry.g4 by ANTLR 4.6
 package org.pp.qry.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QryParser}.
 */
public interface QryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code oprnd}
	 * labeled alternative in {@link QryParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOprnd(QryParser.OprndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code oprnd}
	 * labeled alternative in {@link QryParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOprnd(QryParser.OprndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code paran}
	 * labeled alternative in {@link QryParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParan(QryParser.ParanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code paran}
	 * labeled alternative in {@link QryParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParan(QryParser.ParanContext ctx);
	/**
	 * Enter a parse tree produced by {@link QryParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(QryParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link QryParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(QryParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relopr}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterRelopr(QryParser.ReloprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relopr}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitRelopr(QryParser.ReloprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithLow}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterArithLow(QryParser.ArithLowContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithLow}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitArithLow(QryParser.ArithLowContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithHigh}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterArithHigh(QryParser.ArithHighContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithHigh}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitArithHigh(QryParser.ArithHighContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cond}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterCond(QryParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cond}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitCond(QryParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bet}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterBet(QryParser.BetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bet}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitBet(QryParser.BetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code in}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterIn(QryParser.InContext ctx);
	/**
	 * Exit a parse tree produced by the {@code in}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitIn(QryParser.InContext ctx);
}