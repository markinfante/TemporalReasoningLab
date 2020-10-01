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
    
    public void updateDistanceMatrix(Edge e, DistanceMatrix dm)
    {
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        Double ew = edge.getWeight();
        network.addEdge(e) //add edge to network graph

        for (int u = 0; u < dm.size(); u++){
            for (int v = 0; v < dm.size(); v++){
                Integer tempEdge = (network.getEdge(u,x) + network.getEdge(x,y) + network.getEdge(y,v))
                //if new path cost is less than the cost in the distance matrix, update distance matrix with new cost
                if (tempEdge < dm.get(u).get(v)) { 
                    dm.get(u).set(v, tempEdge);
                }
            }
        }
    }
}
