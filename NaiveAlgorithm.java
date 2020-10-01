public class NaiveAlgorithm
{
    private TemporalNetwork network;

    public NaiveAlgorithm()
    {
        this.network = network;
    }
    
    public void updateDistanceMatrix(Edge e, DistanceMatrix dm)
    {
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        Double ew = edge.getWeight();
        network.addEdge(e) // is this necessary??

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