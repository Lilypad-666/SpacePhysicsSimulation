package planets;

import java.util.List;
import processing.core.*;

public class PlanetObj {

    double size;
    vector.Vector position;
    vector.Vector velocity;
    double mass;
    double scale;
    String name;
    double g;
    List<PlanetObj> planets;
    PApplet pApp;
    boolean wrap = false;

    public PlanetObj(PApplet pApp, String name, double x, double y, double size, double mass, List<PlanetObj> planets) {
        this.name = name;
        
        position = new vector.Vector(x, y);

        this.size = size;
        this.mass = mass;

        this.scale = 1;

        this.planets = planets;

        this.pApp = pApp;

        g = 6.67e-11;

        velocity = new vector.Vector(0, 0, 0);
    }

    public PlanetObj(PApplet pApp, String name, double x, double y, double size, double mass, List<PlanetObj> planets, double scale) {
        this.name = name;

        this.scale = scale;

        this.size = size;
        this.mass = mass;

        
        position = new vector.Vector(x, y);

        this.planets = planets;

        this.pApp = pApp;

        g = 6.67e-11;

        velocity = new vector.Vector(0, 0, 0);
    }
    
    public void setScale(double scale){
        this.scale = scale;
    }
    
    public void putAtBottom(){
        position = new vector.Vector(pApp.width / 2, 5 * pApp.height / 6 + (this.size) / 2);
    }

    public void draw() {
        pApp.ellipse((float) position.i, (float) position.j, (float) (size), (float) (size));
    }

    public double getX() {
        return position.i;
    }

    public double getY() {
        return position.j;
    }

    public double getSize() {
        return size;
    }

    public void addYVel(double vel) {
        velocity.j += vel;
    }

    public void addXVel(double vel) {
        velocity.i += vel;
    }

    public double getYVel() {
        return velocity.j;
    }

    public double getXVel() {
        return velocity.i;
    }

    public double getMass() {
        return mass;
    }

    public String getName() {
        return name;
    }

    public PlanetObj getPlanet(String name) {
        for (PlanetObj p : planets) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    public double distanceToNearest() {
        double closest = -1;
        for (PlanetObj planet : planets) {
            if (planet.getX() != getX() && planet.getY() != getY()) {
                double distance = distanceToPlanet(planet.getName());

                if (closest == -1 || distance < closest) {
                    closest = distance;
                }
            }
        }

        return closest;
    }

    public double distanceToPlanet(String name) {
        for (PlanetObj planet : planets) {
            if (planet.getName().equals(name)) {
                return (Math.sqrt(Math.pow((planet.getX() - position.i), 2) + Math.pow((planet.getY() - position.j), 2))) - size / 2;
            }
        }

        return -1;
    }

    public double distanceToPlanetSurface(String name) {
        for (PlanetObj planet : planets) {
            if (planet.getName().equals(name)) {
                double distance = (Math.sqrt(Math.pow((planet.getX() - position.i), 2) + Math.pow((planet.getY() - position.j), 2))) - size / 2;

                return distance - getPlanet("earth").getSize() / 2;
            }
        }

        return -1;
    }

    public void doGravity() {

        for (PlanetObj planet : planets) {
            if (!planet.getName().equals(name)) {
                /*double planetMass = planet.getMass();

                double distance = Math.sqrt(Math.pow((planet.getX() - position.i), 2) + Math.pow((planet.getY() - position.j), 2))/scale;
                
                double acc = (g * planetMass) / distance;
                
                acc *= scale;

                double deltaX = planet.getX() - position.i;
                double deltaY = planet.getY() - position.j;
                double direction = Math.atan2(deltaY, deltaX);

                dir.i += (acc * Math.cos(direction));
                dir.j += (acc * Math.sin(direction));*/
                
                vector.Vector r = vector.Vector.subtract(planet.position, position).mult(scale);
                
                double mult = (g*mass*planet.mass)/(r.mag*r.mag);
                
                vector.Vector force = vector.Vector.normalize(r).mult(mult);
                        
                vector.Vector acceleration = vector.Vector.div(force, mass);
                
                velocity.add(vector.Vector.mult(acceleration, PlanetSurface.dt));
            }
        }
    }

    public void step() {
        doGravity();
        position.add(vector.Vector.mult(velocity, PlanetSurface.dt));

        if (wrap && !name.equals("earth")) {
            if (position.i < 0) {
                position.i = pApp.width - 1;
            }

            if (position.i > pApp.width) {
                position.i = 1;
            }

            if (position.j < 0) {
                position.j = pApp.height - 1;
            }

            if (position.j > pApp.height) {
                position.j = 1;
            }
        }
    }

    public void update(List<PlanetObj> planets) {
        this.planets = planets;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }
}
