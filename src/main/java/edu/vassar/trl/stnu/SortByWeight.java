package edu.vassar.trl.stnu;

import java.util.Comparator;

import edu.vassar.trl.stnu.Morris2006.Pair;


public class SortByWeight implements Comparator<Pair> {

    @Override
    public int compare(Pair p1, Pair p2) {
        return (int) Math.ceil(p1.getWeight() - p2.getWeight());
    }
    
}
