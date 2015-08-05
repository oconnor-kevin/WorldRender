/* 
    Author: Kevin O'Connor 
    Email: worldrenderengine@gmail.com
    Summer 2015
*/


import linearalgebra.*;
import java.util.HashMap;

public class Particle {
// Fields // 
    private Vector position;
    private Vector velocity;
    private HashMap<MassEquivalent, Double> massEquivalentValues;
    
//------------------------------------------------------------------------------    
// Constructors
    public Particle(){
        position = new Vector(new double[]{0.0,0.0,0.0});
        velocity = new Vector(new double[]{0.0,0.0,0.0});
        massEquivalentValues = new HashMap();
    }
    
    public Particle(Vector pos, Vector vel){
        position = pos;
        velocity = vel;
        massEquivalentValues = new HashMap();
    }
    
    public Particle(Vector pos, Vector vel, HashMap<MassEquivalent, Double> massEq){
        position = pos;
        velocity = vel;
        massEquivalentValues = massEq;
    }

//------------------------------------------------------------------------------
// Accessor Methods
    public Vector getPosition(){
        return position;
    }
    
    public Vector getVelocity(){
        return velocity;
    }
    
    public HashMap getMassEquivalentValues(){
        return massEquivalentValues;
    }
    
//------------------------------------------------------------------------------    
// Mutator Methods
    public void setPosition(Vector newPosition){
        position = newPosition;
    }
    
    public void setVelocity(Vector newVelocity){
        velocity = newVelocity;
    }
    
    public void addMassEquivalentValue(MassEquivalent me, double val){
        massEquivalentValues.put(me, val);
    }
    
    public void removeMassEquivalentValue(MassEquivalent me){
        massEquivalentValues.remove(me);
    }
    
    public void timeStep(double timeUnits){
        setPosition(Vector.addVectors(getPosition(), (Vector.multiplyVectorByScalar(getVelocity(), timeUnits))));
    }
    
//------------------------------------------------------------------------------    
// Methods 
    
// the following is a simplified timeStep method which assumes acceleration << velocity/time    
    public static Particle timeStep(Particle p, double timeUnits){
        Vector oldVelocity = new Vector(p.getVelocity().getComponents());
        Vector newPosition = Vector.addVectors(p.getPosition(), (Vector.multiplyVectorByScalar(p.getVelocity(), timeUnits)));
        return new Particle(newPosition, p.getVelocity());
    }
   
    public static int generateRandomID(int max){
        return (int) Math.floor(Math.random()*max);
    }
    
    public static String printParticle(Particle p){
        return (String) (p.getID() + ", (" + p.getPosition().getComponents()[0] + ", " + p.getPosition().getComponents()[1] + ", " + p.getPosition().getComponents()[2] + "), (" + p.getVelocity().getComponents()[0] + ", " + p.getVelocity().getComponents()[1] + ", " + p.getVelocity().getComponents()[2] + ") \n");
    }
    

    
}
