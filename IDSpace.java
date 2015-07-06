import linearalgebra.*;
import java.util.ArrayList;

public class IDSpace {

// Fields    
    private int[][][] idspace;
    private ArrayList<Particle> pastActiveParticles;
    private ArrayList<Particle> activeParticles;

// Constructors
    public IDSpace(int[][][] a){
        idspace = a;
        activeParticles = new ArrayList<>();
        pastActiveParticles = new ArrayList<>();
    }
    
    public IDSpace(int i, int j, int k){
        idspace = new int[i][j][k];
        activeParticles = new ArrayList<>();
        pastActiveParticles = new ArrayList<>();
    }
    
    public IDSpace(){
        idspace = new int[0][0][0];
        activeParticles = new ArrayList<>();
        pastActiveParticles = new ArrayList<>();
    }
    
// Accessor Methods
    public int[][][] getIDSpace(){
        return idspace;
    }
    
    public String print2D(int i){
        String s = "";
        for (int j = getIDSpace()[i].length - 1; j>=0; j--){
            for (int k = 0; k<getIDSpace()[0][0].length; k++){
                s += getIDSpace()[i][j][k] + " ";
            }
            s += " \n";
        }
        return s;
    }
    
    public ArrayList<Particle> getActiveParticles(){
        return activeParticles;
    }
    
    public ArrayList<Particle> getPastActiveParticles(){
        return pastActiveParticles;
    }
    
// Mutator Methods
    public void addParticle(Particle p){
        idspace[(int) p.getPosition().getComponents()[0]][(int) p.getPosition().getComponents()[1]][(int) p.getPosition().getComponents()[2]] = p.getID();
        activeParticles.add(p);
    }
    
    public void showParticle(Particle p){
        idspace[(int) p.getPosition().getComponents()[0]][(int) p.getPosition().getComponents()[1]][(int) p.getPosition().getComponents()[2]] = p.getID();
    }
    
    public void removeParticle(Particle p){
        idspace[(int) p.getPosition().getComponents()[0]][(int) p.getPosition().getComponents()[1]][(int) p.getPosition().getComponents()[2]] = 0;
        pastActiveParticles.add(p);
        activeParticles.remove(p);
    }
    
    public void clearObjectSpace(){
        for (int i = 0; i<getIDSpace().length; i++){
            for (int j = 0; j<getIDSpace()[0].length; j++){
                for (int k = 0; k<getIDSpace()[0][0].length; k++){
                    getIDSpace()[i][j][k] = 0;
                }
            }
        }
        activeParticles.clear();
    }

// for now this method assumes no collisions and one time unit increment
/* 
    public void incrementTime(){
        pastActiveParticles.clear();
        for (int i = 0; i<activeParticles.size(); i++){
            removeParticle(activeParticles.get(i));
            activeParticles.get(i).timeStep(1.0);
        }
    }
*/    
    
    public void updateObjectSpace(){
        for (int i = 0; i<activeParticles.size(); i++){
            showParticle(activeParticles.get(i));
        }
    }

}
