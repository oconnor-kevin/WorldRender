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

public class Matter {
// Fields
    private ArrayList<Particle> particles;
    private int id;
    
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
    
}
