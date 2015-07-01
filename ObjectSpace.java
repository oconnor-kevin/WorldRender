import linearalgebra.*;
import java.util.ArrayList;

public class ObjectSpace {

//------------------------------------------------------------------------------    
// Fields    
    private ArrayList<Matter> pastActiveMatter;
    private ArrayList<Matter> activeMatter;
    private Vector nextCollision;

//------------------------------------------------------------------------------    
// Constructors
    public ObjectSpace(){
        activeMatter = new ArrayList<>();
        pastActiveMatter = new ArrayList<>();
        nextCollision = new Vector();
    }
    
    public ObjectSpace(ArrayList<Matter> a, ArrayList<Matter> b){
        activeMatter = a;
        pastActiveMatter = b;
        nextCollision = new Vector();
    }
    
    public ObjectSpace(ArrayList<Matter> a){
        activeMatter = a;
        pastActiveMatter = new ArrayList<>();
        nextCollision = new Vector();
    }
    
// -----------------------------------------------------------------------------    
// Accessor Methods
    public ArrayList<Matter> getActiveMatter(){
        return activeMatter;
    }
    
    public ArrayList<Matter> getPastActiveMatter(){
        return pastActiveMatter;
    }
    
//-----------------------------------------------------------------------------    
// Mutator Methods
    public void addMatter(Matter m){
        pastActiveMatter.add(new Matter());
        activeMatter.add(m);
    }
    
    public void removeMatter(Matter m){
        pastActiveMatter.add(m);
        activeMatter.set(activeMatter.indexOf(m), new Matter());
    }
    
    public void clearObjectSpace(){
        for (int i = 0; i<activeMatter.size(); i++){
            removeMatter(activeMatter.get(i));
        }
    }

// for now this method assumes no collisions and one time unit increment
    public void incrementTime(double timeUnits){
        pastActiveMatter = activeMatter;
        ArrayList<Matter> activeMatterCopy = new ArrayList<Matter>();
        for (int i = 0; i<activeMatter.size(); i++){
            
            activeMatter.set(i, activeMatterCopy.get(i).incrementTime(timeUnits));
        }
    }
    
    public boolean collisionCheck(double timeUnits){
        ArrayList<Vector> displacementVectors = new ArrayList<Vector>();
        for (int i = 0; i<activeMatter.size(); i++){
            displacementVectors.add()
        }
    }
    
//------------------------------------------------------------------------------
// Methods
    public static String printObjectSpace(ObjectSpace o){
        String s = "";
        for (int i = 0; i<o.getActiveMatter().size(); i++){
            s += Matter.printParticles(o.getActiveMatter().get(i));
        }
        return s;
    }

}
