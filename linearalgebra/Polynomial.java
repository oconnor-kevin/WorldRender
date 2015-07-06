/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        coefficients = new ArrayList<Double>();
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
    
//------------------------------------------------------------------------------
// Mutator Methods
    public void setCoefficients(ArrayList<Double> c){
        coefficients = c;
    }
    
    public void setCoefficientOfOrderTo(int i, double newCoef){
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
    
    public static double valueAt(double x, Polynomial p){
        double val = 0.0;
        for (int i = 0; i<p.getCoefficients().size(); i++){
            val += p.getCoefficients().get(i)*Math.pow(x, (double) i);
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
    
/*  TODO    
    public static double[] rootsWithinTolerance(Polynomial p, double t){
        
    }
*/    
    
}
