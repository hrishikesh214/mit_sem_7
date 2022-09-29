#include <iostream>
#include <chrono>
#include <map>
#include <vector>
using namespace std;

int main()
{
    map<int, vector<string>> testdata;
    map<int, bool> expected;

    // testdata 1
    testdata[1].push_back("h");
    testdata[1].push_back("v");
    expected[1] = true;
    // testdata 2
    testdata[1].push_back("hr");
    testdata[1].push_back("vaze");
    // testdata 3
    testdata[1].push_back("hrishi");
    testdata[1].push_back("v");
    // testdata 4
    testdata[1].push_back("h");
    testdata[1].push_back("vaze");
    // testdata 5
    testdata[1].push_back("hrishi");
    testdata[1].push_back("vaz");

    string fname, lname;
    bool isValidFname = true, isValidLname = true;
    cout << "Enter firstname and lastname: ";
    cin >> fname >> lname;

    chrono::steady_clock::time_point begin = chrono::steady_clock::now();

    if (fname.size() < 3)
        isValidFname = false;
    if (lname.size() < 3)
        isValidLname = false;

    chrono::steady_clock::time_point end = chrono::steady_clock::now();
    cout << endl
         << isValidFname << " " << isValidLname;
    cout << endl
         << "Took: " << chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() << " ns" << endl;

    return 0;
}
