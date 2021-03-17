// Generated from Qry.g4 by ANTLR 4.6
 package org.pp.qry.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link QryParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface QryVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code oprnd}
	 * labeled alternative in {@link QryParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOprnd(QryParser.OprndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code paran}
	 * labeled alternative in {@link QryParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParan(QryParser.ParanContext ctx);
	/**
	 * Visit a parse tree produced by {@link QryParser#operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(QryParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relopr}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelopr(QryParser.ReloprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithLow}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithLow(QryParser.ArithLowContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithHigh}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithHigh(QryParser.ArithHighContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cond}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(QryParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bet}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBet(QryParser.BetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code in}
	 * labeled alternative in {@link QryParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIn(QryParser.InContext ctx);
}