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
        
    public void displaceBy(Vector disp){}
    
    public void rotateAroundAxis(Vector axis, double dTheta){}
    
    public void accelerateBy(Vector acc){}
    
    
//------------------------------------------------------------------------------    
// Methods 
    
    // This method returns a string which describes the attributes of the argument particle.
    public static String printParticle(Particle p){
        String me = "(";
        for (int i = 0; i<(p.getMassEquivalentValues()).length(); i++){
            me += p.getMassEquivalentValues().keySet().toArray()[i].printMassEquivalent() + ": " + (p.getMassEquivalentValues).get(p.getMassEquivalentValues.keySet().toArray()[i]);
        }
        return p.getPosition().printVector() + p.getVelocity().printVector() + me;
    }
    
    // This method calls printParticle for the active particle.
    public String printParticle(){
        return printParticle(this);
    }

    
}
