package assignment1.strangecounter.mylong;

import java.util.function.LongBinaryOperator;

public class MyLongAtomicModulo extends MyLongAtomic {

	@Override
	public long incrementAndGet() {
		LongBinaryOperator lbo = (x, y) -> ((x+y) % 16);
		long count = counter.accumulateAndGet(1, lbo);
		return count;
	}

}
