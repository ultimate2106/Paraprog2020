package achterbahn.thread;

import achterbahn.monitor.Steuerung;

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
