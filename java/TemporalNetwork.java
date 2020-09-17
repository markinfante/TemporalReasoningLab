package java;

import java.util.List;

/* TemporalNetwork.java
 * Author: Mark Infante
 *      Abstract class for temporal networks. 
 *      
*/

public abstract class TemporalNetwork{
    TemporalNetworks networkType; 
    List<String> timePointNames;
    Integer numTimePoints;

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

    abstract void addNode();
    abstract void addEdge(Edge edge);
    abstract void removeNode();
    abstract void removeEdge(Edge edge);

}
