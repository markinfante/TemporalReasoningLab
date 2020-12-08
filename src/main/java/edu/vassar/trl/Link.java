package edu.vassar.trl;

/**
 * A Link class, where a Link is an object that we use in priority queues
 * 
 * @author Ciara O'Donnell
 * @version 1.0
*/

public class Link
{
    private int node;
    private double priority;

    public Link(int node, double priority)
    {
        this.node = node;
        this.priority = priority;
    }

    public int getNode()
    {
        return node;
    }

    public double getPriority()
    {
        return priority;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (! (o instanceof Link))
            return false;
        Link l = (Link) o;

        return Integer.compare(node, l.getNode()) == 0 && Double.compare(priority, l.getPriority()) == 0;
    }

}