package assignment1.strangecounter.main;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import assignment1.strangecounter.mylong.MyLong;
import assignment1.strangecounter.mylong.MyLongAtomic;
import assignment1.strangecounter.mylong.MyLongAtomicModulo;

public class StrangeCounter {
	private final static int INCREMENTERS = 20;
	private final static int RUNS = 50;
	
	private static MyLong counter = new MyLong();
	//private static MyLongAtomic counter = new MyLongAtomic();
	//private static MyLongAtomicModulo counter= new MyLongAtomicModulo();
	
	private static void test(int counterType) 
	{
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		Thread[] Incrementers = new Thread[INCREMENTERS];
		
		ExecutorService executorService=null;
		switch(counterType) 
		{
			case 1:
				executorService=Executors.newCachedThreadPool();
				break;
			case 2:
				executorService=Executors.newFixedThreadPool(INCREMENTERS);
				break;
			case 3:
				executorService=Executors.newSingleThreadExecutor();
				break;
		}		
		
		for (int i = 0; i < INCREMENTERS; i++) {
			if(executorService==null) 
			{
				Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, counter, RUNS));
				Incrementers[i].start();
			}
			else 
			{
				executorService.submit(new Incrementer(startLatch,endLatch,counter,RUNS));
			}			
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
	
	private static void startTest(String name, int length,int counterType) 
	{
		for(int i=0;i<10;++i) 
		{
			System.out.println(name+" Test "+i);
			test(counterType);
			counter=new MyLong();
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {	
		
		startTest("Normal Threads",10,0);
		startTest("CachedThreadPool",10,1);
		startTest("FixedThreadPool",10,2);
		startTest("SingleThreadPool",10,3);
		
		
		/*CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		Thread[] Incrementers = new Thread[INCREMENTERS];
		
		ExecutorService executorService=Executors.newCachedThreadPool();
		//ExecutorService executorService=Executors.newFixedThreadPool(INCREMENTERS);
		//ExecutorService executorService=Executors.newSingleThreadExecutor();
		for (int i = 0; i < INCREMENTERS; i++) {
			//Incrementers[i] = new Thread(new Incrementer(startLatch, endLatch, counter, RUNS));
			//Incrementers[i].start();
			executorService.submit(new Incrementer(startLatch,endLatch,counter,RUNS));
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
		}*/
	}
}
