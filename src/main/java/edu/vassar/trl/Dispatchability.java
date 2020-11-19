package src;

import java.util.*;

class Dispatchability{
    DistanceMatrix dm;
    STN network;


    Dispatchability(STN stn){
        // some stn or stnu?
        network = stn;
        dm = network.getDistanceMatrix();
    }

    public void p(String str){
        System.out.println(str);
    }

    public HashMap<Integer,Integer> timeDispatchingAlgorithm(int startTimePoint){
        System.out.println("timeDispatchingAlgorithm");
        System.out.println(dm.toString());
        // note where peudo code comes from
        HashMap<Integer,Integer> timeMap = new HashMap<Integer,Integer>(); // swap to integer,integer
        HashMap<Integer,Integer> lowerBoundMap = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> upperBoundMap = new HashMap<Integer,Integer>();
        for(int i =0; i <network.getNumTimePoints(); i++){
            int lowerTimeBound = (int)Math.round(-dm.get(i).get(startTimePoint));
            int upperTimeBound = (int)Math.round(dm.get(startTimePoint).get(i));

            lowerBoundMap.put(i,lowerTimeBound);
            upperBoundMap.put(i,upperTimeBound);

        }
        ArrayList<Integer> frontier = new ArrayList<Integer>(); // A
        ArrayList<Integer> traversedNodes = new ArrayList<Integer>(); // S

        frontier.add(startTimePoint);

        int currentTime = 0;

        // starting time includes 0

        while(frontier.size() > 0){
            p("lower bound map: "+ lowerBoundMap.toString());
            p("upper bound map: "+ upperBoundMap.toString());
            
            
            
            int randomIndex =(int)(Math.random() * frontier.size());
            int currentNode = frontier.remove(randomIndex);
            // for(int i = 0; i <frontier.size(); i++){
            //     int tp = frontier.get(i);
            //     double lowerTimeBound = -dm.get(tp).get(startTimePoint);

            //     double upperTimeBound = dm.get(startTimePoint).get(tp);
            //     if(lowerTimeBound < currentTime && currentTime < upperTimeBound){
            //         currentNode = frontier.remove(i);
            //         hasCurrentNode = true;
            //         System.out.println("this should be printing");
            //         break;
            //     } // bad
            // }
            timeMap.put(currentNode, currentTime);

            traversedNodes.add(currentNode);
            for(Map.Entry<Integer,Double> entry : network.getSuccsOf(currentNode).entrySet()){
                int succVal = (int)Math.round(entry.getValue());
                if(succVal >= 0){
                    int succNode = entry.getKey();
                    if(upperBoundMap.get(succNode) > succVal){ // set the lower value to upperbound
                        upperBoundMap.put(succNode, succVal);
                    }
                }
            }
            for(Map.Entry<Integer,Double> entry : network.getPredsOf(currentNode).entrySet()){
                int predVal = (int)Math.round(entry.getValue());
                if(predVal < 0){
                    int succNode = entry.getKey();
                    if(upperBoundMap.get(succNode) < predVal){ // set the lower value to upperbound
                        upperBoundMap.put(succNode, predVal);
                    }
                }
            }
            p("S" + traversedNodes.toString());
            p("A" + frontier.toString());
            for(int i = 0; i <network.getNumTimePoints(); i++){ // not in S
                boolean addToFrontier= true;
                if(traversedNodes.contains(i) || frontier.contains(i)){ // 
                    addToFrontier = false;
                } else {
                    for(Map.Entry<Integer,Double> entry : network.getSuccsOf(i).entrySet()){
                        p("key: "+ entry.getKey());
                        p("value: "+ entry.getValue());
                        if(entry.getValue() < 0){ 
                            
                            if(!traversedNodes.contains(entry.getKey())){ // if s contains succ, add to frontier
                                addToFrontier = false;
                                break;
                            }
                        }
                        
                    }
                }
                if(addToFrontier){
                    frontier.add(i);
                }
            }

            if(frontier.size()>0){
                ArrayList<Integer> lowers = new ArrayList<Integer>();
                ArrayList<Integer> uppers = new ArrayList<Integer>();
                for(int i = 0; i <frontier.size(); i++){
                    lowers.add(lowerBoundMap.get(frontier.get(i)));
                    uppers.add(upperBoundMap.get(frontier.get(i)));
                }
                
                int u = Collections.min(lowers);
                int v = Collections.min(uppers);
                p("u:"+u);
                p("v:"+v);
                // check for legal range u <= v
                if(u>v && u > 0 && v > 0){ 
                    p("Non-Dispatchable");
                    return null;
                }
                p(""+currentTime);
                currentTime = (int)(Math.random() * (v-u + 1)) + u;
            }
        
            
        }
        return timeMap;

    }
}