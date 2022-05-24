#include <iostream>
#include <string>
#include <fstream>
#include <chrono>
#include <unordered_map>

using namespace std;
using namespace std::chrono;

void lower(string s)
{
    transform(s.begin(), s.end(), s.begin(), ::tolower);
}

void generateFile(string fileID, string preset)
{
    printf("Generating... {filename=%s, presets=%s}\n", fileID.c_str(), preset.c_str());
    ofstream Filejen(fileID);
    Filejen << preset + "\n";
    Filejen.close();
    cout << "Filejenerated!\n";
}

int main()
{
    cout << "Filejen C++ 2022 V2.0\n";
    cout << "Fast input? (Y)\n";
    string s = "";
    getline(cin, s);
    lower(s);
    bool fInp = (s.compare("y") == 0);
    if (fInp)
    {
        cout << "{filename.filetype, presets}\n";
        string fileID = "";
        string presets = "";
        string inp = "";
        getline(cin, inp);
        string delimiter = ", ";
        fileID = inp.substr(0, inp.find(delimiter));
        presets = inp.substr(inp.find(delimiter) + delimiter.size(), inp.size());
        generateFile(fileID, presets);
    }
    else
    {
        cout << "Enter filename:\n";
        string filename = "";
        getline(cin, filename);
        cout << "Enter filetype:\n";
        string filetype = "";
        getline(cin, filetype);
        lower(filetype);
        cout << "Enter input presets:\n";
        string presets = "";
        getline(cin, presets);
        generateFile(filename + "." + filetype, presets);
    }

    // auto start = high_resolution_clock::now();
    // auto stop = high_resolution_clock::now();
    // auto duration = duration_cast<microseconds>(stop - start);
    // cout << "Program Duration: " << duration.count() << endl;
    return 0;
}