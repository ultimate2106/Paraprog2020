package strangeCounter;

import java.util.concurrent.CountDownLatch;

import interfaces.CounterInterface;

public class Incrementer implements Runnable {
	private CounterInterface counter;
	private final CountDownLatch start, end;
	private final int RUNS;
	
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
				long count = counter.incrementAndGet();
				}
			end.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
