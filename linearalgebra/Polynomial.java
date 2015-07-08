/*
 * This class is a component of the linearalgebra package written by
 * Kevin O'Connor. 
 */
package linearalgebra;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kevinoconnor
 */
public class Polynomial 
{
// Fields    
    private ArrayList<Double> coefficients;
    
//------------------------------------------------------------------------------
// Constructors
    public Polynomial(){
        coefficients = new ArrayList<>();
    }
    
    public Polynomial(ArrayList<Double> c){
        coefficients = c;
    }
    
    public Polynomial(HashMap<Integer, Double> h){
        coefficients = new ArrayList<Double>();
        Object[] keyArray = h.keySet().toArray();
        for (int i = 0; i<h.keySet().size(); i++){
        setCoefficientOfOrderTo((int) keyArray[i], h.get(((int) keyArray[i])));
        }
    }
    
//------------------------------------------------------------------------------
// Accessor Methods
    public ArrayList<Double> getCoefficients(){
        return coefficients;
    }
    
    public boolean isQuadratic(){
        if (coefficients.size() == 3.0){
            return true;
        }
        else{
            return false;
        }
    }
    
//------------------------------------------------------------------------------
// Mutator Methods
    public void setCoefficients(ArrayList<Double> c){
        coefficients = c;
    }
    
    public void setCoefficientOfOrderTo(int i, Double newCoef){
        if (coefficients.size() <= i){
            for (int j = coefficients.size(); j <= i; j++){
                coefficients.add(0.0);
            } 
        }
        coefficients.set(i, newCoef);
    }
    
//------------------------------------------------------------------------------
// Methods
    public static String printPolynomial(Polynomial p){
        String s = "p(x) = ";
        for (int i = 0; i<p.getCoefficients().size(); i++){
            if (p.getCoefficients().get(i) != 0.0){
                s += p.getCoefficients().get(i) + "x^" + i + " + ";
            }
        }
        if (s.compareTo("p(x) = ") == 0){
            s += "0.0";
        }
        else{
        s = s.substring(0, s.length() - 3);
        }
        return s;
    }
    
    public static ComplexNumber valueAt(ComplexNumber x, Polynomial p){
        ComplexNumber val = new ComplexNumber();
        for (int i = 0; i<p.getCoefficients().size(); i++){
            val = ComplexNumber.add(val, ComplexNumber.multiplyByScalar(ComplexNumber.toPower(x,i), p.getCoefficients().get(i)));
        }
        return val;
    }
    
    public static double[] roots(Polynomial p){
        double[] rts = new double[p.getCoefficients().size()];
        int rootsRemaining = p.getCoefficients().size();
        if(p.getCoefficients().get(0) == 0.0){
            rts[0] = 0.0;
            rootsRemaining--;
        }
/* TODO: implement default root-finding algorithm        
*/        
        return rts;
    }
    
/* This method returns a root of a polynomial within a given boundary and tolerance
    TAKES: double corresponding to minimum of the x boundary, double corresponding 
        to the maximum of the x boundary, a Polynomial object, and a double 
        corresponding to the threshold of the computation at which value the 
        method assumes a root has been found.
    RETURNS: double corresponding to the root if one is found, or -1000000.0 if a 
        root is not found.
*/    
    public static double rootWithinBounds(double xmin, double xmax, Polynomial p, double d){
        if((p.getCoefficients().get(0) == 0.0) && (xmin < 0.0) && (xmax > 0.0)){
            return 0.0;
        }
        else if((valueAt(xmin, p) < 0.0) && (valueAt(xmax, p) > 0.0)){
            if(valueAt((xmin + xmax)/2.0, p) < -d){
                return rootWithinBounds((xmin + xmax)/2.0, xmax, p, d);
            }
            else if(valueAt((xmin + xmax)/2.0, p) > d){
                return rootWithinBounds(xmin, (xmin + xmax)/2.0, p, d);
            }
            else {
                return (xmin + xmax)/2.0;
            }
        }
        else if((valueAt(xmin, p) > 0.0) && (valueAt(xmax, p) < 0.0)){
            if(valueAt((xmin + xmax)/2.0, p) > d){
                return rootWithinBounds((xmin + xmax)/2.0, xmax, p, d);
            }
            else if(valueAt((xmin + xmax)/2.0, p) < -d){
                return rootWithinBounds(xmin, (xmin + xmax)/2.0, p, d);
            }
            else {
                return (xmin + xmax)/2.0;
            }
        }
        else{
            return -1000000.0;
        } 
    }
  
    public static Polynomial derivative(Polynomial p){
        Polynomial dp = new Polynomial();
        for (int i = 1; i<p.getCoefficients().size(); i++){
            dp.setCoefficientOfOrderTo(i-1, p.getCoefficients().get(i)*i);
        }
        return dp;
    }
    
    public static Polynomial derivativeOfOrder(int n, Polynomial p){
        for (int i = 0; i<n; i++){
            p = derivative(p);
        }
        return p;
    }
    
/*  TODO    
    public static double[] rootsWithinTolerance(Polynomial p, double t){
        
    }
*/    
    
    public static boolean isQuadratic(Polynomial p){
        if ((p.getCoefficients().size() == 3.0) && (p.getCoefficients().get(2) != 0.0)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static ComplexNumber[] quadraticRoots(Polynomial p){
        ComplexNumber[] roots = new ComplexNumber[2];
        roots[0] = new ComplexNumber(-10000000.0, -10000000.0);
        roots[1] = new ComplexNumber(-10000000.0, -10000000.0);
        if (p.isQuadratic()){
            ComplexNumber plusRoot = new ComplexNumber();
            ComplexNumber minusRoot = new ComplexNumber();
            if (Math.pow(p.getCoefficients().get(1), 2.0) < (4.0*p.getCoefficients().get(0)*p.getCoefficients().get(2))){
                plusRoot.setReal(-0.5*p.getCoefficients().get(1)/p.getCoefficients().get(2));
                minusRoot.setReal(-0.5*p.getCoefficients().get(1)/p.getCoefficients().get(2));
                plusRoot.setImag(Math.sqrt(Math.abs(Math.pow(p.getCoefficients().get(1), 2.0) - 4.0*p.getCoefficients().get(0)*p.getCoefficients().get(2)))/(2.0*p.getCoefficients().get(2)));
                minusRoot.setImag(-1.0*Math.sqrt(Math.abs(Math.pow(p.getCoefficients().get(1), 2.0) - 4.0*p.getCoefficients().get(0)*p.getCoefficients().get(2)))/(2.0*p.getCoefficients().get(2)));
            }
            else{
                plusRoot.setReal(-0.5*p.getCoefficients().get(1)/p.getCoefficients().get(2) + Math.sqrt(Math.abs(Math.pow(p.getCoefficients().get(1), 2.0) - 4.0*p.getCoefficients().get(0)*p.getCoefficients().get(2)))/(2.0*p.getCoefficients().get(2)));
                minusRoot.setReal(-0.5*p.getCoefficients().get(1)/p.getCoefficients().get(2) - Math.sqrt(Math.abs(Math.pow(p.getCoefficients().get(1), 2.0) - 4.0*p.getCoefficients().get(0)*p.getCoefficients().get(2)))/(2.0*p.getCoefficients().get(2)));
            }
            roots[0] = plusRoot;
            roots[1] = minusRoot;
        }
        return roots;
    }
    
    
    
    
    
    
}
