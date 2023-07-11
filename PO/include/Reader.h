#ifndef READER_H
#define READER_H

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;


class Reader
{
     public:
        Reader();

        string getFname() { return fname; }
        vector<vector<string>> getContent() { return content; }
        vector<vector<string>> getStore() { return store; }
        vector<string> getRow() { return row; }
        vector<string> getPiece() { return piece; }

        void setFname(string n){this->fname = n;}

        void read();
        void printContent();

    protected:

    private:
        string fname;
        vector<vector<string>> content;
        vector<vector<string>>store;
        vector<string> row;
        vector<string> piece;
};

#endif // READER_H
