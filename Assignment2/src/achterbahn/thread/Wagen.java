package achterbahn.thread;

import achterbahn.interfaces.Steuerung;

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
				for(int i = 0; i < 5; ++i) {
					System.out.println(i+1);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Steuerung.aussteigen();
			}
		}
	}
}
