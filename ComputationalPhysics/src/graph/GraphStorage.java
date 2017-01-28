package graph;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class GraphStorage{
    PApplet pApp;
    List<GraphObj> graphs;
    double largestX;
    double largestY;
    double smallestX;
    double smallestY;
    double rangeX;
    double rangeY;
    boolean changedLargeSmall;
    
    public GraphStorage(PApplet pApp){
        graphs = new ArrayList<>();
        this.pApp = pApp;
        changedLargeSmall = false;
    }
    
    public void createGraph(String name){
        graphs.add(new GraphObj(pApp, name));
    }
    
    public void createGraph(String name, double r, double g, double b){
        graphs.add(new GraphObj(pApp, name, r, g, b));
    }
    
    public void draw(boolean running){
        graphs.forEach((g)->g.draw(rangeX, rangeY, smallestX, largestY, running));
    }
    
    public void drawLines(){
        graphs.forEach((g)->g.drawLines(rangeX, rangeY, smallestX, largestY));
    }
    
    public void setPoint(String name, double x, double y){
        double reverseY = -y;
        if (!changedLargeSmall){
            largestX = x;
            largestY = reverseY;
            smallestX = x;
            smallestY = reverseY;
            changedLargeSmall = true;
        } else{
            if (x > largestX)
                largestX = x;

            if (reverseY < largestY)
                largestY = reverseY;

            if (x < smallestX)
                smallestX = x;

            if (reverseY > smallestY)
                smallestY = reverseY;
        }
        
        //Set Scales
        rangeX = Math.abs(largestX - smallestX);
        rangeY = Math.abs(smallestY - largestY);
        
        for (GraphObj g : graphs) {
            if (g.getName().equals(name)){
                g.setPoint(x, reverseY);
            }
        }
    }
    
    public GraphObj getGraph(String name){
        for (GraphObj g : graphs){
            if (g.getName().equals(name)){
                return g;
            }
        }
        
        return null;
    }
    
    public int numberOfGraphs(){
        return graphs.size();
    }
    
    public GraphObj getGraph(int index){
        return graphs.get(index);
    }
}
