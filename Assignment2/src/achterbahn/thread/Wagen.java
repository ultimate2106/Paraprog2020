package achterbahn.thread;

import achterbahn.interfaces.Steuerung;

public class Wagen implements Runnable{
	
	private boolean isAchterbahnActive = true;
	private Steuerung Steuerung;
	private boolean isWagen1;
	
	public Wagen(Steuerung steuerung, boolean isWagen1) {
		this.isWagen1 = isWagen1;
		Steuerung = steuerung;
	}
	
	@Override
	public void run() {
		if(Steuerung != null) {
			while(isAchterbahnActive) {
				Steuerung.abfahrt(isWagen1);
			}
		}
	}
}
