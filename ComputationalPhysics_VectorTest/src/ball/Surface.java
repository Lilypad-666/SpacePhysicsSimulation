package ball;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class Surface extends PApplet {
    List<PlanetObj> planets;
    double gravity;
    boolean running;
    
    public void settings() {
        size(1000, 1000);
    }
    
    public void addToPlanets(PlanetObj planet){
        planets.add(planet);
        planets.forEach((p)->p.update(planets));
    }
    
    public void setup() {
        background(0);
        stroke(255, 255, 0);
        frameRate(60);
        
        planets = new ArrayList<>();
        
        running = true;
        
        PlanetObj smallPlanet = new PlanetObj(this, "smallPlanet", 400, 400, 25, 5e10, planets);
        
        addToPlanets(smallPlanet);
        
        smallPlanet.addXVel(20);
        smallPlanet.addYVel(-20);
        
        PlanetObj earth = new PlanetObj(this, "earth", width/2, height/2, 50, 5.972e12, planets);
        
        addToPlanets(earth);
        
        PlanetObj smallPlanet2 = new PlanetObj(this, "smallPlanet2", 600, 600, 25, 5e10, planets);
        
        addToPlanets(smallPlanet2);
        
        smallPlanet2.addXVel(-20);
        smallPlanet2.addYVel(20);
        
        PlanetObj mediumPlanet = new PlanetObj(this, "mediumPlanet", 700, 100, 38, 9e11, planets);
        
        addToPlanets(mediumPlanet);
        
        mediumPlanet.addXVel(-5);
        
        String[] args = {"GraphAddOn"};
        
        graph.Graph graph = new graph.Graph();
        
        graph.planetIn(smallPlanet);
        
        //Run Graph
        //PApplet.runSketch(args, graph);
    }
    
    public void draw() {
        background(0);
        
        if (running){
            planets.forEach((p)->p.step());
        }
        
        planets.forEach((p)->p.draw());
    }
    
    @Override
    public void keyPressed(){
        if (key == ' '){
            running = !running;
        }
    }
    
    public static void main(String[] args) {
        PApplet.main(new String[]{ball.Surface.class.getName()});
    }
}
