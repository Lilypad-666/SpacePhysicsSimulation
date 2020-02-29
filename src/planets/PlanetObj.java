package planets;

import java.util.List;
import processing.core.*;

public class PlanetObj extends GravityObj{

    double size;
    boolean scaleSize;
    vector.Vector color;

    public PlanetObj(PApplet pApp, String name, double x, double y, double size, boolean scaleSize, double mass, List<GravityObj> objects, double scale) {
        super(pApp, name, x, y, mass, objects, scale);

        this.scaleSize = scaleSize;
        if (scaleSize)
            this.size = size/scale;
        else
            this.size = size;
        
        this.color = new vector.Vector(Math.random()*255, Math.random()*255, Math.random()*255);
    }
    
    public PlanetObj(PApplet pApp, String name, double x, double y, double size, double mass, List<GravityObj> objects, double scale) {
        super(pApp, name, x, y, mass, objects, scale);

        this.size = size;
        
        this.color = new vector.Vector(Math.random()*255, Math.random()*255, Math.random()*255);
    }
    
    public void putAtBottom(){
        position = new vector.Vector(pApp.width / 2,  ((5*pApp.height/6)+(size/2)));
    }
    
    public void setSize(double size){
        this.size = size;
    }

    @Override
    public void draw() {
        if (!dead){
            pApp.fill((float)color.i, (float)color.j, (float)color.k);
            pApp.stroke((float)color.i, (float)color.j, (float)color.k);
            pApp.ellipse((float) position.i, (float) position.j, (float) (size), (float) (size));
        }
    }

    public double getSize() {
        return size;
    }
    
    public double distanceToPlanet(String name) {
        for (GravityObj g : objects) {
            if (g instanceof PlanetObj){
                PlanetObj p = (PlanetObj)g;
                
                if (p.getName().equals(name)) {
                    return (Math.sqrt(Math.pow((p.getX() - position.i), 2) + Math.pow((p.getY() - position.j), 2))) - size / 2;
                }
            }
        }

        return -1;
    }
    
    public double distanceToNearestPlanet() {
        double closest = -1;
        for (GravityObj g : objects) {
            if (g instanceof PlanetObj){
                PlanetObj p = (PlanetObj)g;
                if (p.getX() != getX() && p.getY() != getY()) {
                    double distance = distanceToPlanet(p.getName());

                    if (closest == -1 || distance < closest) {
                        closest = distance;
                    }
                }
            }
        }

        return closest;
    }
    
    public PlanetObj getPlanet(String name) {
        for (GravityObj g : objects) {
            if (g instanceof PlanetObj){
                PlanetObj p = (PlanetObj)g;
            
                if (p.getName().equals(name)) {
                    return p;
                }
            }
        }

        return null;
    }

    public double distanceToPlanetSurface(String name) {
        for (GravityObj g : objects) {
            if (g instanceof PlanetObj){
                PlanetObj p = (PlanetObj)g;
            
                if (p.getName().equals(name)) {
                    double distance = (Math.sqrt(Math.pow((p.getX() - position.i), 2) + Math.pow((p.getY() - position.j), 2))) - size / 2;

                    return distance - getPlanet("earth").getSize() / 2;
                }
            }
        }

        return -1;
    }
}
