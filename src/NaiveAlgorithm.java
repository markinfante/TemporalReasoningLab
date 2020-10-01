package src;
/**
 * Implements the Naive Algorithm for updating the distance matrix
 * @author Ciara O'Donnell
 * @version 1.0
*/


public class NaiveAlgorithm
{
    private TemporalNetwork network;

    public NaiveAlgorithm(TemporalNetwork network)
    {
        this.network = network;
    }
    
    public void updateDistanceMatrix(Edge edge, DistanceMatrix dm)
    {
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        network.addEdge(edge); //add edge to network graph

        for (int u = 0; u < dm.size(); u++){
            for (int v = 0; v < dm.size(); v++){
                Double tempEdgeWeight = (network.getEdge(u,x).getWeight() + network.getEdge(x,y).getWeight() + network.getEdge(y,v).getWeight());
                //if new path cost is less than the cost in the distance matrix, update distance matrix with new cost
                if (tempEdgeWeight < dm.get(u).get(v)) { 
                    dm.get(u).set(v, tempEdgeWeight);
                }
            }
        }
    }
}
