package linearalgebra;

public class Vector {
	
// Fields
	private double[] components;
	
// Constructors
	public Vector(){
		components = new double[1];}
	
	public Vector(int n){
		components = new double[n];}
	
	public Vector(double[] a){
		components = a;}
	
// Accessor Methods
	public double[] getComponents(){
		return components;}
	
	public void setComponentAtIndexTo(int i, double d){
		components[i] = d;}
	
	public double getMagnitude(){
		double a = 0;
		for (int i=0; i<components.length; i++){
			a += Math.pow(components[i], 2.0);}
		return Math.pow(a, 0.5);}
	
	public double[] getComponentsInSphericalCoordinates(){
		double[] a = new double[3];
		if (components.length == 3){
			a[0] = getMagnitude();
			a[1] = Math.atan(components[1]/components[0]);				
			a[2] = Math.atan(Math.pow(components[0]*components[0] + components[1]*components[1], 0.5)/components[2]);}
		return a;}		
// Methods
	public static Vector addVectors(Vector a, Vector b){
		double[] c = new double[a.getComponents().length];
		for (int i=0; i<a.getComponents().length; i++){
			c[i] = a.getComponents()[i] + b.getComponents()[i];}
	Vector d = new Vector(c);
	return d;}
	
	public static Vector multiplyVectorByScalar(Vector v, double s){
		for (int i=0; i<v.getComponents().length; i++){
			v.setComponentAtIndexTo(i, s*v.getComponents()[i]);}
		return v;}
	
	public static double dotProduct(Vector a, Vector b){
		double c = 0;
		for (int i=0; i<a.getComponents().length; i++){
			c += a.getComponents()[i]*b.getComponents()[i];}
		return c;}
	
	public static Vector crossProduct(Vector a, Vector b){
		double[] c = new double[3];
		if (a.getComponents().length == 3){
			c[0] = a.getComponents()[1]*b.getComponents()[2] - a.getComponents()[2]*b.getComponents()[1];
			c[1] = a.getComponents()[2]*b.getComponents()[0] - a.getComponents()[0]*b.getComponents()[2];
			c[2] = a.getComponents()[0]*b.getComponents()[1] - a.getComponents()[1]*b.getComponents()[0];}
		return new Vector(c);}
	
	public static double[] convertToRectangularCoordinates(double[] a){
		double[] b = new double[3];
		if (a.length == 3){
			b[0] = a[0]*Math.sin(a[2])*Math.cos(a[1]);
			b[1] = a[0]*Math.sin(a[2])*Math.sin(a[1]);
			b[2] = a[0]*Math.cos(a[2]);}
		return b;}
	
	public static Vector rotatePhi(Vector v, double dPhi){
		double[] a = v.getComponentsInSphericalCoordinates();
		if (v.getComponents().length == 3){
			a[2] += dPhi;
			a = convertToRectangularCoordinates(a);}
		return new Vector(a);}
	
	public static Vector rotateTheta(Vector v, double dTheta){
		double[] a = v.getComponentsInSphericalCoordinates();
		if (v.getComponents().length == 3){
			a[1] += dTheta;
			a = convertToRectangularCoordinates(a);}
		return new Vector(a);}
	
	public static Vector increaseMagnitudeBy(Vector v, double dRho){
		double[] a = v.getComponentsInSphericalCoordinates();
		if (v.getComponents().length == 3){
			a[0] += dRho;
			a = convertToRectangularCoordinates(a);}
		return new Vector(a);}
	/* TODO:public static Vector rotatedBasis(Vector v, double[] b){}
	*/	
	
        /* TODO:public static double[] eigenValues(Vector v)
        */
        
        /* TODO:public static ArrayList<Vector> eigenVectors(Vector v)
        */
        
			

}
