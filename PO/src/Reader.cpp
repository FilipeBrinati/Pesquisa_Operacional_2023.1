#include "Reader.h"

Reader::Reader()
{
    setFname("data/sample.csv");
}

void Reader::read()
{
    string line, word;

    fstream file(fname, ios::in);
    if (file.is_open())
    {
        int j = 0;
        while (getline(file, line))
        {
            int i = 0;
            this->row.clear();

            stringstream str(line);

            while (getline(str, word, ',')){
                if(i == 0 && j != 0){
                    this->piece.push_back(word);
                    i++;
                } else
                    this->row.push_back(word);
            }
            if(j == 0){
                this->store.push_back(row);
            }else
                this->content.push_back(row);
            j++;
        }
    }
    else
        cout << "Could not open the file\n";
    file.close();
}

void Reader::printContent()
{
    for (int i = 0; i < this->store.size(); i++)
    {
        for (int j = 0; j < this->store[i].size(); j++)
        {
            cout << this->store[i][j] << " | ";
        }
        cout << "\n";
    }

    for (int i = 0; i < this->content.size(); i++)
    {
        cout << this->piece[i] << " | ";
        for (int j = 0; j < this->content[i].size(); j++)
        {
            cout << this->content[i][j] << " | ";
        }
        cout << "\n";
    }
}
