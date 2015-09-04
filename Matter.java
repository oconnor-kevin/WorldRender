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


import java.util.ArrayList;
import linearalgebra.*;
import java.awt.Color;
import java.util.HashMap;

public class Matter {

//------------------------------------------------------------------------------    
// Fields
    // Vector representing the position of the origin of the matter coordinate
    //  system within the object space coordinate system.
    Vector originPosition;
    
    // Vector representing the velocity of the origin of the matter coordinate
    //  system within the object space coordinate system.
    Vector originVelocity;
    
    // Vector representing the origin of the rotational axis for the matter 
    //  object.  In most cases this should be the center of mass.
    Vector rotationOrigin;
    
    // An array of three doubles which represents the rotational velocity of the
    //  mater object about each of the three axes.
    Vector rotationalVelocity;
    
    // ArrayList of the particles contained in the matter object.
    ArrayList<Particle> particles;
    
    // A list of all the centers of mass equivalents for the Matter object in
    //  the object space frame.
    HashMap<String, Vector> centersOfMassEquivalents;
    
    // A hashmap of the mass equivalents and there values for the matter object.
    //  This can be calculated from the particles contained in the matter object
    //  or assigned manually.
    HashMap<String, Double> massEquivalentValues;
    
    // The color fo the matter object if it is to be rendered.
    Color matterColor;
    
    // Boolean denoting whether the matter object will be fixed in the object
    //  space or movable.
    Boolean fixed;
    
//------------------------------------------------------------------------------    
// Constructors
    public Matter(){
        particles = new ArrayList<>();
        originPosition = new Vector(3);
        originVelocity = new Vector(3);
        rotationalVelocity = new Vector(3);
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        massEquivalentValues = new HashMap();
        matterColor = Color.BLACK;
        fixed = true;
    }
    
    public Matter(ArrayList<Particle> parts){
        particles = parts;
        originPosition = new Vector(3);
        originVelocity = new Vector(3);
        rotationalVelocity = new Vector(3);
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        massEquivalentValues = new HashMap();
        matterColor = Color.BLACK;
        fixed = true;
        
        // Adds moment of inertia to the mass equivalent values field.
        calcMomIn();
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = new Vector(3);
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        massEquivalentValues = new HashMap();
        matterColor = Color.BLACK;
        fixed = true;
        
        // Adds moment of inertia to the mass equivalent values field.
        calcMomIn();
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Boolean fix){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = new Vector(3);
        rotationOrigin = new Vector(3);        
        centersOfMassEquivalents = new HashMap();
        massEquivalentValues = new HashMap();
        matterColor = Color.BLACK;
        fixed = fix;
        
        // Adds moment of inertia to the mass equivalent values field.
        calcMomIn();
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Vector rot, Boolean fix){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = rot;
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        massEquivalentValues = new HashMap();
        matterColor = Color.BLACK;
        fixed = fix;
        
        // Adds moment of inertia to the mass equivalent values field.
        calcMomIn();
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Vector rot, Boolean fix, HashMap centers, HashMap values, Vector rotOri, Color col){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = rot;
        rotationOrigin = rotOri;
        centersOfMassEquivalents = centers;
        massEquivalentValues = values;
        matterColor = col;
        fixed = fix;
        
        // Adds moment of inertia to the mass equivalent values field.
        calcMomIn();
    }
    
//------------------------------------------------------------------------------    
// Accessor Methods    
    // Returns particles field.
    public ArrayList<Particle> getParticles(){
        return particles;
    }
    
    // Returns originPosition field.
    public Vector getOriginPosition(){
        return originPosition;
    }
    
    // Returns originVelocity field.
    public Vector getOriginVelocity(){
        return originVelocity;
    }
    
    // Returns rotationalVelocity field.
    public Vector getRotationalVelocity(){
        return rotationalVelocity;
    }
    
    // Returns rotationOrigin field.
    public Vector getRotationOrigin(){
        return rotationOrigin;
    }
    
    // Returns centersOfMassEquivalents field.
    public HashMap getCentersOfMassEquivalents(){
        return centersOfMassEquivalents;
    }
    
    // Returns massEquivalentValues field.
    public HashMap getMEqVal(){
        return massEquivalentValues;
    }
   
    // Returns matterColor field.
    public Color getMatterColor(){
        return matterColor;
    }
    
    // Returns the fixed field.
    public Boolean getFixed(){
        return fixed;
    }
    
    // Returns the center of the argument mass equivalent.
    public Vector getCenter(String massEq){
        return centersOfMassEquivalents.getOrDefault(massEq, new Vector());
    }
    
    
//------------------------------------------------------------------------------    
// Mutator Methods
    // Sets particles field to argument newParticles.
    public void setParticles(ArrayList<Particle> newParticles){
        particles = newParticles;
    }
    
