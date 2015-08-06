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
    
    // Vector representing the rotational velocity of the matter coordinate 
    //  system within the object space coordinate system.
    Vector rotationalVelocity;
    
    // ArrayList of the particles contained in the matter object.
    ArrayList<Particle> particles;
    
    // A list of all the centers of mass equivalents for the Matter object in
    //  the object space frame.
    HashMap<String, Vector> centersOfMassEquivalents;
    
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
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = true;
    }
    
    public Matter(ArrayList<Particle> parts){
        particles = parts;
        originPosition = new Vector(3);
        originVelocity = new Vector(3);
        rotationalVelocity = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = true;
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = true;
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Boolean fix){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = fix;
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Vector rot, Boolean fix){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = rot;
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = fix;
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
    
    // Returns centersOfMassEquivalents field.
    public HashMap getCentersOfMassEquivalents(){
        return centersOfMassEquivalents;
    }
   
    // Returns matterColor field.
    public Color getMatterColor(){
        return matterColor;
    }
    
    // Returns the fixed field.
    public Boolean getFixed(){
        return fixed;
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
    
    // Sets centersOfMassEquivalents field to newCenters argument.
    public void setCenters(HashMap<String, Vector> newCenters){
        centersOfMassEquivalents = newCenters;
    }
    
    // Sets fixed field to fix argument.
    public void setFix(Boolean fix){
        fixed = fix;
    }
    
    public void addParticles(ArrayList<Particle> additionalParticles){
        for(int i = 0; i<additionalParticles.size(); i++){
            additionalParticles.get(i).setID(id);
        }
        particles.addAll(additionalParticles);
    }
    
    public void addParticle(Particle additionalParticle){
        additionalParticle.setID(id);
        particles.add(additionalParticle);
    }
    
    public void clearAllParticles(){
        particles.clear();
    }
    

//------------------------------------------------------------------------------
// Methods
    public static ArrayList<Vector> particleDisplacement(Matter a, Matter b){
        ArrayList<Vector> particleDisplacements = new ArrayList<Vector>();
        if (a.getParticles().size() == b.getParticles().size()){
            for (int i = 0; i<a.getParticles().size(); i++){
                particleDisplacements.add(Vector.addVectors(a.getParticles().get(i).getPosition(), Vector.multiplyVectorByScalar(b.getParticles().get(i).getPosition(), -1.0)));
            }
        }
        return particleDisplacements;
    }
    
    public static Matter incrementTime(Matter m, double timeUnits){
        ArrayList<Particle> newParticles = new ArrayList<Particle>();
        for(int i = 0; i<m.getParticles().size(); i++){
            newParticles.add(Particle.timeStep(m.getParticles().get(i), timeUnits));
        }
        return new Matter(newParticles);
    }
    
    public static String printParticles(Matter m){
        String s = "";
        for (int i = 0; i<m.getParticles().size(); i++){
            s += Particle.printParticle(m.getParticles().get(i));
        }
        s += " \n";
        return s;
    }

}
