package assignment1.matrixvectormult.utils;

import java.util.concurrent.ForkJoinPool;

public class MatrixVectorUtils {
	double[][] getTestMatrix(int dim) {
		double[][] testMatrix = {
				{1, 1, 1}, 
				{2, 2, 2}, 
				{3, 3, 3}
				};
		return testMatrix;
	}
	
	/**
	 * 
	 * @param dim
	 * @return
	 */
	double[] getTestVector(int dim) {
		double[] testVector = {3, 2, 1};
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
	void matVecMult(ForkJoinPool pool, final double[][] matrix, final double[]vector, final double[] result) {
		//TODO: Die Berechnung mit dem FJP starten.
		//		Siehe dazu: https://www.geeksforgeeks.org/java-util-concurrent-recursiveaction-class-in-java-with-examples/
		
	}
}
