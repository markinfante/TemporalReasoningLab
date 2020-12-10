package edu.vassar.trl.stnu;

import edu.vassar.trl.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class STNUParser {

    private HashMap<File,String> echoMap; // A mapping of file to echo string

    /**
     * Creates new STNU parser object.
     */
    public STNUParser(){
        echoMap = new HashMap<File,String>();
    }

    /**
     * # KIND OF NETWORK 
     * STNU 
     * # Num Time-Points 
     * 5 
     * # Num Ordinary Edges 
     * 4 
     * # Num Contingent Links 
     * 2 
     * # Time-Point Names 
     * A0 C0 A1 C1 X 
     * # Ordinary Edges 
     * X 12 C0 
     * C1 11 C0 
     * C0 -7 X 
     * C0 -1 C1 
     * # Contingent Links 
     * A0 1 3 C0 
     * A1 1 10 C1
     */

    public STNU parseFile(File file) throws Exception {
        Scanner fileScanner = null;
        String echo = "";
        String ts = ""; // Temp string
        Integer lStart = 0; // Local start node
        Integer lEnd = 0; // Local end node
        Double lWeight = 0.0; // Local edge weight
        Integer lX = 0;
        Integer lY = 0;
        Integer numTimePoints = 0; // Number of time points from input file
        Integer numLinks = 0;
        List<String> timePointNames = new ArrayList<String>(); // Time point names from input file
        List<String> pseudoEdge = new ArrayList<String>(); // An edge from input file in the format of string list
        STNU network = new STNU();

        try { // Try to read values from input file
            fileScanner = new Scanner(file);
            if (fileScanner.hasNext()) {
                fileScanner.nextLine();
                ts = fileScanner.nextLine().trim(); // Get network type
                TemporalNetworks typ = TemporalNetworks.valueOf(ts);
                echo += "Type of temporal network: " + typ.toString() + "\n";
            } else {
                throw new Exception("File is empty.");
            }
            fileScanner.nextLine();
            ts = fileScanner.nextLine(); // Get num time points
            numTimePoints = Integer.parseInt(ts);
            echo += "Number of time points: " + ts + "\n";
            network.setNumTimePoints(numTimePoints);
            fileScanner.nextLine();
            ts = fileScanner.nextLine();
            echo += "Number of edges: " + ts + "\n";
            fileScanner.nextLine();
            ts = fileScanner.nextLine();
            echo += "Number of Links: " + ts + "\n";
            network.setNumCLinks(Integer.parseInt(ts));
            fileScanner.nextLine();
            ts = fileScanner.nextLine(); // Get time point names
            timePointNames = Arrays.asList(ts.split(" "));
            echo += "Time point names: \n" + ts + "\n";
            network.setTimePointNames(timePointNames);
            fileScanner.nextLine();
            network.init();
            echo += "Edges: \n";
            while (fileScanner.hasNext()) { // Get edges
                ts = fileScanner.nextLine();
                pseudoEdge = Arrays.asList(ts.split(" "));
                if (pseudoEdge.get(0).equals("#")) { // if attempt is start of links, break
                    echo += "Contingent Links: \n";
                    break;
                }
                lStart = timePointNames.indexOf(pseudoEdge.get(0));
                lEnd = timePointNames.indexOf(pseudoEdge.get(2));
                lWeight = Double.parseDouble(pseudoEdge.get(1));
                echo += String.format("Start: %d. End: %d. Weight: %f.\n", lStart, lEnd, lWeight);
                network.addEdge(new Edge(lStart, lEnd, lWeight));
            }
            while (fileScanner.hasNext()) { // get links
                ts = fileScanner.nextLine();
                pseudoEdge = Arrays.asList(ts.split(" "));
                lStart = timePointNames.indexOf(pseudoEdge.get(0));
                lEnd = timePointNames.indexOf(pseudoEdge.get(3));
                lX = Integer.parseInt(pseudoEdge.get(1));
                lY = Integer.parseInt(pseudoEdge.get(2));
                echo += String.format("Start: %d. X: %d. Y: %d. End: %d.\n", lStart, lX, lY, lEnd);
                network.addLink(new Link(lStart, lX, lY, lEnd)); 
            }
            echo += "Finished network creation.\n";
        } catch (NullPointerException np) {
            System.err.println(np.getCause());
        } catch (FileNotFoundException fnf) {
            System.err.println("File not found, make sure to input a valid path.");
        } catch (Exception e) {
            System.err.println(e.getCause());
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }

        echoMap.put(file, echo); // Put echo string in map
        return network; // Return new network
    }

    /**
     * Echos a file after its been parsed.
     * @param file A File to be echoed.
     */
    public void echoFile(File file){
        //TODO: Wrap in try catch, file may not have been parsed.
        System.out.println(echoMap.get(file));
    }
}
