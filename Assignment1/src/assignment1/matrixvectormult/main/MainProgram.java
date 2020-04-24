package assignment1.matrixvectormult.main;

import java.util.concurrent.ForkJoinPool;

import assignment1.matrixvectormult.utils.MatrixVectorUtils;

public class MainProgram {
	
	
	private static void printResult(double[][] matrix,double[] vector,double[] result,int length) 
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
	
	private static boolean checkResult(double[][] matrix, double[] vector,double result[]) 
	{
		boolean isResultCorrect=true; 
		int length=result.length;
		for(int i=0;i<length;++i) 
		{
			double tmp=0;
			for(int j=0;j<length;++j) 
			{
				tmp+=matrix[i][j]*vector[j];
			}
			if(tmp!=result[i]) 
			{
				isResultCorrect=false;
			}
		}
		return isResultCorrect;
	}
	
	private static void callDoIt(ForkJoinPool pool,MatrixVectorUtils utils,int length,int poolNumber)
	{
		System.out.println("=============Pool "+poolNumber+"=============");
		double[][] matrix = utils.getTestMatrix(length);
		double[] vector = utils.getTestVector(length);
		double[] result = new double[length];
		utils.doIt(pool, matrix, vector, result);
		if(checkResult(matrix,vector,result)) 
		{
			printResult(matrix,vector,result,length);	
		}
		else 
		{
			System.out.println("Result was false");
		}
	}
	
	
	public static void main(String[] args) {
		ForkJoinPool pool1 = new ForkJoinPool(1);
		ForkJoinPool pool2 = new ForkJoinPool(3);
		ForkJoinPool pool3 = new ForkJoinPool(7);
		MatrixVectorUtils utils = new MatrixVectorUtils();		
		callDoIt(pool1,utils,20,1);
		callDoIt(pool2,utils,20,2);
		callDoIt(pool3,utils,20,3);			
	}
}
