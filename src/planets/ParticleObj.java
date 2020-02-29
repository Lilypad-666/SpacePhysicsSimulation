package planets;

import java.util.List;
import processing.core.*;

public class ParticleObj extends PlanetObj{
    
    boolean grow;
    
    public ParticleObj(PApplet pApp, String name, double x, double y, double size, double mass, List<GravityObj> objects, double scale) {
        super(pApp, name, x, y, size, mass, objects, scale);
        grow = true;
    }
    
    public void setGrow(boolean grow){
        this.grow = grow;
    }
    
    public void checkCollision(){
        for (GravityObj g : objects) {
            if (!g.dead && g instanceof ParticleObj){
                ParticleObj p = (ParticleObj)g;
                if (!p.equals(this) && p.velocity.mag/p.mass > velocity.mag/mass) {
                    double distance = distanceToParticle(p.getName());
                    
                    if (distance <= p.getSize()/2){
                        vector.Vector temp1 = vector.Vector.mult(velocity, mass);
                        vector.Vector temp2 = vector.Vector.mult(p.velocity, p.mass);
                        
                        vector.Vector top = vector.Vector.add(temp1, temp2);
                        
                        double bottom = mass + p.mass;
                        
                        velocity = vector.Vector.div(top, bottom);

                        if(grow)
                            size += p.getSize()/4;

                        mass += p.getMass();

                        p.kill();

                        p.size = 0;

                        p.mass = 0;
                        
                    }
                }
            }
        }
    }
    
    public void checkCollision(List<GravityObj> objects){
        for (GravityObj g : objects) {
            if (!g.dead && g instanceof ParticleObj){
                ParticleObj p = (ParticleObj)g;
                if (!p.equals(this) && p.velocity.mag/p.mass > velocity.mag/mass) {
                    double distance = distanceToParticle(p.getName());
                    
                    if (distance <= p.getSize()/2){
                        vector.Vector temp1 = vector.Vector.mult(velocity, mass);
                        vector.Vector temp2 = vector.Vector.mult(p.velocity, p.mass);
                        
                        vector.Vector top = vector.Vector.add(temp1, temp2);
                        
                        double bottom = mass + p.mass;
                        
                        velocity = vector.Vector.div(top, bottom);

                        if(grow)
                            size += p.getSize()/4;

                        mass += p.getMass();

                        p.kill();

                        p.size = 0;

                        p.mass = 0;
                        
                    }
                }
            }
        }
    }
    
    public double distanceToNearestParticle() {
        double closest = -1;
        for (GravityObj g : objects) {
            if (g instanceof ParticleObj){
                ParticleObj p = (ParticleObj)g;
                if (!p.equals(this)) {
                    double distance = distanceToParticle(p.getName());

                    if (closest == -1 || distance < closest) {
                        closest = distance;
                    }
                }
            }
        }

        return closest;
    }
    
    public double distanceToParticle(String name) {
        for (GravityObj g : objects) {
            if (g instanceof ParticleObj && g.getName().equals(name)){
                ParticleObj p = (ParticleObj)g;
                return (Math.sqrt(Math.pow((p.getX() - position.i), 2) + Math.pow((p.getY() - position.j), 2))-(size/2));
            }
        }

        return -1;
    }
}
