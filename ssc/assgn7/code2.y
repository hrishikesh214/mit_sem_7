%{
    #include<stdio.h>
    extern int yylex();
    extern int yywrap();
    extern int yyparse();

    void yyerror(char *str);
%}

%token WH IF DO FOR OP CP OCB CCB CMP SC ASG ID NUM COMMA OPR

%%

start: swh | mwh | dowh | sif | mif | sfl | mfl;

swh: WH OP cmpn CP stmt {printf("VALID SINGLE STATEMENT WHILE LOOP\n");}

mwh: WH OP cmpn CP OCB mtst CCB {printf("VALID MULTI STATEMENT WHILE LOOP\n");}

dowh: DO OCB mtst CCB WH OP cmpn CP SC {printf("VALID DO-WHILE LOOP\n");}

sif: IF OP cmpn CP stmt {printf("VALID SINGLE STATEMENT IF\n");}

mif: IF OP cmpn CP OCB mtst CCB {printf("VALID MULTIPLE STATEMENT IF");}

cmpn: ID CMP ID | ID CMP NUM;

stmt: ID OPR | ID ASG ID OPR ID SC | ID ASG ID OPR NUM SC | ID ASG NUM OPR ID SC | ID ASG NUM OPR NUM SC | ID ASG ID SC | ID ASG NUM SC | start {printf("NESTED INSIDE A ");}

mtst: mtst stmt | stmt;

sfl: FOR OP ID ASG NUM SC cmpn SC stmt CP stmt {printf("VALID SINGLE STATEMENT FOR LOOP\n");}

mfl: FOR OP ID ASG NUM SC cmpn SC stmt CP OCB mtst CCB {printf("VALID MULTI STATEMENT FOR LOOP\n");}

%%

void yyerror(char *str) {
    printf("%s", str);
}

int main() {
    // yyin = fopen("sample.txt","r");
    yyparse();
    return 0;
}