package edu.vassar.trl;

import java.util.*;

/**
 * Allows for creation of directed, weighted graph.
 * Extends TemporalNetwork.
 * @author Mark Infante
 * @author Ciara O'Donnell
*/
public class STN {
    private TemporalNetworks networkType;       // Enum describing net type
    private List<String> timePointNames;        // List of strings representing time point names
    private int numTimePoints;              // Number of nodes in the graph
    private DistanceMatrix distanceMatrix;      // A distance matrix initialized to null

    private List<HashMap<Integer, Double>> successors;   // A 2D vector that holds information about a node's successors.
                                                     // index of the list is start, map at index contains end and delta
    private List<HashMap<Integer, Double>> predecessors; // A 2D vector that holds information about a node's preds.
                                                     // index of the list is end, map at index contains start and delta
    private List<Integer> numSuccessors;             // Number of successor nodes for corresponding node index
    

    // Default constructor for simple temporal network
    public STN(){
        networkType = TemporalNetworks.STN;
        numSuccessors = new ArrayList<Integer>();
        successors = new ArrayList<HashMap<Integer, Double>>(); //iniitialize successors as a Vector
        predecessors = new ArrayList<HashMap<Integer, Double>>();
    }
    public STN(Integer numTimePoints){
        this.numTimePoints = numTimePoints; 
        networkType = TemporalNetworks.STN;
        numSuccessors = new ArrayList<Integer>();
        successors = new ArrayList<HashMap<Integer, Double>>(); //iniitialize successors as a Vector
        predecessors = new ArrayList<HashMap<Integer, Double>>();
    }
    
    public void init(){
        int tps = getNumTimePoints(); //populate the spaces in the vector, based on the number of timepoints
        for (int i = 0; i < tps; i++) {
            successors.add(i, new HashMap<Integer, Double>());
            predecessors.add(i, new HashMap<Integer, Double>()); 
            numSuccessors.add(0);
            // for (int k = 0; k < tps; k++)
            // {
            //     successors.get(i).put(k, Double.POSITIVE_INFINITY); // this defeats the entire point of using a hashmap...
            //     predecessors.get(i).put(k, Double.POSITIVE_INFINITY); // only add keys when we have values for them
            // }
        }   
    }

    public void addEdge(Edge edge){ 
        int x = edge.getStart();
        int y = edge.getEnd();
        Double d = edge.getWeight();
        Double w = successors.get(x).get(y);
        
        if (w == null){ //if an edge doesn't exist in successors, input the given edge argument. also increment numsuccessors, since we are adding a new edge
            successors.get(x).put(y, d);
            predecessors.get(y).put(x, d);
            numSuccessors.set(x, numSuccessors.get(x)+1);

        } else if (w > d){ //else if new edge gives a shorter path from x->y than the old edge, replace the old edge with the new one
            successors.get(x).put(y, d);
            predecessors.get(y).put(x,d);
        }
    }

    public void removeEdge(Edge edge){
        if(getEdge(edge.getStart(), edge.getEnd(), true) != null){
            int x = edge.getStart();
            int y = edge.getEnd();
            
            successors.get(x).remove(y);
            predecessors.get(y).remove(x);
            numSuccessors.set(x, numSuccessors.get(x)-1);
            distanceMatrix.setUpToDate(false);
        }
    }
    
    /**
     * Takes the 'from' and 'to' nodes and returns the corresponding edge.
     * @param src Source index.
     * @param dest Destination index.
     * @param isSucc A boolean whether to get succ or pred.
     * @return An edge if found, or null.
     */
    public Edge getEdge(Integer src, Integer dest, boolean isSucc){
        Double weight = Double.POSITIVE_INFINITY;
        
        if (isSucc) {
            if(successors.get(src).containsKey(dest)){
                weight = successors.get(src).get(dest);
            }
        } else {
            if(predecessors.get(src).containsKey(dest)){
                weight = predecessors.get(src).get(dest);
            }
        }

        Edge edge = (weight == Double.POSITIVE_INFINITY)?  null : new Edge(src,dest,weight);
        return edge;
    }

    
    public Map<Integer, Double> getSuccsOf(Integer node){ return successors.get(node); }
    public List<HashMap<Integer, Double>> getSuccs() { return successors; }

    public Map<Integer, Double> getPredsOf(Integer node){ return predecessors.get(node); }
    public List<HashMap<Integer, Double>> getPreds() { return predecessors; }

    public void setNetType(TemporalNetworks networkType){ this.networkType = networkType; }
    public TemporalNetworks getNetType(){ return networkType; }

    public void setTimePointNames(List<String> timePointNames){ this.timePointNames = timePointNames; }
    public List<String> getTimePointNames(){ return timePointNames; }

    public Integer getNumTimePoints(){ return numTimePoints; }
    public void setNumTimePoints(Integer numTimePoints){ this.numTimePoints = numTimePoints; }

    public boolean hasDistanceMatrix(){ return distanceMatrix == null; }
    public void setDistanceMatrix(DistanceMatrix dm){ distanceMatrix = dm; }
    public DistanceMatrix getDistanceMatrix(){ return distanceMatrix; }


    @Override
    public String toString(){
        // TODO: test
        String output = "\n\n";
        Double tWeight = null;
        for (int x = 0; x < getNumTimePoints() + 1; x++){
            for (int y = 0; y <= getNumTimePoints() + 1; y++){
                if (y == getNumTimePoints()+1){     // Create new line in matrix
                    output = output + "\n\n";
                } else if (x == 0){     // Create first row of start nodes
                    if (y == 0) {       // Skip a space for alignment at 0,0 because we use 0 as a node
                        output = output + "\t\t" + Integer.toString(y); 
                    } else if (y < getNumTimePoints()){            
                        output = output + "\t" + Integer.toString(y);
                    }
                } else if (y == 0){     // Create first column of end nodes
                    output = output + "\t" + Integer.toString(x - 1); 
                } else {
                    tWeight = this.successors.get(x-1).get(y-1);
                    if (tWeight == null) {
                        output = output + "\t" + "null";
                    } else if (tWeight == Double.POSITIVE_INFINITY){
                        output = output + "\t" + "inf";
                    } else {
                        output = output + "\t" + tWeight;
                    }
                }
            }
        }
        return output;
    }
}
