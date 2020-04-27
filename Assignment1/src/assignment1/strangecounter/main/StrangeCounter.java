package assignment1.strangecounter.main;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import assignment1.strangecounter.interfaces.CounterInterface;
import assignment1.strangecounter.mylong.MyLong;
import assignment1.strangecounter.mylong.MyLongAtomic;
import assignment1.strangecounter.mylong.MyLongAtomicModulo;

/**
 * 
 * @author Benjamin Scheer, Dominic Schr�der, Dominic J�ger
 *
 */
public class StrangeCounter {
	private final static int INCREMENTERS = 20;
	private final static int RUNS = 50;
	
	/**
	 * In dieser Methode wird der CountDownLatch mit seinem Start- und Grenzwert erzeugt
	 * sowie ein Array aus Threads. Durch den �bergebenen Wert executorType wird
	 * mithilfe von switch entschieden, mit welchem Executor Service gearbeitet werden soll.
	 * In einer Schleife wird das Thread Array mit Threads gef�llt und direkt gestartet.
	 * 
	 * @param executorType Auswahlm�glichkeit der einzelnen Executor Services.
	 * @param counter Objekt MyLong, MyLongAtomic oder MyLongAtomicModul
	 */
	private static void test(ExecutorService executorService,CounterInterface counter) 
	{
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(INCREMENTERS);
		Thread[] Incrementers = new Thread[INCREMENTERS];	
		
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
	
	/**
	 * Ruft die Methode test 
	 * @param name Name des Tests
	 * @param length Anzahl an Tests
	 * @param executorType
	 * @param counterType
	 */
	private static void startTest(String name, int length,ExecutorService executorService,int counterType) 
	{
		for(int i=0;i<length;++i) 
		{
			CounterInterface counter=null;
			switch(counterType)
			{
				case 1: 
					counter=new MyLong();
					break;
				case 2:
					counter=new MyLongAtomic();
					break;
				case 3:
					counter=new MyLongAtomicModulo();
					break;
				default:
					counter=new MyLong();
					break;
			}
			System.out.println(name+" Test "+i);
			test(executorService,counter);
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {	
		
		for(int counterType=1;counterType<=3;++counterType) {
			System.out.println("----------------------------------------------");
			startTest("Normal Threads",10,null,counterType);
			startTest("CachedThreadPool",10,Executors.newCachedThreadPool(),counterType);
			startTest("FixedThreadPool",10,Executors.newFixedThreadPool(INCREMENTERS),counterType);
			startTest("SingleThreadPool",10,Executors.newSingleThreadExecutor(),counterType);	
		}
	}
}
