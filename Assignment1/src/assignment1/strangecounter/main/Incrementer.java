package assignment1.strangecounter.main;

import java.util.concurrent.CountDownLatch;

import assignment1.strangecounter.interfaces.CounterInterface;

/**
 * In dieser Klasse wird der Counter mithilfe der Threads hochgezählt.
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class Incrementer implements Runnable {
	private CounterInterface counter;
	private final CountDownLatch start, end;
	private final int RUNS;
	
	/**
	 * In diesem Konstruktor werden folgende Variablen inizialisiert.
	 * 
	 * @param start
	 * @param end
	 * @param counter
	 * @param runs
	 */
	public Incrementer(CountDownLatch start,CountDownLatch end, CounterInterface counter, int runs) {
		this.RUNS = runs;
		this.start = start;
		this.end = end;
		this.counter = counter;
	}
	
	/**
	 * In dieser Methode werden die Threads zusammengeführt, die danach in einer Schleife einen Zähler mithilfe
	 * einer exterenen Methode hochzählen. Nach dieser Schleife werden die Threads beendet.
	 * Falls dabei ein Fehler auftritt, wird dieser aufgefangen und ausgegeben.
	 */
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
