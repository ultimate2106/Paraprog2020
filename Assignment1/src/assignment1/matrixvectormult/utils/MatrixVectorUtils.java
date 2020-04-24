package assignment1.matrixvectormult.utils;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MatrixVectorUtils {
	
	/**
	 * 
	 * @param dim
	 * @return Quadratische Matrix der größe dim
	 */
	public double[][] getTestMatrix(int dim) {
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
	public double[] getTestVector(int dim) {
		//double[] testVector = {3, 2, 1};
		Random rand=new Random();
		double[] testVector=new double[dim];
		for(int i=0;i<dim;++i) 
		{
			testVector[i]=Math.round((rand.nextDouble()*10) * 100.0) / 100.0;
		}
		return testVector;
	}
	
	/**
	 * Diese Methode nutzt ForkJoinPool für die Multiplikation
	 * einer Matrix mit einem Vektor.
	 * 
	 * @param pool Der ForkJoinPool zur Berechnung. 
	 * @param matrix Die Matrix für die Multiplikation.
	 * @param vector Der Vektor für die Multiplikation.
	 * @param result Der Vektor in dem das Ergebnis gespeichert wird.
	 */
	public void doIt(ForkJoinPool pool, final double[][] matrix, final double[]vector, final double[] result) {
		MatrixVectorMultiplication task = new MatrixVectorMultiplication(matrix, vector, result, 0, matrix.length);
		pool.invoke(task);
	}
}
