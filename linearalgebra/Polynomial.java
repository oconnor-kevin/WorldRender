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

import java.util.ArrayList;
import java.util.Arrays;
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
        coefficients.add(0.0);
    }
    
    public Polynomial(ArrayList<Double> c){
        coefficients = c;
    }
    
    public Polynomial(double[] d){
        coefficients = new ArrayList<Double>();
        for (int i = 0; i<d.length; i++){
            coefficients.add(d[i]);
        }
    }
    
    public Polynomial(HashMap<Integer, Double> h){
        coefficients = new ArrayList<Double>();
        Object[] keyArray = h.keySet().toArray();
        for (int i = 0; i<h.keySet().size(); i++){
        setCoefficientOfOrderTo((int) keyArray[i], h.get(((int) keyArray[i])));
        }
    }
    
    public Polynomial(int order){
        coefficients = new ArrayList<>();
        setCoefficientOfOrderTo(order, 0.0);
    }
    
//------------------------------------------------------------------------------
// Accessor Methods
    public ArrayList<Double> getCoefficients(){
        return coefficients;
    }
    
    public double getCoef(int i){
        return getCoefficients().get(i);
    }

    public boolean isConstant(){
        return getOrder() == 0;
    }
    
    public boolean isLinear(){
        return getOrder() == 1;
    }
    
    public boolean isQuadratic(){
        return getOrder() == 3.0;
    }
    
    public boolean isCubic(){
        return getOrder() == 3;
    }
    
    public boolean isQuartic(){
        return getOrder() == 4;
    }
    
    public ComplexNumber valueAt(ComplexNumber x){
        ComplexNumber val = new ComplexNumber();
        for (int i = 0; i<getCoefficients().size(); i++){
            val.add(ComplexNumber.multiplyByScalar(ComplexNumber.toPower(x, i), getCoef(i)));
        }
        return val;
    }
    
    public ComplexNumber valueAt(double x){
        double val = 0.0;
        for (int i = 0; i<getOrder()+1; i++){
            val += getCoef(i)*Math.pow(x, i);
        }
        return new ComplexNumber(val);
    }
    
    public int getOrder(){
        return (getCoefficients().size() - 1);
    }

    private boolean hasTrailingZeroes(){
        return (getCoef(getOrder()) == 0.0);
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
    
    public void clean(){
        while (hasTrailingZeroes()){
            if (getCoef(getOrder()) == 0.0){
                coefficients.remove(getOrder());
            }
        }
    }
    
//------------------------------------------------------------------------------
// Methods
    public static String printPolynomial(Polynomial p){
        String s = "p(x) = ";
        for (int i = 0; i<=p.getOrder(); i++){
            if (p.getCoef(i) != 0.0){
                s += p.getCoef(i) + "x^" + i + " + ";
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

    
    public static ArrayList<ComplexNumber> roots(Polynomial p){
        ArrayList<ComplexNumber> rts = new ArrayList<>();
        if (p.getOrder() == 0){
        }
        else if (p.getOrder() == 1){
            rts.add(new ComplexNumber(p.getCoef(0)/p.getCoef(1)*-1.0));
        }
        else if (p.getOrder() == 2){
            rts.add(quadraticRoots(p)[0]);
            rts.add(quadraticRoots(p)[1]);
        }
        else {
            if (p.getCoef(0) == 0.0){
                rts.add(new ComplexNumber(0.0));
                rts.addAll(roots(divide(p,new Polynomial(new double[]{0.0, 1.0}))));
            }
            
        }
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
/*
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
*/  


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
  
// Polynomial Arithmetic    
    public static Polynomial multiply(Polynomial a, Polynomial b){
        Polynomial res = new Polynomial();
        res.setCoefficientOfOrderTo(a.getOrder()+ b.getOrder(), 0.0);
        for (int i = 0; i<a.getCoefficients().size(); i++){
            for (int j = 0; j<b.getCoefficients().size(); j++){
                res.setCoefficientOfOrderTo(i+j, res.getCoef(i+j) + a.getCoef(i)*b.getCoef(j));
            }
        }
        return res;
    }
    
    public static Polynomial add(Polynomial a, Polynomial b){
        Polynomial polySum = new Polynomial();
        polySum.setCoefficientOfOrderTo(Math.max(a.getOrder(), b.getOrder()), 0.0);
        for (int i = 0; i<=a.getOrder(); i++){
            polySum.setCoefficientOfOrderTo(i, a.getCoef(i));
        }
        for (int i = 0; i<=b.getOrder(); i++){
            polySum.setCoefficientOfOrderTo(i, polySum.getCoef(i) + b.getCoef(i));
        }
        return polySum;
    }
    
    public static Polynomial subtract(Polynomial a, Polynomial b){
        Polynomial polyDiff = new Polynomial();
        polyDiff.setCoefficientOfOrderTo(Math.max(a.getOrder(), b.getOrder()), 0.0);
        for (int i = 0; i<=a.getOrder(); i++){
            polyDiff.setCoefficientOfOrderTo(i, a.getCoef(i));
        }
        for (int i = 0; i<=b.getOrder(); i++){
            polyDiff.setCoefficientOfOrderTo(i, polyDiff.getCoef(i) - b.getCoef(i));
        }
        polyDiff.clean();
        return polyDiff;
    }
    
    public static Polynomial multiplyByScalar(Polynomial a, double d){
        Polynomial newPoly = new Polynomial(a.getOrder());
        for (int i = 0; i<=a.getOrder(); i++){
            newPoly.setCoefficientOfOrderTo(i, d*a.getCoef(i));
        }
        return newPoly;
    }

    public static Polynomial divide(Polynomial a, Polynomial b){
        if (isDivisibleBy(a,b)){
            Polynomial quotient = new Polynomial();
            if (b.getOrder() == a.getOrder()){
                quotient.setCoefficientOfOrderTo(0, a.getCoef(1)/b.getCoef(1)); //assumes that a and b divide with no remainder
            }
            else if (a.getOrder() > b.getOrder()){
                quotient.setCoefficientOfOrderTo(a.getOrder() - b.getOrder(), a.getCoef(a.getOrder())/b.getCoef(b.getOrder()));
                quotient = add(quotient, divide(subtract(a, multiply(quotient, b)), b));
            }
            return quotient;
        }
        else return a;
    }

    public static boolean isDivisibleBy(Polynomial a, Polynomial b){
        boolean divisible = true;
        Polynomial quotient = new Polynomial();
        if (b.getOrder() == a.getOrder()){
            quotient.setCoefficientOfOrderTo(0, a.getCoef(1)/b.getCoef(1)); //assumes that a and b divide with no remainder
            double k = a.getCoef(a.getOrder())/b.getCoef(b.getOrder());
            for (int i = a.getOrder()-1; i>=0; i--){
                if (a.getCoef(i)/b.getCoef(i) != k){
                    divisible = false;
                }
            }
        }
        else if (a.getOrder() > b.getOrder()){
            quotient.setCoefficientOfOrderTo(a.getOrder() - b.getOrder(), a.getCoef(a.getOrder())/b.getCoef(b.getOrder()));
            divisible = divisible && isDivisibleBy(subtract(a, multiply(quotient, b)), b);
        }
        else {
            divisible = false;
        }
        return divisible;
    }
    
}
