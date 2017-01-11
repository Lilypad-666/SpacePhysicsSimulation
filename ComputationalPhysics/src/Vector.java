
/**
 *
 * @author Classmate
 */
public class Vector {
    /*
    NOTES:
    static methods may not be necessary (commented out rn)
    might not want to encapsulate
    might want to encapsulate more? (switch all x to x())?
    can simplify operations to make some dependent on others, calls may slow it down tho
    */
    
    public final double x;
    public final double y;
    public final double z;
    public final double mag;
    
    //creates a new vector with an x, y, and z component
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        mag = Math.sqrt(x*x + y*y + z*z);
    }
    
    //prints out a representation of the vector
    public String toString() {
        return "<" + x + ", " + y + ", " + z + ">";
    }
    
    //if this vector and another vector are the same (same magnitude, same direction)
    public boolean equals(Vector other) {
        return x == other.x & y == other.y & z == other.z;
    }
    
    
//    //returns the magnitude of vector a
//    public static double mag(Vector a) {
//        return a.mag;
//    }
    
    //returns a new vector in the same direction but scale times larger
    public Vector mult(double scale) {
        return new Vector(scale*x, scale*y, scale*z);
    }
    
    //returns this vector plus another vector (returns this+other)
    public Vector add(Vector other) {
        return new Vector(x+other.x, y+other.y, z+other.z);
    }
    
//    //returns a plus b (returns a+b)
//    public static Vector add(Vector a, Vector b) {
//        return new Vector(a.x+b.x, a.y+b.y, a.z+b.z);
//    }
    
    //returns the negative of this vector (return -this)
    public Vector negative() {
        return new Vector(-x, -y, -z);
    }
    
    //returns this vector minus another vector (returns this-other)
    public Vector subtract(Vector other) {
        return new Vector(x-other.x, y-other.y, z-other.z);
    }
    
//    //returns a minus b (returns a-b)
//    public static Vector subtract(Vector a, Vector b) {
//        return add(a, b.negative());
//    }
    
    //returns a vector in the same direction, but with a magnitude of 1
    public Vector normalize() {
        //only 1 division here, then all multiplication for max efficiency
        double scale = 1/mag;
        return new Vector(scale*x, scale*y, scale*z);
    }
    
//    //returns a vector in the same direction, but with a magnitude of 1
//    public static Vector normalize(Vector v) {
//        double scale = 1/v.mag;
//        return new Vector(scale*x, scale*y, scale*z);
//    }
    
    //returns the cross product of this vector and another vector (returns this x other)
    public Vector crossProduct(Vector other) {
        return new Vector(y*other.z-z*other.y, 
                          z*other.x-x*other.z, 
                          x*other.y-y*other.x);
    }
    
//    //returns the cross product of a and b (returns a x b)
//    public static Vector crossProduct(Vector a, Vector b) {
//        return new Vector(a.y*b.z-a.z*b.y, 
//                          a.z*b.x-a.x*b.z, 
//                          a.x*b.y-a.y*b.x);
//    }
    
    //returns the dot product of this vector and another vector (returns this•other)
    public double dotProduct(Vector other) {
        return x*other.x + y*other.y + z*other.z;
    }
    
//    //returns the dot product of a and b (returns a•b)
//    public static double dotProduct(Vector a, Vector b) {
//        return a.x*b.x + a.y*b.y + a.z*b.z;
//    }
}
