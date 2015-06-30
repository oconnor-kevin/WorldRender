/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinoconnor
 */
import linearalgebra.*;

public class Particle {
// Fields
    private int id;
    private Vector position;
    private Vector velocity;

// Constructors
    public Particle(){
        id = 0;
        position = new Vector(new double[]{0.0,0.0,0.0});
        velocity = new Vector(new double[]{0.0,0.0,0.0});
    }
    
    public Particle(int id2, Vector position2, Vector velocity2){
        id = id2;
        position = position2;
        velocity = velocity2;
    }
    
    public Particle(Vector position2, Vector velocity2){
        position = position2;
        velocity = velocity2;
        id = generateRandomID(1000000);
    }

// Accessor Methods
    public int getID(){
        return id;
    }
    
    public Vector getPosition(){
        return position;
    }
    
    public Vector getVelocity(){
        return velocity;
    }
    
// Mutator Methods
    public void setID(int newID){
        id = newID;
    }
    
    public void setPosition(Vector newPosition){
        position = newPosition;
    }
    
    public void setVelocity(Vector newVelocity){
        velocity = newVelocity;
    }
    
// the following is a simplified timeStep method which assumes acceleration << velocity/time    
    public void timeStep(double timeUnits){
        position = Vector.addVectors(position, (Vector.multiplyVectorByScalar(velocity, timeUnits)));
    }
    
    
// Methods    
    public static int generateRandomID(int max){
        return (int) Math.floor(Math.random()*max);
    }
    

    
}
