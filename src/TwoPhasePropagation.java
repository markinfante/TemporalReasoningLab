package src;

import java.util.*;

public class TwoPhasePropagation {
    private STN network;
    private DistanceMatrix outputMatrix;

    /**
     * public TwoPhasePropagation(STN stn)
     * Initializes the input STN in the class and copies the STN's current DistanceMatrix to outputMatrix.
     * This class' functions will manipulate and return a corrected version of the STN's DistanceMatrix using 
     * this copy.
     * @param stn A Simple Temporal Network whose data is saved in private class variables
     * 
     */
    public TwoPhasePropagation(STN stn){
        network = stn;
        outputMatrix = network.getDistanceMatrix();
    }

    /**
     * public void propagate()
     * Called once to run a Two Phase Propagation on the stored STN, loops through every time point's successors
     * and checks their DistanceMatrix values against the edge weights between the two nodes. If it finds an
     * inaccuracy, it calls bacPropagate to propagate the correction through the network
     * @return outputMatrix, an up-to-date DistanceMatrix of the STN
     * 
     */
    public DistanceMatrix propagate(){
        int matrixSize = network.getNumTimePoints();
        // loop through every node
        for(int i = 0; i < matrixSize; i++){                                    
            Map<Integer, Double> successorMap = network.getSuccsOf(i);
            // check each successor to that node
            for(Map.Entry<Integer, Double> entry : successorMap.entrySet()){    
                int successorIndex = entry.getKey();
                double successorWeight = entry.getValue();
                // if weight in distance matrix is larger than weight of edge 
                if(outputMatrix.get(i).get(successorIndex) > successorWeight){            
                    // set new distance matrix weight from the edge weight
                    outputMatrix.get(i).set(successorIndex, successorWeight);   
                    // record nodes so we don't create a loop
                    ArrayList<Integer> predecessorsChecked = new ArrayList<Integer>();
                    // adding a node to this list as we start to backPropagate it
                    // avoids an extra layer of function calls     
                    predecessorsChecked.add(i);
                    // then recursively back propagate any changed nodes
                    backPropagate(i, successorIndex, predecessorsChecked);
                    // now we have checked a single successor node and ensured 
                    // every predecessor is up to date
                }
            }
        }

        return outputMatrix;
    }

    /**
     * public void backPropagate(int nodeFrom, int nodeTo, ArrayList<Integer> predecessorsChecked)
     * Called for every predecessor, checks the current recorded distance between
     * nodeFrom and nodeTo and compared that to the sum of the edges connecting them.
     * The smaller value is set as the new distance. 
     * @param nodeFrom The predecessor node's integer index whose weight is being corrected
     * @param nodeTo The original successor's integer index passed down by forwardPropagate
     * @param oredecessorsChecked An ArrayList of Integers which records nodes that have been backPropagated.
     *                            Each backPropagate "thread" has its own list to account for the possibility
     *                            of reaching a node from two different routes.
     */
    public void backPropagate(int nodeFrom, int nodeTo, ArrayList<Integer> predecessorsChecked){
        Map<Integer, Double> predecessorMap = network.getPredsOf(nodeFrom);
        // loop through every predecessors
        for(Map.Entry<Integer, Double> entry : predecessorMap.entrySet()){ 
            int predecessorIndex = entry.getKey();
            double predecessorWeight = entry.getValue();
            // get the ideal weight of (i --> nodeTo), 
            // which is: (i --> nodeFrom) + (nodeFrom --> nodeTo)
            // The second value in the equation is accurate due to having 
            // just being checked in the last function call
            double idealWeight = predecessorWeight + outputMatrix.get(nodeFrom).get(nodeTo); 
            // check if weight is worse than ideal for this path
            if(outputMatrix.get(predecessorIndex).get(nodeTo) > idealWeight){ 
                // set the weight to the better weight
                outputMatrix.get(predecessorIndex).set(nodeTo, idealWeight);
                // if the next predecessor has not been backPropagated yet
                if(!predecessorsChecked.contains(predecessorIndex)){
                    // mark that the next predecessor has been checked
                    // adding a node to this list as we start to backPropagate it
                    // avoids an extra layer of function calls  
                    predecessorsChecked.add(predecessorIndex); 
                    // recursively back propagate 
                    backPropagate(predecessorIndex, nodeTo, predecessorsChecked);
                } else {
                    // let me know that this is working and not 
                    // causing infinite loops in some corner case
                    System.out.println("This Network has a loop, backPropagation stopped at node " +  predecessorIndex);
                }
            }
        }

    }


}
