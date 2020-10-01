package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**  TemporalLaboratory.java
 *      Main class for project.
 *      Creates specified Temporal Network from resource file and prompts user for action.
 *  @author Mark Infante
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

    public static void main(String[] args){
        TemporalLaboratory lab = null; 
        File file = null;   // Resource file containing type of network and constraints
        Scanner fileScanner = null; // Scanner for file
        Scanner inputScanner = null;     
        String ts; // Temporary string for reading from file 
        Integer lStart = 0;   // Local start node 
        Integer lEnd = 0;     // Local end node
        Double lWeight = 0.0; // Local edge weight
        Integer numTimePoints = 0; 
        List<String> timePointNames = new ArrayList<String>();
        List<String> pseudoEdge = new ArrayList<String>();

        if (args.length != 1){
            System.out.println("Temporal laboratory should take in a resource file of graph constraints.");
            return;
        }
        try {
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
            System.out.println("Finished network creation.");
        } catch (NullPointerException np){
            System.err.println("Path to file cannot be null.");
        } catch (FileNotFoundException fnf){
            System.err.println("File not found, make sure to input a valid path.");
        } catch (Exception e){
            System.err.println(e);
        } finally {
            if (fileScanner != null){ fileScanner.close(); }     
        }
        try {
            inputScanner = new Scanner(System.in);
            while (inputScanner.hasNext()){
                ts = inputScanner.nextLine().trim();
                if (ts.equals("print") || ts.equals("p")){
                    System.out.println(lab.network.toString());
                } else if (ts.equals("quit") || ts.equals("q")){
                    break;
                }
            }
            
            
            
        } catch (Exception e){
            System.err.println("In lab class: " + e);
        }
        
    }
}
