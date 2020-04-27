package assignment1.strangecounter.interfaces;
/**
 * 
 * @author User Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public interface CounterInterface {	
	/**
	 * Abstrakte Methode die den aktuellen Zählerstand zurückgibt
	 * @return Aktueller Zählerstand
	 */
	long get();
	/**
	 * Abstrakte Methode die den aktuellen Zählerstand um eins erhöht und zurückgibt 
	 * @return Aktueller Zählerstand
	 */
	long incrementAndGet();
	/**
	 * Vergleicht den aktuellen Zählerstand mit dem übergebenen Sollwert und gibt eine Meldung
	 * auf der Konsole aus falls die Werte nicht miteinander übereinstimmen.
	 * @param desired Sollwert
	 */
	default void check(long desired) {
		long counter = get();
		if(counter != desired) {
			System.out.println("Counter stimmt nicht! Ist: " + counter + ", Soll: " + desired);
		}
	}
}
