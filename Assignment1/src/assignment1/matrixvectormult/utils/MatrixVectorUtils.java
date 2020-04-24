package assignment1.matrixvectormult.utils;

import java.util.Random;

public class MatrixVectorUtils {
	
	/**
	 * 
	 * @param dim
	 * @return Quadratische Matrix der größe dim
	 */
	public static double[][] getTestMatrix(int dim) {
		/*double[][] testMatrix = {
				{1, 1, 1}, 
				{2, 2, 2}, 
				{3, 3, 3}
				};
		*/
		double[][] testMatrix = new double[dim][dim];
		Random rand = new Random();
		
		for(int i=0;i<dim;++i) {
			for(int j=0;j<dim;++j) {			
				testMatrix[i][j] = Math.round((rand.nextDouble()*10) * 100.0) / 100.0;
			}
		}
		return testMatrix;
	}
	
	/**
	 * 
	 * @param dim
	 * @return Vektor der größe dim
	 */
	public static double[] getTestVector(int dim) {
		//double[] testVector = {3, 2, 1};
		Random rand=new Random();
		double[] testVector=new double[dim];
		for(int i=0;i<dim;++i) 
		{
			testVector[i]=Math.round((rand.nextDouble()*10) * 100.0) / 100.0;
		}
		return testVector;
	}
}
