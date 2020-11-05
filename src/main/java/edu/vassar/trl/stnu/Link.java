package edu.vassar.trl.stnu;

public class Link {
    
    private int start;
    private int end;
    private int x;
    private int y;

    public Link(int startNode, int x, int y, int endNode){
        this.start = startNode;
        this.x = x;
        this.y = y;
        this.end = endNode;
    }

    public int getStart() { return start; }
    public int getEnd() { return end; }
    public int getX() { return x; }
    public int getY() { return y; }

}
