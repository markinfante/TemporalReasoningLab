package src;

import java.util.ArrayList;

public class DistanceMatrix extends ArrayList<ArrayList<Double>>{
    
    private static final long serialVersionUID = 1L;
    private boolean upToDate; 
    private MatrixIncrementorType incType;
    private ConsistencyCheckerType checkType;

    public DistanceMatrix(){
        upToDate = false; 
        incType = null;
        checkType = null;
    }

    public DistanceMatrix(MatrixIncrementorType incrementorType){
        upToDate = false; 
        incType = incrementorType;
        checkType = null;
    }

    public DistanceMatrix(MatrixIncrementorType incrementorType, 
                            ConsistencyCheckerType consistencyCheckerType){
        upToDate = false; 
        incType = incrementorType;
        checkType = consistencyCheckerType;
    }

    public void incrementMatrix(){
        switch(incType){
            case NAIVE:
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
