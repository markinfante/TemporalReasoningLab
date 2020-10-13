package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**  
 * Main class for project.
 * Creates specified Temporal Network from resource file and prompts user for action.
 * @author Mark Infante
*/
public class TemporalLaboratory{

    private STN network;

    public TemporalLaboratory(){}

    /**
     * Prints user help to standard out.
     */
    private void printHelp(){
        String helpString = ""; // The help string to be returned
        helpString += "After the creation of a network is completed you may prompt\n";
        helpString += "a variety of actions. They include: \n";
        helpString += "\t-> \'help\' or \'h\' to print this message again.\n";
        helpString += "\t-> \'print\' or \'p\' to print a representation of the network.\n";
        helpString += "\t-> \'quit\' or \'q\' to quit.\n";
        System.out.println(helpString);
    }

    /**
     * Prints flags to standard out.
     */
    private void printFlags(){
        String flagString = ""; // The help string to be returned
        flagString += "\nAvailable flags: \n";
        flagString += "\t-> None implemented.\n";
        //flagString += "\t-> \'print\' or \'p\' to print a representation of the network.\n";
        //flagString += "\t-> \'quit\' or \'q\' to quit.\n";
        System.out.println(flagString);
    }

    public static void main(String[] args){
        TemporalLaboratory lab = new TemporalLaboratory(); 
        STNParser parser = null;
        File file = null;   // Resource file containing type of network and constraints
        Scanner inputScanner = null;     
        String ts = ""; // Temporary string for reading from file  
        String flag = "";
        ArrayList<STN> stnList = new ArrayList<>();

        if (args.length < 1){  // Dont let the user carry on without an input file
            System.out.println("Temporal laboratory should take in a resource file of graph constraints.");
            return;
        } else if (args.length == 1){
            System.out.println("A flag should be specified. If none, a new network will be created without");
            System.out.println(" a distance matrix and your actions will be limited.\n");
            System.out.println("If you would like to continue without a flag enter \'y\', else \'n\' and");
            System.out.println(" a list of available flags will be printed for you.");
            try{
                inputScanner = new Scanner(System.in);
                ts = inputScanner.nextLine();
            } catch (Exception e) {
                System.err.println("We didn't like that... " + e);
                return;
            } 
        
            if (ts.equals("y")){
                try {  // Try to parse input file
                    file = new File("resources/" + args[0]);
                    parser = new STNParser();
                    lab.network = parser.parseFile(file);
                } catch (NullPointerException np){
                    System.err.println("Path to file cannot be null.");
                } catch (Exception e){
                    System.err.println(e);
                }
            } else if (ts.equals("n")){
                lab = new TemporalLaboratory(); // placeholder
                lab.printFlags();
            } else {
                System.out.println("That was not valid. Exiting the program.");
            }
        

            try { // try to read input from user after network creation
                while (inputScanner.hasNext()){
                    ts = inputScanner.nextLine().trim();
                    if (ts.equals("print") || ts.equals("p")){
                        System.out.println(lab.network.toString());
                    } else if (ts.equals("quit") || ts.equals("q")){
                        break;
                    } else if (ts.equals("help") || ts.equals("h")){
                        lab.printHelp();
                    }
                }
            } catch (Exception e){
                System.err.println("In lab class: " + e);
            } finally {
                if (inputScanner != null){ inputScanner.close(); }     
            }
        } else if (args.length == 2) {
            // Of the form <flag> <option>
            flag = args[0];
            ts = args[1];
            switch (flag){
                case "-t":
                    switch (ts){
                        case "all":
                            try{
                                file = new File("resources/");
                                parser = new STNParser();
                                stnList = parser.parseDirectory(file);
                            } catch (Exception e){
                                System.err.println(e);
                            }
                    }
                    break;
                default:
                    System.out.println("Flag not recognized.");
                    break;
            }
        }
    }
}
