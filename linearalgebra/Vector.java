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



package linearalgebra;

import java.util.Arrays;

public class Vector {
	
// Fields
    private double[] components;
	
//------------------------------------------------------------------------------        
// Constructors
    // Creates a 1-dimensional zero vector.
    public Vector(){
	components = new double[1];
    }
	
    // Creates an n-dimensional zero vector.  If n <= 0, creates a 1-dimensional
    //  zero vector object.
    public Vector(int n){
        if (n > 0){
            components = new double[n];
        }
        else {
            components = new double[1];
        }
    }
	
    // Creates a vector with the components specified by the double array a.  If
    //  a is an empty array, creates a 1-dimensional zero vector object.
    public Vector(double[] a){
        if (a.length > 0){
            components = a;
        }
        else {
            components = new double[1];
        }
    }
	
    // Creates a 3-dimensional vector with the given arguments as components.
    public Vector(double a, double b, double c){
        components = new double[]{a,b,c};
    }
        
//------------------------------------------------------------------------------        
// Accessor Methods
    // Returns components field.  This is assumed to be in Cartesian coords.
    public double[] getComp(){
	return components;
    }
	
    // Returns the magnitude of the vector.
    public double getMag(){
	double a = 0;
	for (int i=0; i<components.length; i++){
            a += Math.pow(components[i], 2.0);
        }
	return Math.pow(a, 0.5);
    }
	
    // Returns the components of the active vector object in spherical
    //  coordinates where the angles are given in radians.  If the vector object
    //  is not 3-dimensional, the Cartesian coordinates are returned instead.
    public double[] getSphereComp(){
	if (components.length == 3){
            double[] a = new double[3];
            
            double x = components[0];
            double y = components[1];
            double z = components[2];
            
            // Setting radius.
            a[0] = getMag();
            
            // Setting theta.
            a[1] = Math.atan(y/x);
            
            // Setting phi.
            a[2] = Math.atan(Math.pow(x*x + y*y, 0.5)/z);
            
            // Readjusting quadrants.
            if ((x < 0.0) && (z > 0.0)){
                a[1] += Math.PI;
                if (a[1] > Math.PI){
                    a[1] -= 2.0*Math.PI;
                }
            }
            else if ((x > 0.0) && (z < 0.0)){
                a[2] += Math.PI;
            }
            else if ((x < 0.0) && (z < 0.0)){
                a[1] += Math.PI;
                if (y < 0.0){
                    a[2] -= Math.PI;
                }
                else {
                    a[2] += Math.PI;
                }
            }
            
            // Setting angles to within [-Pi, Pi].
            if ((a[1] < -1.0*Math.PI) || (a[1] > Math.PI)){
                a[1] = (a[1] - Math.signum(a[1])*2.0*Math.PI);
            }
            if ((a[2] < -1.0*Math.PI) || (a[2] > Math.PI)){
                a[2] = (a[2] - Math.signum(a[2])*2.0*Math.PI);
            }
            
            return a;
        }
        else {
            return components;
        }
    }	

//------------------------------------------------------------------------------
// Mutator Methods
    // Sets the component at index i of the currect vector object to the 
    //  double, d.
    public void set(int i, double d){
	components[i] = d;
    }
        
    // Add the vector v to the current vector object.
    public void add(Vector v){
        if (v.getComp().length == getComp().length){
            for (int i = 0; i<v.getComp().length; i++){
                set(i, getComp()[i]+v.getComp()[i]);
            }
        }
    }
        
    // Subtracts the vector v from the current vector object.
    public void subtract(Vector v){
        if (v.getComp().length == getComp().length){
            for (int i = 0; i<v.getComp().length; i++){
                set(i, getComp()[i] - v.getComp()[i]);
            }
        }
    }
        
    // Multiplies the current vector object by the scalar s.
    public void multiply(double s){
        for (int i = 0; i<getComp().length; i++){
            set(i, getComp()[i]*s);
        }
    }
        
    // Divides the current vector object by the scalar s.
    public void divide(double s){
        if (s != 0.0){
            for (int i = 0; i<getComp().length; i++){
                set(i, getComp()[i]/s);
            }
        }
    }
        
    // Rotates the current vector object by dPhi in the phi direction.
    public void rotatePhi(double dPhi){
        Vector newVec = new Vector(getComp().length);
        newVec = rotatePhi(this, dPhi);
        for (int i = 0; i<getComp().length; i++){
            set(i, newVec.getComp()[i]);
        }
    }
       
    // Rotates the current vector object by dTheta in the theta direction.
    public void rotateTheta(double dTheta){
        Vector newVec = new Vector(getComp().length);
        newVec = rotateTheta(this, dTheta);
        for (int i = 0; i<getComp().length; i++){
            set(i, newVec.getComp()[i]);
        }
    }
        
    // Rotates the current vector object by dTheta about the x axis.
    public void xRot(double dTheta){
        Vector newVec = Matrix.multiply(Matrix.xRot(dTheta), this);
        for (int i = 0; i<3; i++){
            set(i, newVec.getComp()[i]);
        }
    }
        
    // Rotates the current vector object by dTheta about the y axis.
    public void yRot(double dTheta){
        Vector newVec = Matrix.multiply(Matrix.yRot(dTheta), this);
        for (int i = 0; i<3; i++){
            set(i, newVec.getComp()[i]);
        }
    }
        
