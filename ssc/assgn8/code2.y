%{
    #include<stdio.h>
    extern int yylex();
    extern int yywrap();
    extern int yyparse();

    void yyerror(char *str);
%}

%union{
    char* fchar;
    double fval;
    int intval;
};

%token SIN
%token COS
%token TAN
%token <fchar>NAME
%token <intval>NUMBER
%type <fval>exp
%left '+','-'
%left '*','/'
%left '^',' '

%%

stmt: NAME'='exp { printf("=%f\t\n", $3); }
    | exp {printf("=%f\n", $1);}
    ;
 
exp : exp'+'exp { $$ = $1 + $3;}
    | exp'-'exp { $$ = $1 - $3;}
    | exp'*'exp { $$ = $1 * $3;}
    | SIN' 'exp { $$ = sin($3*3.14/180); }
    | COS' 'exp { $$ = cos($3*3.14/180); }
    | TAN' 'exp { $$ = tan($3*(22/7)/180); }
    | exp'/'exp { 
                if ($3==0)
                {
                    printf("\nDivide by zero");
                    return;
                }
                else
                {
                    $$ = $1 / $3;
                }
    }
    | NUMBER {$$ = $1;}
    ;

%%

void yyerror(char *str) {
    printf("%s", str);
}

int main() {
    yyparse();
    return 0;
}