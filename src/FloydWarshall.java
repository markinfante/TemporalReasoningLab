package src;

import java.util.ArrayList;

/**
 * Implementation of Floyd-Warshall algorithm for generating a new distance matrix from a network.
 * @author Mark Infante
 */
public class FloydWarshall {

    private DistanceMatrix outputMatrix;

    /**
     * Creates a new instance of the algorithm and intitializes output matrix.
     * @param network The local temporal network 
     * @deprecated
     */
    @Deprecated
    public FloydWarshall(TemporalNetwork network){
        Edge tEdge = null;  // temp edge

        for (int i = 0; i < network.getSizeEdgesMatrix(); i++){
            outputMatrix.add(new ArrayList<Double>());  // Add lists for every edge 
            for (int j = 0; j < network.getSizeEdgesMatrix(); j++){
                tEdge = network.getEdge(i, j);
                if (tEdge != null){
                    outputMatrix.get(i).add(tEdge.getWeight());
                } else {
                    outputMatrix.get(i).add(Double.POSITIVE_INFINITY);  
                }
            }
        }
    }

    public DistanceMatrix generateMatrix(){                                     
        for (int k = 0; k < outputMatrix.size(); k++){                      
            for (int i = 0; i < outputMatrix.size(); i++){              
                for (int j = 0; j< outputMatrix.size(); j++){
                    if (outputMatrix.get(i).get(j) > outputMatrix.get(i).get(k) + outputMatrix.get(k).get(j)){
                        outputMatrix.get(i).set(j, (outputMatrix.get(i).get(k) + outputMatrix.get(k).get(j)));
                    }
                }
            }
        }
        return outputMatrix;
    }
    
}
