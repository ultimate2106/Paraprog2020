package assignment1.matrixvectormult.utils;

import java.util.concurrent.RecursiveAction;

/**
 * Diese Klasse bietet Methoden zur Berechnung einer
 * Matrix x Vektor Multiplikation unter Verwendung
 * der Divide and Conquer Methode. 
 *
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 * 
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
	 * @param result Array zum Speichern des Ergebnisses der Multiplikation
	 * @param startIndex Startindex der Berechnung
	 * @param length Endindex der Berechnung
	 */
	public MatrixVectorMultiplication(final double[][] matrix, final double[] vector,
			final double[] result, final int startIndex, final int length) {
		this.matrix = matrix;
		this.vector = vector;
		this.result = result;
		this.startIndex = startIndex;
		this.length = length;
	}
	
	/**
	 * F�hrt die Berechnung einer Matrix x Vektor Multiplikation
	 * durch, indem entweder nach dem "Divide and Conquer" Prinzip
	 * die Berechnung weiter runtergebrochen wird, oder, sollte
	 * der Threashold bereits erreicht sein, die Berechnung der 
	 * einzelnen Zeilen durchgef�hrt wird.
	 */
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
