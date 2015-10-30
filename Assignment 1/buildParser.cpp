/** Doug Fultz
 *  CMPSC470 - Assignment 1
 *  Dr. El Ariss
 *  buildParser.cpp
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <string>
#include <cctype>

using namespace std;
//-------------------------------------------------------------------
//Globals
const string separator="%%";
const string ruleSep="::=";
//-------------------------------------------------------------------
//http://www.cplusplus.com/doc/tutorial/structures/
struct rule{
    string LHS;
    string RHS;
};
//-------------------------------------------------------------------
//https://www.cs.upc.edu/~jordicf/Teaching/programming/pdf4/IP10_Matrices-4slides.pdf
typedef vector<int> Row;
typedef vector<Row> Matrix;
//Matrix my_matrix(3,Row(4));
//-------------------------------------------------------------------
string removeWhitespace(string input){
    //http://en.cppreference.com/w/cpp/algorithm/remove
    input.erase(remove(input.begin(),input.end(),' '),input.end());
    input.erase(remove(input.begin(),input.end(),'\t'),input.end());
    
    //will also remove new lines
    //requires "-std=c++11" flag for g++
    //input.erase(remove_if(input.begin(),input.end(),[](char x){return isspace(x);}),input.end());
    
    return(input);
}
//-------------------------------------------------------------------
bool match(ifstream &ts, char token){
    //From textbook, page 149
    //Figure 5.5: Utility for matching tokens in an input stream.
    if((char)ts.peek()==token){
        ts.get();
        return(true);
    }else{
        cerr<<"Expected token: "<<token<<endl;
        return(false);
    }
}
//===================================================================
int main(int argc, char * argv[]){
    cout<<"Hello World"<<endl;
    
    //Accept file as only argument.
    //http://www.cprogramming.com/tutorial/lesson14.html
    //http://www.cplusplus.com/articles/DEN36Up4/
    if(argc!=2){
        cerr<<"Usage: "<<argv[0]<<" FILE"<<endl;
        cerr<<endl;
        cerr<<"    FILE - Path to file containing parse table and CFG"<<endl;
        return(1);
    }else{
        ifstream inputFile(argv[1]);
        if(!inputFile.is_open()){
            cerr<<"Could not open file: "<<argv[1]<<endl;
            return(1);
        }else{
            cout<<"File opened."<<endl;
            
            vector<rule> CFG;
            vector<char> rowHeader;
            vector<char> colHeader;
            Matrix lookaheadTable;
            string line;
            string tempStr;
            size_t loc;
            
            //Read CFG grammar
            while(getline(inputFile,line)){
                //Check for separater
                if(line.compare(separator)==0)
                    break;
                //Check if line is a rule
                loc=line.find(ruleSep);
                if(loc==string::npos){
                    cerr<<"Line: "<<line<<endl;
                    cerr<<"Missing token: "<<ruleSep<<endl;
                }else{
                    //Rule found, add it to the CFG
                    rule newRule;
                    newRule.LHS=removeWhitespace(line.substr(0,loc-1));
                    newRule.RHS=removeWhitespace(line.substr(loc+ruleSep.length()));
                    CFG.push_back(newRule);
                }
            }
            cout<<"Rules added."<<endl;
            
            //Read Header of table
            if(getline(inputFile,line)){
                tempStr=removeWhitespace(line);
            }
            
        }
        inputFile.close();
    }
}