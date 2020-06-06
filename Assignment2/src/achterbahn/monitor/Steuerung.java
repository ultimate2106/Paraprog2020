package achterbahn.monitor;

public class Steuerung {
	private int Passagiere = 0;
	
	public synchronized void passagier() {
		try {
			while(Passagiere >= 5) {
				wait();	
			}
			
			++Passagiere;
			
			System.out.println("Ein Passagier ist eingestiegen. Aktuelle Anzahl " + Passagiere);
			
			if(Passagiere >= 5) {
				System.out.println("Alle Sitzplätze belegt. Drehkreuz wird geschlossen!");
			} else {
				System.out.println("Noch sind Sitzplätze frei. Wagen wartet!");
			}
			
			Thread.sleep(1000);
			
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void abfahrt() {
		try {
			while(Passagiere < 5) {
				wait();	
			}
			
			System.out.println("Abfahrt! :)");
			
			for(int i = 0; i < 5; ++i) {
				System.out.println(i+1);
				Thread.sleep(1000);
			}
			
			aussteigen();
			
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void aussteigen() {
		System.out.println("Fahrt zu Ende. Alles bitte aussteigen!");
		
		Passagiere = 0;
	}
}
