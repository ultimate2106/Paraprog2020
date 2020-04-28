package assignment1.strangecounter.mylong;

import java.util.function.LongBinaryOperator;
/**
 * Kapselt den LongAtomic typen ein. Bei jeder Erh�hung des Z�hlerstandes wird mod 16 gerechnet
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class MyLongAtomicModulo extends MyLongAtomic {

	/**
	 * Erh�ht den Z�hlerstand um 1.
	 * @return Gibt den aktuellen Z�hlerstand mod 16 zur�ck.
	 */
	@Override
	public long incrementAndGet() {
		LongBinaryOperator lbo = (x, y) -> ((x+y) % 16);
		long count = counter.accumulateAndGet(1, lbo);
		return count;
	}

}
