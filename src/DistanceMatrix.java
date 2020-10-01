package src;

import java.util.ArrayList;

public class DistanceMatrix extends ArrayList<ArrayList<Double>>{
    
    private static final long serialVersionUID = 1L;
    private boolean upToDate; 
    private MatrixGeneratorType genType;
    private MatrixIncrementorType incType;
    private ConsistencyCheckerType checkType;
    private TemporalNetwork network;

    public DistanceMatrix(MatrixGeneratorType generatorType, TemporalNetwork network){
        upToDate = false; 
        genType = generatorType;
        incType = null;
        checkType = null;
        this.network = network;
        makeCleanMatrix();
    }

    public DistanceMatrix(MatrixGeneratorType generatorType, TemporalNetwork network, MatrixIncrementorType incrementorType){
        upToDate = false; 
        genType = generatorType;
        incType = incrementorType;
        checkType = null;
        this.network = network;
        makeCleanMatrix();
    }

    public DistanceMatrix(MatrixGeneratorType generatorType, TemporalNetwork network, MatrixIncrementorType incrementorType, 
                            ConsistencyCheckerType consistencyCheckerType){
        upToDate = false; 
        genType = generatorType;
        incType = incrementorType;
        checkType = consistencyCheckerType;
        this.network = network;
        makeCleanMatrix();
    }
    
    private void makeCleanMatrix(){
        Edge tEdge = null;

        for (int i = 0; i < network.getSizeEdgesMatrix(); i++){
            this.add(new ArrayList<Double>());  // Add lists for every edge 
            for (int j = 0; j < network.getSizeEdgesMatrix(); j++){
                tEdge = network.getEdge(i, j);
                if (tEdge != null){
                    this.get(i).add(tEdge.getWeight());
                } else {
                    this.get(i).add(Double.POSITIVE_INFINITY);  
                }
            }
        }
    }

    public void generateMatrix(){
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

    public boolean isUpToDate() { return upToDate; }
    public void setUpToDate(boolean bool) { upToDate = bool; }

    public MatrixIncrementorType getIncType() { return incType; }
    public void setIncType(MatrixIncrementorType incrementorType) { incType = incrementorType; }

    public ConsistencyCheckerType getCheckType() { return checkType; }
    public void setCheckType(ConsistencyCheckerType checkerType) { checkType = checkerType; }
}
