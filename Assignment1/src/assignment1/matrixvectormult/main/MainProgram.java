package assignment1.matrixvectormult.main;

import java.util.concurrent.ForkJoinPool;

import assignment1.matrixvectormult.utils.MatrixVectorUtils;

public class MainProgram {
	public static void main(String[] args) {
		//TODO: ForkJoinPool erstellen,
		//		TestMatrix und TestVektor holen (Utils),
		//		matVecMult() anstoﬂen.
		ForkJoinPool MVMult = new ForkJoinPool();
		MatrixVectorUtils test = new MatrixVectorUtils();
		int length = 3;
		double[][] matrix = test.getTestMatrix(length);
		double[] vector = test.getTestVector(length);
		double[] result = new double[length];
		test.doIt(MVMult, matrix, vector, result);
		for(int i=0;i<length;++i) {
			System.out.println(result[i]);
		}
	}
}
