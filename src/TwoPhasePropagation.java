package src;

// import java.util.ArrayList;

public class TwoPhasePropagation {
    private STNWithDistance networkWithDistance;
    private DistanceMatrix outputMatrix;

    public TwoPhasePropagation(TemporalNetwork TN){
        networkWithDistance = new STNWithDistance(TN, MatrixGeneratorType.JOHNSONS, MatrixIncrementorType.TWOPHASE);
        
        // outputMatrix = networkWithDistance.getDistanceMatrix();
        // Edge tEdge;  // temp edge
        outputMatrix = networkWithDistance.getDistanceMatrix();

        // Copied from Mark's code in FloydWarshall, initializes distance matrix 
        // for (int i = 0; i < networkWithDistance.getNumTimePoints(); i++){
        //     outputMatrix.add(new ArrayList<Double>());  // Add list of connection weights for every time point
        //     for (int j = 0; j < networkWithDistance.getNumTimePoints(); j++){ // check every other point for a connection
        //         double nodeDistance = dm.get(i).get(j);
        //         outputMatrix.get(i).add(nodeDistance);
        //     }
        // }
    }

    public DistanceMatrix forwardPropagate(){
        int matrixSize = networkWithDistance.getNumTimePoints();
        for(int i = 0; i < matrixSize; i++){ // loop through every time point
            for(int j = 0; j < matrixSize; j++){ // check each connection to that point
                if(outputMatrix.get(i).get(j) > networkWithDistance.getEdge(i, j).getWeight()){ // if weight of this connection less than the saved weight
                    // set new distance matrix weight from the edge weight
                    outputMatrix.get(i).set(j, networkWithDistance.getEdge(i, j).getWeight());
                    // then recursively back propagate any changed nodes
                    backPropagate(i,j); // i is nodeFrom, j is nodeTo

                    // now we have checked a single successor node and ensured every predecessor is up to date
                }
            }
        }
        return null;
    }

    public void backPropagate(int nodeFrom, int nodeTo){ // the edge that was just changed lead from nodeFrom to nodeTo
        for(int i = 0; i < outputMatrix.size(); i++){ // checking every node for predecessors
            if(outputMatrix.get(i).get(nodeFrom) != Double.POSITIVE_INFINITY){ // if we find a predecessor i->nodeFrom->nodeTo
                // get the ideal weight of (i --> nodeTo), which is: (i --> nodeFrom) + (nodeFrom --> nodeTo)
                double idealWeight = networkWithDistance.getEdge(i, nodeFrom).getWeight() + networkWithDistance.getEdge(nodeFrom, nodeTo).getWeight();
                if(outputMatrix.get(i).get(nodeTo) > idealWeight){
                    // set the weight to the better weight
                    outputMatrix.get(i).set(nodeTo, idealWeight);
                    // recursively back propagate
                    backPropagate(i, nodeTo);
                }
            }
        }

    }


}
