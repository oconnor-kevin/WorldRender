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

public class ComplexNumber 
{
// Fields
    private Vector parts;
    public static ComplexNumber i = new ComplexNumber(0.0, 1.0);
    
//------------------------------------------------------------------------------    
// Constructors
    public ComplexNumber(){
        parts = new Vector(2);
    }
    
    public ComplexNumber(double re, double im){
        parts = new Vector(new double[]{re, im});
    }
    
    public ComplexNumber(Vector pa){
        if (pa.getComponents().length == 2){
            parts = pa;
        }
    }
    
    public ComplexNumber(double[] p){
        if (p.length == 2){
            parts = new Vector(p);
        }    
    }
    
    public ComplexNumber(double re){
        parts = new Vector(new double[]{re, 0.0});
    }

//------------------------------------------------------------------------------    
// Accessor Methods
    public double getReal(){
        return parts.getComponents()[0];
    }
    
    public double getImag(){
        return parts.getComponents()[1];
    }
    
    public Vector getParts(){
        return parts;
    }
    
    public double getAng(){
        return Math.atan(getImag()/getReal());
    }
    
    public ComplexNumber getConjugate(){
        return (new ComplexNumber(getReal(), getImag()*(-1.0)));
    }
    
    public double getMag(){
        return getParts().getMagnitude();
    }
    
//------------------------------------------------------------------------------    
// Mutator Methods
    public void setReal(double re){
        parts.setComponentAtIndexTo(0, re);
    }
    
    public void setImag(double im){
        parts.setComponentAtIndexTo(1, im);
    }
    
    public void setParts(Vector p){
        parts = p;
    }
    
    public void scaleMag(double fac){
        setReal(getReal()*fac);
        setImag(getImag()*fac);
    }
    
    public void add(ComplexNumber a){
        setReal(getReal() + a.getReal());
        setImag(getImag() + a.getImag());
    }
    
    public void subtract(ComplexNumber a){
        setReal(getReal() - a.getReal());
        setImag(getImag() - a.getImag());
    }
    
    public void multiply(ComplexNumber a){
        setReal(getReal()*a.getReal() - getImag()*a.getImag());
        setImag(getReal()*a.getImag() + getImag()*a.getReal());
    }
    
    public void multiplyByScalar(double d){
        setParts(Vector.multiplyVectorByScalar(getParts(), d));
    }
    
    public void divide(ComplexNumber a){
        if(a.getMag() != 0.0){
            multiply(conjugate(a));
            multiplyByScalar(1.0/a.getMag());
        }
    }        
    
//------------------------------------------------------------------------------
// Method
    public static ComplexNumber add(ComplexNumber a, ComplexNumber b){
        return (new ComplexNumber(Vector.addVectors(a.getParts(), b.getParts())));
    }
    
    public static ComplexNumber subtract(ComplexNumber a, ComplexNumber b){
        return new ComplexNumber(Vector.subtractVectors(a.getParts(), b.getParts()));
    }
    
    public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b){
        return new ComplexNumber(a.getReal()*b.getReal()-a.getImag()*b.getImag(), a.getImag()*b.getReal() + a.getReal()*b.getImag());
    }
    
    public static ComplexNumber divide(ComplexNumber a, ComplexNumber b){
        if (b.getParts().getMagnitude() != 0.0){
            ComplexNumber c;
            c = multiply(a, multiplyByScalar(b.getConjugate(), 1.0/b.getParts().getMagnitude()));
            return c;
        }
        else {
            return new ComplexNumber(-10000000.0, -10000000.0);
        }
    }

    public static ComplexNumber multiplyByScalar(ComplexNumber a, double d){
        return new ComplexNumber(Vector.multiplyVectorByScalar(a.getParts(), d));
    }
    
    public static String printComplexNumber(ComplexNumber c){
        return (String) (c.getReal() + " + " + c.getImag() + "i");
    }
    
    public static ComplexNumber toPower(ComplexNumber c, int i){
        ComplexNumber val = new ComplexNumber(c.getParts());
        for (int j = 1; j<i; j++){
            val = multiply(val, c);
        }
        return val;
    }
    
    public static ComplexNumber conjugate(ComplexNumber c){
        ComplexNumber conj = new ComplexNumber();
        conj.setReal(c.getReal());
        conj.setImag(-1.0*c.getImag());
        return conj;
    }
    
}

