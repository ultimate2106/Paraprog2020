package assignment1.strangecounter.mylong;

import java.util.function.LongBinaryOperator;
/**
 * 
 * @author User Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class MyLongAtomicModulo extends MyLongAtomic {

	/**
	 * Merkt sich eine Anonyme Funktion, die (x+y)%16 rechnet, in der Variable lbo und �bergibt 
	 * der accumulateAndGet Methode von AtomicLong. 
	 * @return Gibt den Wert zur�ck den accumulateAndGet zur�ckgibt.
	 */
	@Override
	public long incrementAndGet() {
		LongBinaryOperator lbo = (x, y) -> ((x+y) % 16);
		long count = counter.accumulateAndGet(1, lbo);
		return count;
	}

}
