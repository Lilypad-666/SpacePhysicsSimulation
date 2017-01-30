package graph;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class Graph extends PApplet {

    GraphStorage gStore;
    boolean drawLines;
    boolean drawDots;
    boolean running;
    boolean dragging;
    boolean zoomed;
    boolean cycloid;
    double draggingStart;

    //Cycloitd Stuff
    double t;
    double t2;
    double dt;
    double c;
    
    List<planets.PlanetObj> planets = new ArrayList<>();
    double time;
    double scale;

    @Override
    public void settings() {
        size(1800, 400);
    }

    @Override
    public void setup() {
        background(0);
        frameRate(60);

        running = false;
        zoomed = false;

        gStore = new GraphStorage(this);

        if (cycloid){
            gStore.createGraph("cycloid", 255, 0, 0);
            gStore.createGraph("cycloid2", 0, 255, 0);
            t = 0;
            dt = 0.1;
            c = 50;
            t2 = 10;
        } else{
            gStore.createGraph("yVel", 255, 255, 255);
            gStore.createGraph("xVel", 100, 255, 255);
        }
    }

    @Override
    public void draw() {

        background(0);
        if (zoomed) {
            running = false;
        }

        if (drawDots) {
            gStore.draw(running);

            if (drawLines) {
                gStore.drawLines();
            }
        } else {
            drawLines = false;
        }

        if (running) {
            time += 1;
            
            if (cycloid){
                t += dt;
                t2 += dt;

                gStore.setPoint("cycloid", c*(t-Math.sin(t)), c*(1-Math.cos(t)));
                gStore.setPoint("cycloid2", c*(t-Math.sin(t2)), c*(1-Math.cos(t2)));
            } else {
                for (planets.PlanetObj p : planets) {
                    if (p.getName().equals("ball") || p.getName().equals("smallPlanet")) {
                        gStore.setPoint("yVel", time, p.getYVel() / scale);
                        gStore.setPoint("xVel", time, p.getXVel() / scale);
                    }
                }
            }
        }

        if (drawDots) {
            fill(0, 200, 50);
            stroke(0, 200, 50);
        } else {
            fill(200, 50, 0);
            stroke(200, 50, 0);
        }
        //Draw Dot Rect
        rect(width - 50, 0, 50, 50);

        if (drawLines) {
            fill(0, 200, 50);
            stroke(0, 200, 50);
        } else {
            fill(200, 50, 0);
            stroke(200, 50, 0);
        }
        //Draw Line Rect
        rect(0, 0, 50, 50);

        fill(200, 100, 100);
        stroke(200, 100, 100);
        textSize(20);
        text("Lines", 2, 30);
        text("Dots", width - 48, 30);

        if (dragging) {
            if (Math.abs(mouseX - draggingStart) < 100) {
                fill(200, 50, 0, 126);
                stroke(200, 50, 0, 126);
            } else {
                fill(211, 211, 211, 126);
                stroke(211, 211, 211, 126);
            }

            rect((float) draggingStart, 0, (float) (mouseX - draggingStart), height);
        }
    }
    
    public void setCycloid(boolean cycloid){
        this.cycloid = cycloid;
    }

    @Override
    public void mousePressed() {
        if (mouseButton == LEFT) {
            if (mouseY <= 50 && mouseX <= 50) {
                drawLines = !drawLines;
            } else if (mouseY <= 50 && mouseX >= width - 50) {
                drawDots = !drawDots;
            }
        }

        if (mouseButton == RIGHT) {
            dragging = true;
            draggingStart = mouseX;
        }
    }

    @Override
    public void mouseReleased() {
        if (dragging) {
            dragging = false;

            if (Math.abs(draggingStart - mouseX) > 100) {
                zoomed = true;

                double lowBound = draggingStart;
                if (draggingStart > mouseX) {
                    lowBound = mouseX;
                }

                double range = Math.abs(draggingStart - mouseX);

                List<Double> points = gStore.getPoints();

                //Junk number
                double smallX = -1.838576;
                double largeX = -1.838576;

                double rangeX = gStore.getRangeX();
                double minX = gStore.getMinX();

                for (double d : points) {
                    double tempD = (d - minX) * (width / rangeX);
                    if ((smallX == -1.838576 && tempD > lowBound) || (tempD > lowBound && tempD < smallX)) {
                        smallX = d;
                    }

                    if ((largeX == -1.838576 && tempD < lowBound + range) || (tempD > largeX && tempD < lowBound + range)) {
                        largeX = d;
                    }
                }

                gStore.setLargeSmallX(largeX, smallX);
            }
        }
    }

    public void keyPressed() {
        if (key == ENTER) {
            if (zoomed) {
                resetZoom();
            }
        }
    }

    public void planetIn(planets.PlanetObj planet) {
        planets.add(planet);
        this.scale = 1;
    }

    public void planetIn(planets.PlanetObj planet, double scale) {
        planets.add(planet);
        this.scale = scale;
    }

    public List<planets.PlanetObj> getPlanets() {
        return planets;
    }

    public void pause(boolean running) {
        if (zoomed) {
            resetZoom();
        }
        this.running = running;
    }

    public void resetZoom() {
        zoomed = false;
        gStore.resetZoom();
    }

    public void setLines(boolean lines) {
        drawLines = lines;
    }

    public void setDots(boolean dots) {
        drawDots = dots;
    }

    public boolean getDragging() {
        return dragging;
    }
}
