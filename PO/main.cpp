#include <iostream>
#include "include/Reader.h"

using namespace std;

int main()
{
    Reader *test = new Reader();
    test->read();
    test->printContent();
    delete test;
    return 0;
}
