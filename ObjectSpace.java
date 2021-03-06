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
import java.util.HashSet;
import java.io.PrintWriter;

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
        if (getForces().size() != 0){
            // Taking care of the change in velocity and rotational velocity in 
            //  one loop.
            Vector forceVec = new Vector(3);
            Vector tempForceVec = new Vector(3); // temporarily stores a single force vector
            Vector torqueVec = new Vector(3);
            Vector rad = new Vector(3); // to store the distance from force to cm
            for (int i = 0; i<activeForces.size(); i++){
                tempForceVec = activeForces.get(i).interact(a);
                forceVec = Vector.add(forceVec, tempForceVec);
/*
                rad = Vector.subtract(a.getCenter(activeForces.get(i).getME()), a.getCenter("Mass"));
                torqueVec = Vector.add(torqueVec, Vector.cross(rad, tempForceVec));
*/                
                torqueVec = Vector.add(torqueVec, activeForces.get(i).findTorque(a));
            }
            a.addVelocity(Vector.multiply(Vector.multiply(forceVec, 1.0 / (double) a.getMEqVal().get("Mass")), timeIncrement));
            if ((double) a.getMEqVal().get("Moment of Inertia") != 0.0){
                a.addRotation(Vector.multiply(Vector.multiply(torqueVec, 1.0/ ((double) a.getMEqVal().get("Moment of Inertia"))), timeIncrement));
            }
        }
    }
    
    // Acts the relevant two argument forces upon the argument matter object.  
    //  Specifically, will alter the first argument's properties as a result of
    //  forces from the second object.
    public void actForces(Matter a, Matter b){
        if (getForces().size() != 0){
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
            a.addVelocity(Vector.multiply(Vector.multiply(forceVec, 1.0 / (double) a.getMEqVal().get("Mass")), timeIncrement));
            if ((double) a.getMEqVal().get("Moment of Inertia") != 0.0){
                a.addRotation(Vector.multiply(Vector.multiply(torqueVec, 1.0 / ((double) a.getMEqVal().get("Moment of Inertia"))), timeIncrement));
            }
        }
    }
    
    // Acts all forces upon each matter object in the object space.
    public void actAll(){
        for (int i = 0; i<activeMatter.size(); i++){
            for (int j = 0; j<activeMatter.size(); j++){
                if (i != j){
                    actForces(activeMatter.get(i), activeMatter.get(j));
                }
            }
            actForces(activeMatter.get(i));
        }
    }
    

