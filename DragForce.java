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

public class DragForce extends Force {
// Constructor
    public DragForce(double coef){
        coefficient = coef;
    }
    
// Methods
    // Implements one argument interact method for a drag force.
    @Override
    public Vector interact(Matter a){
        double mag = coefficient * Vector.dot(a.getOriginVelocity(), a.getOriginVelocity());
        Vector dir = Vector.multiply(a.getOriginVelocity(), -1.0 * a.getOriginVelocity().getMag());
        return Vector.multiply(dir, mag);
    }
    
    // Implements two argument interact method to make the class concrete.
    @Override 
    public Vector interact(Matter a, Matter b){
        return new Vector(new double[]{0.0, 0.0, 0.0});
    }
}
