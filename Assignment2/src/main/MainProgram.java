package main;

import achterbahn.interfaces.Steuerung;
import achterbahn.monitor.Steuerung1;
import achterbahn.monitor.Steuerung2;
import achterbahn.thread.Drehkreuz;
import achterbahn.thread.Wagen;

public class MainProgram {

	public static void main(String[] args) {
		//Steuerung steuerung = new Steuerung1();
		Steuerung steuerung = new Steuerung2();
		Drehkreuz drehkreuz = new Drehkreuz(steuerung);
		Wagen wagen = new Wagen(steuerung, true);
		Wagen wagen2 = new Wagen(steuerung, false);

		Thread wagenThread = new Thread(wagen);
		Thread wagen2Thread = new Thread(wagen2);
		Thread drehkreuzThread = new Thread(drehkreuz);
		
		wagenThread.start();
		wagen2Thread.start();
		drehkreuzThread.start();
	}

}
