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
    //  object.
    Vector rotationOrigin;
    
    // Vector representing the rotational velocity of the matter coordinate 
    //  system within the object space coordinate system in radians/second.
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
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = new HashMap();
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
        matterColor = Color.BLACK;
        fixed = true;
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = new Vector(3);
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = true;
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Boolean fix){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = new Vector(3);
        rotationOrigin = new Vector(3);        
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = fix;
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Vector rot, Boolean fix){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = rot;
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = new HashMap();
        matterColor = Color.BLACK;
        fixed = fix;
    }
    
    public Matter(ArrayList<Particle> parts, Vector pos, Vector vel, Vector rot, Boolean fix, HashMap centers){
        particles = parts;
        originPosition = pos;
        originVelocity = vel;
        rotationalVelocity = rot;
        rotationOrigin = new Vector(3);
        centersOfMassEquivalents = centers;
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
    
    // Returns rotationOrigin field.
    public Vector getRotationOrigin(){
        return rotationOrigin;
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
    
    // Returns the center of the argument mass equivalent.
    public Vector getCenter(String massEq){
        return centersOfMassEquivalents.get(massEq);
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
    //  NOTE: I have no idea how to implement this method or what this method 
    //   needs as its argument.  Will do more research before moving forward.
    public void addRotation(){}
    
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
    public void fillCenters(){}
    
    
    

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
    
    
    
}


