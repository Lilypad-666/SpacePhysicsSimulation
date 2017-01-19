package vector;

import processing.core.*;
/**
 *
 * @author Classmate
 */
public class Visualize extends PApplet {
    private BenchMark sim;
    private int steps = 0;
    
    public void settings() {
        size(400, 400);
    }
    
    public void setup() {
        sim = new BenchMark();
        sim.setup();
        background(0);
        stroke(255, 255, 0);
        frameRate(2000);
    }
    
    public void draw() {
        if (sim.hasNext()) {
            steps++;
            Vector v = sim.next();
            point(width/2 + (float)v.i, height/2 - (float)v.j);
        } else {
            noLoop();
        }
    }
    
    public void keyPressed() {
        looping = !looping;
        if(!looping)
            System.out.println(steps);
    }
    
    public static void main(String[] args) {
        PApplet.main(new String[]{vector.Visualize.class.getName()});
    }
}
