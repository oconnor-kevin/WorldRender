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
        activeForces = new ArrayList<>();
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
    public void timeStep(){
        // ArrayList of 2-element Matter arrays to save collisions.
        ArrayList<Matter[]> collisions = new ArrayList<Matter[]>();
        
        // Set the coefficient of restitution for collisions.
        double coefRest = 0.5;
        
        // Checks for collisions between all active matter objects.  When a 
        //  collision is expected, the two objects are sent through the collide
        //  method.
        for (int i = 0; i<activeMatter.size(); i++){
            for (int j = i; j<activeMatter.size(); j++){
                if (collisionCheck(activeMatter.get(i), activeMatter.get(j))){
                    collide(activeMatter.get(i), activeMatter.get(j), coefRest);
                    collisions.add(new Matter[]{activeMatter.get(i), activeMatter.get(j)});
                }
            }
        }
        
        // Now time steps each matter object.
        for (int i = 0; i<activeMatter.size(); i++){
            activeMatter.get(i).timeStep(timeIncrement);
        }
        
        // Then acts all forces upon each matter object.
        actAll();
        
        // Increments the time.
        incTime();
        
        // Appends the object space and collisions to the output file.
        // STATEMENTS //
    }
    
    // Alters velocity and rotational velocity of two Matter objects as if 
    //  collided.  Takes a third double argument indicating the coefficient of
    //  restitution.  A coefficient of 0 corresponds to a perfectly inelastic 
    //  collision while a coefficient of 1 corresponds to a perfectly elastic
    //  collision.
    public void collide(Matter a, Matter b, double coef){
        // Getting a and b masses.
        double aMass = (double) a.getMEqVal().get("Mass");
        double bMass = (double) b.getMEqVal().get("Mass");
        
        // Getting initial momentum vectors.
        Vector aInitMom = Vector.multiply(a.getOriginVelocity(), aMass);
        Vector bInitMom = Vector.multiply(b.getOriginVelocity(), bMass);
        Vector totalMom = Vector.add(aInitMom, bInitMom);
        
        // Calculating difference in initial velocities and multiplying by the
        //  coefficient of restitution.
        Vector velDiff = Vector.subtract(b.getOriginVelocity(), a.getOriginVelocity());
        velDiff.multiply(coef);
        
        // Calculating final velocity vectors.
        Vector aFinVel = Vector.multiply(Vector.add(totalMom, Vector.multiply(velDiff, bMass)), 1.0/(aMass + bMass));
        velDiff.multiply(-1.0);
        Vector bFinVel = Vector.multiply(Vector.add(totalMom, Vector.multiply(velDiff, aMass)), 1.0/(aMass + bMass));

        // Setting final velocities of the matter objects.
        a.setVelocity(aFinVel);
        b.setVelocity(bFinVel);
    }
    
    // Acts the relevant one argument forces upon the argument matter object.
    public void actForces(Matter a){
        // Taking care of the change in velocity and rotational velocity in 
        //  one loop.
        Vector forceVec = new Vector(3);
        Vector tempForceVec = new Vector(3); // temporarily stores a single force vector
        Vector torqueVec = new Vector(3);
        Vector rad = new Vector(3); // to store the distance from force to cm
        for (int i = 0; i<activeForces.size(); i++){
            tempForceVec = activeForces.get(i).interact(a);
            forceVec = Vector.add(forceVec, tempForceVec);
            rad = Vector.subtract(a.getCenter(activeForces.get(i).getME()), a.getCenter("Mass"));
            torqueVec = Vector.add(torqueVec, Vector.cross(rad, tempForceVec));
        }
        a.addVelocity(Vector.multiply(forceVec, timeIncrement));
        a.addRotation(Vector.multiply(Vector.multiply(torqueVec, 1.0/ ((double) a.getMEqVal().get("Moment of Inertia"))), timeIncrement));
    }
    
    // Acts the relevant two argument forces upon the argument matter object.  
    //  Specifically, will alter the first argument's properties as a result of
    //  forces from the second object.
    public void actForces(Matter a, Matter b){
        Vector forceVec = new Vector(3);
        Vector tempForceVec = new Vector(3);
        Vector torqueVec = new Vector(3);
        Vector torqueRad = new Vector(3);
        Vector rad = new Vector(3);
        for (int i = 0; i<activeForces.size(); i++){
            tempForceVec = activeForces.get(i).interact(a, b);
            forceVec = Vector.add(forceVec, tempForceVec);
            rad = Vector.subtract(a.getCenter(activeForces.get(i).getME()), a.getCenter("Mass"));
            torqueVec = Vector.add(torqueVec, Vector.cross(rad, tempForceVec));
        }
        a.addVelocity(Vector.multiply(forceVec, timeIncrement));
        a.addRotation(Vector.multiply(Vector.multiply(torqueVec, 1.0/ ((double) a.getMEqVal().get("Moment of Inertia"))), timeIncrement));
    }
    
    // Acts all forces upon each matter object in the object space.
    public void actAll(){
        for (int i = 0; i<activeMatter.size(); i++){
            for (int j = 0; j<activeMatter.size(); j++){
                actForces(activeMatter.get(i), activeMatter.get(j));
            }
            actForces(activeMatter.get(i));
        }
    }
    

//------------------------------------------------------------------------------
// Methods

    
    // Checks whether a collision is expected between the two argument matter
    //  objects in the next time step.
    public boolean collisionCheck(Matter a, Matter b){
        boolean willCollide = false;
        // Checks whether the displacement vectors of the two matter objects
        //  will intersect.
        Vector dispA = Vector.multiply(a.getOriginVelocity(), timeIncrement);
        Vector dispB = Vector.multiply(b.getOriginVelocity(), timeIncrement);
        
        // Checks for overlap in each dimension by checking if the total 
        //  displacement is greater than the initial distance between the two.
        Vector dist = Vector.subtract(a.getOriginPosition(), b.getOriginPosition());
        Vector totDisp = Vector.add(Vector.makePos(dispA), Vector.makePos(dispB));
        Vector compVec = Vector.subtract(dist, totDisp);
        if (compVec.getComp()[0] <= 0.0 && compVec.getComp()[1] <= 0.0 && compVec.getComp()[2] <= 0.0){
            willCollide = true;
        }
        
        return willCollide;
    }
    
    // Prints a String representation of the object space for purposes of 
    //  writing file data.  Does not include all properties.  This method is 
    //  meant for repetitive printing.  I.e. only fields which change over time
    //  will be printed.
    public String printObjectSpaceState(){
        // Output begins with the current time of the space.
        String s = time + "\n";
        
        // Appending each Matter object in the space to the string.
        for (int i = 0; i<activeMatter.size(); i++){
            s += "     " + activeMatter.get(i).printMatterState() + "\n";
        }
        
        return s;
    }
    
    // Prints a String representation of the contents and permanent properties
    //  of the object space.
    public String printObjectSpaceProperties(){
        // Initialize output string.
        String s = "";
        
        // Append immutable fields.
        s += "Time Increment: " + timeIncrement;
        for (int i = 0; i<activeForces.size(); i++){
            s += activeForces.get(i) + "     " + activeForces.get(i).printForce() + "\n";
        }
        
        // Appending each matter object.
        for (int i = 0; i<activeMatter.size(); i++){
            s += "     " + activeMatter.get(i) + "     " + activeMatter.get(i).printMatterProperties() + "\n";
        }
        
        return s;
    }
    
    
}
