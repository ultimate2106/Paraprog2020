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
		ForkJoinPool MVMult = new ForkJoinPool();
		MatrixVectorUtils test = new MatrixVectorUtils();
		int length = 3;
		double[][] matrix = test.getTestMatrix(length);
		double[] vector = test.getTestVector(length);
		double[] result = new double[length];
		test.doIt(MVMult, matrix, vector, result);
		printResult(matrix,vector,result,length);		
	}
}
