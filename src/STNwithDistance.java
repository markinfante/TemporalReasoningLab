import java.util.*;

/**
 * Allows for creation of directed, weighted graph.
 * Extends TemporalNetwork.
 * @author Mark Infante
 * @author Ciara O'Donnell
*/

public class STNWithDistance extends STN{
    
    private List<Map<Integer, Edge>> successors;   // A 2D vector that holds information about a node's successors.
    private List<Integer> numSuccessors;            // Number of successor nodes for corresponding node index
    private List<ArrayList <Edge>> edgesMatrix;        // A 2D matrix of edge weights for corresponding node index pair
    private List<Map<Integer, Edge>> predecessors;  // A 2D vector that holds information about a node's preds.
    private DistanceMatrix distanceMatrix;    //A 2D matrix that holds the shortests distances between each pair of nodes
    private TemporalNetwork network;                 

    /**
     * Default constructor. Every matrix needs a reference to its network and a means to generate.
     * @param generatorType
     * @param network
     */
    public STNWithDistance(TemporalNetwork network, MatrixGeneratorType generatorType){
        setNetType(TemporalNetworks.STN);
        numSuccessors = new ArrayList<Integer>();
        successors = new ArrayList<Map<Integer, Edge>>(); //iniitialize successors as a Vector
        edgesMatrix = new ArrayList<ArrayList<Edge>>();
        this.network = network;
        distanceMatrix = new DistanceMatrix(generatorType, this.network);
    }

    /**
     * Overloaded constructor. Used when the user specifies an incrementor type 
     * @param generatorType
     * @param network
     * @param incrementorType
     */
    public STNWithDistance(TemporalNetwork network, MatrixGeneratorType generatorType, 
                            MatrixIncrementorType incrementorType){
        setNetType(TemporalNetworks.STN);
        numSuccessors = new ArrayList<Integer>();
        successors = new ArrayList<Map<Integer, Edge>>(); //iniitialize successors as a Vector
        edgesMatrix = new ArrayList<ArrayList<Edge>>();
        this.network = network;
        distanceMatrix = new DistanceMatrix(generatorType, this.network, incrementorType);
    }
    
    /**
     * Overloaded constructor. Used when the user specifies an incrementor type and a checker type
     * @param generatorType
     * @param network
     * @param incrementorType
     * @param checkerType
     */
    public STNWithDistance(TemporalNetwork network, MatrixGeneratorType generatorType, 
                            MatrixIncrementorType incrementorType, ConsistencyCheckerType checkerType){
        setNetType(TemporalNetworks.STN);
        this.network = network;
        numSuccessors = new ArrayList<Integer>();
        successors = new ArrayList<Map<Integer, Edge>>(); //iniitialize successors as a Vector
        edgesMatrix = new ArrayList<ArrayList<Edge>>();
        distanceMatrix = new DistanceMatrix(generatorType, this.network, incrementorType, checkerType);
    }
    
    @Override
    /**
     * Overridden from the superclass. Used to initialize the spaces in the successors matrix, 
     * predecessors matrix, edges matrix, and numSuccessors based on the number of timepoints
     * */
    public void init(){
        int tps = getSizeEdgesMatrix(); 
        for (int i = 0; i < tps; i++) {
            successors.add(i, new HashMap<Integer, Edge>()); 
            predecessors.add(i, new HashMap<Integer, Edge>()); 
            numSuccessors.add(0);
            edgesMatrix.add(i, new ArrayList<Edge>());
            for (int k = 0; k < tps; k++)
            {
                edgesMatrix.get(i).add(null);
            }
        }   
    }
    
    /**
     * Overloaded from the superclass. Used to add an edge to the STN, while also optionally updating the 
     * successors and predecessors matrices. 
     * @param edge The edge to add
     * @param updateSuccessors A flag indicating whether or not the user wants to update the successor matrix
     * @param updatePredecessors A flag indicating whether or not the user wants to update the predecessors matrix
     * */    
    public void addEdge(Edge edge, boolean updateSuccessors, boolean updatePredecessors){ 
        // TODO: test
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        Double d = edge.getWeight();
        Edge e = successors.get(x).get(y);
        
        if (e == null){ //if an edge doesn't exist in successors, input the given edge argument. also increment numsuccessors, since we are adding a new edge
            if (updateSuccessors){
                successors.get(x).put(y, e);
                numSuccessors.set(x, numSuccessors.get(x)+1);
            }
            else if (updatePredecessors)
                predecessors.get(y).put(x, edge);
                
            edgesMatrix.get(x).add(y, edge);
        } else if (e.getWeight() > d){ //else if new edge gives a shorter path from x->y than the old edge, replace the old edge with the new one
            if (updateSuccessors){
                successors.get(x).put(y, edge);
            }
            else if (updatePredecessors)
                predecessors.get(y).put(x, edge);
                
            edgesMatrix.get(x).set(y, edge);
        }
    }

    /**
     * Overloaded from the superclass. Used to remove an edge to the STN, while also optionally updating the 
     * successors and predecessors matrices. 
     * @param edge The edge to remove
     * @param updateSuccessors A flag indicating whether or not the user wants to update the successor matrix
     * @param updatePredecessors A flag indicating whether or not the user wants to update the predecessors matrix
     * */  
    public void removeEdge(Edge edge, boolean updateSuccessors, boolean updatePredecessors){
        Integer x = edge.getStart();
        Integer y = edge.getEnd();
        
        if (updateSuccessors){
            successors.get(x).put(y, null);
            numSuccessors.set(x, numSuccessors.get(x)-1);
        }
        else if (updatePredecessors)
            predecessors.get(y).put(x, null);

        edgesMatrix.get(x).set(y, null);
        
        return;
    }
    
    @Override
    /**
     * Overridden from the superclass. Used to retrieve an edge of the STN
     * @param src The source node of the edge
     * @param dest The destination node of the edge
     * 
     * */  
    public Edge getEdge(Integer src, Integer dest){
        return edgesMatrix.get(src).get(dest);
    }
    
    public void createDistanceMatrix()
    {
        return;
    }

    @Override
    public void addNode() {
        // TODO: all
        return;
    }

    @Override
    public void removeNode() {
        // TODO Auto-generated method stub
        return;

    }

    @Override
    public Integer getSizeEdgesMatrix(){
        return edgesMatrix.size();
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}