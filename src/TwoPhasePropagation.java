package src;

import java.util.ArrayList;

public class TwoPhasePropagation {
    private TemporalNetwork network;
    private DistanceMatrix distanceMatrix;

    public TwoPhasePropagation(TemporalNetwork TN){
        network = TN;
        Edge tEdge = null;  // temp edge
        distanceMatrix = new DistanceMatrix(network);

        // Copied from Mark's code in FloydWarshall, initializes distance matrix
        this.network = network;
        for (int i = 0; i < network.getSizeEdgesMatrix(); i++){
            distanceMatrix.add(new ArrayList<Double>());  // Add lists for every edge
            for (int j = 0; j < network.getNumTimePoints(); j++){
                tEdge = network.getEdge(i, j);
                if (tEdge != null){
                    distanceMatrix.get(i).add(tEdge.getWeight());
                } else {
                    distanceMatrix.get(i).add(Double.POSITIVE_INFINITY);
                }
            }
        }
        network.setDistanceMatrix(distanceMatrix);
    }

    public DistanceMatrix forwardPropagate(){
        int matrixSize = distanceMatrix.size();
        for(int i = 0; i < matrixSize; i++){ // loop through every node
            for(int j = 0; j < matrixSize; j++){ // check each connection
                if(network.getEdge(i,j).getWeight() > distanceMatrix.get(i).get(j)){ // if weight update is needed
                    // set new edge weight from the distance matrix
                    network.getEdge(i,j).setWeight(distanceMatrix.get(i).get(j)); // TODO: THIS IS VERY WRONG
                    // then recursively back propagate any changed nodes
                    backPropagate(i,j); // i is nodeFrom, j is nodeTo

                    // now we have checked a single successor node and ensured every predecessor is up to date
                }
            }
        }
        return null;
    }

    public void backPropagate(int nodeFrom, int nodeTo){ // the edge that was just changed lead from nodeFrom to nodeTo
        for(int i = 0; i < distanceMatrix.size(); i++){
            if(distanceMatrix.get(i).get(nodeFrom) != Double.POSITIVE_INFINITY){ // if we find a predecessor
                // get the ideal weight (i --> nodeFrom) + (nodeFrom --> nodeTo)
                double idealWeight = network.getEdge(i, nodeFrom).getWeight() + network.getEdge(nodeFrom, nodeTo).getWeight();
                if(network.getEdge(i, nodeTo).getWeight() > idealWeight){
                    // set the weight to the better weight
                    network.getEdge(i, nodeTo).setWeight(idealWeight);
                    // recursively back propagate
                    backPropagate(i, nodeTo);
                }
            }
        }

    }


}
