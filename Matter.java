/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinoconnor
 */
import java.util.ArrayList<E>;



public class Matter {
// Fields
    private ArrayList<Particle> particles;
    
// Constructors
    public Matter(){
        particles = new ArrayList<>();
    }
    
    public Matter(ArrayList<Particle> particles2){
        particles = particles2;
    }
    
// Accessor Methods    
    public ArrayList<Particle> getParticles(){
        return particles;
    }
    
// Mutator Methods
    public void setParticles(ArrayList<Particle> newParticles){
        particles = newParticles;
    }
    
    public void addParticles(ArrayList<Particle> additionalParticles){
        particles.addAll(additionalParticles);
    }
    
    public void addParticle(Particle additionalParticle){
        particles.add(additionalParticle);
    }
    
    public void clearAllParticles(){
        particles.clear();
    }
    
}
