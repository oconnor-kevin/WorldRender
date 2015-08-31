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

    // Creates a Vector object that is a clone of the argument Vector object.
    public Vector(Vector v){
        components = new double[v.getComp().length];
        
        for (int i = 0; i<v.getComp().length; i++){
            components[i] = v.getComp()[i];
        }
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
    //  Theta has a range of [0, 2Pi], Phi has a range of [0, Pi].
    public double[] getSphereComp(){
	if (components.length == 3){
            double[] a = new double[3];
            
            double x = components[0];
            double y = components[1];
            double z = components[2];
            
            // Setting radius.
            a[0] = getMag();
            
            // Setting theta.
            if ((Math.signum(x) >= 0.0) && (Math.signum(y) > 0.0)){
                a[1] = Math.atan(y/x);
            }
            else if ((Math.signum(x) < 0.0) && (Math.signum(y) >= 0.0)){
                a[1] = Math.PI + Math.atan(y/x);
            }
            else if ((Math.signum(x) < 0.0) && (Math.signum(y) < 0.0)){
                a[1] = Math.PI + Math.atan(y/x);
            }
            else if ((Math.signum(x) >= 0.0) && (Math.signum(y) < 0.0)){
                a[1] = 2.0*Math.PI + Math.atan(y/x);
            }
            else {
                a[1] = 0.0;
            }
                
            // Setting phi.
            if (Math.signum(z) > 0.0){
                a[2] = Math.atan(Math.pow(x*x + y*y, 0.5)/z);
            }
            else if (Math.signum(z) < 0.0){
                a[2] = Math.PI + Math.atan(Math.pow(x*x + y*y, 0.5)/z);
            }
            else {
                a[2] = 0.5*Math.PI;
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
        
        // Eventually, this method should throw an exception if the two vectors
        //  do not have the same length.  
        else {
            System.out.println("Vectors of different dimensions added.");
        }
    }
        
    // Subtracts the vector v from the current vector object.
    public void subtract(Vector v){
        if (v.getComp().length == getComp().length){
            for (int i = 0; i<v.getComp().length; i++){
                set(i, getComp()[i] - v.getComp()[i]);
            }
        }
        
        // Eventually, this method should throw an exception if the two vectors
        //  do not have the same length.  
        else {
            System.out.println("Vectors of different dimensions subtracted.");
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
        double theta = getSphereComp()[1];
        
        // Perform rotations.
        rotateTheta(-1.0*theta);
        yRot(-1.0*dPhi);
        rotateTheta(theta);
    }
       
    // Rotates the current vector object by dTheta in the theta direction.  This
    //  corresponds simply to a counter-clockwise rotation about the z-axis.
    public void rotateTheta(double dTheta){
        zRot(-1.0*dTheta);
    }
        
    // Rotates the current vector object by dTheta clockwise about the x axis.
    public void xRot(double dTheta){
        Vector newVec = Matrix.multiply(Matrix.xRot(dTheta), this);
        for (int i = 0; i<3; i++){
            set(i, newVec.getComp()[i]);
        }
    }
        
    // Rotates the current vector object by dTheta clockwise about the y axis.
    public void yRot(double dTheta){
        Vector newVec = Matrix.multiply(Matrix.yRot(dTheta), this);
        for (int i = 0; i<3; i++){
            set(i, newVec.getComp()[i]);
        }
    }
        
    // Rotates the current vector object by dTheta clockwise about the z axis.
    public void zRot(double dTheta){
        Vector newVec = Matrix.multiply(Matrix.zRot(dTheta), this);
        for (int i = 0; i<3; i++){
            set(i, newVec.getComp()[i]);
        }
    }
        
    // Rotates the vector clockwise around an axis defined by the Vector 
    //  argument, axis.
    public void rotateAroundAxis(Vector axis, double ang){
        Vector sphereComp = new Vector(axis.getSphereComp());
        double theta = sphereComp.getComp()[1];
        double phi = sphereComp.getComp()[2];
        zRot(theta);
        yRot(phi);
        zRot(ang);
        yRot(-1.0*phi);
        zRot(-1.0*theta);
    }
    
//------------------------------------------------------------------------------        
// Methods
    // Returns the Vector sum of the two vectors, a and b.  If they have 
    //  different dimensions, will print a message to the system and return a
    //  zero vector.
    public static Vector add(Vector a, Vector b){
        // Checks that the two vectors have the same dimensionality.
        if (a.getComp().length == b.getComp().length){
            double[] c = new double[a.getComp().length];
            for (int i=0; i<a.getComp().length; i++){
                c[i] = a.getComp()[i] + b.getComp()[i];
            }
            Vector d = new Vector(c);
            return d;
        }
        
        // Else prints a warning and returns a zero Vector.
        else {
            System.out.println("Vectors of different dimensions added.");
            return null;
        }
    }
        
    // Returns the Vector difference of the two vectors, a and b.  If they have
    //  different dimensions, will print a message to the system and return 
    //  null.
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
	
    // Returns the dot product of vectors a and b.  If they have unequal 
    //  dimensionality, prints message and returns 0.0.
    public static double dot(Vector a, Vector b){
        // Check first to see that the two vectors have equal dimensionality.
        if (a.getComp().length == b.getComp().length){
            double c = 0;
            for (int i=0; i<a.getComp().length; i++){
        	c += a.getComp()[i]*b.getComp()[i];
            }
            return c;
        }
        
        // If they have unequal dimensionality, print message and return 0.0.
        else {
            System.out.println("Attempted dot product operation between Vectors of unequal dimensionality.");
            return 0.0;
        }
    }
	
    // Returns the cross product between vectors a and b.
    public static Vector cross(Vector a, Vector b){
        // Check that dimensions of the two Vectors are equal.
        if (a.getComp().length == b.getComp().length){
            double[] c = new double[3];
            if (a.getComp().length == 3){
                c[0] = a.getComp()[1]*b.getComp()[2] - a.getComp()[2]*b.getComp()[1];
                c[1] = a.getComp()[2]*b.getComp()[0] - a.getComp()[0]*b.getComp()[2];
                c[2] = a.getComp()[0]*b.getComp()[1] - a.getComp()[1]*b.getComp()[0];
            }
            return new Vector(c);
        }
        
        // If unequal, print message and return zero vector.
        else {
            System.out.println("Attempted cross product operation between Vectors of unequal dimensionality.");
            return new Vector();
        }
    }
	
    // Returns the rectangular components of a vector which has its 
    //  components in spherical components.  If the array has length other than
    //  3, prints a message and returns a 3-dimensional zero vector.
    public static double[] getRectComp(double[] a){
	double[] b = new double[3];
        
        // Checks length of the array.
	if (a.length == 3){
            b[0] = a[0]*Math.sin(a[2])*Math.cos(a[1]);
            b[1] = a[0]*Math.sin(a[2])*Math.sin(a[1]);
            b[2] = a[0]*Math.cos(a[2]);
        }
        
        else {
            System.out.println("getRectComp operation attempted for array with length != 3.");
        }
	return b;
    }
	
    // Returns the vector corresponding to the vector v rotated in the phi
    //  direction by dPhi radians.  
    public static Vector rotatePhi(Vector v, double dPhi){
	Vector newVec = new Vector(v);
        newVec.rotatePhi(dPhi);
        return newVec;
    }
	
    // Returns the vector corresponding to the vector v rotated in the theta
    //  direction by dTheta radians.
    public static Vector rotateTheta(Vector v, double dTheta){
	Vector newVec = new Vector(v);
        newVec.rotateTheta(dTheta);
        return newVec;
    }
	
    // Returns the vector corresponding to the vector v with its magnitude
    //  increased by dRho.
    public static Vector increaseMagnitudeBy(Vector v, double dRho){
	double[] a = v.getSphereComp();
        
        // Check that dimension of v is 3.
	if (v.getComp().length == 3){
            if (a[0] + dRho >= 0.0){
               a[0] += dRho;
               a = getRectComp(a);
               return new Vector(a);
            }
            else {
                System.out.println("Negative radial component error.");
                return null;
            }
        }
        
        // If dimension other than 3, print message and return null.
        else {
            System.out.println("increaseMagnitudeBy operation attempted on Vector with dimension other than 3.");
            return null;
        }
    }
        
    // Returns a vector object with the components of the argument vector
    //  made positive.
    public static Vector makePos(Vector v){
        Vector newVec = new Vector(v);
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
        
        // Check that the two vectors have equal dimensionality.
        if (v.getComp().length == getComp().length){
            for (int i = 0; i<getComp().length; i++){
                sum += v.getComp()[i]*getComp()[i];
            }
        return sum;
        }
        
        // Otherwise print a message and return 0.0.
        else {
            System.out.println("Dot product operation attempted on Vectors with unequal dimensions.");
            return 0.0;
        }
    }
        
    // Returns a vector object equal to the cross product between the
    //  Vector object and the vector v.  If dimensions of the two vectors
    //  are not both 3, returns zero vector.
    public Vector cross(Vector v){
        double[] c = new double[3];
        
        // Checks that vector dimensions are equal.
	if (v.getComp().length == 3 && getComp().length == 3){
            c[0] = getComp()[1]*v.getComp()[2] - getComp()[2]*v.getComp()[1];
            c[1] = getComp()[2]*v.getComp()[0] - getComp()[0]*v.getComp()[2];
            c[2] = getComp()[0]*v.getComp()[1] - getComp()[1]*v.getComp()[0];
        return new Vector(c);
        }
        
        // If unequal, prints warning message and returns null.
        else {
            System.out.println("Cross product operation attempted on Vectors with unequal dimensions.");
            return null;
        }
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
            
    // Returns a vector in the same direction as the argument vec and with the 
    //  magnitude mag.
    public static Vector assignMag(Vector vec, double mag){
        Vector vec2 = multiply(vec, 1.0/vec.getMag());
        vec2.multiply(mag);
        return vec2;
    }
	
    /* TODO:public static double[] eigenValues(Vector v)
        */
        
    /* TODO:public static ArrayList<Vector> eigenVectors(Vector v)
        */
        
		
//==============================================================================
//  TESTING
        
    public static void main(String[] args){
        Vector vec = new Vector(1.0, 0.0, 0.0);
        
        System.out.println(vec.printVector());
        
        vec.rotateAroundAxis(new Vector(0.0, 1.0, 0.0), Math.PI/2.0);
        System.out.println(vec.printVector());
        
        vec.rotateAroundAxis(new Vector(0.0, 1.0, 0.0), Math.PI/2.0);
        System.out.println(vec.printVector());
        
        vec.rotateAroundAxis(new Vector(0.0, 0.0, 1.0), Math.PI/2.0);
        System.out.println(vec.printVector());
    }
        

}
