/* 
    Author: Kevin O'Connor 
    Email: worldrenderengine@gmail.com
    Summer 2015
*/


import linearalgebra.*;
import java.util.HashMap;

public class Particle {
// Fields
    private int id;
    private Vector position;
    private Vector velocity;
    private HashMap<MassEquivalent, Double> massEquivalents;
    
//------------------------------------------------------------------------------    
// Constructors
    public Particle(){
        id = generateRandomID(1000000);
        position = new Vector(new double[]{0.0,0.0,0.0});
        velocity = new Vector(new double[]{0.0,0.0,0.0});
        massEquivalents = new HashMap();
    }
    
    public Particle(int id2, Vector position2, Vector velocity2){
        id = id2;
        position = position2;
        velocity = velocity2;
        massEquivalents = new HashMap();
    }
    
    public Particle(Vector position2, Vector velocity2){
        position = position2;
        velocity = velocity2;
        id = generateRandomID(1000000);
        massEquivalents = new HashMap();
    }

//------------------------------------------------------------------------------
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
    
    public HashMap getMassEquivalents(){
        return massEquivalents;
    }
    
//------------------------------------------------------------------------------    
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
    
    public void addMassEquivalentOfValue(MassEquivalent me, double val){
        massEquivalents.put(me, val);
    }
    
//------------------------------------------------------------------------------    
// Methods 
    
// the following is a simplified timeStep method which assumes acceleration << velocity/time    
    public static Particle timeStep(Particle p, double timeUnits){
        Vector oldVelocity = new Vector(p.getVelocity().getComponents());
        Vector newPosition = Vector.addVectors(p.getPosition(), (Vector.multiplyVectorByScalar(p.getVelocity(), timeUnits)));
        return new Particle(p.getID(), newPosition, p.getVelocity());
    }
   
    public static int generateRandomID(int max){
        return (int) Math.floor(Math.random()*max);
    }
    
    public static String printParticle(Particle p){
        return (String) (p.getID() + ", (" + p.getPosition().getComponents()[0] + ", " + p.getPosition().getComponents()[1] + ", " + p.getPosition().getComponents()[2] + "), (" + p.getVelocity().getComponents()[0] + ", " + p.getVelocity().getComponents()[1] + ", " + p.getVelocity().getComponents()[2] + ") \n");
    }
    

    
}
