package assignment1.strangecounter.mylong;

import java.util.concurrent.atomic.AtomicLong;

import assignment1.strangecounter.interfaces.CounterInterface;

/**
 * Kapselt den LongAtomic typen ein und stellt Methoden zur R�ckgabe und erh�hung des Typens zu Verf�gung
 * @author User Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class MyLongAtomic implements CounterInterface {
	/**
	 * Z�hler des Typs AtomicLong
	 */
	protected AtomicLong counter = new AtomicLong();
	
	/**
	 * Gibt den Z�hlerstand mittels der get Methode von AtomicLong zur�ck
	 */
	@Override
	public long get() {
		return counter.get();
	}

	/**
	 * Erh�ht den aktuellen Z�hlerstand mittels der incrementAndGet() Methode von AtomicLong
	 * und gibt den erh�hten Z�hlerstand zur�ck.
	 */
	@Override
	public long incrementAndGet() {
		return counter.incrementAndGet();
	}

}
