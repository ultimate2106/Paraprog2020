package main;

import achterbahn.interfaces.Steuerung;
import achterbahn.monitor.Steuerung1;
import achterbahn.thread.Drehkreuz;
import achterbahn.thread.Wagen;

public class MainProgram {

	public static void main(String[] args) {
		Steuerung steuerung = new Steuerung1();
		Drehkreuz drehkreuz = new Drehkreuz(steuerung);
		Wagen wagen = new Wagen(steuerung);
		
		Thread wagenThread = new Thread(wagen);
		Thread drehkreuzThread = new Thread(drehkreuz);
		
		wagenThread.start();
		drehkreuzThread.start();
	}

}
