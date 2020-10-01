package src;

import java.util.ArrayList;

/**
 * A matrix abstraction using a 2D ArrayList of Doubles. Must call generateMatrix() for initialization. 
 */
public class DistanceMatrix extends ArrayList<ArrayList<Double>>{
    
    private static final long serialVersionUID = 1L; // necessary field for extending ArrayList
    private boolean upToDate;       // flag that maintains up to date status of the matrix
    private MatrixGeneratorType genType;    // an enum that specifies which algorithm to use for generating a matrix 
    private MatrixIncrementorType incType;  // an enum that specifies which algorithm to use for incrementing a matrix
    private ConsistencyCheckerType checkType; // an enum that specifies which algorithm to use for checking matrix consistency
    private TemporalNetwork network; // the temporal network associated with this distance matrix

    /**
     * Default constructor. Every matrix needs a reference to its network and a means to generate.
     * @param generatorType
     * @param network
     */
    public DistanceMatrix(MatrixGeneratorType generatorType, TemporalNetwork network){
        upToDate = false; 
        genType = generatorType;
        incType = null;
        checkType = null;
        this.network = network;
    }

    /**
     * Overloaded constructor. If the incremental updator type is known.
     * @param generatorType
     * @param network
     * @param incrementorType
     */
    public DistanceMatrix(MatrixGeneratorType generatorType, TemporalNetwork network, MatrixIncrementorType incrementorType){
        upToDate = false; 
        genType = generatorType;
        incType = incrementorType;
        checkType = null;
        this.network = network;
    }

    /**
     * Overloaded constructor. If the incremental updator and consistency checker are known. 
     * @param generatorType
     * @param network
     * @param incrementorType
     * @param consistencyCheckerType
     */
    public DistanceMatrix(MatrixGeneratorType generatorType, TemporalNetwork network, MatrixIncrementorType incrementorType, 
                            ConsistencyCheckerType consistencyCheckerType){
        upToDate = false; 
        genType = generatorType;
        incType = incrementorType;
        checkType = consistencyCheckerType;
        this.network = network;
    }
    
    /**
     * Initializes a distance matrix with network edge weights and infinity values if no edge weight exists.  
     */
    private void makeCleanMatrix(){
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

    /**
     * Initializes a matrix and updates every edge to have best path values, 
     * according to generator type.  
     */
    public void generateMatrix(){
        this.makeCleanMatrix();
        switch (genType) {
            case FWARSHALL:
                this.floydWarshall();  // run floyd warshall alg
                break;
            case JOHNSONS:
                break;
            default:
                break;
        }
    }

    /**
     * Incrementally updates the distance matrix according to an edge (which?) and the
     * specified incrementor type. 
     * @param edge (Some edge?)
     */
    public void incrementMatrix(Edge edge){
        switch(incType){
            case NAIVE:
                NaiveAlgorithm na = new NaiveAlgorithm(network); 
                na.updateDistanceMatrix(edge, this);
                break;
            case TWOPHASE:
                break;
            default:
                break;
        }
    } 

    /**
     * Checks if there are any negative loops in the distance matrix.
     * @return A boolean representing the distance matrix's consistency.  
     */
    public boolean isConsistent(){
        switch(checkType){
            case BELLFORD:
                break;
            case DPC:
                break;
            default:
                break; 
        }
        return true;
    }

    /**
     * Floyd-Warshall algorithm that generates a matrix with best path weights in O(n^3) time.
     * @author Mark Infante
     */
    private void floydWarshall(){
        for (int k = 0; k < this.size(); k++){                      
            for (int i = 0; i < this.size(); i++){              
                for (int j = 0; j< this.size(); j++){
                    if (this.get(i).get(j) > this.get(i).get(k) + this.get(k).get(j)){
                        this.get(i).set(j, (this.get(i).get(k) + this.get(k).get(j)));
                    }
                }
            }
        }
    }

    /* ==== GETTERs and SETTERs ==== */

    public boolean isUpToDate() { return upToDate; }
    public void setUpToDate(boolean bool) { upToDate = bool; }

    public MatrixIncrementorType getIncType() { return incType; }
    public void setIncType(MatrixIncrementorType incrementorType) { incType = incrementorType; }

    public ConsistencyCheckerType getCheckType() { return checkType; }
    public void setCheckType(ConsistencyCheckerType checkerType) { checkType = checkerType; }
}
