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
				for(int i = 0; i < 5; ++i) {
					System.out.println(i+1);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Steuerung.aussteigen(isWagen1);
			}
		}
	}
}
