package assignment1.matrixvectormult.main;

import java.util.concurrent.ForkJoinPool;

import assignment1.matrixvectormult.utils.MatrixVectorMultiplication;
import assignment1.matrixvectormult.utils.MatrixVectorUtils;
/**
 * Stellt das Hauptprogramm zur Berechnung der Matrix Vector Multiplikation dar.
 * 
 * @author User Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class MainProgram {

	//Länge der Matrix, des Vektors und des Ergebnisvektors
	private static final int length = 3;

	//Zweidimensionales Array dass eine Matrix darstellen soll
	private static double[][] matrix;
	
	//Eindimensionales Array dass einen Vektor darstellen soll
	private static double[] vector;
	
	public static void main(String[] args) {
		matrix = MatrixVectorUtils.getTestMatrix(length);
		vector = MatrixVectorUtils.getTestVector(length);
		
		ForkJoinPool pool1 = new ForkJoinPool(1);
		ForkJoinPool pool2 = new ForkJoinPool(3);
		ForkJoinPool pool3 = new ForkJoinPool(7);
		
		calc(pool1, 1, true);
		calc(pool2, 2, true);
		calc(pool3, 3, true);
	}
	
	/**
	 * Startet die Berechnung der Matrix Vektor Multiplikation.
	 * 
	 * @param pool   Der ForkJoinPool der für die Rechnung benutzt wird.
	 * @param poolNr Nummer zur unterscheidung der Pools (ID)
	 */
	private static void calc(ForkJoinPool pool, int poolNr, boolean isTest) {
		double[] result = new double[length];
		
		System.out.println("=============Pool " + poolNr + "=============");
		doIt(pool, matrix, vector, result);
		
		//Nur mit kleiner length Variable oder den Testwerten aufrufen!
		printResult(result, isTest);
	}
	
	/**
	 * Gibt die Matrix, den Vektor und das Ergebnis der Multiplikation auf der Konsole aus.
	 * 
	 * @param result Das Ergebnis von MatrixVectorMultiplication
	 */
	private static void printResult(double[] result, boolean isTest) 
	{
		boolean isCorrect = false;
		if(isTest) {
			isCorrect = MatrixVectorUtils.checkTest(result, length);
		} else {
			isCorrect = checkResult(result);
		}
		
		if(isCorrect) {
			System.out.println("-----Matrix-----");
			for(int i = 0; i < length; ++i) 
			{
				for(int j = 0; j < length; ++j) 
				{
					System.out.print(matrix[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println("-----Vector-----");
			for(int i = 0; i < length; ++i) {
				System.out.println(vector[i]);
			}
			System.out.println("-----Result-----");
			for(int i = 0; i < length; ++i) {
				System.out.println(result[i]);
			}
		} else {
			System.out.println("Result was false");
		}	
	}
	
	
	/**
	 * Berechnet sequentiell eine Matrix Vector Multiplikation,
	 * um es dann mit result zu überprüfen.
	 * 
	 * @param result Das Ergebnis von MatrixVectorMultiplication
	 * @return Gibt an, ob das Ergebnis von MatrixVectorMultiplication richtig oder falsch ist 
	 * 
	 */
	private static boolean checkResult(double[] result) 
	{
		boolean isResultCorrect=true; 
		
		for(int i = 0; i < length; ++i) 
		{
			double tmp = 0;
			for(int j = 0; j < length; ++j) 
			{
				tmp += matrix[i][j] * vector[j];
			}
			if(tmp != result[i]) 
			{
				isResultCorrect=false;
			}
		}
		return isResultCorrect;
	}
	
	/**
	 * Nutzt ForkJoinPool für die Multiplikation
	 * einer Matrix mit einem Vektor.
	 * 
	 * @param pool Der ForkJoinPool zur Berechnung. 
	 * @param matrix Die Matrix für die Multiplikation.
	 * @param vector Der Vektor für die Multiplikation.
	 * @param result Der Vektor in dem das Ergebnis gespeichert wird.
	 */
	private static void doIt(ForkJoinPool pool, final double[][] matrix, final double[]vector, final double[] result) {
		MatrixVectorMultiplication task = new MatrixVectorMultiplication(matrix, vector, result, 0, matrix.length);
		long startTime = System.nanoTime();
		pool.invoke(task);
		double duration = ((double) (System.nanoTime() - startTime)) / 1000000.0;
		System.out.println("Time passed: " + duration);
		System.out.println();
	}
}
