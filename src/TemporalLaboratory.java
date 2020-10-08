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

    private TemporalNetwork network;

    public TemporalLaboratory(TemporalNetworks networkType){
        switch (networkType) {
            case STN:
                network = new STN(); 
            default:  
        }
    }

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
        TemporalLaboratory lab = null; 
        File file = null;   // Resource file containing type of network and constraints
        Scanner fileScanner = null; // Scanner for file
        Scanner inputScanner = null;     
        String ts = ""; // Temporary string for reading from file 
        Integer lStart = 0;   // Local start node 
        Integer lEnd = 0;     // Local end node
        Double lWeight = 0.0; // Local edge weight
        Integer numTimePoints = 0; // Number of time points from input file
        List<String> timePointNames = new ArrayList<String>();  // Time point names from input file
        List<String> pseudoEdge = new ArrayList<String>();  // An edge from input file in the format of string list 

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
        }
        if (ts.equals("y")){
            try {  // Try to read values from input file
                file = new File("resources/" + args[0]);
                fileScanner = new Scanner(file);
                if (fileScanner.hasNext()){
                    fileScanner.nextLine();
                    ts = fileScanner.nextLine().trim(); // Get network type 
                    TemporalNetworks typ = TemporalNetworks.valueOf(ts);
                    System.out.println("Type of temporal network: " + typ.toString());
                    lab = new TemporalLaboratory(typ);
                } else {
                    System.out.println("File is empty.");
                    return; 
                }
                fileScanner.nextLine();
                ts = fileScanner.nextLine();  // Get num time points
                numTimePoints = Integer.parseInt(ts);
                System.out.println("Number of time points: " + ts);
                lab.network.setNumTimePoints(numTimePoints);
                fileScanner.nextLine();
                ts = fileScanner.nextLine();  // Get time point names
                timePointNames = Arrays.asList(ts.split(" "));
                System.out.println("Time point names: \n" + ts);
                lab.network.setTimePointNames(timePointNames);
                fileScanner.nextLine();
                lab.network.init();
                System.out.println("Edges: ");
                while (fileScanner.hasNext()){   // Get constraints
                    ts = fileScanner.nextLine();
                    pseudoEdge = Arrays.asList(ts.split(" "));
                    lStart = timePointNames.indexOf(pseudoEdge.get(0));
                    lEnd = timePointNames.indexOf(pseudoEdge.get(2));
                    lWeight = Double.parseDouble(pseudoEdge.get(1));
                    System.out.printf("Start: %d. End: %d. Weight: %f.\n", lStart, lEnd, lWeight);
                    lab.network.addEdge(new Edge(lStart,lEnd, lWeight));
                }
                System.out.println("Finished network creation.\n\nType \'help\' for options list.\n");
            } catch (NullPointerException np){
                System.err.println("Path to file cannot be null.");
            } catch (FileNotFoundException fnf){
                System.err.println("File not found, make sure to input a valid path.");
            } catch (Exception e){
                System.err.println(e);
            } finally {
                if (fileScanner != null){ fileScanner.close(); }     
            }
        } else if (ts.equals("n")){
            lab = new TemporalLaboratory(TemporalNetworks.STN); // placeholder
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
        
    }
}
