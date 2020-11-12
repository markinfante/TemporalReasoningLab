package edu.vassar.trl.stnu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.vassar.trl.*;

public class STNU extends STN{
    
    private ArrayList<Link> cLinks;

    public STNU(){
        super();
        cLinks = new ArrayList<Link>();
    }

    public void addLink(Link contingentLink){
        this.cLinks.add(contingentLink);
    }

    public ArrayList<Link> getLinks() { return cLinks; }
    public int getNumLinks() { return cLinks.size(); }

}
