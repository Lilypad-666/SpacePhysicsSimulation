package TestCases;


import Vectors.Vector;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 100000000; i++) {
            Vector a = new Vector(Math.random()*100,Math.random()*100,Math.random()*100);
            Vector b = new Vector(Math.random()*100,Math.random()*100,Math.random()*100);
            Vector c = a.add(b);
            Vector mul = a.mult(a.dotProduct(b));
            Vector nor = a.normalize();
            Vector d = a.crossProduct(b);
            Vector e = b.crossProduct(a);
            Vector sumshouldbe0 = d.add(e);
            if (sumshouldbe0.mag!=0.0) {
                System.out.println("not working");
            }
        }
//        Vector a = new Vector(1,3,-5);
//        Vector b = new Vector(4,-2,-1);
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);
//        System.out.println("mag(a) = " + a.mag);
//        System.out.println("mag(b) = " + b.mag);
//        
//        System.out.println("a•b = " + a.dotProduct(b));
//        System.out.println("a•a = " + a.dotProduct(a));
//        System.out.println("b•a = " + b.dotProduct(a));
//        System.out.println("a+b = " + a.add(b));
//        System.out.println("a-b = " + a.subtract(b));
//        System.out.println("b-a = " + b.subtract(a));
//        System.out.println("axb = " + a.crossProduct(b));
//        System.out.println("axa = " + a.crossProduct(a));
//        System.out.println("bxa = " + b.crossProduct(a));
    }
}