    // Sets position field to newPos argument.
    public void setPosition(Vector newPos){
        originPosition = newPos;
    }
    
    // Sets velocity field to newVel argument.
    public void setVelocity(Vector newVel){
        originVelocity = newVel;
    }
    
    // Sets rotationalVelocity field to newRot argument.
    public void setRotation(Vector newRot){
        rotationalVelocity = newRot;
    }
    
    // Sets rotationOrigin field to newRotOr argument.
    public void setRotationOrigin(Vector newRotOr){
        rotationOrigin = newRotOr;
    }
    
    // Sets centersOfMassEquivalents field to newCenters argument.
    public void setCenters(HashMap<String, Vector> newCenters){
        centersOfMassEquivalents = newCenters;
    }
    
    // Sets massEquivalentValues field to newValues argument.
    public void setMassEq(HashMap<String, Double> newValues){
        massEquivalentValues = newValues;
    }
    
    // Sets color field to col argument.
    public void setColor(Color col){
        matterColor = col;
    }
    
    // Sets fixed field to fix argument.
    public void setFix(Boolean fix){
        fixed = fix;
    }
   
    
    // Adds ArrayList argument to the particles field.
    public void addParticles(ArrayList<Particle> additionalParticles){
        particles.addAll(additionalParticles);
    }
    
    // Adds particle argument to the particles field.
    public void addParticle(Particle additionalParticle){
        particles.add(additionalParticle);
    }
    
    // Sets particles field to an empty ArrayList.
    public void clearAllParticles(){
        particles.clear();
    }
    
    // Removes particle argument from the particles field.
    public void removeParticle(Particle part){
        particles.remove(part);
    }
    
    // Moves position of the origin of the active matter object within the 
    //  frame of the object space by the argument vector.
    public void displaceBy(Vector disp){
        originPosition = Vector.add(originPosition, disp);
    }
    
    // Adds Vector argument to the velocity field of the active matter object.
    public void addVelocity(Vector dVel){
        originVelocity = Vector.add(originVelocity, dVel);
    }
    
    // Adds the argument to the rotation of the matter object.
    public void addRotation(Vector dRot){
        rotationalVelocity.add(dRot);
    }
    
    // Adds the argument String and vector pair to the list of centers of 
    //  mass equivalents.  
    public void addCenter(String massEq, Vector cen){
        centersOfMassEquivalents.put(massEq, cen);
    }
    
    // Removes the argument massEq (String) from the list of centers of mass
    //  equivalents.
    public void removeCenter(String massEq){
        centersOfMassEquivalents.remove(massEq);
    }
    
    // Fills centersOfMassEquivalents field with the existing massEquivs.
    public void fillCenters(){
        if (particles.size() > 0){
            for (int i = 0; i<particles.get(0).getMassEquivalentValues().keySet().size(); i++){
                calcAndAddCenter((String) particles.get(0).getMassEquivalentValues().keySet().toArray()[i]);
            }
        }
    }
    
    // Calculates and adds the center of the argument mass equivalent.
    public void calcAndAddCenter(String massEq){
        addCenter(massEq, calculateCenter(massEq));
    }
    
    // Adds a mass equivalent - value pair to the massEquivalentValues field.
    public void addMEqVal(String massEq, double val){
        massEquivalentValues.put(massEq, val);
    }
    
    // Removes a mass equivalent - value pair from the massEquivalentValues
    //  field.
    public void removeMEqVal(String massEq){
        massEquivalentValues.remove(massEq);
    }
    
    // Calculates and adds a mass equivalent value to the massEquivalentValues 
    //  field.
    public void calcAndAddME(String massEq){
        addMEqVal(massEq, calculateME(massEq));
    }
    
    // Fills out all mass equivalent values.
    public void fillME(){
        if (particles.size() > 0){
            for (int i = 0; i<particles.get(0).getMassEquivalentValues().keySet().size(); i++){
                calcAndAddME((String) particles.get(0).getMassEquivalentValues().keySet().toArray()[i]);
            }
        }
    }
    
    // Rotates the matter object about its rotation origin according to the 
    //  argument Vector where elements denote the number of radians which the 
    //  object is to be rotated about the corresponding axis.
    public void rotate(Vector rot){
        // getting center of mass position in matter frame
        Vector cenMass = Vector.subtract(centersOfMassEquivalents.get("Mass"), originPosition);
        
        // setting the center of mass as the origin, rotating, and replacing
        //  each particle.
        for (int i = 0; i< particles.size(); i++){
            particles.get(i).displaceBy(Vector.multiply(cenMass, -1.0));
            particles.get(i).rotateAroundAxis(rot, rot.getMag());
            particles.get(i).displaceBy(cenMass);
        }
    }

