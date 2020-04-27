package assignment1.matrixvectormult.utils;

import java.util.concurrent.RecursiveAction;

/**
 * 
 * @author Benjamin Scheer, Dominic Schröder, Dominic Jäger
 *
 */
@SuppressWarnings("serial")
public class MatrixVectorMultiplication extends RecursiveAction {

	private final double[][] matrix;
	private final double[] vector;
	private final double[] result;
	
	//Zeigt auf die die erste Reihe, die dieser Task berechnen soll
	private final int startIndex;
	
	//TODO: Hier bin ich nicht sicher, wie das zu verweden ist..
	//		Entweder zeigt es auf die letzte zu berechnende Reihe,
	//		oder es ist der Threshold -> Die anzahl der zu
	//		berechnenden Reihen. Sollten wir nochmal fragen.
	private final int length;
	
	/**
	 * 
	 * @param matrix Quadratische Matrix
	 * @param vector Vektor
	 * @param result Ergebnis der Multiplikation von Matrix und Vektor
	 * @param startIndex Startwert der Berechnung
	 * @param length Endwert der Berechnung
	 */
	public MatrixVectorMultiplication(final double[][] matrix, final double[] vector,
			final double[] result, final int startIndex, final int length) {
		this.matrix = matrix;
		this.vector = vector;
		this.result = result;
		this.startIndex = startIndex;
		this.length = length;
	}
	
	@Override
	protected void compute() {
		if((length - startIndex) > 1) {
			int mid = (startIndex+length) / 2;		
			invokeAll(new MatrixVectorMultiplication(matrix,vector,result,startIndex,mid),
					 new MatrixVectorMultiplication(matrix,vector,result,mid,length));
		}
		else 
		{
			result[startIndex] = MatrixVectorUtils.multVecVec(matrix[startIndex], vector);
		}
	}
}
