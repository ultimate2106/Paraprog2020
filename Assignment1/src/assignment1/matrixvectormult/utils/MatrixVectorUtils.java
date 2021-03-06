package assignment1.matrixvectormult.utils;

import java.util.Random;

/**
 * In dieser Klasse befinden sich die ausgelagerten Methoden 
 * zum erzeugen einer zuf�lligen Matrix und eines zuf�lligen Vektors,
 * sowie eine Methode zur Berechnung zweier Vektoren. 
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class MatrixVectorUtils {
	private static double testValue = 2;
	
	/**
	 * Erzeugt eine Quadratische Matrix der Gr��e dim x dim,
	 * die mit zuf�lligen Zahlen gef�llt wird.
	 * 
	 * @param dim Gr��e der Matrix
	 * @return Quadratische Matrix der Gr��e dim
	 */
	public static double[][] getTestMatrix(int dim) {
		double[][] testMatrix = new double[dim][dim];
		for(int i = 0; i < dim; ++i) {
			for(int j = 0; j < dim; ++j) {
				testMatrix[i][j] = i;
			}
		}
		return testMatrix;
	}
	
	/**
	 * Erzeugt einen Vektor der Gr��e dim, der mit
	 * zuf�lligen Zahlen gef�llt wird.
	 * 
	 * @param dim Gr��e des Vektors
	 * @return Vektor der gr��e dim
	 */
	public static double[] getTestVector(int dim) {
		double[] testVector = new double[dim];
		for(int i = 0; i < dim; ++i) {
			testVector[i] = testValue;
		}
		
		return testVector;
	}
	
	public static double[][] getRandomMatrix(int dim) {
		double[][] testMatrix = new double[dim][dim];
		Random rand = new Random();
		
		for(int i=0;i<dim;++i) {
			for(int j = 0; j < dim; ++j) {			
				testMatrix[i][j] = Math.round((rand.nextDouble()*10) * 100.0) / 100.0;
			}
		}
		
		return testMatrix;
	}
	
	public static double[] getRandomVector(int dim) {
		Random rand=new Random();
		double[] testVector=new double[dim];
		for(int i = 0; i < dim; ++i) 
		{
			testVector[i]=Math.round((rand.nextDouble()*10) * 100.0) / 100.0;
		}
		
		return testVector;
	}
	
	/**
	 * Multipliziert zwei Vektoren zu einem Skalar.
	 * 
	 * @param vec1 Erster Vektor
	 * @param vec2 Zweiter Vektor
	 * @return Ergebnis der Multiplikation
	 */
	public static double multVecVec(double[] vec1, double[] vec2) {
		double result = 0;
		
		for(int i = 0; i < vec2.length; ++i) {
			result += vec1[i] * vec2[i];
		}
		
		return result;
	}
	
	public static boolean checkTest(double[] testResult, int dim) {
		for(int i = 0; i < testResult.length; ++i) {
			if(testResult[i] != (i*dim)*testValue)
				return false;
		}
		
		return true;
	}
}
