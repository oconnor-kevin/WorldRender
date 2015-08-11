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

import linearalgebra.*;

public class RadialForce extends Force{
// Constructors
    public RadialForce(double coef, String me){
        coefficient = coef;
        massEquivalent = me;
    }
    
// Methods    
    // Implements the two argument interact method.
    @Override
    public Vector interact(Matter a, Matter b){
        Vector rad = Vector.subtract((Vector) b.getCentersOfMassEquivalents().get(super.massEquivalent), (Vector) a.getCentersOfMassEquivalents().get(super.massEquivalent));
        double nume = super.coefficient * (double) a.getMEqVal().get(super.massEquivalent)* (double) b.getMEqVal().get(super.massEquivalent);
        Vector force = Vector.multiply(rad, 1.0/rad.getMag());
        return Vector.multiply(force, nume/(rad.getMag() * rad.getMag()));
    }
    
    // Implements the one argument interact method to make the class concrete.  
    //  Since a radial force requires two objects, this version of the interact
    //  method does nothing.
    @Override
    public Vector interact(Matter a){
        return new Vector(new double[]{0.0, 0.0, 0.0});
    }
}
