package assignment1.strangecounter.mylong;

import java.util.concurrent.atomic.AtomicLong;

import assignment1.strangecounter.interfaces.CounterInterface;

public class MyLongAtomic implements CounterInterface {
	protected AtomicLong counter = new AtomicLong();
	
	@Override
	public long get() {
		return counter.get();
	}

	@Override
	public long incrementAndGet() {
		return counter.incrementAndGet();
	}

}
