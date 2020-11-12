package edu.vassar.trl;

/**
 * Implements the Naive Algorithm for updating the distance matrix
 * @author Ciara O'Donnell
 * @version 1.0
*/
public class NaiveAlgorithm
{
    private STN network;

    /**
     * Create a new instance of the Naive update algorithm
     * @param network The local temporal network
     */
    public NaiveAlgorithm(STN network)
    {
        this.network = network;
    }

    /**
     * Updates the distance matrix using the naive algorithm. 
     * @param edge the edge being added to the network
     * @param dm the distance matrix which will be updated and returned
     */
    public DistanceMatrix updateDistanceMatrix(Edge edge, DistanceMatrix dm)
    {
        int x = edge.getStart();
        int y = edge.getEnd();
        Double w = edge.getWeight();
        network.addEdge(edge);

        for (int u = 0; u < dm.size(); u++){
            for (int v = 0; v < dm.size(); v++){
                Double tempEdgeWeight;
                if (x == u && y == v)
                    tempEdgeWeight = w;
                else if (x == u)
                    tempEdgeWeight = (w + dm.get(y).get(v));
                else if (y == v)
                    tempEdgeWeight = (dm.get(u).get(x) + w);
                else 
                    tempEdgeWeight = (dm.get(u).get(x) + w + dm.get(y).get(v));

                    //if new path cost is less than the cost in the distance matrix, update distance matrix with new cost
                if (tempEdgeWeight < dm.get(u).get(v)) { 
                    dm.get(u).set(v, tempEdgeWeight);
                }
            }
        }
        return dm;
    }
}

