package edu.vassar.trl;
import java.util.Comparator;

/**
 * A custom comparator for Links. 
 * 
 * @author Ciara O'Donnell
 * @version 1.0
*/

public class LinkSort implements Comparator<Link> {
    /***
     * A custom comparator for Links. This method compares the priority of the first link to
     * the priority of the second link.
     * 
     * @param li1 The first Link
     * @param li2 The second Link
     */
    @Override
    public int compare(Link li1, Link li2) {
        if (li1.getPriority()  < li2.getPriority())
            return -1;
        if (li1.getPriority()  > li2.getPriority())
            return 1;
        return 0;
    }
}
