package vector;

import processing.core.*;

/**
 *
 * @author Classmate
 */
public class Vector extends PApplet {

    public double i;
    public double j;
    public double k;
    public double mag;
    private double normalScale;

    /**
     * sets magnitude and normalScale at the beginning so you only have to get
     * the fields instead of calculate them every time, normalScale so you only
     * have to divide once
     *
     * @param i x component of the vector
     * @param j y component of the vector
     * @param k z component of the vector
     */
    public Vector(double i, double j, double k) {
        this.i = i;
        this.j = j;
        this.k = k;
        mag = Math.sqrt(i * i + j * j + k * k);
        normalScale = 1 / mag;
    }

    public Vector() {
        this(0, 0, 0);
    }

    public Vector(double i, double j) {
        this(i, j, 0);
    }

    /**
     *
     * @return the string representation of this vector
     */
    public String toString() {
        return "<" + i + ", " + j + ", " + k + ">";
    }
    
    /**
     * keeps the direction the same but divides the magnitude
     *
     * @param scale the scale to divide the vector by
     * @return new vector in the same direction as this with a magnitude that is
     * scale times smaller than the original
     */
    public Vector div(double scale) {
        i /= scale;
        j /= scale;
        k /= scale;
        mag = Math.sqrt(i * i + j * j + k * k);
        normalScale = 1 / mag;
        return this;
    }

    /**
     * returns a vector with the same direction but a different magnitude
     *
     * @param v vector to divide by scale
     * @param scale the scale to divide the vector by
     * @return new vector in the same direction as this with a magnitude that is
     * scale times smaller than the original
     */
    public static Vector div(Vector v, double scale) {
        return new Vector(v.i / scale, v.j / scale, v.k / scale);
    }

    /**
     * keeps the direction the same but multiplies the magnitude
     *
     * @param scale the scale to multiply the vector by
     * @return new vector in the same direction as this with a magnitude that is
     * scale times larger than the original
     */
    public Vector mult(double scale) {
        i *= scale;
        j *= scale;
        k *= scale;
        mag = Math.sqrt(i * i + j * j + k * k);
        normalScale = 1 / mag;
        return this;
    }

    /**
     * returns a vector with the same direction but a different magnitude
     *
     * @param v vector to multiply by scale
     * @param scale the scale to multiply the vector by
     * @return new vector in the same direction as this with a magnitude that is
     * scale times larger than the original
     */
    public static Vector mult(Vector v, double scale) {
        return new Vector(scale * v.i, scale * v.j, scale * v.k);
    }

    /**
     * adds this vector to another vector and returns the resultant
     *
     * @param other the vector to add to this
     * @return the resultant vector of this+other
     */
    public Vector add(Vector other) {
        i += other.i;
        j += other.j;
        k += other.k;
        mag = Math.sqrt(i * i + j * j + k * k);
        normalScale = 1 / mag;
        return this;
    }

    /**
     * returns the sum of vector a and b
     *
     * @param a a vector to add
     * @param b another vector to add
     * @return the resultant vector of a+b
     */
    public static Vector add(Vector a, Vector b) {
        return new Vector(a.i + b.i, a.j + b.j, a.k + b.k);
    }

    /**
     * makes this the negative of this vector
     *
     * @return the negative (complement) of this vector
     */
    public Vector neg() {
        return mult(-1);
    }

    /**
     * returns the negative of vector v
     *
     * @param v a vector to get the negative of
     * @return the negative (complement) of v
     */
    public static Vector neg(Vector v) {
        return mult(v, -1);
    }

    /**
     * subtracts other from this, returns this-other
     *
     * @param other the vector to subtract from this vector
     * @return the resultant of this-other
     */
    public Vector subtract(Vector other) {
        return add(neg(other));
    }

    /**
     * returns the difference of vector a and b
     *
     * @param a initial vector
     * @param b vector to subtract from a
     * @return the resultant vector of a-b
     */
    public static Vector subtract(Vector a, Vector b) {
        return add(a, neg(b));
    }

    /**
     * sets this vector to its unit vector
     *
     * @return the unit vector of this
     */
    public Vector normalize() {
        return mult(normalScale);
    }

    /**
     * returns the unit vector in the same direction as v
     *
     * @param v vector to normalize
     * @return new vector in the same direction as this with a magnitude of 1
     */
    public static Vector normalize(Vector v) {
        return mult(v, v.normalScale);
    }

    /**
     * returns the vector product of this vector and another vector
     *
     * @param other the vector to cross with this
     * @return the cross product of this and other (this x other)
     */
    public Vector crossProduct(Vector other) {
        return new Vector(j * other.k - k * other.j,
                k * other.i - i * other.k,
                i * other.j - j * other.i);
    }

    /**
     * returns the vector product of a and b
     *
     * @param a first vector
     * @param b second vector
     * @return the cross product of a and b (a x b)
     */
    public static Vector crossProduct(Vector a, Vector b) {
        return new Vector(a.j * b.k - a.k * b.j,
                a.k * b.i - a.i * b.k,
                a.i * b.j - a.j * b.i);
    }

    /**
     * returns the scalar product of this vector and another vector
     *
     * @param other vector to get the dot with
     * @return dot product of this and other (this•other)
     */
    public double dotProduct(Vector other) {
        return i * other.i + j * other.j + k * other.k;
    }

    /**
     * returns the scalar product of a and b
     *
     * @param a first vector
     * @param b second vector
     * @return the dot product of a and b (returns a•b)
     */
    public static double dotProduct(Vector a, Vector b) {
        return a.i * b.i + a.j * b.j + a.k * b.k;
    }

    public double heading() {
        return (Math.atan2(j, i));
    }

    public static double heading(Vector other) {
        return (Math.atan2(other.j, other.i));
    }
}
