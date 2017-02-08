package planets;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;

public class NBodySquare {
    List<GravityObj> objects;
    NBodySquare[][] otherSquares;
    vector.Vector totalVel;
    vector.Vector position;
    PApplet pApp;
    double scale;
    double gConst;
    
    public NBodySquare(PApplet pApp, int row, int column, double x, double y){
        objects = new ArrayList<>();
        otherSquares = new NBodySquare[row][column];
        totalVel = new vector.Vector();
        position = new vector.Vector(x, y);
        gConst = 6.67e-11;
        this.pApp = pApp;
    }
    
    public void setScale(double scale){
        this.scale = scale;
    }
    
    public void addObj(GravityObj obj){
        objects.add(obj);
    }
    
    public void clear(){
        objects.clear();
        totalVel.i = 0;
        totalVel.j = 0;
        totalVel.k = 0;
    }
    
    public vector.Vector getTotalVelocity(){
        vector.Vector direction = new vector.Vector();
        
        objects.forEach((o)->direction.add(o.velocity));
        
        return direction;
    }
    
    public vector.Vector getTotalGravity(){
        vector.Vector tempVel = new vector.Vector();
        for (GravityObj o : objects) {
            for (GravityObj g : objects){
                tempVel.add(o.getGravity(g));
            }
        }
        return tempVel;
    }
    
    public double totalMass(){
        double totalMass = 0;
        for (GravityObj o : objects) {
            totalMass += o.mass;
        }
        
        return totalMass;
    }
    
    public vector.Vector calc(NBodySquare g){
        vector.Vector r = vector.Vector.subtract(g.position, position).mult(scale);
        
        double mult = (gConst*totalMass()*g.totalMass())/(r.mag*r.mag);
        
        if (mult == 0){
                
            vector.Vector force = vector.Vector.normalize(r).mult(mult);

            vector.Vector acceleration = vector.Vector.div(force, totalMass());

            acceleration.div(scale);

            return vector.Vector.mult(acceleration, PlanetSurface.dt);
        }
        
        return new vector.Vector();
        
    }
    
    public void doGravity(){
        for (int rowNdx = 0; rowNdx < otherSquares.length; rowNdx++){
            for (int columnNdx = 0; columnNdx < otherSquares[0].length; columnNdx++){
                if (!otherSquares[rowNdx][columnNdx].equals(this)){
                    totalVel.add(otherSquares[rowNdx][columnNdx].calc(this));
                    System.out.println(totalVel);
                }
            }
        }
        
        for (GravityObj o : objects){
            o.step(totalVel, objects);
            //o.velocity.add(totalVel);
        }
        pApp.textAlign(pApp.CENTER);
        //pApp.text((float)totalMass(), (float)position.i, (float)position.j);
    }
    
    public void setSquares(NBodySquare[][] squares){
        otherSquares = squares;
    }
}
