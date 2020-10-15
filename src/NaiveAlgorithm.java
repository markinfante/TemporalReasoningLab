package src;
/**
 * Implements the Naive Algorithm for updating the distance matrix
 * @author Ciara O'Donnell
 * @version 1.0
*/


public class NaiveAlgorithm
{
    private STN network;
    private DistanceMatrix dm;

    public NaiveAlgorithm(STN network)
    {
        this.network = network;
    }
    
    public DistanceMatrix updateDistanceMatrix(Edge edge, DistanceMatrix dm)
    {
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        Double w = edge.getWeight();
        network.addEdge(edge);

        for (int u = 0; u < dm.size(); u++){
            for (int v = 0; v < dm.size(); v++){
                Double tempEdgeWeight = (dm.get(u).get(x) + w + dm.get(y).get(v));
                //if new path cost is less than the cost in the distance matrix, update distance matrix with new cost
                if (tempEdgeWeight < dm.get(u).get(v)) { 
                    dm.get(u).set(v, tempEdgeWeight);
                }
            }
        }
        return dm;
    }
}

