package assignment1.strangecounter.mylong;

import assignment1.strangecounter.interfaces.CounterInterface;
/**
 * 
 * @author User Dominic Schroeder
 *
 */
public class MyLong implements CounterInterface {
	/**
	 * Zähler des Typs long
	 */
	private long counter = 0;
	/**
	 * Siehe CounterInterface
	 */
	@Override
	public long get() {
		return counter;
	}

	/**
	 * Siehe CounterInterface
	 */
	@Override
	public long incrementAndGet() {
		return ++counter;
	}

}
