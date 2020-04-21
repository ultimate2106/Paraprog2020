package myLong;

import java.util.concurrent.atomic.AtomicLong;

import interfaces.CounterInterface;

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
