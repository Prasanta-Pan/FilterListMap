// Generated from Qry.g4 by ANTLR 4.6
 package org.pp.qry.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, Number=4, MUL=5, DIV=6, MOD=7, ADD=8, SUB=9, CON_GT=10, 
		CON_LT=11, CON_GTE=12, CON_LTE=13, CON_EQ=14, CON_NEQ=15, REL_AND=16, 
		REL_OR=17, PLH=18, NOT=19, NULL=20, TRUE=21, FALSE=22, IN=23, BET=24, 
		NIN=25, NBET=26, PRN=27, String=28, Identifier=29, WS=30;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "Number", "MUL", "DIV", "MOD", "ADD", "SUB", "CON_GT", 
		"CON_LT", "CON_GTE", "CON_LTE", "CON_EQ", "CON_NEQ", "REL_AND", "REL_OR", 
		"PLH", "NOT", "NULL", "TRUE", "FALSE", "IN", "BET", "NIN", "NBET", "PRN", 
		"Exponent", "String", "EscapeSequence", "OctalEscape", "UnicodeEscape", 
		"Identifier", "HexDigit", "Letter", "JavaIDDigit", "WS"
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


	public QryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Qry.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2 \u0108\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\5\5\5W\n\5\3\5\6\5Z\n\5\r\5\16\5[\3\5\5\5_\n\5\3\5\6\5b\n\5\r\5\16\5"+
		"c\3\5\3\5\7\5h\n\5\f\5\16\5k\13\5\3\5\5\5n\n\5\3\5\3\5\6\5r\n\5\r\5\16"+
		"\5s\3\5\6\5w\n\5\r\5\16\5x\3\5\5\5|\n\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32"+
		"\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35\5\35\u00cb"+
		"\n\35\3\35\6\35\u00ce\n\35\r\35\16\35\u00cf\3\36\3\36\3\36\7\36\u00d5"+
		"\n\36\f\36\16\36\u00d8\13\36\3\36\3\36\3\37\3\37\3\37\3\37\5\37\u00e0"+
		"\n\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \5 \u00eb\n \3!\3!\3!\3!\3!\3!\3!\3\""+
		"\3\"\3\"\7\"\u00f7\n\"\f\"\16\"\u00fa\13\"\3#\3#\3$\3$\3%\3%\3&\6&\u0103"+
		"\n&\r&\16&\u0104\3&\3&\2\2\'\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\2;\36=\2?\2A\2C\37E\2G\2I\2K \3\2\n\4\2--//\4\2"+
		"GGgg\4\2))^^\n\2$$))^^ddhhppttvv\5\2\62;CHch\16\2&&C\\aac|\u00c2\u00d8"+
		"\u00da\u00f8\u00fa\u2001\u3042\u3191\u3302\u3381\u3402\u3d2f\u4e02\ua001"+
		"\uf902\ufb01\21\2\62;\u0662\u066b\u06f2\u06fb\u0968\u0971\u09e8\u09f1"+
		"\u0a68\u0a71\u0ae8\u0af1\u0b68\u0b71\u0be9\u0bf1\u0c68\u0c71\u0ce8\u0cf1"+
		"\u0d68\u0d71\u0e52\u0e5b\u0ed2\u0edb\u1042\u104b\5\2\13\f\16\17\"\"\u0116"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\2;\3\2\2\2\2C\3"+
		"\2\2\2\2K\3\2\2\2\3M\3\2\2\2\5O\3\2\2\2\7S\3\2\2\2\t{\3\2\2\2\13}\3\2"+
		"\2\2\r\177\3\2\2\2\17\u0081\3\2\2\2\21\u0083\3\2\2\2\23\u0085\3\2\2\2"+
		"\25\u0087\3\2\2\2\27\u0089\3\2\2\2\31\u008b\3\2\2\2\33\u008e\3\2\2\2\35"+
		"\u0091\3\2\2\2\37\u0093\3\2\2\2!\u0096\3\2\2\2#\u0099\3\2\2\2%\u009c\3"+
		"\2\2\2\'\u009e\3\2\2\2)\u00a2\3\2\2\2+\u00a7\3\2\2\2-\u00ac\3\2\2\2/\u00b2"+
		"\3\2\2\2\61\u00b5\3\2\2\2\63\u00bd\3\2\2\2\65\u00c1\3\2\2\2\67\u00c6\3"+
		"\2\2\29\u00c8\3\2\2\2;\u00d1\3\2\2\2=\u00df\3\2\2\2?\u00ea\3\2\2\2A\u00ec"+
		"\3\2\2\2C\u00f3\3\2\2\2E\u00fb\3\2\2\2G\u00fd\3\2\2\2I\u00ff\3\2\2\2K"+
		"\u0102\3\2\2\2MN\7+\2\2N\4\3\2\2\2OP\7c\2\2PQ\7p\2\2QR\7f\2\2R\6\3\2\2"+
		"\2ST\7.\2\2T\b\3\2\2\2UW\t\2\2\2VU\3\2\2\2VW\3\2\2\2WY\3\2\2\2XZ\4\62"+
		";\2YX\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\|\3\2\2\2]_\t\2\2\2^]\3"+
		"\2\2\2^_\3\2\2\2_a\3\2\2\2`b\4\62;\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3"+
		"\2\2\2de\3\2\2\2ei\7\60\2\2fh\4\62;\2gf\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij"+
		"\3\2\2\2j|\3\2\2\2ki\3\2\2\2ln\t\2\2\2ml\3\2\2\2mn\3\2\2\2no\3\2\2\2o"+
		"q\7\60\2\2pr\4\62;\2qp\3\2\2\2rs\3\2\2\2sq\3\2\2\2st\3\2\2\2t|\3\2\2\2"+
		"uw\4\62;\2vu\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2yz\3\2\2\2z|\59\35\2"+
		"{V\3\2\2\2{^\3\2\2\2{m\3\2\2\2{v\3\2\2\2|\n\3\2\2\2}~\7,\2\2~\f\3\2\2"+
		"\2\177\u0080\7\61\2\2\u0080\16\3\2\2\2\u0081\u0082\7\'\2\2\u0082\20\3"+
		"\2\2\2\u0083\u0084\7-\2\2\u0084\22\3\2\2\2\u0085\u0086\7/\2\2\u0086\24"+
		"\3\2\2\2\u0087\u0088\7@\2\2\u0088\26\3\2\2\2\u0089\u008a\7>\2\2\u008a"+
		"\30\3\2\2\2\u008b\u008c\7@\2\2\u008c\u008d\7?\2\2\u008d\32\3\2\2\2\u008e"+
		"\u008f\7>\2\2\u008f\u0090\7?\2\2\u0090\34\3\2\2\2\u0091\u0092\7?\2\2\u0092"+
		"\36\3\2\2\2\u0093\u0094\7#\2\2\u0094\u0095\7?\2\2\u0095 \3\2\2\2\u0096"+
		"\u0097\7(\2\2\u0097\u0098\7(\2\2\u0098\"\3\2\2\2\u0099\u009a\7~\2\2\u009a"+
		"\u009b\7~\2\2\u009b$\3\2\2\2\u009c\u009d\7A\2\2\u009d&\3\2\2\2\u009e\u009f"+
		"\7p\2\2\u009f\u00a0\7q\2\2\u00a0\u00a1\7v\2\2\u00a1(\3\2\2\2\u00a2\u00a3"+
		"\7p\2\2\u00a3\u00a4\7w\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7n\2\2\u00a6"+
		"*\3\2\2\2\u00a7\u00a8\7v\2\2\u00a8\u00a9\7t\2\2\u00a9\u00aa\7w\2\2\u00aa"+
		"\u00ab\7g\2\2\u00ab,\3\2\2\2\u00ac\u00ad\7h\2\2\u00ad\u00ae\7c\2\2\u00ae"+
		"\u00af\7n\2\2\u00af\u00b0\7u\2\2\u00b0\u00b1\7g\2\2\u00b1.\3\2\2\2\u00b2"+
		"\u00b3\7k\2\2\u00b3\u00b4\7p\2\2\u00b4\60\3\2\2\2\u00b5\u00b6\7d\2\2\u00b6"+
		"\u00b7\7g\2\2\u00b7\u00b8\7v\2\2\u00b8\u00b9\7y\2\2\u00b9\u00ba\7g\2\2"+
		"\u00ba\u00bb\7g\2\2\u00bb\u00bc\7p\2\2\u00bc\62\3\2\2\2\u00bd\u00be\7"+
		"p\2\2\u00be\u00bf\7k\2\2\u00bf\u00c0\7p\2\2\u00c0\64\3\2\2\2\u00c1\u00c2"+
		"\7p\2\2\u00c2\u00c3\7d\2\2\u00c3\u00c4\7g\2\2\u00c4\u00c5\7v\2\2\u00c5"+
		"\66\3\2\2\2\u00c6\u00c7\7*\2\2\u00c78\3\2\2\2\u00c8\u00ca\t\3\2\2\u00c9"+
		"\u00cb\t\2\2\2\u00ca\u00c9\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cd\3\2"+
		"\2\2\u00cc\u00ce\4\62;\2\u00cd\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0:\3\2\2\2\u00d1\u00d6\7)\2\2\u00d2"+
		"\u00d5\5=\37\2\u00d3\u00d5\n\4\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d3\3\2"+
		"\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7"+
		"\u00d9\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9\u00da\7)\2\2\u00da<\3\2\2\2\u00db"+
		"\u00dc\7^\2\2\u00dc\u00e0\t\5\2\2\u00dd\u00e0\5A!\2\u00de\u00e0\5? \2"+
		"\u00df\u00db\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00de\3\2\2\2\u00e0>\3"+
		"\2\2\2\u00e1\u00e2\7^\2\2\u00e2\u00e3\4\62\65\2\u00e3\u00e4\4\629\2\u00e4"+
		"\u00eb\4\629\2\u00e5\u00e6\7^\2\2\u00e6\u00e7\4\629\2\u00e7\u00eb\4\62"+
		"9\2\u00e8\u00e9\7^\2\2\u00e9\u00eb\4\629\2\u00ea\u00e1\3\2\2\2\u00ea\u00e5"+
		"\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb@\3\2\2\2\u00ec\u00ed\7^\2\2\u00ed\u00ee"+
		"\7w\2\2\u00ee\u00ef\5E#\2\u00ef\u00f0\5E#\2\u00f0\u00f1\5E#\2\u00f1\u00f2"+
		"\5E#\2\u00f2B\3\2\2\2\u00f3\u00f8\5G$\2\u00f4\u00f7\5G$\2\u00f5\u00f7"+
		"\5I%\2\u00f6\u00f4\3\2\2\2\u00f6\u00f5\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8"+
		"\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9D\3\2\2\2\u00fa\u00f8\3\2\2\2"+
		"\u00fb\u00fc\t\6\2\2\u00fcF\3\2\2\2\u00fd\u00fe\t\7\2\2\u00feH\3\2\2\2"+
		"\u00ff\u0100\t\b\2\2\u0100J\3\2\2\2\u0101\u0103\t\t\2\2\u0102\u0101\3"+
		"\2\2\2\u0103\u0104\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105"+
		"\u0106\3\2\2\2\u0106\u0107\b&\2\2\u0107L\3\2\2\2\25\2V[^cimsx{\u00ca\u00cf"+
		"\u00d4\u00d6\u00df\u00ea\u00f6\u00f8\u0104\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}