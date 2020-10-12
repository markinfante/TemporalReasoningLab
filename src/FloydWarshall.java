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
     */
    public FloydWarshall(STN network){
        Edge tEdge = null;  // temp edge
        outputMatrix = new DistanceMatrix();
        outputMatrix.makeCleanMatrixFromSTN(network);
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
