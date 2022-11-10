%{
    #include<stdlib.h>
    #include<stdio.h>
    #include<string.h>
    #include "y.tab.h"
    extern void yyerror(char *str);
    extern int yyparser();
%}

%%

[0-9]+ {yylval.intval=atoi(yytext);
    return NUMBER;}
"sin" {return SIN;}
"cos" {return COS;}
"tan" {return TAN;}
[a-z]+ {(yylval.fchar=*yytext);
    return NAME;}
 
[\t ];
\n return 0;
 
. {return yytext[0];}

%%

int yywrap()
{
 return 1;
}