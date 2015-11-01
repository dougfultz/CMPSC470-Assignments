import java.io.*;
import java.util.*;

/** buildParser.java
 *  @author Doug Fultz
 */
class buildParser{
    private static final String sectionDelim="%%";
    private static final String outputFilePath="./recDescent.cpp";
    private static final boolean appendToFile=false;
    
    private static String inputString;
    private static String[] sections;
    private static String[] rules;
    private static String[] rows;
    private static HashMap<String,Integer> Terminals = new HashMap<String,Integer>();
    private static HashMap<String,Integer> nonTerminals = new HashMap<String,Integer>();
    private static String[][] table;
    
    private static FileWriter outputFile;
    private static PrintWriter printToFile;
    
    /** Parses input into class data structures.
     */
    private static void parseCFG(){
        //Split inputString into sections
        sections=inputString.split(sectionDelim);
        
        //Split CFG into rules
        rules=sections[0].split("\n");
        
        /*for(int i=0; i<rules.length; i++){
            System.out.println(i+" '"+rules[i]+"'");
        }*/
        
        //Split table into rows
        rows=sections[1].split("\n");
        
        /*for(int i=0; i<rows.length; i++){
            System.out.println(i+" '"+rows[i]+"'");
        }*/
        
        //Create hashmap of column headers
        //rows[0] is empty due to how .split works when spliting between new lines then splitting on the new line
        String[] termHeader=rows[1].split(" ");
        
        //Check if first 2 elements are empty, this means that the first two characters in the header are spaces
        if(termHeader[0].isEmpty() && termHeader[1].isEmpty()){
            for (int i=2;i<termHeader.length; i++){
                //System.out.println(i+" '"+termHeader[i]+"'");
                Terminals.put(termHeader[i],i-2);
            }
            //System.out.println(Terminals.toString());
        }else{
            System.err.println("Error: first two characters of header row must be spaces");
            System.exit(-1);
        }
        
        //Initialize table
        table=new String[rows.length-2][termHeader.length-2];
        
        //Create hashmap of non-Terminals
        for(int i=2;i<rows.length; i++){
            //System.out.println(rows[i]);
            String[] currentRow=rows[i].split(" ");
            //First element is the non-Terminal
            nonTerminals.put(currentRow[0],i-2);
            
            //Remaining elements are table contents
            for(int j=1;j<currentRow.length; j++){
                table[i-2][j-1]=currentRow[j];
            }
        }
        //System.out.println(nonTerminals.toString());
        /*for(int i=0;i<table.length; i++){
            for(int j=0;j<table[i].length; j++){
                System.out.print(table[i][j]+" ");
            }
            System.out.print("\n");
        }*/
    } //parseCFG
    
    /** Writes the beginning of the class
     *  Content will be printed as it appears to the output file
     */
    private static void recDescentHead(){
        printToFile.println("// Generated recDescent");
        printToFile.println();
        printToFile.println("#include <iostream>");
        printToFile.println("#include <fstream>");
        printToFile.println();
        printToFile.println("using namespace std;");
        printToFile.println();
        printToFile.println("class recDescent {");
        printToFile.println();
        printToFile.println("    private:");
        printToFile.println("        ifstream ts;");
        printToFile.println();
        printToFile.println("        void MATCH(char token){");
        printToFile.println("            //From textbook, page 149");
        printToFile.println("            //Figure 5.5: Utility for matching tokens in an input stream.");
        printToFile.println("            if((char)ts.peek()==token){");
        printToFile.println("                ts.get();");
        printToFile.println("            }else{");
        printToFile.println("                //ERROR(token);");
        printToFile.println("                cerr<<\"Expected: \"+token<<endl;");
        printToFile.println("            }");
        printToFile.println("        }");
        printToFile.println();
    }
    
    /** Writes the end of the class
     *  Content will be printed as it appears to the output file
     */
    private static void recDescentTail(){
        printToFile.println("};");
        printToFile.println();
        printToFile.println("int main() {");
        printToFile.println("}");
    }
    
