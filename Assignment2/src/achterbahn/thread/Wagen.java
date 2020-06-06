package achterbahn.thread;

import achterbahn.interfaces.Steuerung;

public class Wagen implements Runnable{
	
	private boolean isAchterbahnActive = true;
	private Steuerung Steuerung;
	private boolean wagen;
	
	public Wagen(Steuerung steuerung, boolean wagen) {
		this.wagen = wagen;
		Steuerung = steuerung;
	}
	
	@Override
	public void run() {
		if(Steuerung != null) {
			while(isAchterbahnActive) {
				Steuerung.abfahrt(wagen);
			}
		}
	}
}
