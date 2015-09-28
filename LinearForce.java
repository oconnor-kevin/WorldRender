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

/**
 *
 * @author kevinoconnor
 */

import linearalgebra.*;

public class LinearForce extends Force{
// Fields
    Vector direction;
    
// Constructors
    public LinearForce(double coef, String me, Vector dir){
        coefficient = coef;
        massEquivalent = me;
        direction = dir;
    }
    
// Methods
    // Implements the one argument interact method for a linear force.
    @Override
    public Vector interact(Matter a){
        return Vector.multiply(direction, coefficient * (double) a.getMEqVal().get(massEquivalent));
    }
    
    // Implements the two argument interact method to make the class concrete.
    @Override
    public Vector interact(Matter a, Matter b){
        return new Vector(new double[]{0.0, 0.0, 0.0});
    }
}