    /** MAIN function
     *  Starts program
     */
    public static void main(String args[]) throws java.io.IOException {
        //Check for correct number of parameters
        if(args.length!=1){
            System.err.println("Error: Input file must be named on command line.");
            System.exit(-1);
        }
        
        //Read entire file into inputString
        try{
            //http://abhinandanmk.blogspot.com/2012/05/java-how-to-read-complete-text-file.html
            inputString = new Scanner(new File(args[0])).useDelimiter("\\A").next();
            //System.out.println(inputString);
        }catch(FileNotFoundException notFound){
            System.err.println("Error: file not found: "+args[0]);
            System.exit(-1);
        }
        
        //Parse input
        parseCFG();
        
        //Prepare output file
        try{
            //http://www.homeandlearn.co.uk/java/write_to_textfile.html
            outputFile = new FileWriter(outputFilePath);
            //outputFile = new FileWriter(outputFilePath,appendToFile);
            printToFile = new PrintWriter(outputFile);
        }catch(IOException e){
            System.err.println("Error: unable to open file for output: "+outputFilePath);
            System.exit(-1);
        }
        
        //Place beginning of class structure
        recDescentHead();
        
        //Build a procedure for each non-Terminal
        //http://java67.blogspot.com/2013/08/best-way-to-iterate-over-each-entry-in.html
        for(Map.Entry<String,Integer> entryNT : nonTerminals.entrySet()){
            //System.out.println("Key: "+entry.getKey()+" Value: "+entry.getValue());
            printToFile.println("        void "+entryNT.getKey()+"(){");
            //http://www.cplusplus.com/doc/tutorial/control/
            printToFile.println("            switch((char)ts.peek()){");
            //Create a case for each possible non-terminal
            for(Map.Entry<String,Integer> entryT : Terminals.entrySet()){
                printToFile.println("                case '"+entryT.getKey()+"':");
                //Determine rule to useDelimiter
                Integer ruleNum = Integer.parseInt(table[nonTerminals.get(entryNT.getKey())][Terminals.get(entryT.getKey())]);
                printToFile.println("                    //RULE: "+ruleNum);
                //If rule is 0, error
                if(ruleNum==0){
                    printToFile.println("                    cerr<<\"Does not match to a rule\"<<endl;");
                }else{
                    //Need to look at rule
                    String ruleString = new String(rules[ruleNum-1]);
                    printToFile.println("                    //"+ruleString);
                    String[] ruleSplit = ruleString.split("::=");
                    //Rule does not derive empty
                    if(ruleSplit.length==2){
                        String[] ruleRHS = ruleSplit[1].split(" ");
                        for(int i=0; i<ruleRHS.length; i++){
                            //printToFile.println("//'"+ruleRHS[i]+"'");
                            if(!ruleRHS[i].isEmpty()){
                                //Check if token is a non-terminal
                                if(nonTerminals.containsKey(ruleRHS[i])){
                                    printToFile.println("                    "+entryNT.getKey()+"();");
                                //Check if token is a terminal
                                }else if(Terminals.containsKey(ruleRHS[i])){
                                    printToFile.println("                    MATCH('"+entryT.getKey()+"');");
                                //PROBLEM!!!
                                }else{
                                    System.err.println("Error: symbol is neither a terminal or nonterminal: "+ruleRHS[i]);
                                    System.exit(-1);
                                }
                            }
                        }
                    //Rule derives empty
                    }else if(ruleSplit.length==1){
                        //do nothing
                        printToFile.println("                    return;");
                    //Rule has extra separater
                    }else{
                        System.err.println("Rule "+ruleNum+" has extra RHS");
                        System.exit(-1);
                    }
                    
                }
                printToFile.println("                    break;");
            }
            printToFile.println("            }");
            printToFile.println("        }");
            printToFile.println();
        }
        
        //Place end of class structure
        recDescentTail();
        
        //Always close the file
        printToFile.close();
        
    } //main()
} //class buildParser
