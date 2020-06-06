package main;

import achterbahn.monitor.Steuerung;
import achterbahn.thread.Drehkreuz;
import achterbahn.thread.Wagen;

public class MainProgram {

	public static void main(String[] args) {
		Steuerung steuerung = new Steuerung();
		Drehkreuz drehkreuz = new Drehkreuz(steuerung);
		Wagen wagen = new Wagen(steuerung);
		
		Thread wagenThread = new Thread(wagen);
		Thread drehkreuzThread = new Thread(drehkreuz);
		
		wagenThread.start();
		drehkreuzThread.start();
	}

}
