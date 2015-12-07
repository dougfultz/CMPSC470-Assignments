import java.io.*;
import java.util.*;
import java.lang.*;

/** codegenerator.java
 *  @author Doug Fultz
 */
class codegenerator{
    private static String enterScore = "Enter score: ";
    private static String endOfInput = "-9";
    
    private static Vector<Double> scores = new Vector<Double>();
    
    private static String strNoScore = "ERROR: no score provided.";
    private static String strNotAScore = "ERROR: input is not a number.";
    
    private static final String jasminJarPath="./jasmin.jar";
    private static final String outputFilePath="./simple.j";
    
    private static FileWriter outputFile;
    private static PrintWriter printToFile;
    
    /** Writes the beginning of the class
     *  Content will be printed as it appears to the output file
     */
    private static void outputHead(){
        printToFile.println(".class simple");
        printToFile.println(".super java/lang/Object");
        printToFile.println(".method public <init>()V");
        printToFile.println("    .limit stack 1");
        printToFile.println("    .limit locals 1");
        printToFile.println("    aload_0");
        printToFile.println("    invokespecial java/lang/Object/<init>()V");
        printToFile.println("    return");
        printToFile.println(".end method");
        printToFile.println();
        printToFile.println();
        printToFile.println();
    }
    
    /** Reads test scores provided by user from console
     *  Input will be stored in class vector
     */
    private static void readScores(){
        //https://docs.oracle.com/javase/tutorial/essential/io/cl.html
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        
        String input;
        Double dblInput;
        
        //https://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html
        do{
            //Read input from user
            input = c.readLine(enterScore);
            
            //Check if not end of input
            if(!input.equals(endOfInput)){
                
                //Check if input is empty
                if(input.isEmpty()){
                    System.out.println(strNoScore);
                    continue;
                }
                
                //Check if input parses to a double
                try{
                    dblInput=new Double(input);
                    
                    //Add score to vector
                    scores.add(dblInput);
                    
                }catch(NumberFormatException e){
                    System.out.println(strNotAScore);
                    continue;
                }
            }
        }while(!input.equals(endOfInput));
        
        System.out.println();
    }
    
    /** MAIN function
     *  Starts program
     */
    public static void main(String args[]) throws java.io.IOException {
        //Output instructions to user
        System.out.println("Enter test scores one at a time.");
        System.out.println("Score of \""+endOfInput+"\" is end of input.");
        System.out.println();
        
        //Read scores
        readScores();
        
        //Output vector
        System.out.println("Scores entered:");
        System.out.println(scores.toString());
        
        //Prepare output file
        try{
            //http://www.homeandlearn.co.uk/java/write_to_textfile.html
            outputFile = new FileWriter(outputFilePath);
            printToFile = new PrintWriter(outputFile);
        }catch(IOException e){
            System.err.println("Error: unable to open file for output: "+outputFilePath);
            System.exit(-1);
        }
        
        //Place beginning of class structure
        outputHead();
        
        //Final newline
        printToFile.println();
        
        //Always close the file
        printToFile.close();
        
        //Execute jasmine
        Runtime.getRuntime().exec("java -jar "+jasminJarPath+" "+outputFilePath);
    } //main()
} //class codegenerator
