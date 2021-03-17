// Derived from http://json.org

grammar SQL;

@header{ package org.mynosql.db.parser.sql.generated; }

expr : cond reloperator ?   	
     | '(' expr ')'    
     ;
    
reloperator : '||'  expr   #and
            | '&&'  expr   #or                   
            ;

cond : STRING '=' operand expr ? 						#equal
     | STRING '>' operand expr ? 						#gt
     | STRING '>=' operand expr ?						#gte
     | STRING '<' operand expr ?						#lt
     | STRING '<=' operand expr ? 						#lte
     | STRING '!=' operand expr ?   					#neq
     | STRING 'in' '?' expr ? 							#in
     | STRING 'not' 'in' '?' expr ?						#nin
     | STRING 'between' '(' NUMBER ',' NUMBER ')'		#bet
     | STRING 'between' '?' 							#betp
     | STRING 'not' 'between' '?'						#nbetp
     | STRING 'not' 'between' '(' NUMBER ',' NUMBER ')' #nbet     
     ;

operand : STRING
       | NUMBER
       | '?'
       ;

STRING :  (ESC | ~["\\])* ;

fragment ESC :   '\\' (["\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX ;
fragment HEX : [0-9a-fA-F] ;

NUMBER
    :   '-'? INT '.' [0-9]+ EXP? // 1.35, 1.35E-9, 0.3, -4.5
    |   '-'? INT EXP             // 1e10 -3e4
    |   '-'? INT                 // -3, 45
    ;
fragment INT :   '0' | [1-9] [0-9]* ; // no leading zeros
fragment EXP :   [Ee] [+\-]? INT ; // \- since - means "range" inside [...]

WS  :   [ \t\n\r]+ -> skip ;