    // Rotates the current vector object by dTheta about the z axis.
    public void zRot(double dTheta){
        Vector newVec = Matrix.multiply(Matrix.zRot(dTheta), this);
        for (int i = 0; i<3; i++){
            set(i, newVec.getComp()[i]);
        }
    }
        
//------------------------------------------------------------------------------        
// Methods
    // Returns the Vector sum of the two vectors, a and b.
    public static Vector add(Vector a, Vector b){
    	double[] c = new double[a.getComp().length];
	for (int i=0; i<a.getComp().length; i++){
		c[i] = a.getComp()[i] + b.getComp()[i];
        }
        Vector d = new Vector(c);
        return d;
    }
        
    // Returns the Vector difference of the two vectors, a and b.
    public static Vector subtract(Vector a, Vector b){
        return add(a, multiply(b, -1.0));
    }
	
    // Returns the scalar product of the vector v and scalar s.
    public static Vector multiply(Vector v, double s){
        Vector v2 = new Vector(v.getComp().length);
        for (int i=0; i<v.getComp().length; i++){
		v2.set(i, s*v.getComp()[i]);}
	return v2;
    }
	
    // Returns the dot product of vectors a and b.
    public static double dot(Vector a, Vector b){
	double c = 0;
	for (int i=0; i<a.getComp().length; i++){
        	c += a.getComp()[i]*b.getComp()[i];
        }
	return c;
    }
	
    // Returns the cross product between vectors a and b.
    public static Vector cross(Vector a, Vector b){
	double[] c = new double[3];
	if (a.getComp().length == 3){
            c[0] = a.getComp()[1]*b.getComp()[2] - a.getComp()[2]*b.getComp()[1];
            c[1] = a.getComp()[2]*b.getComp()[0] - a.getComp()[0]*b.getComp()[2];
            c[2] = a.getComp()[0]*b.getComp()[1] - a.getComp()[1]*b.getComp()[0];
        }
        return new Vector(c);
    }
	
    // Returns the rectangular components of a vector which has its 
    //  components in spherical components.
    public static double[] getRectComp(double[] a){
	double[] b = new double[3];
	if (a.length == 3){
            b[0] = a[0]*Math.sin(a[2])*Math.cos(a[1]);
            b[1] = a[0]*Math.sin(a[2])*Math.sin(a[1]);
            b[2] = a[0]*Math.cos(a[2]);
        }
	return b;
    }
	
    // Returns the vector corresponding to the vector v rotated in the phi
    //  direction by dPhi radians.  
    public static Vector rotatePhi(Vector v, double dPhi){
	double[] a = v.getSphereComp();
	if (v.getComp().length == 3){
            a[2] += dPhi;
            a = getRectComp(a);
        }
	return new Vector(a);
    }
	
    // Returns the vector corresponding to the vector v rotated in the theta
    //  direction by dTheta radians.
    public static Vector rotateTheta(Vector v, double dTheta){
	double[] a = v.getSphereComp();
	if (v.getComp().length == 3){
            a[1] += dTheta;
            a = getRectComp(a);
        }
	return new Vector(a);
    }
	
    // Returns the vector corresponding to the vector v with its magnitude
    //  increased by dRho.
    public static Vector increaseMagnitudeBy(Vector v, double dRho){
	double[] a = v.getSphereComp();
	if (v.getComp().length == 3){
            a[0] += dRho;
            a = getRectComp(a);
        }
	return new Vector(a);
    }
        
    // Returns a vector object with the components of the argument vector
    //  made positive.
    public static Vector makePos(Vector v){
        Vector newVec = v;
        for (int i = 0; i<newVec.getComp().length; i++){
            if (newVec.getComp()[i] < 0.0){
                newVec.set(i, newVec.getComp()[i]*(-1.0));
            }
        }
        return newVec;
    }
        
    // Returns the dot product between the current Vector object and the 
    //  Vector v.
    public double dot(Vector v){
        double sum = 0.0;
        if (v.getComp().length == getComp().length){
            for (int i = 0; i<getComp().length; i++){
                sum += v.getComp()[i]*getComp()[i];
            }
        }
        return sum;
    }
        
    // Returns a vector object equal to the cross product between the
    //  Vector object and the vector v.  If dimensions of the two vectors
    //  are not both 3, returns zero vector.
    public Vector cross(Vector v){
        double[] c = new double[3];
	if (v.getComp().length == 3 && getComp().length == 3){
            c[0] = getComp()[1]*v.getComp()[2] - getComp()[2]*v.getComp()[1];
            c[1] = getComp()[2]*v.getComp()[0] - getComp()[0]*v.getComp()[2];
            c[2] = getComp()[0]*v.getComp()[1] - getComp()[1]*v.getComp()[0];
        }
        return new Vector(c);
    }
        
    // Returns a formatted String with the components of the active Vector
    //  object.
    public String printVector(){
        String st = "(";
        for (int i = 0; i<getComp().length; i++){
            st += getComp()[i];
            if (i != getComp().length - 1){
                st += ", ";
            }
        }
        st += ")";
        return st;
    }
        
        
        
	/* TODO:public static Vector rotatedBasis(Vector v, double[] b){}
	*/	
	
        /* TODO:public static double[] eigenValues(Vector v)
        */
        
        /* TODO:public static ArrayList<Vector> eigenVectors(Vector v)
        */
        
		
//==============================================================================
//  TESTING
        
    public static void main(String[] args){
        Vector a = new Vector(100, 100, 100);
        
        System.out.println(a.getMag());
        
        

    }
        

}
