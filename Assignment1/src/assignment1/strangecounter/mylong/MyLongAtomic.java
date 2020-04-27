package assignment1.strangecounter.mylong;

import java.util.concurrent.atomic.AtomicLong;

import assignment1.strangecounter.interfaces.CounterInterface;

/**
 * 
 * @author User Dominic Schroeder
 *
 */
public class MyLongAtomic implements CounterInterface {
	/**
	 * Zähler des Typs AtomicLong
	 */
	protected AtomicLong counter = new AtomicLong();
	
	/**
	 * Gibt den Zählerstand mittels der get Methode von AtomicLong zurück
	 */
	@Override
	public long get() {
		return counter.get();
	}

	/**
	 * Erhöht den aktuellen Zählerstand mittels der incrementAndGet() Methode von AtomicLong
	 * und gibt den erhöhten Zählerstand zurück.
	 */
	@Override
	public long incrementAndGet() {
		return counter.incrementAndGet();
	}

}
