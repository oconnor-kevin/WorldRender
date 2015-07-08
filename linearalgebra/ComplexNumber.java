/*
 * This class is a component of the linearalgebra package written by
 * Kevin O'Connor.  
 *
 * ComplexNumber allows for the creation and manipulation of ComplexNumbers
 *  which, at their heart consist of a two dimensional vector with alternate
 *  multiplication and division operations defined for them.  
 */
package linearalgebra;

/**
 *
 * @author kevinoconnor
 */
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
    
//------------------------------------------------------------------------------    
// Mutator Methods
    public void setReal(double re){
        parts.setComponentAtIndexTo(0, re);
    }
    
    public void setImag(double im){
        parts.setComponentAtIndexTo(1, im);
    }
    
    public void scaleMag(double fac){
        setReal(getReal()*fac);
        setImag(getImag()*fac);
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
    
}

