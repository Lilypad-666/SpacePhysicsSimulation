package graph;

import java.util.List;
import processing.core.*;

public class Graph extends PApplet {
    GraphStorage gStore;
    boolean drawLines;
    boolean drawDots;
    boolean running;
    boolean dragging;
    boolean zoomed;
    double draggingStart;
    
    /*double t;
    double t2;
    double dt;
    double c;*/
    ball.PlanetObj planet;
    double time;
    
    @Override
    public void settings(){
        size(1800, 400);
    }
    
    @Override
    public void setup() {
        background(0);
        frameRate(120);
        
        drawLines = false;
        drawDots = true;
        running = false;
        zoomed = false;
        
        gStore = new GraphStorage(this);
        
        //gStore.createGraph("cycloid", 255, 0, 0);
        //gStore.createGraph("cycloid2", 0, 255, 0);
        
        gStore.createGraph("distanceToEarth", 200, 200, 100);
        
        /*t = 0;
        dt = 0.1;
        c = 50;
        
        t2 = 10;*/
    }
    
    @Override
    public void draw() {
        
        background(0);
        if (zoomed)
            running = false;
        
        if (drawDots){
            gStore.draw(running);
        
            if (drawLines)
                gStore.drawLines();
        } else {
            drawLines = false;
        }
        
        if (running){
            /*t += dt;
            t2 += dt;*/

            time += 1;
            
            //gStore.setPoint("cycloid", c*(t-Math.sin(t)), c*(1-Math.cos(t)));
            //gStore.setPoint("cycloid2", c*(t-Math.sin(t2)), c*(1-Math.cos(t2)));
            
            gStore.setPoint("distanceToEarth", time, planet.distanceToPlanet("earth"));
        }
        
        if (drawDots){
            fill(0, 200, 50);
            stroke(0, 200, 50);
        } else {
            fill(200, 50, 0);
            stroke(200, 50, 0);
        }
        //Draw Dot Rect
        rect(width-50, 0, 50, 50);
        
        if (drawLines){
            fill(0, 200, 50);
            stroke(0, 200, 50);
        } else {
            fill( 200, 50, 0);
            stroke(200, 50, 0);
        }
        //Draw Line Rect
        rect(0, 0, 50, 50);
        
        fill(200, 100, 100);
        stroke(200, 100, 100);
        textSize(20);
        text("Lines", 2, 30);
        text("Dots", width-48, 30);
        
        if (dragging){
            if (Math.abs(mouseX-draggingStart) < 200){
                fill(200, 50, 0, 126);
                stroke(200, 50, 0, 126);
            } else {
                fill(211, 211, 211, 126);
                stroke(211, 211, 211, 126);
            }
            
            rect((float)draggingStart, 0, (float)(mouseX-draggingStart), height);
        }
    }
    
    @Override
    public void mousePressed(){
        if (mouseY <= 50 && mouseX <= 50)
            drawLines = !drawLines;
        else if (mouseY <= 50 && mouseX >= width-50)
            drawDots = !drawDots;
        else{
            dragging = true;
            draggingStart = mouseX;
        }
    }
    
    @Override
    public void mouseReleased(){
        if (dragging){
            dragging = false;
            
            if (Math.abs(draggingStart - mouseX) > 200){
                zoomed = true;
                
                double lowBound = draggingStart;
                if (draggingStart > mouseX)
                    lowBound = mouseX;
                
                double range = Math.abs(draggingStart - mouseX);
                
                List<Double> points = gStore.getPoints();
                
                //Junk number
                double smallX = -1.838576;
                double largeX = -1.838576;
                
                double rangeX = gStore.getRangeX();
                double minX = gStore.getMinX();
                
                for (double d : points){
                    double tempD = (d-minX)*(width/rangeX);
                    if ((smallX == -1.838576 && tempD > lowBound) || (tempD > lowBound && tempD < smallX)){
                        smallX = d;
                    }
                    
                    if ((largeX == -1.838576 && tempD < lowBound+range) || (tempD > largeX && tempD < lowBound+range)){
                        largeX = d;
                    }
                }
                
                gStore.setLargeSmallX(largeX, smallX);
            }
        }
    }
    
    public void keyPressed(){
        if (key == ENTER){
            resetZoom();
        }
    }
    
    public void planetIn(ball.PlanetObj planet){
        this.planet = planet;
    }
    
    public void pause(boolean running){
        if (zoomed)
            resetZoom();
        this.running = running;
    }
    
    public void resetZoom(){
        zoomed = false;
        gStore.resetZoom();
    }
}
