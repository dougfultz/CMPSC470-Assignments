import java.io.*;

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
    } //main()
} //class buildParser
