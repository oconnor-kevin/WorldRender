/*
 * The MIT License
 *
 * Copyright 2015 kevinoconnor.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/* 
    Author: Kevin O'Connor 
    Email: worldrenderengine@gmail.com
    Summer 2015
*/


import java.awt.EventQueue;
import linearalgebra.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;


public class WorldRenderTest {
    
    public static void main(String[] args){
        
// Testing the collisionCheck method
/*        
        Particle part1 = new Particle(1, new Vector(new double[]{0.0,0.0,0.0}), new Vector(new double[]{0.0,0.0,1.0}));
        Particle part2 = new Particle(2, new Vector(new double[]{0.0,5.0,5.0}), new Vector(new double[]{0.0,0.0,0.0}));
        
        ArrayList<Particle> particles1 = new ArrayList<>();
        ArrayList<Particle> particles2 = new ArrayList<>();
        
        particles1.add(part1);
        particles2.add(part2);
        
        Matter matt1 = new Matter(particles1);
        Matter matt2 = new Matter(particles2);
        
        ObjectSpace os = new ObjectSpace();
        os.addMatter(matt1);
        os.addMatter(matt2);
        
        for (int i = 0; i<10; i++){
            System.out.println("Collision will occur? " + os.collisionCheck(1.0)); 
            os.incrementTime(1.0);        
            System.out.println(ObjectSpace.printObjectSpace(os));      
        }
*/
        
// Testing the ComplexNumber class
/*        
        ComplexNumber a = new ComplexNumber(5.0, 6.0);
        ComplexNumber b = new ComplexNumber(10.0, 3.0);
        
        System.out.println(ComplexNumber.printComplexNumber(ComplexNumber.toPower(a,2)));
*/
 
/*        
// Testing the Quadratic functionalities of the Polynomial class
        ArrayList<Double> coefs1 = new ArrayList<>();
        coefs1.add(1.0);
        coefs1.add(0.0);
        coefs1.add(1.0);
        
        ArrayList<Double> coefs2 = new ArrayList<>();
        coefs2.add(2.0);
        coefs2.add(1.0);
        
        Polynomial a = new Polynomial(coefs1);
        Polynomial b = new Polynomial(coefs2);
        
        System.out.println(a.isQuadratic());
        System.out.println(Polynomial.isQuadratic(a));
        System.out.println(ComplexNumber.printComplexNumber(Polynomial.quadraticRoots(a)[0]));
        System.out.println(ComplexNumber.printComplexNumber(Polynomial.quadraticRoots(a)[1]));
        
        System.out.println(b.isQuadratic());
        System.out.println(Polynomial.isQuadratic(b));
        System.out.println(ComplexNumber.printComplexNumber(Polynomial.quadraticRoots(b)[0]));
        System.out.println(ComplexNumber.printComplexNumber(Polynomial.quadraticRoots(b)[1]));
*/
        
// Testing Polynomial arithmetic
        /*
        ArrayList<Double> coefs1 = new ArrayList<>();
        coefs1.add(-5.0);
        coefs1.add(1.0);
        
        ArrayList<Double> coefs2 = new ArrayList<>();
        coefs2.add(1.0);
        coefs2.add(-2.0);
        coefs2.add(1.0);
        
        Polynomial a = new Polynomial(coefs1);
        Polynomial b = new Polynomial(coefs2);
        
        ArrayList<Double> coefs3 = new ArrayList<>();
        coefs3.add(1.0);
        coefs3.add(-1.0);
        coefs3.add(-1.0);
        coefs3.add(1.0);
        
        Polynomial c = new Polynomial(coefs3);
        

        System.out.println(Polynomial.printPolynomial(Polynomial.divide(c, a)));
        
        ArrayList<Double> coefs4 = new ArrayList<>();
        coefs4.add(1.0);
        coefs4.add(-4.0);
        coefs4.add(6.0);
        coefs4.add(-4.0);
        coefs4.add(1.0);
        
        Polynomial d = new Polynomial(coefs4);
        
        ArrayList<Double> coefs5 = new ArrayList<>();
        coefs5.add(1.0);
        coefs5.add(-3.0);
        coefs5.add(3.0);
        coefs5.add(-1.0);
        coefs5.add(-1.0);
        coefs5.add(3.0);
        coefs5.add(-3.0);
        coefs5.add(1.0);
        
        Polynomial e = new Polynomial(coefs5);
        
        System.out.println(Polynomial.isDivisibleBy(e, b));

        */
        
        
        // Testing polynomial root finder
        
        
        /* TODO:
        // PROBLEM 1
        // isDivisibleBy() does not consider a polynomial with getCoef(0) == 0.0 as being divisible 
        //  by x.  
        */
        /*
        Polynomial polyA = new Polynomial(new double[]{0.0, 1.0, 2.0, 3.0});
//        ArrayList<ComplexNumber> rootsA = Polynomial.roots(polyA);

        System.out.println(Polynomial.printPolynomial(Polynomial.divide(polyA, new Polynomial(new double[]{0.0, 1.0}))));
        System.out.println(Polynomial.isDivisibleBy(polyA, new Polynomial(new double[]{0.0, 1.0})));
        System.out.println((new Polynomial(new double[]{0.0, 1.0})).getOrder());

        for (int i = 0; i<rootsA.size(); i++){
            System.out.println(ComplexNumber.printComplexNumber(rootsA.get(i)));
        }
        
        System.out.println(Polynomial.printPolynomial(polyA));
        */
        
        /*
        // Testing ObjectSpaceScreen class
        
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                ObjectSpaceScreen screen = new ObjectSpaceScreen();
                screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                screen.setVisible(true);
            }
        });
        */
        
        // Testing the restructured Vector class.
        
       Vector vec1 = new Vector(new double[]{1.0,1.0,1.0});
       Vector vec2 = new Vector(new double[]{1.0,2.0,3.0});
       
       System.out.println(vec1.printVector());
       System.out.println((vec1.dot(vec2)));
       System.out.println(Vector.dot(vec1, vec2));
       System.out.println((vec1.cross(vec2)).printVector());
       System.out.println((Vector.cross(vec1, vec2)).printVector());
       System.out.println((Vector.multiply(vec1, 45.5)).printVector());
       vec1.subtract(vec2);
       System.out.println(vec1.printVector());
    }
}
