package src;

import java.util.List;

/** 
 * Abstract class for temporal networks. 
 * @author Mark Infante
 * @author Ciara O'Donnell
*/
public abstract class TemporalNetwork{
    
    private TemporalNetworks networkType; 
    private List<String> timePointNames;
    private Integer numTimePoints;
    private DistanceMatrix distanceMatrix;

    public TemporalNetwork(){}

    public void setNetType(TemporalNetworks networkType){
        this.networkType = networkType;
    }

    public TemporalNetworks getNetType(){
        return networkType;
    }

    public void setTimePointNames(List<String> timePointNames){
        this.timePointNames = timePointNames;
    }

    public Integer getNumTimePoints(){
        return numTimePoints;
    }

    public void setNumTimePoints(Integer numTimePoints){
        this.numTimePoints = numTimePoints;
    }

    public List<String> getTimePointNames(){
        return timePointNames;
    }

    public boolean hasDistanceMatrix(){
        return distanceMatrix == null;
    }

    public void setDistanceMatrix(DistanceMatrix dm){
        distanceMatrix = dm;
    }

    public DistanceMatrix getDistanceMatrix(){
        return distanceMatrix;
    }
    
    /**
     * Initializes a new temporal network by creating necessary, unfilled matrices. 
     */
    public abstract void init();

    /**
     * Adds a node to the network.
     */
    public abstract void addNode();

    /**
     * Adds an edge to the network.
     * @param edge The edge to be added.
     */
    public abstract void addEdge(Edge edge);

    /**
     * Removes a node from the network.
     */
    public abstract void removeNode();

    /**
     * Removes an edge from the network.
     * @param edge The edge to be removed.
     */
    public abstract void removeEdge(Edge edge);

    /**
     * Gets an edge from the network.
     * @param x The start node value.
     * @param y The end node value
     * @return An edge.
     */
    public abstract Edge getEdge(Integer x, Integer y);

    /**
     * Returns the length of an NxN matrix.
     * @return An integer N.
     */
    public abstract Integer getSizeEdgesMatrix();  // This doesn't belong here but theres a lot to move around for now, so leave it -mark

    /**
     * Converts and returns the network to a tab-dilineated string representing a matrix. 
     * @return A string representation of the network.
     */
    public abstract String toString();
}
