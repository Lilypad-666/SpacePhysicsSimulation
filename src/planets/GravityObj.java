package planets;

import java.util.List;
import processing.core.*;

public class GravityObj {
    PApplet pApp;
    String name;
    double x;
    double y;
    double mass;
    double gConst;
    double scale;
    boolean wrap;
    boolean dead;
    List<GravityObj> objects;
    vector.Vector position;
    vector.Vector velocity;
    vector.Vector acceleration;
    
    public GravityObj(PApplet pApp, String name, double x, double y, double mass, List<GravityObj> objects, double scale){
        this.pApp = pApp;
        this.name = name;
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.objects = objects;
        this.scale = scale;
        
        gConst = 6.67e-11;
        
        position = new vector.Vector(x, y);
        velocity = new vector.Vector();
        acceleration = new vector.Vector();
    }
    
    public double getX() {
        return position.i;
    }
    
    @Override
    public String toString(){
        return name + " " + mass;
    }

    public double getY() {
        return position.j;
    }
    
    public void setScale(double scale){
        this.scale = scale;
    }
    
    public double getScale(){
        return scale;
    }
    
    public void addYVel(double vel) {
        velocity.j += vel*(1/(Math.abs(scale/1e4)));
    }

    public void addXVel(double vel) {
        velocity.i += vel*(1/(Math.abs(scale/1e4)));
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
    
    public double distanceToNearest() {
        double closest = -1;
        for (GravityObj planet : objects) {
            if (planet.getX() != getX() && planet.getY() != getY()) {
                double distance = distanceToObject(planet.getName());

                if (closest == -1 || distance < closest) {
                    closest = distance;
                }
            }
        }

        return closest;
    }
    
    public double distanceToObject(String name) {
        for (GravityObj g : objects) {
            if (g.getName().equals(name)) {
                return (Math.sqrt(Math.pow((g.getX() - position.i), 2) + Math.pow((g.getY() - position.j), 2)));
            }
        }

        return -1;
    }
    
    public GravityObj getObject(String name) {
        for (GravityObj g : objects) {
            if (g.getName().equals(name)) {
                return g;
            }
        }

        return null;
    }
    
    public void doGravity() {

        for (GravityObj g : objects) {
            if (!g.getName().equals(name)) {
                
                vector.Vector r = vector.Vector.subtract(g.position, position).mult(scale);
                
                double mult = (gConst*mass*g.mass)/(r.mag*r.mag);
                
                vector.Vector force = vector.Vector.normalize(r).mult(mult);
                        
                acceleration = vector.Vector.div(force, mass);
                
                acceleration.div(scale);
                
                velocity.add(vector.Vector.mult(acceleration, PlanetSurface.dt));
            }
        }
    }
    
    public void doGravity(vector.Vector addVel, List<GravityObj> objects) {

        for (GravityObj g : objects) {
            if (!g.getName().equals(name)) {
                
                vector.Vector r = vector.Vector.subtract(g.position, position).mult(scale);
                
                double mult = (gConst*mass*g.mass)/(r.mag*r.mag);
                
                vector.Vector force = vector.Vector.normalize(r).mult(mult);
                        
                acceleration = vector.Vector.div(force, mass);
                
                acceleration.div(scale);
                
                velocity.add(vector.Vector.mult(acceleration, PlanetSurface.dt));
            }
        }
        
        velocity.add(addVel);
    }
    
    public vector.Vector getGravity(GravityObj g) {
        vector.Vector tempVel = new vector.Vector();
        
        if (!g.equals(this)){
        
            vector.Vector r = vector.Vector.subtract(g.position, position).mult(scale);

            double mult = (gConst*mass*g.mass)/(r.mag*r.mag);

            vector.Vector force = vector.Vector.normalize(r).mult(mult);

            vector.Vector tempAcc = vector.Vector.div(force, mass);

            tempAcc.div(scale);

            tempVel.add(vector.Vector.mult(tempAcc, PlanetSurface.dt));
        }
        
        return tempVel;
    }
    
    public vector.Vector getAcceleration(){
        return acceleration;
    }
    
    public void update(List<GravityObj> objects) {
        this.objects = objects;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }
    
    public void setMass(double mass){
        this.mass = mass;
    }
    
    public void step() {
        if (!dead){
            
            if (this instanceof ParticleObj){
                ParticleObj po = (ParticleObj)this;
                po.checkCollision();
            }

            doGravity();
            position.add(vector.Vector.mult(velocity, PlanetSurface.dt));

            if (wrap) {
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
    }
    
    public void step(vector.Vector addVel, List<GravityObj> objects) {
        if (!dead){
            
            if (this instanceof ParticleObj){
                ParticleObj po = (ParticleObj)this;
                po.checkCollision(objects);
            }

            doGravity(addVel, objects);
            position.add(vector.Vector.mult(velocity, PlanetSurface.dt));

            if (wrap) {
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
    }
    
    public void kill(){
        dead = true;
    }
    
    public void draw(){};
}
