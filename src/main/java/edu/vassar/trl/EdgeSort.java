package edu.vassar.trl;
import java.util.Comparator;
/**
 * A custom comparator for Edges. 
 * 
 * @author Ciara O'Donnell
 * @version 1.0
*/

public class EdgeSort implements Comparator<Edge> {
    /***
     * A custom comparator for Edges. This method compares the weight of the first edge to
     * the weight of the second edge.
     * 
     * @param e1 The first edge
     * @param e2 The second edge
     */
    @Override
    public int compare(Edge e1, Edge e2) {
        if (e1.getWeight()  < e2.getWeight())
            return -1;
        if (e1.getWeight()  > e2.getWeight())
            return 1;
        return 0;
    }
}
