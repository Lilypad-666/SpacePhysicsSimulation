package graph;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import processing.core.*;

public class GraphObj{
    HashMap<Double, Double> points;
    List<Double> xPoints;
    String name;
    PApplet pApp;
    double r;
    double g;
    double b;
    
    
    public GraphObj(PApplet pApp, String name, double r, double g, double b){
        xPoints = new ArrayList<>();
        points = new HashMap<>();
        this.name = name;
        this.pApp = pApp;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public GraphObj(PApplet pApp, String name){
        xPoints = new ArrayList<>();
        points = new HashMap<>();
        this.name = name;
        this.pApp = pApp;
        this.r = 255;
        this.g = 255;
        this.b = 255;
    }
    
    public boolean hasNoPoints(){
        return (xPoints.size() == 0);
    }
        
    public String getName(){
        return name;
    }
    
    public void setPoint(double x, double y){
        if (!xPoints.contains(x)){
            xPoints.add(x);
        }
        points.put(x, y);
    }
    
    public boolean removePoint(double x){
        if (xPoints.contains(x)){
            points.remove(x);
            xPoints.remove(x);
            return true;
        }
        return false;
    }
    
    public void drawLabel(double rangeX, double rangeY, double minX, double minY, boolean running){
        if (!running && pApp.mouseX > 50 && pApp.mouseY > 50){
            for (double x : xPoints){
                if (pApp.mouseX > (x-minX)*(pApp.width/rangeX)-5 && pApp.mouseX < (x-minX)*(pApp.width/rangeX)+5){
                    if (pApp.mouseY > (points.get(x)-minY)*(pApp.height/rangeY)-5 && pApp.mouseY < (points.get(x)-minY)*(pApp.height/rangeY)+5){
                        
                        pApp.fill(255, 255, 255);
                        pApp.stroke(255, 255, 255);
                        
                        DecimalFormat df = new DecimalFormat("#.##");
                        df.setRoundingMode(RoundingMode.CEILING);
                        pApp.text(df.format(x) + ", " + df.format(points.get(x)), pApp.mouseX + 12, pApp.mouseY + 12);
                    }
                }
            } 
        }
    }
    
    public void draw(double rangeX, double rangeY, double minX, double minY, boolean running){
        if (pApp.mousePressed){
            drawLabel(rangeX, rangeY, minX, minY, running);
        }
        
        pApp.fill((float)r, (float)g, (float)b);
        pApp.stroke((float)r, (float)g, (float)b);
        
        xPoints.forEach((x)->pApp.ellipse((float)((x-minX)*(pApp.width/rangeX)), (float)((points.get(x)-minY)*(pApp.height/rangeY)), 10f, 10f));
    }
    
    public void drawLines(double rangeX, double rangeY, double minX, double minY){
        pApp.fill((float)r, (float)g, (float)b);
        pApp.stroke((float)r, (float)g, (float)b);
        pApp.strokeWeight(4);
        
        double oldX = -1;
        for(double x : xPoints){
            if (oldX != -1)
                pApp.line((float)((oldX-minX)*(pApp.width/rangeX)), (float)((points.get(oldX)-minY)*(pApp.height/rangeY)), (float)((x-minX)*(pApp.width/rangeX)), (float)((points.get(x)-minY)*(pApp.height/rangeY)));
            oldX = x;
        }
    }
}
