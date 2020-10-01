package src;

import java.util.List;

/** 
 * Abstract class for temporal networks. 
 * @author Mark Infante
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
    
    public abstract void init();
    public abstract void init(boolean wantDistanceMatrix, MatrixGeneratorType generatorType);
    public abstract void addNode();
    public abstract void addEdge(Edge edge);
    public abstract void removeNode();
    public abstract void removeEdge(Edge edge);
    public abstract Edge getEdge(Integer x, Integer y);
    public abstract Integer getSizeEdgesMatrix();  // This doesn't belong here but theres a lot to move around for now, so leave it -mark
    public abstract String toString();
}
