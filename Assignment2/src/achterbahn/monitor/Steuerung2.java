package achterbahn.monitor;

import achterbahn.interfaces.Steuerung;

public class Steuerung2 implements Steuerung {
	
	private final int MaxPassagiere = 10;
	private int w1Passagiere = 0;
	private int w2Passagiere = 0;
	
	private boolean w1Vorne = false;
	
	public Steuerung2() {
		double random = Math.random();
		if(random < 0.5) {
			System.out.println("Wagen1 steht vorne.");
			w1Vorne = true;
		} else {
			System.out.println("Wagen2 steht vorne.");
		}
	}

	@Override
	public synchronized void passagier() {
		try {
			while(!(w1Passagiere < MaxPassagiere || w2Passagiere < MaxPassagiere)) {
				wait();
			}
			
			randomizedEinstieg();
			
			notifyAll();
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	
	private void randomizedEinstieg() {
		double random = Math.random();
		
		if(random < 0.5) {
			if(w1Passagiere < MaxPassagiere) {
				incrementPassagiereW1();
			} else {
				incrementPassagiereW2();
			}
		} else {
			if(w2Passagiere < MaxPassagiere) {
				incrementPassagiereW2();
			} else {
				incrementPassagiereW1();
			}
		}
	}
	
	private void incrementPassagiereW1() {
		++w1Passagiere;
		if(w1Passagiere < MaxPassagiere) {
			System.out.println("Ein Passagier ist in Wagen1 eingestiegen. Aktuelle Anzahl " + w1Passagiere);
		} else {
			System.out.println("Wagen1 hat " + MaxPassagiere + " und ist voll!");
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementPassagiereW2() {
		++w2Passagiere;
		if(w2Passagiere < MaxPassagiere) {
			System.out.println("Ein Passagier ist in Wagen2 eingestiegen. Aktuelle Anzahl " + w2Passagiere);
		} else {
			System.out.println("Wagen2 hat " + MaxPassagiere + " und ist voll!");
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void abfahrt() {
		
		try {			
			while(!((Thread.currentThread().getName().equals("Wagen1") && (w1Passagiere >= MaxPassagiere) && w1Vorne) || 
					(Thread.currentThread().getName().equals("Wagen2") && (w2Passagiere >= MaxPassagiere) && !w1Vorne))) {
					       wait();
					}
			
			if(Thread.currentThread().getName().equals("Wagen1")) {
				/*while(!(w1Passagiere >= MaxPassagiere) || !(w1Vorne)) {
					wait();	
				}*/
				
				System.out.println("Abfahrt Wagen1! :)");
			} else {
				/*while(!(w2Passagiere >= MaxPassagiere) || w1Vorne) {
					wait();	
				}*/
				
				System.out.println("Abfahrt Wagen2! :)");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void aussteigen() {
		if(Thread.currentThread().getName().equals("Wagen1")) {
			System.out.println("Fahrt in Wagen1  ist zu Ende. Alles bitte aussteigen!");
            w1Vorne = false;
            w1Passagiere = 0;
		} else {
			System.out.println("Fahrt in Wagen2  ist zu Ende. Alles bitte aussteigen!");
            w1Vorne = true;
            w2Passagiere = 0;
		}
		notifyAll();
	}

}
