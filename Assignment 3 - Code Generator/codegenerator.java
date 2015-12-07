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
    
    /** MAIN function
     *  Starts program
     */
    public static void main(String args[]){
        //Prepare output file
        try{
            //http://www.homeandlearn.co.uk/java/write_to_textfile.html
            outputFile = new FileWriter(outputFilePath);
            printToFile = new PrintWriter(outputFile);
        }catch(IOException e){
            System.err.println("Error: unable to open file for output: "+outputFilePath);
            System.exit(-1);
        }
        
        //Final newline
        printToFile.println();
        
        //Always close the file
        printToFile.close();
        
        //Execute jasmine
        Runtime.getRuntime().exec("java -jar "+jasminJarPath+" "+outputFilePath);
    } //main()
} //class codegenerator
