package assignment1.matrixvectormult.utils;

import java.util.concurrent.RecursiveAction;

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
		//TODO: Hier muss mittels DivideAndConquer die Aufgabe
		//		verteilt werden.
		//		Siehe dazu: https://www.geeksforgeeks.org/java-util-concurrent-recursiveaction-class-in-java-with-examples/

		// Am besten sollten die Aufgaben einfach nur auf
		// die erste Dimension der Matrix beschränkt werden.
		// Das bedeutet jeder Task (Instanz von dieser Klasse)
		// übernimmt die multiplikation des Vectors mit
		// entsprechend vielen Reihen aus der Matrix.
		if((length-startIndex)>1) {
			int mid=(startIndex+length)/2;		
			invokeAll(new MatrixVectorMultiplication(matrix,vector,result,startIndex,mid),
					 new MatrixVectorMultiplication(matrix,vector,result,mid,length));
		}
		else 
		{
			for(int i=0;i<vector.length;++i) {
				result[startIndex] += matrix[startIndex][i] * vector[i];
			}	
		}
	}
}
