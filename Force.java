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


public abstract class Force {
// Fields
    // Constant which will be used when calculating force vectors for 
    //  interacting matter.
    private final double coefficient;
    
    // Mass Equivalent which the force uses.
    private final String massEquivalent;
    
// Constructors
    public Force(){
        coefficient = 0;
        massEquivalent = "";
    }
    
    public Force(double coef, String massE){
        coefficient = coef;
        massEquivalent = massE;
    }
    
// Accessor Methods
    public double getCoef(){
        return coefficient;
    }
    
    public String getME(){
        return massEquivalent;
    }
    
// Methods
    // Matter interaction method which alters velocity of the argument Matter
    //  object a from its interaction with Matter object b.
    public abstract void interact(Matter a, Matter b);
    
    // Matter interaction method which alters velocity of the argument Matter 
    //  object a according to the force it currently feels.  
    public abstract void interact(Matter a);
    
}
