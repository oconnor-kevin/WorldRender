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
    // Creates a particle with 3-dimensional zero vectors for its position and 
    //  velocity and an empty hashmap for its massEquivalentValues.  
    public Particle(){
        position = new Vector(new double[]{0.0,0.0,0.0});
        velocity = new Vector(new double[]{0.0,0.0,0.0});
        massEquivalentValues = new HashMap();
    }
    
    // Creates a particle with pos set as its position field, vel set as its 
    //  velocity field, and an empty hashmap set as its massEquivalentValues
    //  field.  
    public Particle(Vector pos, Vector vel){
        position = pos;
        velocity = vel;
        massEquivalentValues = new HashMap();
    }
    
    // Creates a particle with pos set as its position field, vel set as its
    //  velocity field, and massEq set as its massEquivalentValues. 
    public Particle(Vector pos, Vector vel, HashMap<MassEquivalent, Double> massEq){
        position = pos;
        velocity = vel;
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
    // Sets the position field to the argument newPosition.
    public void setPosition(Vector newPosition){
        position = newPosition;
    }
    
    // Sets the velocity field to the argument newVelocity.
    public void setVelocity(Vector newVelocity){
        velocity = newVelocity;
    }
    
    // Adds the mass equivalent me and value val to the massEquivalentValues
    //  hashmap.
    public void addMassEquivalentValue(MassEquivalent me, double val){
        massEquivalentValues.put(me, val);
    }
    
    // Removes the mass equivalent me and value val from the 
    //  massEquivalentValues hashmap.
    public void removeMassEquivalentValue(MassEquivalent me){
        massEquivalentValues.remove(me);
    }
        
    // Adds the displacement vector disp to the position vector.
    public void displaceBy(Vector disp){
        setPosition(Vector.add(getPosition(), disp));
    }
    
    // Rotates the position of the particle around an axis defined by the 
    //  Vector argument, axis.
    public void rotateAroundAxis(Vector axis, double ang){
        Vector sphereComp = new Vector(axis.getSphereComp());
        double theta = sphereComp.getComp()[1];
        double phi = sphereComp.getComp()[2];
        setPosition(Vector.rotateTheta(getPosition(), -theta));
        setPosition(Vector.rotatePhi(getPosition(), -phi));
        setPosition(Vector.rotatePhi(getPosition(), ang));
        setPosition(Vector.rotatePhi(getPosition(), phi));
        setPosition(Vector.rotateTheta(getPosition(), theta));
        setPosition(new Vector(Vector.getRectComp(getPosition().getComp())));
    }
    
    // Adds the vector dVel to the velocity field of the particle.
    public void increaseVel(Vector dVel){
        setVelocity(Vector.add(getVelocity(), dVel));
    }
    
    
//------------------------------------------------------------------------------    
// Methods 
    
    // This method returns a string which describes the position and velocity
    //  of the particle, p.  
    public static String printParticle(Particle p){
        return p.getPosition().printVector() + p.getVelocity().printVector();
    }
    
    // This method calls printParticle for the active particle.
    public String printParticle(){
        return printParticle(this);
    }

    
}
