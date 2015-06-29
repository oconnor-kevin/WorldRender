package linearalgebra;


public class Matrix {
// Fields 
	private double[][] elements;
	
// Constructors
	public Matrix(){
		elements = new double[1][1];}
	
        public Matrix(double[][] a){
		elements = a;}
	
        public Matrix(int m, int n){
		elements = new double[m][n];}
	// Accessor Methods
	public double[][] getElements(){
		return elements;}
	
	public void setElementAtIndex(int i, int j, double d){
		elements[i][j] = d;}
	
// Methods
	public static double getTrace(Matrix a){
		double b = 0.0;
		if (a.getElements().length == a.getElements()[0].length){
			for (int i=0; i<a.getElements().length; i++){
				b += a.getElements()[i][i];}}
		return b;}

	public static Matrix matrixMultiply(Matrix a, Matrix b){
            Matrix c = new Matrix(a.getElements().length, b.getElements()[0].length);
            if(a.getElements()[0].length == b.getElements().length){
                    for(int i = 0; i<a.getElements().length; i++){
			for (int j = 0; j<b.getElements()[0].length; j++){
                            double sum = 0.0;
                            for (int k = 0; k<b.getElements().length; k++){
                                sum += a.getElements()[i][k]*b.getElements()[k][j];}
                                c.getElements()[i][j] = sum;}}}
                return c;}

        public static Matrix matrixTranspose(Matrix a){
            Matrix b = new Matrix(a.getElements()[0].length, a.getElements().length);
                for (int i = 0; i<a.getElements().length; i++){
                    for (int j = 0; j<a.getElements()[0].length; j++){
                        b.setElementAtIndex(i,j,a.getElements()[j][i]);}}
            return b;}

        public static Vector multiplyMatrixByVector(Matrix a, Vector b){
            Matrix c = new Matrix(new double[][]{b.getComponents()});
            return new Vector(matrixMultiply(c,a).getElements()[0]);}
        
        public static Matrix scalarMultiply(double a, Matrix b){
            Matrix c = new Matrix(b.getElements().length, b.getElements()[0].length);
            for (int i = 0; i<b.getElements().length; i++){
                for (int j = 0; j<b.getElements()[0].length; j++){
                    c.getElements()[i][j] = b.getElements()[i][j]*a;}}
            return c;}
        
        public static Matrix sumMatrix(Matrix a, Matrix b){
            Matrix c = new Matrix(a.getElements().length, a.getElements()[0].length);
            if (a.getElements().length == b.getElements().length && a.getElements()[0].length == b.getElements()[0].length){
                for (int i = 0; i<b.getElements().length; i++){
                    for (int j = 0; j<b.getElements()[0].length; j++){
                        c.getElements()[i][j] = a.getElements()[i][j] + b.getElements()[i][j];}}}
            return c;}
        
        public static Matrix removeRow(int i, Matrix a){
            Matrix b = new Matrix(a.getElements().length - 1, a.getElements()[0].length);
            for (int j = 0; j<a.getElements().length; j++){
                for (int k = 0; k<a.getElements()[0].length; k++){
                    if (j < i){
                        b.getElements()[j][k] = a.getElements()[j][k];}
                    else if (j > i){
                        b.getElements()[j-1][k] = a.getElements()[j][k];}}}
            return b;}
        
        public static Matrix removeColumn(int i, Matrix a){
            Matrix b = new Matrix(a.getElements().length, a.getElements()[0].length - 1);
            for (int j = 0; j<a.getElements().length; j++){
                for (int k = 0; k<a.getElements()[0].length; k++){
                    if (k < i){
                        b.getElements()[j][k] = a.getElements()[j][k];}
                    else if (k > i){
                        b.getElements()[j][k-1] = a.getElements()[j][k];}}}
            return b;}
        
        public static double determinant(Matrix a){
            double det = 0.0;
            if (a.getElements().length == a.getElements()[0].length){
                if (a.getElements().length == 2){
                    return (a.getElements()[0][0]*a.getElements()[1][1] - a.getElements()[0][1]*a.getElements()[1][0]);}
                else {
                    for (int j = 0; j<a.getElements()[0].length; j++){
                        det += Math.pow(-1.0, j)*a.getElements()[0][j]*determinant((removeRow(0, removeColumn(j,a))));}}}
            return det;}
                
        public static Matrix identityMatrix(int i){
            Matrix a = new Matrix(i, i);
            for (int j = 0; j < i; j++){
                    a.getElements()[j][j] = 1.0;}
            return a;}
        
// TODO: implement an invert method that works for all square matrices        
        
        public static Matrix invert(Matrix a){
            Matrix b = new Matrix(a.getElements().length, a.getElements()[0].length);
            if (a.getElements().length == a.getElements()[0].length && determinant(a) != 0.0){
                if (a.getElements().length == 2){
                    b.getElements()[0][0] = a.getElements()[1][1];
                    b.getElements()[0][1] = -1.0*a.getElements()[0][1];
                    b.getElements()[1][0] = -1.0*a.getElements()[1][0];
                    b.getElements()[1][1] = a.getElements()[0][0];
                    b = scalarMultiply(1.0/determinant(b), b);
                }
            }
            return b;}
        
        public static Matrix addRowToRowWithFactor(int i, int j, double k, Matrix m){
            Matrix m2 = new Matrix(m.getElements());
            for (int a=0; a<m.getElements()[0].length; a++){
                m2.getElements()[j][a] += k*m.getElements()[i][a];}
            return m2;}
 
        public static Vector multiplyVectorByMatrix(Vector v, Matrix m){
            Vector v2 = new Vector(m.getElements().length);
            if (v.getComponents().length == m.getElements()[0].length){
                for (int i = 0; i<m.getElements().length; i++){
                    double sum = 0.0;
                    for (int j = 0; j<m.getElements()[0].length; j++){
                        sum += m.getElements()[i][j]*v.getComponents()[j];}
                    v2.getComponents()[i] = sum;}}
            return v2;}
}


