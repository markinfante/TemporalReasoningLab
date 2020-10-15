package src;

import java.util.ArrayList;

/**
 * Implementation of Floyd-Warshall algorithm for generating a new distance matrix from a network.
 * @author Mark Infante
 */
public class FloydWarshall {

    private STN network; // STN to generate distance matrix

    /**
     * Creates a new instance of the Floyd-Warshall algorithm.
     * @param network The local temporal network 
     */
    public FloydWarshall(STN network){
        this.network = network;
    }

    /**
     * Generates a distance matrix for this instance of Floyd Warshall.
     * @return A DistanceMatrix.
     */
    public DistanceMatrix generateMatrix(){     
        DistanceMatrix outputMatrix = new DistanceMatrix();

        outputMatrix.makeCleanMatrixFromSTN(network); // initialize matrix

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
