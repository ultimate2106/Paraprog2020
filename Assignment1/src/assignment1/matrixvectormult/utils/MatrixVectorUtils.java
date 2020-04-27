package assignment1.matrixvectormult.utils;

import java.util.Random;

/**
 * In dieser Klasse befinden sich die ausgelagerten Methoden 
 * zum erzeugen einer zufälligen Matrix und eines zufälligen Vektors,
 * sowie eine Methode zur Berechnung zweier Vektoren. 
 * 
 * @author Benjamin Scheer, Dominic Schröder, Dominic Jäger
 *
 */
public class MatrixVectorUtils {
	
	/**
	 * Diese Methode erzeugt eine Quadratische Matrix der Größe dim x dim
	 * die mit zufälligen Zahlen gefüllt wird.
	 * 
	 * @param dim Größe der Matrix
	 * @return Quadratische Matrix der Größe dim
	 */
	public static double[][] getTestMatrix(int dim) {
//		double[][] testMatrix = {
//				{1, 1, 1}, 
//				{2, 2, 2}, 
//				{3, 3, 3}
//				};
		
		double[][] testMatrix = new double[dim][dim];
		Random rand = new Random();
		
		for(int i=0;i<dim;++i) {
			for(int j = 0; j < dim; ++j) {			
				testMatrix[i][j] = Math.round((rand.nextDouble()*10) * 100.0) / 100.0;
			}
		}
		return testMatrix;
	}
	
	/**
	 * Diese Methode erzeugt einen Vektor der Größe dim der mit
	 * zufälligen Zahlen gefüllt wird.
	 * 
	 * @param dim Größe des Vektors
	 * @return Vektor der größe dim
	 */
	public static double[] getTestVector(int dim) {
//		double[] testVector = {3, 2, 1};
		
		Random rand=new Random();
		double[] testVector=new double[dim];
		for(int i = 0; i < dim; ++i) 
		{
			testVector[i]=Math.round((rand.nextDouble()*10) * 100.0) / 100.0;
		}
		return testVector;
	}
	
	/**
	 * Hier wird eine Reihe der Matrix mit dem Vektor Multipliziert.
	 * Hierfür werden die einzelnen Zeilen der beiden Vektoren miteinander
	 * Multipliziert und als Ergebnis auf eine Variable hinzu Addiert. 
	 * 
	 * @param vec1 Erster Vektor
	 * @param vec2 Zweiter Vektor
	 * @return Ergebnis der Vektoren
	 */
	public static double multVecVec(double[] vec1, double[] vec2) {
		double result = 0;
		
		for(int i = 0; i < vec2.length; ++i) {
			result += vec1[i] * vec2[i];
		}
		
		return result;
	}
}
