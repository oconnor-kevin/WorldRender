/* 
    Author: Kevin O'Connor 
    Email: worldrenderengine@gmail.com
    Summer 2015
*/



package linearalgebra;

public class Vector{
	
// Fields
	private double[] components;
	
//------------------------------------------------------------------------------        
// Constructors
	public Vector(){
		components = new double[1];}
	
	public Vector(int n){
		components = new double[n];}
	
	public Vector(double[] a){
		components = a;}
	
//------------------------------------------------------------------------------        
// Accessor Methods
	public double[] getComp(){
		return components;}
	
	public double getMag(){
		double a = 0;
		for (int i=0; i<components.length; i++){
			a += Math.pow(components[i], 2.0);}
		return Math.pow(a, 0.5);}
	
	public double[] getSphereComp(){
		double[] a = new double[3];
		if (components.length == 3){
			a[0] = getMag();
			a[1] = Math.atan(components[1]/components[0]);				
			a[2] = Math.atan(Math.pow(components[0]*components[0] + components[1]*components[1], 0.5)/components[2]);}
		return a;}	

//------------------------------------------------------------------------------
// Mutator Methods
	public void set(int i, double d){
		components[i] = d;}
        
        public void add(Vector v){}
        
        public void subtract(Vector v){}
        
        public void multiply(double s){}
        
        public void divide(double s){}
        
        public void dot(Vector v){}
        
        public void cross(Vector v){}
        
        
 
/*        
//------------------------------------------------------------------------------
// Interface Implementation
        public int compareTo(Comparable vec){
            Vector v = (Vector) vec;
            Boolean equal = true;
            if (v.getComponents().length != getComponents().length){
                equal = false;
            }
            else{
                for (int i = 0; i<getComponents().length; i++){
                    if (v.getComponents()[i] != getComponents()[i]){
                        equal = false;
                        break;
                    }
                }
            }
            if (equal == true){
                return 0;
            }
            else if (v.getMagnitude() < getMagnitude()){
                return 1;
            }
            else if (v.getMagnitude() > getMagnitude()){
                return -1;
            }
            else {
                return -1;
            }
        }
*/        
        
//------------------------------------------------------------------------------        
// Methods
	public static Vector add(Vector a, Vector b){
		double[] c = new double[a.getComp().length];
		for (int i=0; i<a.getComp().length; i++){
			c[i] = a.getComp()[i] + b.getComp()[i];}
                Vector d = new Vector(c);
                return d;
        }
        
        public static Vector subtract(Vector a, Vector b){
            return add(a, multiply(b, -1.0));
        }
	
	public static Vector multiply(Vector v, double s){
                Vector v2 = new Vector(v.getComp().length);
		for (int i=0; i<v.getComp().length; i++){
			v2.set(i, s*v.getComp()[i]);}
		return v2;
        }
	
	public static double dot(Vector a, Vector b){
		double c = 0;
		for (int i=0; i<a.getComp().length; i++){
			c += a.getComp()[i]*b.getComp()[i];}
		return c;
        }
	
	public static Vector cross(Vector a, Vector b){
		double[] c = new double[3];
		if (a.getComp().length == 3){
			c[0] = a.getComp()[1]*b.getComp()[2] - a.getComp()[2]*b.getComp()[1];
			c[1] = a.getComp()[2]*b.getComp()[0] - a.getComp()[0]*b.getComp()[2];
			c[2] = a.getComp()[0]*b.getComp()[1] - a.getComp()[1]*b.getComp()[0];}
		return new Vector(c);
        }
	
	public static double[] getRectComp(double[] a){
		double[] b = new double[3];
		if (a.length == 3){
			b[0] = a[0]*Math.sin(a[2])*Math.cos(a[1]);
			b[1] = a[0]*Math.sin(a[2])*Math.sin(a[1]);
			b[2] = a[0]*Math.cos(a[2]);}
		return b;
        }
	
	public static Vector rotatePhi(Vector v, double dPhi){
		double[] a = v.getSphereComp();
		if (v.getComp().length == 3){
			a[2] += dPhi;
			a = getRectComp(a);}
		return new Vector(a);
        }
	
	public static Vector rotateTheta(Vector v, double dTheta){
		double[] a = v.getSphereComp();
		if (v.getComp().length == 3){
			a[1] += dTheta;
			a = getRectComp(a);}
		return new Vector(a);
        }
	
	public static Vector increaseMagnitudeBy(Vector v, double dRho){
		double[] a = v.getSphereComp();
		if (v.getComp().length == 3){
			a[0] += dRho;
			a = getRectComp(a);}
		return new Vector(a);
        }
        
	/* TODO:public static Vector rotatedBasis(Vector v, double[] b){}
	*/	
	
        /* TODO:public static double[] eigenValues(Vector v)
        */
        
        /* TODO:public static ArrayList<Vector> eigenVectors(Vector v)
        */
        
			

}
