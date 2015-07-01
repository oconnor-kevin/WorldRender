/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinoconnor
 */
import java.util.ArrayList;
import linearalgebra.*;

public class Matter {

//------------------------------------------------------------------------------    
// Fields
    private ArrayList<Particle> particles;
    private int id;
    
//------------------------------------------------------------------------------    
// Constructors
    public Matter(){
        particles = new ArrayList<>();
    }
    
    public Matter(ArrayList<Particle> particles2){
        particles = particles2;
    }
    
//------------------------------------------------------------------------------    
// Accessor Methods    
    public ArrayList<Particle> getParticles(){
        return particles;
    }
    
//------------------------------------------------------------------------------    
// Mutator Methods
    public void setParticles(ArrayList<Particle> newParticles){
        for (int i = 0; i<newParticles.size(); i++){
            newParticles.get(i).setID(id);
        }
        particles = newParticles;
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
    public ArrayList<Vector> particleDisplacement(Matter a, Matter b){
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
        
    }

}
