package edu.vassar.trl;
import java.util.Comparator;


public class EdgeSort implements Comparator<Edge> {

    @Override
    public int compare(Edge e1, Edge e2) {
        if (e1.getWeight()  < e2.getWeight())
            return -1;
        if (e1.getWeight()  > e2.getWeight())
            return 1;
        return 0;
    }
    
}
