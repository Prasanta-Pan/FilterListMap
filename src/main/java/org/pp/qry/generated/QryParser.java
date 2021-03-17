// Generated from Qry.g4 by ANTLR 4.6
 package org.pp.qry.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, Number=4, MUL=5, DIV=6, MOD=7, ADD=8, SUB=9, CON_GT=10, 
		CON_LT=11, CON_GTE=12, CON_LTE=13, CON_EQ=14, CON_NEQ=15, REL_AND=16, 
		REL_OR=17, PLH=18, NOT=19, NULL=20, TRUE=21, FALSE=22, IN=23, BET=24, 
		NIN=25, NBET=26, PRN=27, String=28, Identifier=29, WS=30;
	public static final int
		RULE_expr = 0, RULE_operand = 1, RULE_operator = 2;
	public static final String[] ruleNames = {
		"expr", "operand", "operator"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "')'", "'and'", "','", null, "'*'", "'/'", "'%'", "'+'", "'-'", 
		"'>'", "'<'", "'>='", "'<='", "'='", "'!='", "'&&'", "'||'", "'?'", "'not'", 
		"'null'", "'true'", "'false'", "'in'", "'between'", "'nin'", "'nbet'", 
		"'('"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "Number", "MUL", "DIV", "MOD", "ADD", "SUB", "CON_GT", 
		"CON_LT", "CON_GTE", "CON_LTE", "CON_EQ", "CON_NEQ", "REL_AND", "REL_OR", 
		"PLH", "NOT", "NULL", "TRUE", "FALSE", "IN", "BET", "NIN", "NBET", "PRN", 
		"String", "Identifier", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Qry.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParanContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public ParanContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterParan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitParan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitParan(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OprndContext extends ExprContext {
		public OperandContext operand() {
			return getRuleContext(OperandContext.class,0);
		}
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public OprndContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterOprnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitOprnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitOprnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expr);
		int _la;
		try {
			setState(16);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Number:
			case PLH:
			case NULL:
			case TRUE:
			case FALSE:
			case String:
			case Identifier:
				_localctx = new OprndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(6);
				operand();
				setState(8);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << ADD) | (1L << SUB) | (1L << CON_GT) | (1L << CON_LT) | (1L << CON_GTE) | (1L << CON_LTE) | (1L << CON_EQ) | (1L << CON_NEQ) | (1L << REL_AND) | (1L << REL_OR) | (1L << NOT) | (1L << IN) | (1L << BET))) != 0)) {
					{
					setState(7);
					operator();
					}
				}

				}
				break;
			case PRN:
				_localctx = new ParanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(10);
				match(PRN);
				setState(11);
				expr();
				setState(12);
				match(T__0);
				setState(14);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << ADD) | (1L << SUB) | (1L << CON_GT) | (1L << CON_LT) | (1L << CON_GTE) | (1L << CON_LTE) | (1L << CON_EQ) | (1L << CON_NEQ) | (1L << REL_AND) | (1L << REL_OR) | (1L << NOT) | (1L << IN) | (1L << BET))) != 0)) {
					{
					setState(13);
					operator();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperandContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(QryParser.Identifier, 0); }
		public TerminalNode String() { return getToken(QryParser.String, 0); }
		public TerminalNode Number() { return getToken(QryParser.Number, 0); }
		public TerminalNode TRUE() { return getToken(QryParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(QryParser.FALSE, 0); }
		public TerminalNode NULL() { return getToken(QryParser.NULL, 0); }
		public TerminalNode PLH() { return getToken(QryParser.PLH, 0); }
		public OperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterOperand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitOperand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitOperand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperandContext operand() throws RecognitionException {
		OperandContext _localctx = new OperandContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_operand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Number) | (1L << PLH) | (1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << String) | (1L << Identifier))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorContext extends ParserRuleContext {
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
	 
		public OperatorContext() { }
		public void copyFrom(OperatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArithLowContext extends OperatorContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ArithLowContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterArithLow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitArithLow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitArithLow(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BetContext extends OperatorContext {
		public Token op;
		public TerminalNode BET() { return getToken(QryParser.BET, 0); }
		public List<OperandContext> operand() {
			return getRuleContexts(OperandContext.class);
		}
		public OperandContext operand(int i) {
			return getRuleContext(OperandContext.class,i);
		}
		public TerminalNode NOT() { return getToken(QryParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BetContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterBet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitBet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitBet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReloprContext extends OperatorContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReloprContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterRelopr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitRelopr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitRelopr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InContext extends OperatorContext {
		public Token op;
		public TerminalNode IN() { return getToken(QryParser.IN, 0); }
		public List<OperandContext> operand() {
			return getRuleContexts(OperandContext.class);
		}
		public OperandContext operand(int i) {
			return getRuleContext(OperandContext.class,i);
		}
		public TerminalNode NOT() { return getToken(QryParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public InContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterIn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitIn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitIn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CondContext extends OperatorContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CondContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterCond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitCond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitCond(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArithHighContext extends OperatorContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ArithHighContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).enterArithHigh(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QryListener ) ((QryListener)listener).exitArithHigh(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QryVisitor ) return ((QryVisitor<? extends T>)visitor).visitArithHigh(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_operator);
		int _la;
		try {
			setState(57);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new ReloprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(20);
				((ReloprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==REL_AND || _la==REL_OR) ) {
					((ReloprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(21);
				expr();
				}
				break;
			case 2:
				_localctx = new ArithLowContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(22);
				((ArithLowContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
					((ArithLowContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(23);
				expr();
				}
				break;
			case 3:
				_localctx = new ArithHighContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(24);
				((ArithHighContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
					((ArithHighContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(25);
				expr();
				}
				break;
			case 4:
				_localctx = new CondContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(26);
				((CondContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CON_GT) | (1L << CON_LT) | (1L << CON_GTE) | (1L << CON_LTE) | (1L << CON_EQ) | (1L << CON_NEQ))) != 0)) ) {
					((CondContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(27);
				expr();
				}
				break;
			case 5:
				_localctx = new BetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(28);
					match(NOT);
					}
				}

				setState(31);
				match(BET);
				setState(32);
				operand();
				setState(33);
				match(T__1);
				setState(34);
				operand();
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==REL_AND || _la==REL_OR) {
					{
					setState(35);
					((BetContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==REL_AND || _la==REL_OR) ) {
						((BetContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(36);
					expr();
					}
				}

				}
				break;
			case 6:
				_localctx = new InContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(39);
					match(NOT);
					}
				}

				setState(42);
				match(IN);
				setState(43);
				match(PRN);
				setState(44);
				operand();
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(45);
					match(T__2);
					setState(46);
					operand();
					}
					}
					setState(51);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(52);
				match(T__0);
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==REL_AND || _la==REL_OR) {
					{
					setState(53);
					((InContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==REL_AND || _la==REL_OR) ) {
						((InContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(54);
					expr();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3 >\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\3\2\3\2\5\2\13\n\2\3\2\3\2\3\2\3\2\5\2\21\n\2\5\2\23\n\2\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4 \n\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\5\4(\n\4\3\4\5\4+\n\4\3\4\3\4\3\4\3\4\3\4\7\4\62\n\4\f\4\16\4\65"+
		"\13\4\3\4\3\4\3\4\5\4:\n\4\5\4<\n\4\3\4\2\2\5\2\4\6\2\7\6\2\6\6\24\24"+
		"\26\30\36\37\3\2\22\23\3\2\n\13\3\2\7\t\3\2\f\21G\2\22\3\2\2\2\4\24\3"+
		"\2\2\2\6;\3\2\2\2\b\n\5\4\3\2\t\13\5\6\4\2\n\t\3\2\2\2\n\13\3\2\2\2\13"+
		"\23\3\2\2\2\f\r\7\35\2\2\r\16\5\2\2\2\16\20\7\3\2\2\17\21\5\6\4\2\20\17"+
		"\3\2\2\2\20\21\3\2\2\2\21\23\3\2\2\2\22\b\3\2\2\2\22\f\3\2\2\2\23\3\3"+
		"\2\2\2\24\25\t\2\2\2\25\5\3\2\2\2\26\27\t\3\2\2\27<\5\2\2\2\30\31\t\4"+
		"\2\2\31<\5\2\2\2\32\33\t\5\2\2\33<\5\2\2\2\34\35\t\6\2\2\35<\5\2\2\2\36"+
		" \7\25\2\2\37\36\3\2\2\2\37 \3\2\2\2 !\3\2\2\2!\"\7\32\2\2\"#\5\4\3\2"+
		"#$\7\4\2\2$\'\5\4\3\2%&\t\3\2\2&(\5\2\2\2\'%\3\2\2\2\'(\3\2\2\2(<\3\2"+
		"\2\2)+\7\25\2\2*)\3\2\2\2*+\3\2\2\2+,\3\2\2\2,-\7\31\2\2-.\7\35\2\2.\63"+
		"\5\4\3\2/\60\7\5\2\2\60\62\5\4\3\2\61/\3\2\2\2\62\65\3\2\2\2\63\61\3\2"+
		"\2\2\63\64\3\2\2\2\64\66\3\2\2\2\65\63\3\2\2\2\669\7\3\2\2\678\t\3\2\2"+
		"8:\5\2\2\29\67\3\2\2\29:\3\2\2\2:<\3\2\2\2;\26\3\2\2\2;\30\3\2\2\2;\32"+
		"\3\2\2\2;\34\3\2\2\2;\37\3\2\2\2;*\3\2\2\2<\7\3\2\2\2\13\n\20\22\37\'"+
		"*\639;";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}