//Task 8

grammar T10_37_16221_Gharam_Zakaria;

start: operation EOF;

///////////////////
//Place your code here

operation
:	NUMBER '*' NUMBER | NUMBER '/' NUMBER | NUMBER '+' NUMBER

| NUMBER '-' NUMBER	;

///////////////////
NUMBER : ('0' .. '9') + ('.' ('0' .. '9') +)? ;
WS : [ \r\n\t] + -> skip;