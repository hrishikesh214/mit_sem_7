%{
    /*
    lab 5 - java lexical analysys
    */
%}

letter [a-zA-Z]
digit [0-9]
symbol (\,|\[|\])
statementEnd (;)
space [" "]

Integer ({digit}+)
Double (({digit}+\.{digit}*))
String (\"({letter}|{symbol})*\")

operator (\=|\+|\.)
identifier ({letter}({letter}|{digit})*)
accessSpecifier (public|private|protected)
datatype (void|int|char|float|String)
keyword (import|class|static)

packageName ({identifier}(\.({identifier}|\*))*)
packageImport (import{space}{packageName}{statementEnd})

variable ({identifier}(\.{identifier})*)
value ({String}|{Integer}|{Double}|{identifier})
arrayName ({value}\[\])
functionCall ({identifier}\((.*)\))

systemClass (System)
userDefinedClass (class{space}{identifier})


%%

[\t ]+ /* ignore */;

{packageImport} {printf("[%s] => ImportStatement\n", yytext);}

{Integer} {printf("[%s] => Integer\n", yytext);}
{Double} {printf("[%s] => Double\n", yytext);}
{String} {printf("[%s] => String\n", yytext);}
{operator} {printf("[%s] => Operator\n", yytext);}
{datatype} {printf("[%s] => Datatype\n", yytext);}
{systemClass} {printf("[%s] => systemClass\n", yytext);}
{userDefinedClass} {printf("[%s] => userDefinedClass\n", yytext);}
{arrayName} {printf("[%s] => arrayName\n", yytext);}
{functionCall} {printf("[%s] => Function\n", yytext);}

{accessSpecifier} {printf("[%s] => AccessSpecifier\n", yytext);}
{keyword} {printf("[%s] => Keyword\n", yytext);}
{userDefinedClass} {printf("[%s] => userDefinedClass\n", yytext);}
{identifier} {printf("[%s] => Identifier\n", yytext);}

{statementEnd} {printf("[%s] => StatementEnd\n", yytext);}

.|\n {ECHO; /*normal default*/}

%%

main(){
    yyin = fopen("java_code.txt","r");
    yylex();
    yywrap();
}

int yywrap(void){
    return 1;
}