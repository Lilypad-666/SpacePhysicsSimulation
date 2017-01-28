package ball;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class Surface extends PApplet {
    List<PlanetObj> planets;
    double gravity;
    boolean running;
    graph.Graph graph;
    
    public void settings() {
        size(1000, 600);
    }
    
    public void addToPlanets(PlanetObj planet){
        planets.add(planet);
        planets.forEach((p)->p.update(planets));
    }
    
    public void setup() {
        background(0);
        stroke(255, 255, 0);
        frameRate(120);
        
        planets = new ArrayList<>();
        
        running = false;
        
        PlanetObj smallPlanet = new PlanetObj(this, "smallPlanet", 400, 100, 25, 5e10, planets);
        
        addToPlanets(smallPlanet);
        
        smallPlanet.addXVel(20);
        smallPlanet.addYVel(-20);
        
        PlanetObj earth = new PlanetObj(this, "earth", width/2, height/2, 50, 9e12, planets);
        
        addToPlanets(earth);
        
        /*PlanetObj mediumPlanet = new PlanetObj(this, "mediumPlanet", 700, 100, 38, 9e11, planets);
        
        addToPlanets(mediumPlanet);
        
        mediumPlanet.addXVel(-10);
        mediumPlanet.addYVel(-5);*/
        
        String[] args = {"GraphAddOn"};
        
        graph = new graph.Graph();
        
        graph.planetIn(smallPlanet);
        
        //Run Graph
        PApplet.runSketch(args, graph);
    }
    
    public void draw() {
        background(0);
        
        if (running){
            planets.forEach((p)->p.step());
        }
        
        planets.forEach((p)->p.draw());
        
        frame.requestFocus();
    }
    
    @Override
    public void keyPressed(){
        if (key == ' '){
            running = !running;
            graph.pause(running);
        }
    }
    
    public static void main(String[] args) {
        PApplet.main(new String[]{ball.Surface.class.getName()});
    }
    
    public void pause(boolean running){
        this.running = running;
    }
}
