package edu.vassar.trl.stnu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.vassar.trl.*;

public class STNU extends STN{
    
    private int numContignentLinks;
    private ArrayList<Link> cLinks;
    private ArrayList<Integer> activationTPs;
    private ArrayList<Integer> contingentTPs;
    private List<HashMap<Integer, Double>> ucSuccs;

    public STNU(){
        super();
        numContignentLinks = 0;
        cLinks = new ArrayList<Link>();
        activationTPs = new ArrayList<Integer>();
        contingentTPs = new ArrayList<Integer>();
        ucSuccs = new ArrayList<HashMap<Integer, Double>>();
    }

    @Override
    public void init(){
        super.init();
        for (int i = 0; i < numContignentLinks; i++){
            ucSuccs.add(i, new HashMap<Integer,Double>());
        }
    }

    // Assuming a link isnt already in the graph
    public void addLink(Link contingentLink){
        this.cLinks.add(contingentLink);
        this.activationTPs.add(contingentLink.getStart());
        this.contingentTPs.add(contingentLink.getEnd());
    }

    public ArrayList<Link> getLinks() { return cLinks; }
    public int getNumCLinks() { return numContignentLinks; }
    public void setNumCLinks(int numCLinks){ numContignentLinks = numCLinks; }


}