    // Steps the matter object forward in time, changing position based on 
    //  velocity and particle arrangement based on rotation.  Does not take
    //  forces into account.  Assumes that there is no relative motion between
    //  particles that comprise the matter object.
    public void timeStep(double time){
        if (!fixed){
            // Moving matter object in the object space frame.
            displaceBy(Vector.multiply(originVelocity, time));
            rotate(Vector.multiply(rotationalVelocity, time));
            
            // Moving particles in the matter frame and rotating their 
            //  velocities.
            for (int i = 0; i<particles.size(); i++){
                particles.get(i).timeStep(time);
            }
        }
    }
    
    // Calculates the moment of inertia of the object and adds it to the mass
    //  equivalents field.
    public void calcMomIn(){
        Vector CM = centersOfMassEquivalents.getOrDefault("Mass", new Vector()); // Center of mass
        double moment = 0.0; // What will be saved as moment of inertia
        double mass = 0.0; // Temporary variable for the mass of the particle
        Vector rad = new Vector(3); // Vector from CM to particle
        
        // Looping through all particles in the matter object.
        for (int i = 0; i<particles.size(); i++){
            mass = (double) particles.get(i).getMassEquivalentValues().getOrDefault("Mass", 0.0);
            rad = Vector.subtract(particles.get(i).getPosition(), CM);
            moment += mass*rad.getMag()*rad.getMag();
        }
        
        // Removes moment of inertia from the mass equivalents field if it 
        //  already exists there.
        removeMEqVal("Moment of Inertia");
        
        // Adding moment of inertia to the mass equivalents field.
        addMEqVal("Moment of Inertia", moment);
    }
    
//------------------------------------------------------------------------------
// Methods
    // Returns a vector corresponding to the center of the argument mass 
    //  equivalent in the frame of the Matter object.
    public Vector calculateCenter(String massEq){
        double total = 0.0;
        Vector mR = new Vector(new double[]{0.0, 0.0, 0.0});
        for (int i = 0; i< particles.size(); i++){
            mR = Vector.add(mR, Vector.multiply(particles.get(i).getPosition(), (double) particles.get(i).getMassEquivalentValues().get(massEq)));
            total += (double) particles.get(i).getMassEquivalentValues().get(massEq);
        }
        return Vector.multiply(mR, 1.0/total);
    }
    
    // Calculates the mass equivalent value for a given mass equivalent.
    public double calculateME(String massEq){
        double total = 0.0;
        for (int i = 0; i<particles.size(); i++){
            total += (double) particles.get(i).getMassEquivalentValues().getOrDefault(massEq, 0.0);
        }
        return total;
    }
    
    // Prints the originPosition, originVelocity, and rotationalVelocity of the
    //  matter object.
    public String printMatterState(){
        return "r: " + originPosition.printVector() + "  v: " + originVelocity.printVector() + "  w: " + rotationalVelocity.printVector();
    }
    
    // Prints the unchanging properties which describe the matter object.
    public String printMatterProperties(){
        // Initialize output string.
        String s = "";
        
        // Appending fields.
        s += "Mass Equivalents \n";
        s += massEquivalentValues + "\n";
        /* for (int i = 0; i<massEquivalentValues.size(); i++){
            s += "     " + massEquivalentValues.keySet().toArray()[i] + ": " + massEquivalentValues.get(massEquivalentValues.keySet().toArray()[i]) + "\n";
        } */
        s += "Centers of Mass Equivalents \n";
        for (int i = 0; i<centersOfMassEquivalents.size(); i++){
            s += "     " + centersOfMassEquivalents.keySet().toArray()[i] + ": " + centersOfMassEquivalents.get(centersOfMassEquivalents.keySet().toArray()[i]).printVector() + "\n";
        } 
        
        s += "Fixed: " + fixed + "\n";
        s += "Color: " + matterColor + "\n";
        s += "Particles \n";
        for (int i = 0; i<particles.size(); i++){
            s += "     " + particles.get(i).printParticle() + particles.get(i).getMassEquivalentValues().toString() + "\n";
        }
        
        return s;
    }
    
////////////////////////////////////////////////////////////////////////////////
// TESTING
    
    public static void main(String[] args){
        
        Particle partA = new Particle(new Vector(1.0, 1.0, 1.0), new Vector(1.0, 1000.0, 0.0));
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
        
        for (int i = 0; i<50; i++){
            System.out.println(Mat1.printMatterProperties());
            System.out.println(Mat1.printMatterState());
            Mat1.timeStep(1.0);
        }    
    }
    
    
}


