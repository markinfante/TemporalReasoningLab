package edu.vassar.trl;

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
    private static final String PATH_TO_STN = "src/main/resources/stn/";
    private static final String PATH_TO_STNU = "src/main/resources/stnu/";

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
        helpString += "\t-> \'johnsons\' or \'j\' to run Johnson's Algorithm on the network.\n";
        helpString += "\t-> \'add edge\' prompts the creation of a new edge. This can then be followed by:\n";
        helpString += "\t\t-> \'naive\' to test the incrementor algorithm. (ex. 'add edge 1 2 3.0 naive')\n";
        helpString += "\t\t-> \'fwdback\' to test the forward backward propagation algorithm. (ex. 'add edge 1 2 3.0 fwdback')\n";
        System.out.println(helpString);
    }

    /**
     * Prints flags to standard out.
     */
    private void printFlags(){
        String flagString = ""; // The help string to be returned
        flagString += "\nAvailable flags: \n";
        flagString += "\t-> <flag> <option> :\n";
        flagString += "\t-> \'-t all\' to run default tests on all STNs in test repo.\n";
        System.out.println(flagString);
    }

    public static void main(String[] args){
        TemporalLaboratory lab = new TemporalLaboratory(); 
        STNParser parser = null;
        TestSuite tSuite = null;
        File file = null;   // Resource file containing type of network and constraints
        Scanner inputScanner = null;     
        String ts = ""; // Temporary string for reading from file  
        String flag = "";
        String option = "";
        STN tSTN = null;
        ArrayList<STN> stnList = new ArrayList<>();
        String[] inputArgs = {};

        if (args.length < 1){  // Dont let the user carry on without an input file
            System.out.println("Temporal laboratory should take in a resource file of graph constraints.");

            /*
            System.out.println("Welcome to the Temporal Laboratory. If you would like to create an STN, type "create.");
            System.out.println("Else, type "help" or "h" to see a list of other options.");
            boolean run = true;
            // should probably put everything in the while in a try catch block
            inputScanner = new Scanner(System.in);
            while (run)
            {
                ts = inputScanner.nextLine();
                if (ts.equals("create"))
                {
                    System.out.println("To add an edge to the STN, type in "add edge" and then the start node, end node, and edge weight.");
                    System.out.println("Example: 'add edge 1 2 3' ");
                    lab.network = new STN();
                    ts = inputScanner.nextLine();
                    while(true)
                    {
                        inputargs = ts.split(" ");
                    }
                }
                else if (ts.equals("help") || ts.equals("h")){
                    lab.printHelp();
                }
                else if (ts.equals("quit") || ts.equals("q"))
                {
                    run = false;
                    return;
                }
            }
            */
            return;
        } else if (args.length == 1){
            System.out.println("A flag should be specified. If none, a new network will be created without");
            System.out.println(" a distance matrix and your actions will be limited.\n");
            System.out.println("If you would like to continue without a flag enter \'y\', else \'n\' and");
            System.out.println(" a list of available flags will be printed for you and the program will exit.");
            try{
                inputScanner = new Scanner(System.in);
                ts = inputScanner.nextLine();
            } catch (Exception e) {
                System.err.println("We didn't like that... " + e);
                return;
            } 
        
            if (ts.equals("y")){
                try {  // Try to parse input file
                    file = new File(PATH_TO_STN + args[0]);
                    parser = new STNParser();
                    lab.network = parser.parseFile(file);
                    parser.echoFile(file);
                } catch (NullPointerException np){
                    System.err.println("Path to file cannot be null.");
                } catch (Exception e){
                    System.err.println(e);
                }
            } else if (ts.equals("n")){
                lab.printFlags();
                return;
            } else {
                System.out.println("That was not valid. Exiting the program.");
                return;
            }
        

            try { // try to read input from user after network creation
                lab.printHelp();
                while (inputScanner.hasNext()){
                    ts = inputScanner.nextLine().trim();
                    if (ts.equals("print") || ts.equals("p")){
                        System.out.println(lab.network.toString());
                    } else if (ts.equals("quit") || ts.equals("q")){
                        break;
                    } else if (ts.equals("help") || ts.equals("h")){
                        lab.printHelp();
                    } else if (ts.equals("johnsons") || ts.equals("j")){ 
                        tSuite = new TestSuite();
                        tSuite.testJohnsons(lab.network);
                    }else {
                        try{
                            inputArgs = ts.split(" ");
                            switch(inputArgs[0]){
                                case "add":
                                    switch (inputArgs[1]){
                                        case "edge":
                                            // take in values for new edge
                                            int x = Integer.parseInt(inputArgs[2]);
                                            int y = Integer.parseInt(inputArgs[3]);
                                            Double z = Double.parseDouble(inputArgs[4]);
                                            // add edge to network
                                            // create new test suite
                                            tSuite = new TestSuite();
                                            // run stn test
                                            switch(inputArgs[5]){
                                                case "naive":
                                                    tSuite.testNaive((new Edge (x,y,z)), lab.network);
                                                    // run new incrementor test (should print distance matrix)
                                                    break;
                                                case "fwdback":
                                                    tSuite.testFwdBackPropagation((new Edge (x,y,z)), lab.network);
                                            }
                                            break;
                                        default:
                                            System.out.println("Error. (Maybe misspelled 'edge'?)");
                                            break;
                                    }
                                    break;
                                default:
                                    System.out.println("Error.");
                                    break;
                            }
                        } catch (Exception e){
                            System.out.println(e);
                        }
                        
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
            option = args[1];
            switch (flag){
                case "-t":
                    switch (option){
                        case "all":
                            try{
                                file = new File(PATH_TO_STN);
                                parser = new STNParser();
                                tSuite = new TestSuite();
                                for (File f : file.listFiles()){
                                    tSTN = parser.parseFile(f);
                                    parser.echoFile(f);
                                    tSuite.testSTN(tSTN);
                                    tSuite.testConsistencyChecker(tSTN);
                                    tSuite.testNaive((new Edge (3,4,4.0)), tSTN);
                                    
                                }
                            } catch (Exception e){
                                System.err.println(e);
                            }
                            break;
                        default:
                            System.out.println("Not implemented.");
                            break;
                    }
                    break;
                default:
                    System.out.println("Flag not recognized.");
                    break;
            }
        }
    }
}
