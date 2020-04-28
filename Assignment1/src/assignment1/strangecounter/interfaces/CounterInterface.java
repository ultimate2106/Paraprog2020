package assignment1.strangecounter.interfaces;
/**
 * Ein Interface dass dazu dient den Long Typen einzukapseln.
 * @author User Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public interface CounterInterface {	

	long get();
	
	long incrementAndGet();
	
	/**
	 * Default Methode.
	 * Vergleicht den aktuellen Z�hlerstand mit dem �bergebenen
	 * Sollwert und gibt eine Meldung auf der Konsole aus falls
	 * die Werte nicht miteinander �bereinstimmen.
	 * 
	 * @param desired Der gew�nschte Sollwert
	 */
	default void check(long desired) {
		long counter = get();
		if(counter != desired) {
			System.out.println("Counter stimmt nicht! Ist: " + counter + ", Soll: " + desired);
		}
	}
}
