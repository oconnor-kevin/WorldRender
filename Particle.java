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


import java.util.Arrays;
import linearalgebra.*;
import java.util.HashMap;

public class Particle {
// Fields // 
    private Vector position;
    private Vector velocity;
    private HashMap<String, Double> massEquivalentValues;
    
//------------------------------------------------------------------------------    
// Constructors
    // Creates a particle with 3-dimensional zero vectors for its position and 
    //  velocity and an empty hashmap for its massEquivalentValues.  
    public Particle(){
        position = new Vector(3);
        velocity = new Vector(3);
        massEquivalentValues = new HashMap();
    }
    
    // Creates a particle with pos set as its position field, vel set as its 
    //  velocity field, and an empty hashmap set as its massEquivalentValues
    //  field.  If pos and vel are not 3-dimensional, calls Particle().
    public Particle(Vector pos, Vector vel){
        if (pos.getComp().length == 3 && vel.getComp().length == 3){
            position = pos;
            velocity = vel;
        }
        else {
            System.out.println("At least one vector argument in Particle constructor is not 3-dimensional.");
            position = new Vector(3);
            velocity = new Vector(3);
        }
        massEquivalentValues = new HashMap();
    }
    
    // Creates a particle with pos set as its position field, vel set as its
    //  velocity field, and massEq set as its massEquivalentValues. 
    public Particle(Vector pos, Vector vel, HashMap<String, Double> massEq){
        if (pos.getComp().length == 3 && vel.getComp().length == 3){
            position = pos;
            velocity = vel;
        }
        else {
            System.out.println("At least one vector argument in Particle constructor is not 3-dimensional.");
            position = new Vector(3);
            velocity = new Vector(3);
        }
        massEquivalentValues = massEq;
    }

//------------------------------------------------------------------------------
// Accessor Methods
    // Returns position field.
    public Vector getPosition(){
        return position;
    }
    
    // Returns velocity field.
    public Vector getVelocity(){
        return velocity;
    }
    
    // Returns massEquivalentValues field.
    public HashMap getMassEquivalentValues(){
        return massEquivalentValues;
    }
    
//------------------------------------------------------------------------------    
// Mutator Methods
    // Sets the position field to the argument newPosition.  if newPosition is 
    //  not 3-dimensional, sets position to null.
    public void setPosition(Vector newPosition){
        if (newPosition.getComp().length == 3){
            position = newPosition;
        }
        else {
            System.out.println("Non 3-dimensional Vector argument for setPosition method.");
            position = null;
        }
    }
    
    // Sets the velocity field to the argument newVelocity.
    public void setVelocity(Vector newVelocity){
        if (newVelocity.getComp().length == 3){    
            velocity = newVelocity;
        }
        else {
            System.out.println("Non 3-dimensional Vector argument for setVelocity method.");
            velocity = null;
        }
    }
    
    // Adds the mass equivalent me and value val to the massEquivalentValues
    //  hashmap.
    public void addMassEquivalentValue(String me, double val){
        massEquivalentValues.put(me, val);
    }
    
    // Removes the mass equivalent me and value val from the 
    //  massEquivalentValues hashmap if it is contained.
    public void removeMassEquivalentValue(String me){
        massEquivalentValues.remove(me);
    }
        
    // Adds the displacement vector disp to the position vector.
    public void displaceBy(Vector disp){
        setPosition(Vector.add(getPosition(), disp));
    }
    
    // Rotates the position and velocity of the particle clockwise around an 
    //  axis defined by the Vector argument, axis.
    public void rotateAroundAxis(Vector axis, double ang){
        // Getting angular components of the axis.
        Vector sphereComp = new Vector(axis.getSphereComp());
        double theta = sphereComp.getComp()[1];
        double phi = sphereComp.getComp()[2];
        
        // Creating Position-Velocity vector which will be rotated to find the 
        //  new velocity.
        Vector posVel = Vector.add(position, velocity);
        
        // Rotating the position+velocity vector.
        posVel.rotateAroundAxis(axis, ang);
        
        // Rotating the position vector.
        position.rotateAroundAxis(axis, ang);
        
        // Solving for velocity vector and setting it as velocity.
        posVel.subtract(position);
        setVelocity(posVel);
    }
    
    // Adds the vector dVel to the velocity field of the particle.
    public void increaseVel(Vector dVel){
        setVelocity(Vector.add(getVelocity(), dVel));
    }
    
    // Changes the particle's position based on its velocity and the argument
    //  time unit.
    public void timeStep(double time){
        displaceBy(Vector.multiply(velocity, time));
    }
    
//------------------------------------------------------------------------------    
// Methods 
    
    // This method returns a string which describes the position and velocity
    //  of the particle, p.  
    public static String printParticle(Particle p){
        return "r: " + p.getPosition().printVector() + "  v: " + p.getVelocity().printVector() + "  M.E.: " + p.getMassEquivalentValues();
    }
    
    // This method calls printParticle for the active particle.
    public String printParticle(){
        return printParticle(this);
    }
    
//==============================================================================
// TESTING
    
    public static void main(String[] args){
        Particle a = new Particle(new Vector(0.0,0.0,1.0), new Vector(-1.0,1.0,1.0), new HashMap<String, Double>());
        
        System.out.println(a.printParticle());
        a.rotateAroundAxis(new Vector(1.0, 0.0, 0.0), 0.5*Math.PI);
        System.out.println(a.printParticle());
        a.rotateAroundAxis(new Vector(1.0, 0.0, 0.0), 0.5*Math.PI);
        System.out.println(a.printParticle());
    }
    
}
