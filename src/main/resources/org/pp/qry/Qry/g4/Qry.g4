
/** 
 * A sample sql like query processor
 */
grammar Qry;

@header { package org.pp.qry.generated;}

// starting point
expr 
    	:	operand operator ? 		# oprnd
    	|	'(' expr ')' operator ?	# paran																		
    	;

/**
 * Operand could either be literals or Identifier
 */
operand 
   		: Identifier 	
        | String		
        | Number		
        | TRUE			
	    | FALSE
		| NULL
		| PLH
        ;
        
operator 
		: op=( '&&' | '||' ) expr 													# relopr
		| op=( '+' | '-' ) expr														# arithLow
		| op=( '*' | '/' | '%' ) expr												# arithHigh
		| op=( '>' | '>='  | '<' | '<=' | '=' | '!=' ) expr 						# cond
		| NOT ? BET  operand 'and' operand  ( op=( '&&' | '||' ) expr )?			# bet
		| NOT ? IN '(' operand ( ',' operand )* ')' ( op=( '&&' | '||' ) expr )?	# in
		;

/** 
 *  Acceptable number format
 */   
Number
    :  ('+' | '-' ) ? ('0'..'9')+ 
    |  ('+' | '-' ) ? ('0'..'9')+ '.' ('0'..'9')*
    |  ('+' | '-' ) ? '.' ('0'..'9')+
    |  ('0'..'9')+ Exponent
    ;  
/**
 *  Declare constants for operators
 */
MUL 				:  	'*' ;
DIV	  				:	'/';		
MOD					:	'%';
ADD					:	'+';
SUB					:	'-';
CON_GT				:	'>';
CON_LT				:	'<';
CON_GTE				:	'>=';
CON_LTE				:	'<=';
CON_EQ				:	'=';
CON_NEQ				:	'!=';
REL_AND				:	'&&';
REL_OR				:	'||';
PLH					:	'?' ;
NOT					:	'not';
NULL				:	'null';
TRUE				:	'true';
FALSE				:	'false';
IN					:	'in';
BET					:	'between';
NIN					:	'nin';
NBET				:	'nbet';
PRN					:	'(';

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

/**
 * String Literal 
 */
String
    :   '\'' ( EscapeSequence | ~('\''|'\\') )* '\''
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;
    
Identifier
    :   Letter (Letter|JavaIDDigit)*
    ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

fragment
JavaIDDigit
    :  '\u0030'..'\u0039' |
       '\u0660'..'\u0669' |
       '\u06f0'..'\u06f9' |
       '\u0966'..'\u096f' |
       '\u09e6'..'\u09ef' |
       '\u0a66'..'\u0a6f' |
       '\u0ae6'..'\u0aef' |
       '\u0b66'..'\u0b6f' |
       '\u0be7'..'\u0bef' |
       '\u0c66'..'\u0c6f' |
       '\u0ce6'..'\u0cef' |
       '\u0d66'..'\u0d6f' |
       '\u0e50'..'\u0e59' |
       '\u0ed0'..'\u0ed9' |
       '\u1040'..'\u1049'
   ;
WS  :   [ \r\t\u000C\n]+ -> channel(HIDDEN)
    ;
