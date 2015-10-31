import java.io.*;
import java.util.*;

/** buildParser.java
 *  @author Doug Fultz
 */
class buildParser{
    private static final String sectionDelim="%%";
    
    private static String inputString;
    private static String[] sections;
    private static String[] rules;
    private static String[] rows;
    private static HashMap Terminals = new HashMap();
    private static HashMap nonTerminals = new HashMap();
    private static String[][] table;
    
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
        
        //Split inputString into sections
        sections=inputString.split(sectionDelim);
        
        //Split CFG into rules
        rules=sections[0].split("\n");
        
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
        /*System.out.println(nonTerminals.toString());
        for(int i=0;i<table.length; i++){
            for(int j=0;j<table[i].length; j++){
                System.out.print(table[i][j]+" ");
            }
            System.out.print("\n");
        }*/
        
    } //main()
} //class buildParser
