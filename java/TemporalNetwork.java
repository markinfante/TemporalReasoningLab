package java;

/* TemporalNetwork.java
 * Author: Mark Infante
 *      Abstract class for temporal networks. 
 *      
*/

public abstract class TemporalNetwork{
    TemporalNetworks networkType; 

    public TemporalNetwork(){}

    public void setNetType(TemporalNetworks networkType){
        this.networkType = networkType;
    }

    public TemporalNetworks getNetType(){
        return networkType;
    }

    abstract void addNode();
    abstract void addEdge(Successor successor);
    abstract void removeNode();
    abstract void removeEdge(Successor successor);
    abstract void addSuccessor(Successor successor);

}