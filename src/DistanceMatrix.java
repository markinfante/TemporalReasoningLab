package src;

import java.util.ArrayList;

public class DistanceMatrix extends ArrayList<ArrayList<Double>>{
    
    private static final long serialVersionUID = 1L;
    private boolean upToDate; 
    private MatrixIncrementorType incType;
    private ConsistencyCheckerType checkType;
    private TemporalNetwork network;

    public DistanceMatrix(TemporalNetwork network){
        upToDate = false; 
        incType = null;
        checkType = null;
        this.network = network;
    }

    public DistanceMatrix(TemporalNetwork network, MatrixIncrementorType incrementorType){
        upToDate = false; 
        incType = incrementorType;
        checkType = null;
        this.network = network;
    }

    public DistanceMatrix(TemporalNetwork network, MatrixIncrementorType incrementorType, 
                            ConsistencyCheckerType consistencyCheckerType){
        upToDate = false; 
        incType = incrementorType;
        checkType = consistencyCheckerType;
        this.network = network;
    }

    public void incrementMatrix(Edge edge){
        switch(incType){
            case NAIVE:
                NaiveAlgorithm na = new NaiveAlgorithm(tn); 
                na.updateDistanceMatrix(edge, this);
                break;
            case TWOPHASE:
                break;
            default:
                break;
        }
    } 

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

    public boolean isUpToDate() { return upToDate; }
    public void setUpToDate(boolean bool) { upToDate = bool; }

    public MatrixIncrementorType getIncType() { return incType; }
    public void setIncType(MatrixIncrementorType incrementorType) { incType = incrementorType; }

    public ConsistencyCheckerType getCheckType() { return checkType; }
    public void setCheckType(ConsistencyCheckerType checkerType) { checkType = checkerType; }
}
