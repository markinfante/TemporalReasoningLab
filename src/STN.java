package src;
import java.util.*;

/**
 * Allows for creation of directed, weighted graph.
 * Extends TemporalNetwork.
 * @author Mark Infante
*/

public class STN extends TemporalNetwork{
    
    private List<Map<Integer, Edge>> successors;   // A 2D vector that holds information about a node's successors.
    private List<Integer> numSuccessors;            // Number of successor nodes for corresponding node index
    private List<ArrayList <Edge>> edgesMatrix;        // A 2D matrix of edge weights for corresponding node index pair
    private List<Map<Integer, Edge>> predecessors;  // A 2D vector that holds information about a node's preds.

    // Default constructor for simple temporal network
    public STN(){
        setNetType(TemporalNetworks.STN);
        numSuccessors = new ArrayList<Integer>();
        successors = new ArrayList<Map<Integer, Edge>>(); //iniitialize successors as a Vector
        edgesMatrix = new ArrayList<ArrayList<Edge>>();
    }
    
    @Override
    public void init(){
        int tps = super.getNumTimePoints(); //populate the spaces in the vector, based on the number of timepoints
        for (int i = 0; i < tps; i++) {
            successors.add(i, new HashMap<Integer, Edge>()); 
            numSuccessors.add(0);
            edgesMatrix.add(i, new ArrayList<Edge>());
            for (int k = 0; k < tps; k++)
            {
                edgesMatrix.get(i).add(null);
            }
        }   
    }

    @Override
    public void init(boolean wantDistanceMatrix, MatrixGeneratorType generatorType){
        init();
        if (wantDistanceMatrix == false) return;
        switch (generatorType) {
            case FWARSHALL:
                FloydWarshall fw = new FloydWarshall(this);
                this.setDistanceMatrix(fw.generateMatrix());
                break;
            case JOHNSONS:
                break;
            default:
                break;
        }

    }

    @Override 
    // @params An existing STN s and an Edge object edge consisting of a source, destination, and a weight.
    public void addEdge(Edge edge){ 
        // TODO: test
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        Double d = edge.getWeight();
        Edge e = successors.get(x).get(y);
        
        if (e == null){ //if an edge doesn't exist in successors, input the given edge argument. also increment numsuccessors, since we are adding a new edge
            successors.get(x).put(y, edge);
            numSuccessors.set(x, numSuccessors.get(x)+1);
            edgesMatrix.get(x).add(y, edge);
        } else if (e.getWeight() > d){ //else if new edge gives a shorter path from x->y than the old edge, replace the old edge with the new one
            successors.get(x).put(y, edge);
            edgesMatrix.get(x).set(y, edge);
        }
    }

    @Override
    public void removeEdge(Edge edge){
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        
        successors.get(x).put(y, null);
        numSuccessors.set(x, numSuccessors.get(x)-1);
        edgesMatrix.get(x).set(y, null);
        
        return;
    }
    
    @Override
    public Edge getEdge(Integer src, Integer dest){
        return successors.get(src).get(dest);
    }

    @Override
    void addNode() {
        // TODO: all
        return;
    }

    @Override
    void removeNode() {
        // TODO Auto-generated method stub
        return;

    }

    @Override
    public Integer getSizeEdgesMatrix(){
        return edgesMatrix.size();
    }
    
    public String toString(){
        // TODO: test
        return successors + "";
    }
}