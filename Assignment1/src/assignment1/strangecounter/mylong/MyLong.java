package assignment1.strangecounter.mylong;

import assignment1.strangecounter.interfaces.CounterInterface;

public class MyLong implements CounterInterface {
	private long counter = 0;
	
	@Override
	public long get() {
		return counter;
	}

	@Override
	public long incrementAndGet() {
		return ++counter;
	}

}
