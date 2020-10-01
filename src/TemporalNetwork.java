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
    
    abstract void init();
    abstract void init(boolean wantDistanceMatrix, MatrixGeneratorType generatorType);
    abstract void addNode();
    abstract void addEdge(Edge edge);
    abstract void removeNode();
    abstract void removeEdge(Edge edge);
    abstract Edge getEdge(Integer x, Integer y);
    abstract Integer getSizeEdgesMatrix();  // This doesn't belong here but theres a lot to move around for now, so leave it -mark

}
