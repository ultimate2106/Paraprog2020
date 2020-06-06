package achterbahn.thread;

import achterbahn.monitor.Steuerung;

public class Wagen implements Runnable{
	
	private boolean isAchterbahnActive = true;
	private Steuerung Steuerung;
	
	public Wagen(Steuerung steuerung) {
		Steuerung = steuerung;
	}
	
	@Override
	public void run() {
		if(Steuerung != null) {
			while(isAchterbahnActive) {
				Steuerung.abfahrt();
			}
		}
	}
}
