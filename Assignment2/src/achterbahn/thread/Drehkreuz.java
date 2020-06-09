package achterbahn.thread;

import achterbahn.interfaces.Steuerung;

public class Drehkreuz implements Runnable{

	private boolean isDrehkreuzOpen = true;
	private Steuerung Steuerung;
	
	public Drehkreuz(Steuerung steuerung) {
		Steuerung = steuerung;
	}
	
	@Override
	public void run() {
		if(Steuerung != null) {
			while(isDrehkreuzOpen) {
				Steuerung.passagier();
			}
		}
	}
	
}
