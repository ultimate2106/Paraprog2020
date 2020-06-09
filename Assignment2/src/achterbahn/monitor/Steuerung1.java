package achterbahn.monitor;

import achterbahn.interfaces.Steuerung;

public class Steuerung1 implements Steuerung{
	private int Passagiere = 0;
	
	public synchronized void passagier() {
		try {
			while(Passagiere >= 5) {
				wait();	
			}
			
			++Passagiere;
			
			System.out.println("Ein Passagier ist eingestiegen. Aktuelle Anzahl " + Passagiere);
			
			if(Passagiere >= 5) {
				System.out.println("Alle Sitzplštze belegt. Drehkreuz wird geschlossen!");
			} else {
				System.out.println("Noch sind Sitzplštze frei. Wagen wartet!");
			}
			
			Thread.sleep(1000);
			
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void abfahrt(boolean isWagen1) {
		try {
			while(Passagiere < 5) {
				wait();	
			}
			
			System.out.println("Abfahrt! :)");
			
			for(int i = 0; i < 5; ++i) {
				System.out.println(i+1);
				Thread.sleep(1000);
			}
			
			aussteigen(true);
			
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void aussteigen(boolean isWagen1) {
		System.out.println("Fahrt zu Ende. Alles bitte aussteigen!");
		
		Passagiere = 0;
	}
}
