package planets;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class PlanetSurface extends PApplet {

    List<PlanetObj> planets;
    double gravity;
    boolean running;
    
    public static double t = 0;
    public static double dt = 0.0166667;

    boolean graphDots = false;
    boolean graphLines = false;
    boolean createGraph = false;
    graph.Graph graph = new graph.Graph();

    Mode mode;
    boolean wrap;

    enum Mode {
        cycloid,
        space,
        earth,
        orbit;
    }

    @Override
    public void settings() {
        size(1200, 800);
    }

    public void addToPlanets(PlanetObj planet) {
        planets.add(planet);
        planets.forEach((p) -> {
            p.update(planets);
            p.setWrap(wrap);
        });
    }

    @Override
    public void setup() {
        background(0);
        stroke(255, 255, 0);
        frameRate(60);
        running = false;

        graph = new graph.Graph();

        planets = new ArrayList<>();

        if (mode == Mode.orbit) {

            double scale = 1e6;

            PlanetObj sun = new PlanetObj(this, "sun", width/2, height/2, 100, 1.989e30, planets, scale);

            addToPlanets(sun);

            //smallPlanet.addXVel(1);
            //smallPlanet.addYVel(-1);

            PlanetObj earth = new PlanetObj(this, "earth", 200, height/2, 50, 5.972e24, planets, scale);

            addToPlanets(earth);
            
            earth.addYVel(400);

            PlanetObj jupiter = new PlanetObj(this, "jupiter", width-200, height/2, 75, 1.898e27, planets, scale);

            addToPlanets(jupiter);
            
            jupiter.addYVel(-400);

            //mediumPlanet.addXVel(-1);
            //mediumPlanet.addYVel(-5);

            graph.planetIn(earth, scale);
        }

        if (mode == Mode.earth) {

            double scale = 1;

            PlanetObj earth = new PlanetObj(this, "earth", width / 2, height / 2, 100, 5.972e24, planets, scale);

            earth.putAtBottom();

            addToPlanets(earth);

            PlanetObj ball = new PlanetObj(this, "ball", width / 2, 50, 1.65, 80, planets, scale);

            addToPlanets(ball);

            ball.addXVel(1);

            graph.planetIn(ball, scale);
        }

        if (mode == Mode.space) {
            double scale = 1e4  ;

            PlanetObj planet1 = new PlanetObj(this, "planet1", 100, 100, 25, 5e25, planets, scale);

            addToPlanets(planet1);

            PlanetObj planet2 = new PlanetObj(this, "planet2", 100, height - 100, 40, 5.1e24, planets, scale);

            addToPlanets(planet2);
            
            PlanetObj planet3 = new PlanetObj(this, "planet3", width - 100, height - 100, 10, 5.972e24, planets, scale);

            addToPlanets(planet3);

            PlanetObj planet4 = new PlanetObj(this, "planet4", width - 100, 100, 70, 8e25, planets, scale);

            addToPlanets(planet4);

            graph.planetIn(planet1, scale);
        }
        
        if (mode == Mode.cycloid){
            graph.setCycloid(true);
        }

        if (createGraph) {
            String[] largs = {"GraphAddOn"};

            PApplet.runSketch(largs, graph);

            graph.setDots(graphDots);
            graph.setLines(graphLines);
        }
    }

    public void draw() {
        background(0);

        if (running) {
            planets.forEach((p) -> p.step());
            t += dt;
        }

        planets.forEach((p) -> p.draw());
    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            running = !running;
            graph.pause(running);
        }
    }

    public void setGraphCreation(boolean c) {
        createGraph = c;
    }

    public void setGraphDots(boolean dots) {
        this.graphDots = dots;
    }

    public void setGraphLines(boolean lines) {
        this.graphLines = lines;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public void pause(boolean running) {
        this.running = running;
    }

    public void setMode(int mode) {
        switch (mode) {
            case 0:
                this.mode = Mode.orbit;
                break;
            case 1:
                this.mode = Mode.space;
                break;
            case 2:
                this.mode = Mode.earth;
                break;
            case 3:
                this.mode = Mode.cycloid;
                break;
        }
    }
}
