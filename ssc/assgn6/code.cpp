// decide the set grammar
// identify set of terminals and non terminals
// write procedure for each grammar
//     for each grammar body
//         call symbol procedure respectively for non terminals
//         for nonterminals

/*

Grammar:
E  ->  TE'
E' -> +TE' | e
T  ->  FT'
T' -> *FT' | e
F  ->  (E) | id

non terminals = {E, E', T, T', F}
terminals = {id, e, +, * , (, )}



*/

#include <bits/stdc++.h>
#include <vector>

using namespace std;

string inStr;
int strP = 0;
char curr;
vector<char> identifiers({'a', 'i', 's'}), skippers({' ', '='});

void advance();
bool E();
bool Eprime();
bool T();
bool Tprime();
bool F();

bool vectorFind(vector<char> v, char k)
{

    for (int i = 0; i < v.size(); i++)
    {
        if (v[i] == k)
        {
            return true;
        }
    }

    return false;
}

void advance()
{
    strP++;
    curr = inStr[strP];
    // if (vectorFind(skippers, curr))
    //     advance();
}

// E  ->  TE'
bool E()
{
    if (!T())
        return false;
    return Eprime();
}

// E' -> +TE' | e
bool Eprime()
{
    if (curr == '+')
    {
        advance();
        if (!T())
            return false;
        return Eprime();
    }
    return true;
}

// T  ->  FT'
bool T()
{
    if (!F())
        return false;
    return Tprime();
}

// T' -> *FT' | e
bool Tprime()
{
    if (curr == '*')
    {
        advance();
        if (!F())
            return false;
        return Tprime();
    }
    return true;
}

// F  ->  (E) | id
bool F()
{
    if (vectorFind(identifiers, curr))
    {
        advance();
    }
    else
    {
        if (curr == '(')
        {
            advance();
            if (!E())
                return false;
            ;
            if (curr == ')')
                advance();
        }
        else
        {
            return false;
        }
    }
    return true;
}

int main()
{
    inStr = "s = a + (i + a)";
    curr = inStr[strP];

    if (E())
    {
        cout << endl
             << "String accepted";
    }
    else
    {
        cout << endl
             << "String not accepted";
    }
    cout << endl;
    return 0;
}
// s = a + i

/*

  #include<stdio.h>
  #include<conio.h>
  #include<stdlib.h>

  void E();
  int i=0;
  char str[10], tp;

  void advance() {
      i++;
      tp=str[i];
  }

  void F() {
      if(tp=='i') {
          advance();
      }
      else {
          if(tp=='(') {
              advance();
              E();
              if(tp==')') {
                  advance();
              }
          }
          else {
              printf("\nString not Accepted");
              exit(1);
          }
      }
  }

  void TP() {
      if(tp=='*') {
          advance();
          F();
          TP();
      }
  }

  void T() {
      F();
      TP();
  }

  void EP() {
      if(tp=='+') {
          advance();
          T();
          EP();
      }
  }

  void E() {
      T();
      EP();
  }

  int main() {
      int op;
      while(1) {
          printf("\nEnter the string: ");
          scanf("%s", &str);
          tp=str[i];
          E();
          if(tp=='\0')
              printf("\nString Accepted");
          else
              printf("\nString not Accepted");

          printf("\nEnter 1 for exit");
          scanf("%d", &op);
          if(op==1)
              exit(0);
      }
  }


*/