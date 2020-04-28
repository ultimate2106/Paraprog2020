package assignment1.strangecounter.main;

import java.util.concurrent.CountDownLatch;

import assignment1.strangecounter.interfaces.CounterInterface;

/**
 * Klasse zum Hochzählen des Counters.
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class Incrementer implements Runnable {
	private CounterInterface counter;
	private final CountDownLatch start, end;
	private final int RUNS;
	
	/**
	 * @param start Startsignal 
	 * @param end Endsignal
	 * @param counter Objekt des Counters
	 * @param runs Anzahl der Zählzyklen
	 */
	public Incrementer(CountDownLatch start,CountDownLatch end, CounterInterface counter, int runs) {
		this.RUNS = runs;
		this.start = start;
		this.end = end;
		this.counter = counter;
	}
	
	@Override
	public void run() {
		try {
			start.await();
			for (int i = 0; i < RUNS; i++) {
				counter.incrementAndGet();
			}
			end.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
