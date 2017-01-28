package ball;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class PlanetObj{
    double size;
    double x;
    double y;
    double yVel;
    double xVel;
    double mass;
    double scale;
    String name;
    double g;
    List<PlanetObj> planets;
    PApplet pApp;
    
    public PlanetObj(PApplet pApp, String name, double x, double y, double size, double mass, List<PlanetObj> planets){
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = size;
        this.mass = mass;
        
        scale = 1;
        
        this.planets = planets;
        
        this.pApp = pApp;
        
        g = 6.67e-11;
        
        yVel = 0;
        xVel = 0;
    }
    
    public void draw(){
        pApp.ellipse((float)x, (float)y, (float)size, (float)size);
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getSize(){
        return size;
    }
    
    public void addYVel(double vel){
        yVel += vel;
    }
    
    public void addXVel(double vel){
        xVel += vel;
    }
    
    public double getYVel(){
        return yVel;
    }
    
    public double getXVel(){
        return xVel;
    }
    
    public double getMass(){
        return mass;
    }
    
    public void doGravity(){
        double xDir = 0;
        double yDir = 0;
        
        for (PlanetObj planet : planets){
            if (planet.getX() != getX() && planet.getY() != getY()){
                double planetMass = planet.getMass();
                double distance = Math.sqrt(Math.pow((planet.getX() - x), 2) + Math.pow((planet.getY() - y), 2));
                
                double acceleration = (g * planetMass)/distance;

                double deltaX = planet.getX()- x;
                double deltaY = planet.getY() - y;
                double direction = Math.atan2(deltaY, deltaX);
                
                xDir += scale*(acceleration * Math.cos(direction));
                yDir += scale*(acceleration * Math.sin(direction));
            }
        }
        
        xVel += xDir;
        yVel += yDir;
    }
    
    public void step(){
        doGravity();
        x += xVel;
        y += yVel;
    }
    
    public void update(List<PlanetObj> planets){
        this.planets = planets;
    }
}
