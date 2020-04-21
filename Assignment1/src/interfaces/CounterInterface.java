package interfaces;

public interface CounterInterface {	
	long get();
	long incrementAndGet();
	default void check(long desired) {
		long counter = get();
		if(counter != desired) {
			System.out.println("Counter stimmt nicht! Ist: " + counter + ", Soll: " + desired);
		}
	}
}
