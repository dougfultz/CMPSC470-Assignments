import java.io.*;
import java.util.*;

/** codegenerator.java
 *  @author Doug Fultz
 */
class codegenerator{
    private static String enterScore = "Enter score: ";
    private static String strNoScore = "ERROR: no score provided.";
    
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
        //https://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html
        do{
            //Read input from user
            input = c.readLine(enterScore);
            
            //Check if input is empty
            if(input.isEmpty()){
                System.out.println(strNoScore);
                continue;
            }
        }while(!input.equals("-9"));
    }
    
    /** MAIN function
     *  Starts program
     */
    public static void main(String args[]) throws java.io.IOException {
        //Output instructions to user
        System.out.println("Enter test scores one at a time.");
        System.out.println("Score of \"-9\" is end of input.");
        System.out.println();
        
        //Read scores
        readScores();
        
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
