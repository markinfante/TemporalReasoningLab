package edu.vassar.trl;

import java.util.*;

/**
 * Class containing the Greedy Dispatcher and Minimal Dispatch Filtering algorithms 
 * @author Jonathan Fong
 * @author Mike Jaklitsch
 */

class Dispatchability{
    DistanceMatrix dm;
    STN network;


    Dispatchability(STN stn){
        // some stn or stnu?
        network = stn;
        dm = network.getDistanceMatrix();
    }

    public STN convert(){
        STN newSTN = new STN();
        FloydWarshall fw = new FloydWarshall(network);
        DistanceMatrix apsp = fw.generateMatrix();
        ArrayList<ArrayList<Boolean>> marks = new ArrayList<ArrayList<Boolean>>();
        int timepoints = network.getNumTimePoints();
        for (int i = 0; i < timepoints; i++){
            for (int j = 0; j < timepoints; j++){
                marks.get(i).add(false);
            }
        }

        for (int i = 0; i < timepoints; i++){ // get first time point

            
            for (int j = 0; j < timepoints; j++){ // get time point connected to first
                if(i == j){
                    continue;
                }                
                if(apsp.get(i).get(j) != Double.POSITIVE_INFINITY && apsp.get(i).get(j) != null){ // i connects to j
                    for (int k = 0; k < timepoints; k++){ // get time point connected to second
                        if(j == k || i == k){
                            continue;
                        }     
                        if((apsp.get(j).get(k) != Double.POSITIVE_INFINITY && apsp.get(j).get(k) != null) && // j connects to k and
                            (apsp.get(i).get(k) != Double.POSITIVE_INFINITY && apsp.get(i).get(k) != null)){ // i connects to k
                            // DistanceMatrix dm = network.getDistanceMatrix();
                            if((apsp.get(i).get(j) + apsp.get(j).get(k) == apsp.get(i).get(k)) &&
                              (apsp.get(i).get(k) + apsp.get(k).get(j) == apsp.get(i).get(j))){// dominate each other (bad relationship dynamics yikes)
                                if(!marks.get(i).get(k) && !marks.get(i).get(j)){
                                    marks.get(i).add(k, true);
                                }
                            } else if(apsp.get(j).get(k) >= 0 && apsp.get(i).get(k) >= 0){ // upper dom
                                double ItoK = apsp.get(i).get(j) + apsp.get(j).get(k);
                                if(ItoK == apsp.get(i).get(k)){ // upper dom
                                    marks.get(i).add(k,true);
                                }
                            } else if(apsp.get(i).get(j) < 0 && apsp.get(i).get(k) < 0){ // lower dom
                                double ItoK = apsp.get(i).get(j) + apsp.get(j).get(k);
                                if(ItoK == apsp.get(i).get(k)){ // upper dom
                                    marks.get(i).add(k,true);
                                }
                            } 
                            // else if (){ 

                            // }
                        }
                    }
                }
            }
        }



        return newSTN;
    }

    public void p(String str){
        System.out.println(str);
    }

    public boolean isValidTimeMap(int startTimePoint, HashMap<Integer,Integer> timeMap){
        if(timeMap.containsKey(-1)){
            return false;
        }
        for(Map.Entry<Integer,Integer> entry: timeMap.entrySet()){
            int key = entry.getKey();
            int lowerTimeBound = (int)Math.round(-dm.get(key).get(startTimePoint));
            int upperTimeBound = (int)Math.round(dm.get(startTimePoint).get(key));

            if(!(entry.getValue()>= lowerTimeBound && entry.getValue() <=upperTimeBound)){
                return false;
            }

        }
        return true;
    }

    public HashMap<Integer,Integer> timeDispatchingAlgorithm(int startTimePoint){
        System.out.println("timeDispatchingAlgorithm");
        System.out.println("STN Edge Graph\n" + dm.toString());
        // note where pseudo code comes from
        // Reformulating Temporal Plans For Efficient Execution.pdf
        /*1. Let
            A = {start_time_point}
            current_time = 0
            S = {}
        */
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
            p("\ncurrent time: "+currentTime);
            p("lower bound map: "+ lowerBoundMap.toString());
            p("upper bound map: "+ upperBoundMap.toString());
            
           
            
            /*
                2. Arbitrarily pick a time point TP in A such
                that current_time belongs to TP's time bound;
            */
            int randomIndex = (int)(Math.random() * frontier.size());
            int currentNode = frontier.remove(randomIndex);

            /*
                3. Set TP's execution time to current_time and add
                TP to S;
            */
            timeMap.put(currentNode, currentTime);
            traversedNodes.add(currentNode);

            /*
                4. Propagate the time of execution
                to its IMMEDIATE NEIGHBORS in the distance
                graph;
            */
            // for each successor of currentNode
            for(Map.Entry<Integer,Double> entry : network.getSuccsOf(currentNode).entrySet()){
                int succVal = (int)Math.round(entry.getValue());
                if(succVal >= 0){
                    int succNode = entry.getKey();
                    if(succVal >= currentTime){
                        if(upperBoundMap.get(succNode) > succVal){ // set the lower value to upperbound
                            upperBoundMap.put(succNode, succVal);
                        }
                    }
                }
            }
            // for each predecessor of currentNode
            for(Map.Entry<Integer,Double> entry : network.getPredsOf(currentNode).entrySet()){
                int predVal = (int)Math.round(entry.getValue());
                if(predVal < 0){
                    int predNode = entry.getKey();
                    if(predVal >= currentTime){
                        if(upperBoundMap.get(predNode) < predVal){ // set the lower value to upperbound
                            upperBoundMap.put(predNode, predVal);
                        }
                    }
                }
            }
            
            /*
                5. Put in A all time points TPx such that all
                negative edges starting from TPx have a
                destination that is already in S;
            */
            for(int i = 0; i <network.getNumTimePoints(); i++){ // not in S
                boolean addToFrontier= true;
                if(traversedNodes.contains(i) || frontier.contains(i)){ // 
                    addToFrontier = false;
                } else {
                    for(Map.Entry<Integer,Double> entry : network.getSuccsOf(i).entrySet()){
                        // p("key: "+ entry.getKey());
                        // p("value: "+ entry.getValue());
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
                if(u<currentTime){
                    u = currentTime;
                }
                int v = Collections.min(uppers);
                p("A" + frontier.toString());
                p("S" + traversedNodes.toString());
                p("u:"+u);
                p("v:"+v);
                // check for legal range u <= v
                if(u>v && u > 0 && v > 0){ 
                    p("Non-Dispatchable");
                    HashMap<Integer,Integer> badMap = new HashMap<Integer,Integer>();
                    badMap.put(-1, 0);
                    p("valid time map? "+isValidTimeMap(startTimePoint, badMap));
                    return badMap;
                }
                // p(""+currentTime);
                /*
                    6. Wait until current_time has advanced to
                    some time between
                        min{lower_bound(TP) : TP in A}
                        and
                        min{upper_bound(TP) : TP in A}
                */
                currentTime = (int)(Math.random() * (v-u + 1)) + u;
            }
        
            // 7. Go to 2 until every time point is in S.
        }
        p(timeMap.toString());
        p("valid time map? "+isValidTimeMap(startTimePoint, timeMap));
        return timeMap;

    }
}
