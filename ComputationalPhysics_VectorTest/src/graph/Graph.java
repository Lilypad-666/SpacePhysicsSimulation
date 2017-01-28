package graph;

import processing.core.*;

public class Graph extends PApplet {
    GraphStorage gStore;
    boolean drawLines;
    boolean running;
    /*double t;
    double t2;
    double dt;
    double c;*/
    ball.PlanetObj planet;
    double time;
    
    @Override
    public void settings(){
        size(1000, 400);
    }
    
    @Override
    public void setup() {
        background(0);
        frameRate(60);
        
        drawLines = false;
        running = true;
        
        gStore = new GraphStorage(this);
        
        //gStore.createGraph("cycloid", 255, 0, 0);
        //gStore.createGraph("cycloid2", 0, 255, 0);
        
        gStore.createGraph("direction", 100, 100, 100);
        
        /*t = 0;
        dt = 0.1;
        c = 50;
        
        t2 = 10;*/
    }
    
    @Override
    public void draw() {
        background(0);
        
        gStore.draw(running);
        
        if (drawLines)
            gStore.drawLines();
        
        if (running){
            /*t += dt;
            t2 += dt;*/

            time += 1;
            
            //gStore.setPoint("cycloid", c*(t-Math.sin(t)), c*(1-Math.cos(t)));
            //gStore.setPoint("cycloid2", c*(t-Math.sin(t2)), c*(1-Math.cos(t2)));
            
            gStore.setPoint("direction", time, planet.getXVel());
        }
        
        fill(255, 0, 0);
        stroke(255, 100, 0);
        rect(0, 0, 50, 50);
    }
    
    @Override
    public void mouseClicked(){
        if (mouseY <= 50 && mouseX <= 50)
            drawLines = !drawLines;
    }
    
    @Override
    public void keyPressed(){
        if (key == ' '){
            running = !running;
        }
    }
    
    public void planetIn(ball.PlanetObj planet){
        this.planet = planet;
    }
}
