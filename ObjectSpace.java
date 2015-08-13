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
import java.util.ArrayList;

public class ObjectSpace {

//------------------------------------------------------------------------------    
// Fields    
    // The list of matter objects that are active in the space.
    private ArrayList<Matter> activeMatter;
    
    // The time increment which will be used when time stepping the objects in
    //  the space.
    private double timeIncrement;
    
    // The time that the object space has been evolving.
    private double time;
    
    // A list of the active forces in the object space.
    private ArrayList<Force> activeForces;
    

//------------------------------------------------------------------------------    
// Constructors
    public ObjectSpace(){
        activeMatter = new ArrayList<>();
        timeIncrement = 1.0;
        time = 0.0;
    }
    
    public ObjectSpace(ArrayList<Matter> mat, double inc, double ti, ArrayList<Force> fo){
        activeMatter = mat;
        timeIncrement = inc;
        time = ti;
        activeForces = fo;
    }
    
// -----------------------------------------------------------------------------    
// Accessor Methods
    // Returns active matter field.
    public ArrayList<Matter> getMatter(){
        return activeMatter;
    }
    
    // Returns time increment field.
    public double getTimeInc(){
        return timeIncrement;
    }
    
    // Returns time field.
    public double getTime(){
        return time;
    }
    
    // Returns the active forces field.
    public ArrayList<Force> getForces(){
        return activeForces;
    }
    
//-----------------------------------------------------------------------------    
// Mutator Methods
    // Adds argument matter object to the list of active matter in the space.
    public void addMatter(Matter m){
        activeMatter.add(m);
    }
    
    // Removes the argument matter object from the list of active matter in the 
    //  space.
    public void removeMatter(Matter m){
        activeMatter.remove(m);
    }
    
    // Removes all matter objects from the object space.
    public void clearObjectSpace(){
        activeMatter.clear();
    }

    // Sets time increment field to the argument.
    public void setTimeInc(double newInc){
        timeIncrement = newInc;
    }
    
    // Sets time field to the argument.
    public void setTime(double newTime){
        time = newTime;
    }
    
    // Increments the time field by the time increment field.
    public void incTime(){
        time += timeIncrement;
    }
    
    // Sets the active forces field to the argument.
    public void setForces(ArrayList<Force> newForces){
        activeForces = newForces;
    }
    
    // Adds the argument force to the active forces field.
    public void addForce(Force fo){
        activeForces.add(fo);
    }
    
    // Removes the argument force from the active forces field.
    public void removeForce(Force fo){
        activeForces.remove(fo);
    }
    
    // Time steps the object space by the time increment field.
    public void timeStep(){}
    
    // Alters velocity and rotational velocity of two Matter objects as if 
    //  collided.  Takes a third boolean argument indicating whether the 
    //  collision can be considered elastic.
    public void collide(Matter a, Matter b, boolean elastic){}
    
    // Acts the relevant one argument forces upon the argument matter object.
    public void actForces(Matter a){}
    
    // Acts the relevant two argument forces upon the argument matter object.  
    //  Specifically, will alter the first argument's properties as a result of
    //  forces from the second object.
    public void actForces(Matter a, Matter b){}
    
    // Acts all forces upon each matter object in the object space.
    public void actAll(){}
    

//------------------------------------------------------------------------------
// Methods

    
    // Checks whether a collision is expected between the two argument matter
    //  objects in the next time step.
    //public boolean collisionCheck(Matter a, Matter b){}
    
    // Prints a String representation of the object space for purposes of 
    //  writing file data.
    //public String printObjectSpace(){}
    
    
}
