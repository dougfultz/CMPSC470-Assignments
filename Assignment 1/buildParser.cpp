/** Doug Fultz
 *  CMPSC470 - Assignment 1
 *  Dr. El Ariss
 *  buildParser.cpp
 */

#include <iostream>
#include <fstream>

using namespace std;
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
        }
        inputFile.close();
    }
}