//------------------------------------------------------------------------------
// Methods

    
    // Checks whether a collision is expected between the two argument matter
    //  objects in the next time step.
    public boolean collisionCheck(Matter a, Matter b){
        Vector relativePositions = Vector.subtract(b.getOriginPosition(), a.getOriginPosition());
        return Vector.overlaps(Vector.multiply(a.getOriginVelocity(), timeIncrement), Vector.multiply(b.getOriginVelocity(), timeIncrement), relativePositions);
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
    
////////////////////////////////////////////////////////////////////////////////
//  TESTING
    
    public static void main(String[] args){
        
       /* 
        Particle partA = new Particle(new Vector(1.0, 1.0, 1.0), new Vector(1.0, 0.0, 0.0));
        Particle partB = new Particle(new Vector(-1.0, -1.0, -1.0), new Vector(3));
        Particle partC = new Particle(new Vector(-1.0, 1.0, 1.0), new Vector(3));
        Particle partD = new Particle(new Vector(1.0, -1.0, 1.0), new Vector(3));
        Particle partE = new Particle(new Vector(1.0, 1.0, -1.0), new Vector(3));
        Particle partF = new Particle(new Vector(-1.0, -1.0, 1.0), new Vector(3));
        Particle partG = new Particle(new Vector(1.0, -1.0, -1.0), new Vector(3));
        Particle partH = new Particle(new Vector(-1.0, 1.0, -1.0), new Vector(3));
        
        
        partA.addMassEquivalentValue("Mass", 15.0);
        partB.addMassEquivalentValue("Mass", 15.0);
        partC.addMassEquivalentValue("Mass", 15.0);
        partD.addMassEquivalentValue("Mass", 15.0);
        partE.addMassEquivalentValue("Mass", 15.0);
        partF.addMassEquivalentValue("Mass", 15.0);
        partG.addMassEquivalentValue("Mass", 15.0);
        partH.addMassEquivalentValue("Mass", 15.0);
        
        partA.addMassEquivalentValue("Charge", 50.0);
        partB.addMassEquivalentValue("Charge", 50.0);
        partC.addMassEquivalentValue("Charge", 50.0);
        partD.addMassEquivalentValue("Charge", 50.0);
        partE.addMassEquivalentValue("Charge", 50.0);
        partF.addMassEquivalentValue("Charge", 50.0);
        partG.addMassEquivalentValue("Charge", 50.0);
        partH.addMassEquivalentValue("Charge", 100.0);
        
        Matter Mat1 = new Matter();
        
        Mat1.addParticle(partA);
        Mat1.addParticle(partB);
        Mat1.addParticle(partC);
        Mat1.addParticle(partD);
        Mat1.addParticle(partE);
        Mat1.addParticle(partF);
        Mat1.addParticle(partG);
        Mat1.addParticle(partH);
        
        Mat1.fillCenters();
        Mat1.fillME();
        Mat1.calcMomIn();

        Mat1.setFix(false);
        
        Mat1.setVelocity(new Vector(5.0, 0.0, 0.0));
        Mat1.setRotation(new Vector(Math.PI, 0.0, 0.0));
        Mat1.setRotationOrigin(Mat1.getCenter("Mass"));
        
        Matter matA = new Matter();
        Matter matB = new Matter();
        
        Particle part1 = new Particle(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 0.0));
        Particle part2 = new Particle(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 0.0));
        
        part1.addMassEquivalentValue("Mass", 0.01);
        part2.addMassEquivalentValue("Mass", 0.01);
        
        matA.addParticle(part1);
        matB.addParticle(part2);
        
        matA.fillCenters();
        matB.fillCenters();
        
        matA.fillME();
        matB.fillME();
        
        matA.calcMomIn();
        matB.calcMomIn();
        
        matA.setPosition(new Vector(0.0, 0.0, 0.0));
        matB.setPosition(new Vector(10.0, 0.0, 0.0));
        
        matA.setVelocity(new Vector(0.0, 1.0, 0.0));
        matB.setVelocity(new Vector(0.0, -1.0, 0.0));
        
        ObjectSpace os = new ObjectSpace();
        os.addForce(new LinearForce(-9.8, "Mass", new Vector(0, 0, 1.0)));
        os.addForce(new LinearForce(9.8, "Mass", new Vector(0, 0, 1.0)));
        
        os.addForce(new RadialForce(1000.0, "Mass"));
        
        
        os.addMatter(matA);
        os.addMatter(matB);
        
        os.setTimeInc(0.0031);
               
               */
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // TESTING SINGLE MATTER OBJECT
        
        /*
        // Single particle 
        Particle part1 = new Particle(new Vector(3), new Vector(3));
        part1.addMassEquivalentValue("Mass", 1.0);
        part1.addMassEquivalentValue("Charge", 2.0);
        
        Matter mat1 = new Matter();
        mat1.addParticle(part1);
        mat1.setFix(false);
        mat1.setPosition(new Vector(3));
        mat1.setVelocity(new Vector(0.0, 0.0, 1.0));
        mat1.setRotation(new Vector(1.0, 1.0, 1.0));
        mat1.fillCenters();
        mat1.fillME();
        mat1.calcMomIn();
        mat1.setRotationOrigin(mat1.getCenter("Mass"));
        
        ObjectSpace os = new ObjectSpace();
        os.addMatter(mat1);
        os.setTimeInc(0.01);
        os.addForce(new LinearForce(100.0, "Mass", new Vector(1.0, 0.0, 0.0)));
        os.addForce(new LinearForce(200.0, "Charge", new Vector(0.0, 1.0, 0.0)));
        os.addForce(new DragForce(10.0)); 
        */
        
        // Two particles
        Particle part1 = new Particle(new Vector(-1.0, 0.0, 0.0), new Vector(3));
        Particle part2 = new Particle(new Vector(1.0, 0.0, 0.0), new Vector(3));
        part1.addMassEquivalentValue("Mass", 1.0);
        part2.addMassEquivalentValue("Mass", 1.0);
        part1.addMassEquivalentValue("Charge", 2.0);
        part2.addMassEquivalentValue("Charge", -2.0);
        
        Matter mat1 = new Matter();
        mat1.addParticle(part1);
        mat1.addParticle(part2);
        mat1.setFix(false);
        mat1.setPosition(new Vector(3));
        mat1.setVelocity(new Vector(0.0, 1.0, 1.0));
        mat1.setRotation(new Vector(0.0, 0.0, 0.0));
        mat1.fillCenters();
        mat1.fillME();
        mat1.calcMomIn();
        mat1.setRotationOrigin(mat1.getCenter("Mass"));
        
        ObjectSpace os = new ObjectSpace();
        os.addMatter(mat1);
        os.setTimeInc(0.01);
        os.addForce(new LinearForce(100.0, "Mass", new Vector(1.0, 0.0, 0.0)));
        os.addForce(new LinearForce(-400.0, "Charge", new Vector(0.0, 1.0, 0.0)));
        
        
        
        
        
        // Output results
        System.out.println(os.printObjectSpaceProperties());
        for (int i = 0; i < 50; i++){
            System.out.println(os.printObjectSpaceState());
            os.timeStep();
        }
        
        
    }
    
    
}
