/*

grammar:

S -> fn(P)
P -> id,P | id | e

S -> fn(P)
P -> id Q | e
Q -> ,P | e

*/

#include <bits/stdc++.h>
using namespace std;

string inStr;
int strp;
char curr;
vector<char> ids({'a', 'i', 's'});

void advance();
bool S();
bool P();
bool Q();

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
    strp++;
    curr = inStr[strp];
}

// S -> fn(P)
bool S()
{
    if (curr == 'f')
    {
        advance();
        if (curr == 'n')
        {
            advance();
            if (curr == '(')
            {
                advance();
                if (P())
                {
                    if (curr == ')')
                    {
                        advance();
                        return true;
                    }
                }
            }
        }
    }
    return false;
}

// P -> id Q | e
bool P()
{
    if (vectorFind(ids, curr))
    {
        advance();
        return Q();
    }
    return true;
}

// Q -> ,P | e
bool Q()
{
    if (curr == ',')
    {
        advance();
        return P();
    }
    return true;
}

int main()
{
    inStr = "fn(a,s)";
    strp = -1;
    advance();
    if (S())
    {
        cout << "Accepted" << endl;
    }
    else
    {
        cout << "Rejected" << endl;
    }
    cout << "strp: " << strp << " curr: " << curr << endl;
    return 0;
}