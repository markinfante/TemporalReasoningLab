package src;

public class NaiveAlgorithm
{
    private TemporalNetwork network;

    public NaiveAlgorithm(TemporalNetwork network)
    {
        this.network = network;
    }
    
    public void updateDistanceMatrix(Edge e, DistanceMatrix dm)
    {
        Integer x = e.getStart();
        Integer y = e.getEnd();
        Double ew = e.getWeight();
        network.addEdge(e); //add edge to network graph

        for (int u = 0; u < dm.size(); u++){
            for (int v = 0; v < dm.size(); v++){
                double tempEdge = (network.getEdge(u,x).getWeight() + network.getEdge(x,y).getWeight() + network.getEdge(y,v).getWeight());
                //if new path cost is less than the cost in the distance matrix, update distance matrix with new cost
                if (tempEdge < dm.get(u).get(v)) { 
                    dm.get(u).set(v, tempEdge);
                }
            }
        }
    }
}   