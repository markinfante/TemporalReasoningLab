package src;

import java.util.ArrayList;

/**
 * A matrix abstraction using a 2D ArrayList of Doubles. 
 */
public class DistanceMatrix extends ArrayList<ArrayList<Double>>{
    
    private static final long serialVersionUID = 1L; // necessary field for extending ArrayList
    private boolean upToDate;       // flag that maintains up to date status of the matrix

    /**
     * Create new DistanceMatrix (2D ArrayList).
     */
    public DistanceMatrix(){
        upToDate = false; 
    }
    
    /**
     * Initializes a distance matrix with network edge weights and infinity values if no edge weight exists.  
     */
    public void makeCleanMatrixFromSTN(STN network){
        Edge tEdge = null;

        for (int i = 0; i < network.getSizeEdgesMatrix(); i++){
            this.add(new ArrayList<Double>());  // Add lists for every edge 
            for (int j = 0; j < network.getSizeEdgesMatrix(); j++){ // Fill every list (ie. j at column i)
                tEdge = network.getEdge(i, j);
                if (tEdge != null){
                    this.get(i).add(tEdge.getWeight());
                } else {
                    this.get(i).add(Double.POSITIVE_INFINITY);  
                }
            }
        }
    }

    @Override
    public String toString(){
        String output = "\nPrinting network: \n\n";
        Double tWeight = Double.POSITIVE_INFINITY;
        for (int y = 0; y < size() + 1; y++){
            for (int x = 0; x <= size() + 1; x++){
                if (x == size()+1){     // Create new line in matrix
                    output = output + "\n\n";
                } else if (y == 0){     // Create first row of start nodes
                    if (x == 0) {       // Skip a space for alignment at 0,0 because we use 0 as a node
                        output = output + "\t\t" + Integer.toString(x); 
                    } else if (x < size()){            
                        output = output + "\t" + Integer.toString(x);
                    }
                } else if (x == 0){     // Create first column of end nodes
                    output = output + "\t" + Integer.toString(y - 1); 
                } else { // fill with weights
                    tWeight = this.get(x-1).get(y-1);
                    if (tWeight == Double.POSITIVE_INFINITY) {
                        output = output + "\t" + "inf";
                    } else {
                        output = output + "\t" + tWeight;
                    }
                }
            }
        }
        return output;
    }

    /* ==== GETTERs and SETTERs ==== */

    public boolean isUpToDate() { return upToDate; }
    public void setUpToDate(boolean bool) { upToDate = bool; }

}
