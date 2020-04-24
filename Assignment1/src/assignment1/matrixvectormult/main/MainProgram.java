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
	
	public static void callDoIt(ForkJoinPool pool,MatrixVectorUtils utils,int length,int poolNumber)
	{
		System.out.println("=============Pool "+poolNumber+"=============");
		for(int i=0;i<length;++i) 
		{
			System.out.println("=============Task "+i+"=============");
			double[][] matrix = utils.getTestMatrix(length);
			double[] vector = utils.getTestVector(length);
			double[] result = new double[length];
			utils.doIt(pool, matrix, vector, result);
			printResult(matrix,vector,result,length);	
		}
	}
	
	
	public static void main(String[] args) {
		//TODO: ForkJoinPool erstellen,
		//		TestMatrix und TestVektor holen (Utils),
		//		matVecMult() ansto�en.
		ForkJoinPool pool1 = new ForkJoinPool();
		ForkJoinPool pool2 = new ForkJoinPool();
		ForkJoinPool pool3 = new ForkJoinPool();
		MatrixVectorUtils utils = new MatrixVectorUtils();		
		callDoIt(pool1,utils,3,1);
		callDoIt(pool2,utils,5,2);
		callDoIt(pool3,utils,7,3);			
	}
}
