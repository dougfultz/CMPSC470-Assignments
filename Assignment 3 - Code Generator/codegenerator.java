import java.io.*;
import java.util.*;

/** codegenerator.java
 *  @author Doug Fultz
 */
class codegenerator{
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
    
    /** MAIN function
     *  Starts program
     */
    public static void main(String args[]) throws java.io.IOException {
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
