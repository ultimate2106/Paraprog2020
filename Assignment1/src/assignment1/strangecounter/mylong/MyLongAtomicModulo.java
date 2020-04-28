package assignment1.strangecounter.mylong;

import java.util.function.LongBinaryOperator;
/**
 * Kapselt den LongAtomic typen ein. Bei jeder Erhöhung des Zählerstandes wird mod 16 gerechnet
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class MyLongAtomicModulo extends MyLongAtomic {

	/**
	 * Erhöht den Zählerstand um 1.
	 * @return Gibt den aktuellen Zählerstand mod 16 zurück.
	 */
	@Override
	public long incrementAndGet() {
		LongBinaryOperator lbo = (x, y) -> ((x+y) % 16);
		long count = counter.accumulateAndGet(1, lbo);
		return count;
	}

}
