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


    public DistanceMatrix twoPhasePropagate(Edge addedEdge){

        // // check if the DM is reliable
        // if(outputMatrix.isUpToDate()){ 
        //     // start working on DM, upToDate = False
        //     outputMatrix.setUpToDate(false);
        // } else {
        //     System.out.println("DM not up to date!");
        //     // DM not reliable, cancel
        //     return null;
        // }

        

        int nodeFrom = addedEdge.getStart(); // x
        int nodeTo = addedEdge.getEnd(); // y 
        double addedEdgeWeight = addedEdge.getWeight(); // delta

        network.addEdge(addedEdge);
        if(addedEdgeWeight<outputMatrix.get(nodeFrom).get(nodeTo)){
            outputMatrix.get(nodeFrom).set(nodeTo, addedEdgeWeight);
        } else {
            outputMatrix.setUpToDate(true);
            return outputMatrix;
        }

        // @Group - Is the DistanceMatrix designed to have negative values on 
        //          backward traversal? If not it should be.
        
        // check if new weight is better than the backwards traversal between these nodes
        if(addedEdgeWeight < -(outputMatrix.get(nodeTo).get(nodeFrom))){
            return null;
        }

        ArrayList<Integer> forwardEncountered = new ArrayList<Integer>();
        ArrayList<Integer> forwardChanged = new ArrayList<Integer>();
        PriorityQueue<Integer> forwardToDoPriorityQueue = new PriorityQueue<Integer>();

        // initialize with nodeTo because this is a forward traversal
        forwardEncountered.add(nodeTo);
        forwardChanged.add(nodeTo);
        forwardToDoPriorityQueue.offer(nodeTo);

        // loop through every node forward of y
        while(!forwardToDoPriorityQueue.isEmpty()){
            
            // pop a node off
            int node = forwardToDoPriorityQueue.poll(); // v
            // loop through successors
            for(Map.Entry<Integer,Double> entry : network.getSuccsOf(node).entrySet()){
                
                int successorIndex = entry.getKey(); // w
                double successorWeight = entry.getValue(); // d
                // ignore node if it has been checked &&
                // given path abc, if (ab + bc) == abc, continue
                if(!forwardEncountered.contains(successorIndex) &&  
                  (outputMatrix.get(nodeTo).get(successorIndex) == // D(y,w) == D(y,v) + d
                  (outputMatrix.get(nodeTo).get(node) + successorWeight))){

                    // record encounter with successor (w)
                    forwardEncountered.add(successorIndex); 
                    
                    // if current path recorded is worse than new path
                double newDistance = addedEdgeWeight + outputMatrix.get(nodeTo).get(successorIndex);
                    if(newDistance < outputMatrix.get(nodeFrom).get(successorIndex)){ // delta + D(y,v) < D(x,w)
                        
                        // get and set new distance, new edge + old stored path
                        
                        outputMatrix.get(nodeFrom).set(successorIndex, newDistance);
                        
                        // record change in successor
                        forwardChanged.add(successorIndex);
                        // propagate forward from successor
                        forwardToDoPriorityQueue.add(successorIndex);
                    }
                }
            }
        }

        for(int i = 0; i < forwardChanged.size(); i++){
            ArrayList<Integer> backwardEncountered = new ArrayList<Integer>();
            PriorityQueue<Integer> backwardToDoPriorityQueue = new PriorityQueue<Integer>();

            // successor node which we propagate back from
            int baseNode = forwardChanged.get(i); // w

            // nodeFrom -> baseNode is known
            backwardEncountered.add(nodeFrom);
            backwardToDoPriorityQueue.offer(nodeFrom);

            // loop back from nodeFrom
            while(!backwardToDoPriorityQueue.isEmpty()){
                // current predecessor
                int thisNode = backwardToDoPriorityQueue.poll(); // f
                
                // loop through thisNode's predecessors
                for(Map.Entry<Integer,Double> entry : network.getPredsOf(thisNode).entrySet()){
                    int predecessorIndex = entry.getKey(); // e
                    double predecessorWeight = entry.getValue(); // a

                    // if predecessor has not been encountered &&
                    // pred -> baseNode == (pred -> thisNode + thisNode -> baseNode)
                    if(!backwardEncountered.contains(predecessorIndex) &&
                      outputMatrix.get(predecessorIndex).get(nodeFrom) == // D(e,x) == a + D(f,x)
                      (predecessorWeight + outputMatrix.get(thisNode).get(nodeFrom))){
                        
                        // record encounter of predecessor
                        backwardEncountered.add(predecessorIndex);
                        
                        // calculate new distance
                        double newDistance = outputMatrix.get(predecessorIndex).get(nodeFrom) + 
                        outputMatrix.get(nodeFrom).get(baseNode); // D(e,x) + D(x,w)
                        
                        // if pred->baseNode + baseNode -> nodeFrom(successor) < pred->successor
                        if(newDistance < outputMatrix.get(predecessorIndex).get(baseNode)){ // D(e,x) + D(x,w) < D(e,w)
                            // set new distance

                            outputMatrix.get(predecessorIndex).set(baseNode, newDistance); // D(e,w) = newDistance
                            // add predecessor onto queue

                            backwardToDoPriorityQueue.offer(baseNode); // add f
                          }
                      }
                }
            }
        }
        // network.setDistanceMatrix(outputMatrix);
        // set distance matrix as up to date
        outputMatrix.setUpToDate(true);
        // return distance matrix
        return outputMatrix;

    }


    /**
     * public void twoPhasePropagate()
     * Called once to run a Two Phase Propagation on the stored STN, loops through every time point's successors
     * and checks their DistanceMatrix values against the edge weights between the two nodes. If it finds an
     * inaccuracy, it calls backPropagate to propagate the correction through the network
     * @deprecated [incorrect implementation]
     * @return outputMatrix, an up-to-date DistanceMatrix of the STN
     * 
     */
    // DistanceMatrix twoPhasePropagate(){
    //     int matrixSize = network.getNumTimePoints();
    //     for(int i = 0; i < matrixSize; i++){                                    
    //         Map<Integer, Double> successorMap = network.getSuccsOf(i);
    //         // check each successor to that node
    //         for(Map.Entry<Integer, Double> entry : successorMap.entrySet()){    
    //             int successorIndex = entry.getKey();
    //             double successorWeight = entry.getValue();
    //             // if weight in distance matrix is larger than weight of edge 
    //             if(outputMatrix.get(i).get(successorIndex) > successorWeight){            
    //                 // set new distance matrix weight from the edge weight
    //                 outputMatrix.get(i).set(successorIndex, successorWeight);   
    //                 // record nodes so we don't create a loop
    //                 ArrayList<Integer> predecessorsChecked = new ArrayList<Integer>();
    //                 // adding a node to this list as we start to backPropagate it
    //                 // avoids an extra layer of function calls     
    //                 predecessorsChecked.add(i);
    //                 // then recursively backPropagate any changed nodes
    //                 backPropagate(i, successorIndex, predecessorsChecked);
    //                 // now we have checked a single successor node and ensured 
    //                 // every one of its predecessors is up to date
    //             }
    //         }
    //     }
    // }


    /**
     * public void backPropagate(int nodeFrom, int nodeTo, ArrayList<Integer> predecessorsChecked)
     * Called for every predecessor, checks the current recorded distance between
     * nodeFrom and nodeTo and compared that to the sum of the edges connecting them.
     * The smaller value is set as the new distance. 
     * @deprecated [incorrect implementation]
     * @param nodeFrom The predecessor node's integer index whose weight is being corrected
     * @param nodeTo The original successor's integer index passed down by forwardPropagate
     * @param oredecessorsChecked An ArrayList of Integers which records nodes that have been backPropagated.
     *                            Each backPropagate "thread" has its own list to account for the possibility
     *                            of reaching a node from two different routes.
     */
    // public void backPropagate(int nodeFrom, int nodeTo, ArrayList<Integer> predecessorsChecked){
    //     Map<Integer, Double> predecessorMap = network.getPredsOf(nodeFrom);
    //     // loop through every predecessor
    //     for(Map.Entry<Integer, Double> entry : predecessorMap.entrySet()){ 
    //         int predecessorIndex = entry.getKey();
    //         double predecessorWeight = entry.getValue();
    //         // get the ideal weight of (i --> nodeTo), 
    //         // which is: (i --> nodeFrom) + (nodeFrom --> nodeTo)
    //         // The second value in the equation is accurate due to having 
    //         // just been checked in the last twoPhasePropagate/backPropagate function call
    //         double idealWeight = predecessorWeight + outputMatrix.get(nodeFrom).get(nodeTo); 
    //         // check if weight is worse than ideal for this path
    //         if(outputMatrix.get(predecessorIndex).get(nodeTo) > idealWeight){ 
    //             // set the weight to the better weight
    //             outputMatrix.get(predecessorIndex).set(nodeTo, idealWeight);
    //             // if the next predecessor has not been backPropagated yet
    //             if(!predecessorsChecked.contains(predecessorIndex)){
    //                 // mark that the next predecessor has been checked
    //                 // adding a node to this list as we start to backPropagate it
    //                 // avoids an extra layer of function calls  
    //                 predecessorsChecked.add(predecessorIndex); 
    //                 // recursively backPropagate 
    //                 backPropagate(predecessorIndex, nodeTo, predecessorsChecked);
    //             } else {
    //                 // let me know that this is working and not 
    //                 // causing infinite loops in some corner case
    //                 System.out.println("This Network has a loop, backPropagation stopped at node " + predecessorIndex);
    //             }
    //         }
    //     }
    // }
}
