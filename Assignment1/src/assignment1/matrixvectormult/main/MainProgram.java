package assignment1.matrixvectormult.main;

import java.util.concurrent.ForkJoinPool;

import assignment1.matrixvectormult.utils.MatrixVectorMultiplication;
import assignment1.matrixvectormult.utils.MatrixVectorUtils;

public class MainProgram {
	private static final int length = 7;
	private static double[][] matrix;
	private static double[] vector;
	
	public static void main(String[] args) {
		matrix = MatrixVectorUtils.getTestMatrix(length);
		vector = MatrixVectorUtils.getTestVector(length);
		
		ForkJoinPool pool1 = new ForkJoinPool(1);
		ForkJoinPool pool2 = new ForkJoinPool(3);
		ForkJoinPool pool3 = new ForkJoinPool(7);
		
		calc(pool1, 1);
		calc(pool2, 2);
		calc(pool3, 3);
	}
	
	private static void calc(ForkJoinPool pool, int poolNr) {
		double[] result = new double[length];
		
		System.out.println("=============Pool " + poolNr + "=============");
		doIt(pool, matrix, vector, result);
		printResult(result);
	}
	
	private static void printResult(double[] result) 
	{
		if(checkResult(result)) {
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
	 * Diese Methode nutzt ForkJoinPool für die Multiplikation
	 * einer Matrix mit einem Vektor.
	 * 
	 * @param pool Der ForkJoinPool zur Berechnung. 
	 * @param matrix Die Matrix für die Multiplikation.
	 * @param vector Der Vektor für die Multiplikation.
	 * @param result Der Vektor in dem das Ergebnis gespeichert wird.
	 */
	public static void doIt(ForkJoinPool pool, final double[][] matrix, final double[]vector, final double[] result) {
		MatrixVectorMultiplication task = new MatrixVectorMultiplication(matrix, vector, result, 0, matrix.length);
		pool.invoke(task);
	}
}
