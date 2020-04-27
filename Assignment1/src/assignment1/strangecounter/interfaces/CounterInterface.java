package assignment1.strangecounter.interfaces;
/**
 * 
 * @author User Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public interface CounterInterface {	
	/**
	 * Abstrakte Methode die den aktuellen Z�hlerstand zur�ckgibt
	 * @return Aktueller Z�hlerstand
	 */
	long get();
	/**
	 * Abstrakte Methode die den aktuellen Z�hlerstand um eins erh�ht und zur�ckgibt 
	 * @return Aktueller Z�hlerstand
	 */
	long incrementAndGet();
	/**
	 * Vergleicht den aktuellen Z�hlerstand mit dem �bergebenen Sollwert und gibt eine Meldung
	 * auf der Konsole aus falls die Werte nicht miteinander �bereinstimmen.
	 * @param desired Sollwert
	 */
	default void check(long desired) {
		long counter = get();
		if(counter != desired) {
			System.out.println("Counter stimmt nicht! Ist: " + counter + ", Soll: " + desired);
		}
	}
}
