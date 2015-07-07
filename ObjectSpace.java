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
        ArrayList<Matter> newActiveMatter = new ArrayList<Matter>();
        for (int i = 0; i<activeMatter.size(); i++){
            newActiveMatter.add(Matter.incrementTime(activeMatter.get(i), timeUnits));
        }
        activeMatter = newActiveMatter;
    }
    
// starting with least efficient algorithm, will adjust later
/* TODO: make collisionCheck algorithm efficient 
   -Also, the algorithm seems to be altering the position of the particle but
    otherwise seems to be functioning properly.
    */
    
    public boolean collisionCheck(double timeUnits){
        boolean collided = false;
        ObjectSpace testOS = new ObjectSpace(this.getActiveMatter(), this.getPastActiveMatter());
        testOS.incrementTime(timeUnits);
        ArrayList<Vector> initialPositions = new ArrayList<>();
        ArrayList<Vector> finalPositions = new ArrayList<>();
        for (int i = 0; i<testOS.getActiveMatter().size(); i++){
            for (int j = 0; j<testOS.getActiveMatter().get(i).getParticles().size(); j++){
                initialPositions.add(testOS.getPastActiveMatter().get(i).getParticles().get(j).getPosition());
                finalPositions.add(testOS.getActiveMatter().get(i).getParticles().get(j).getPosition());
            }
        }
        int[] collisionIndices = new int[2];
        for (int i = 0; i<initialPositions.size(); i++){
            for (int j = 0; j<initialPositions.size(); j++){
                if (!collided){    
                    if (i != j) {
                        if (Math.max(finalPositions.get(i).getComponents()[0], initialPositions.get(i).getComponents()[0]) >= Math.min(finalPositions.get(j).getComponents()[0], initialPositions.get(j).getComponents()[0]) &&
                               Math.max(finalPositions.get(j).getComponents()[0], initialPositions.get(j).getComponents()[0]) >= Math.min(finalPositions.get(i).getComponents()[0], initialPositions.get(i).getComponents()[0]) &&
                               Math.max(finalPositions.get(i).getComponents()[1], initialPositions.get(i).getComponents()[1]) >= Math.min(finalPositions.get(j).getComponents()[1], initialPositions.get(j).getComponents()[1]) &&
                               Math.max(finalPositions.get(j).getComponents()[1], initialPositions.get(j).getComponents()[1]) >= Math.min(finalPositions.get(i).getComponents()[1], initialPositions.get(i).getComponents()[1]) &&
                               Math.max(finalPositions.get(i).getComponents()[2], initialPositions.get(i).getComponents()[2]) >= Math.min(finalPositions.get(j).getComponents()[2], initialPositions.get(j).getComponents()[2]) &&
                               Math.max(finalPositions.get(j).getComponents()[2], initialPositions.get(j).getComponents()[2]) >= Math.min(finalPositions.get(i).getComponents()[2], initialPositions.get(i).getComponents()[2])){
                            collided = true;
                            collisionIndices[0] = i;
                            collisionIndices[1] = j;
                        }
                    }
                }
            }
        }
        nextCollision = Vector.multiplyVectorByScalar(Vector.addVectors(Vector.multiplyVectorByScalar(Vector.addVectors(finalPositions.get(collisionIndices[0]), initialPositions.get(collisionIndices[0])), 0.5),
                Vector.multiplyVectorByScalar(Vector.addVectors(finalPositions.get(collisionIndices[1]), initialPositions.get(collisionIndices[1])), 0.5)), 0.5);
        return collided;
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

    public static ArrayList<Vector> combineArrayLists(ArrayList<Vector> a, ArrayList<Vector> b){
        ArrayList<Vector> c = new ArrayList<Vector>();
        for (int i = 0; i<a.size(); i++){
            c.add(a.get(i));
        }
        for (int i = 0; i<b.size(); i++){
            c.add(b.get(i));
        }
        return c;
    }
    
    public static ArrayList<Vector> combineListOfArrayLists(ArrayList<ArrayList<Vector>> a){
        ArrayList<Vector> b = new ArrayList<Vector>();
        for (int i = 0; i<a.size(); i++){
            b = combineArrayLists(b, a.get(i));
        }
        return b;
    }
}
