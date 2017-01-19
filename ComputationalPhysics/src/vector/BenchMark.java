package vector;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 *
 * @author Classmate
 */
public class BenchMark implements Iterator<Vector> {
    private int i;
    private Vector a;
    private Vector orthogonalUnit;
    public double d;
    
    public void setup() {
        a = new Vector(100,100);
        orthogonalUnit = new Vector(0,0,1);
        d = 0.1;
    }
    
    public boolean isFinished() {
        // there is nothing else to be seen...
        return false;
    }
    
    public Vector step() {
        return a.add(a.crossProduct(orthogonalUnit).normalize().mult(d));
    }
    
    public void end() {
        // finish up...
    }
    
    @Override
    public boolean hasNext() {
        return !isFinished();
    }
    @Override
    public Vector next() {
        if (hasNext())
            return step();
        throw new NoSuchElementException();
    }         
    
    public static void main(String[] args) {
        BenchMark sim = new BenchMark();
        while (sim.hasNext())
            sim.next();
        sim.end();
    }
}
