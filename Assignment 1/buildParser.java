import java.io.*;
import java.util.*;

/** buildParser.java
 *  @author Doug Fultz
 */
class buildParser{
    /** MAIN function
     *  Starts program
     */
    public static void main(String args[]) throws java.io.IOException {
        if(args.length!=1){
            System.err.println("Error: Input file must be named on command line.");
            System.exit(-1);
        }
        
        try{
            //http://abhinandanmk.blogspot.com/2012/05/java-how-to-read-complete-text-file.html
            String inputString = new Scanner(new File(args[0])).useDelimiter("\\A").next();
            System.out.println(inputString);
        }catch(FileNotFoundException notFound){
            System.err.println("Error: file not found: "+args[0]);
            System.exit(-1);
        }
    } //main()
} //class buildParser
