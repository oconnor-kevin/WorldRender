import linearalgebra.*;

public class ObjectSpace {

// Fields    
    private int[][][] idspace;

// Constructors
    public ObjectSpace(int[][][] a){
        idspace = a;
    }
    
    public ObjectSpace(int i, int j, int k){
        idspace = new int[i][j][k];
    }
    
    public ObjectSpace(){
        idspace = new int[0][0][0];
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
    
// Mutator Methods
    public void addParticle(int i, int j, int k, int id){
        idspace[i][j][k] = id;
    }
    
    public void removeParticle(int i, int j, int k){
        idspace[i][j][k] = 0;
    }
    
    public void clearObjectSpace(){
        for (int i = 0; i<getIDSpace().length; i++){
            for (int j = 0; j<getIDSpace()[0].length; j++){
                for (int k = 0; k<getIDSpace()[0][0].length; k++){
                    getIDSpace()[i][j][k] = 0;
                }
            }
        }
    }
    




}
