package Graphs;

import java.util.HashMap;

public class Graph {
    public String label;
    HashMap<Integer, Integer> coords;
    
    public Graph(String label){
        this.label = label;
        
        coords = new HashMap<Integer, Integer>();
    }
    
    //Adds y to x
    public void addCoord(int x, int y){
        coords.put(x, y);
    }
    
    //Removes the pair, returns the y
    public int removeCoord(int x){
        return coords.remove(x);
    }
}
