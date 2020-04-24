package assignment1.matrixvectormult.main;

import java.util.concurrent.ForkJoinPool;

import assignment1.matrixvectormult.utils.MatrixVectorUtils;

public class MainProgram {
	public static void printResult(double[][] matrix,double[] vector,double[] result,int length) 
	{
		System.out.println("-----Matrix-----");
		for(int i=0;i<length;++i) 
		{
			for(int j=0;j<length;++j) 
			{
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("-----Vector-----");
		for(int i=0;i<length;++i) {
			System.out.println(vector[i]);
		}
		System.out.println("-----Result-----");
		for(int i=0;i<length;++i) {
			System.out.println(result[i]);
		}
	}
	public static void main(String[] args) {
		//TODO: ForkJoinPool erstellen,
		//		TestMatrix und TestVektor holen (Utils),
		//		matVecMult() anstoßen.
		ForkJoinPool pool1 = new ForkJoinPool();
		ForkJoinPool pool2 = new ForkJoinPool();
		ForkJoinPool pool3 = new ForkJoinPool();
		MatrixVectorUtils utils = new MatrixVectorUtils();
		
		System.out.println("=============Pool 1=============");
		//Aufruf von 3 Tasks in Pool 1
		for(int i=1;i<=3;++i) {
			double[][] matrix = utils.getTestMatrix(i);
			double[] vector = utils.getTestVector(i);
			double[] result = new double[i];
			utils.doIt(pool1, matrix, vector, result);
			printResult(matrix,vector,result,i);	
		}
		System.out.println("=============Pool 2=============");
		//Aufruf von 5 Tasks in Pool 2
		for(int i=1;i<=5;++i) {
			double[][] matrix = utils.getTestMatrix(i);
			double[] vector = utils.getTestVector(i);
			double[] result = new double[i];
			utils.doIt(pool2, matrix, vector, result);
			printResult(matrix,vector,result,i);	
		}
		
		System.out.println("=============Pool 3=============");
		//Aufruf von 1 Tasks in Pool 3
		{
			int i = 1;
			double[][] matrix = utils.getTestMatrix(i);
			double[] vector = utils.getTestVector(i);
			double[] result = new double[i];
			utils.doIt(pool3, matrix, vector, result);
			printResult(matrix,vector,result,i);	
		}
			
	}
}
