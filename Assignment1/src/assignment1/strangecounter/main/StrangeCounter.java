package assignment1.strangecounter.main;
import java.util.concurrent.CountDownLatch;

import assignment1.strangecounter.mylong.MyLong;
import assignment1.strangecounter.mylong.MyLongAtomic;
import assignment1.strangecounter.mylong.MyLongAtomicModulo;

public class StrangeCounter {
	private final static int INCREMENTERS = 2;
	private final static int RUNS = 1000;
	
	//private static MyLong counter = new MyLong();
	private static MyLongAtomic counter = new MyLongAtomic();
	//private static MyLongAtomicModulo counter = new MyLongAtomicModulo();
	
	public static void main(String[] args) {
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		Thread[] Incrementers = new Thread[INCREMENTERS];
		
		for (int i = 0; i < INCREMENTERS; i++) {
			Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, counter, RUNS));
			Incrementers[i].start();
		}
		
		try {
			System.out.println("Starting with counter = "+ counter.get());
			startLatch.countDown();
			endLatch.await();
			long totalInc = RUNS * INCREMENTERS;
			//System.out.println("Finished after " + totalInc+ " increments with counter = "+ counter.get());
			System.out.println("Finished after " + totalInc+ " increments with counter = "+ counter.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
