package main;

import achterbahn.interfaces.Steuerung;
import achterbahn.monitor.*;
import achterbahn.thread.Drehkreuz;
import achterbahn.thread.Wagen;

public class MainProgram {

	public static void main(String[] args) {
		//Steuerung steuerung = new Steuerung1();
		Steuerung steuerung = new Steuerung2();
		
		Drehkreuz drehkreuz = new Drehkreuz(steuerung);
		
		Wagen wagen = new Wagen(steuerung);
		Wagen wagen2 = new Wagen(steuerung);

		Thread wagenThread = new Thread(wagen,"Wagen1");
		Thread wagen2Thread = new Thread(wagen2, "Wagen2");
		
		Thread drehkreuzThread = new Thread(drehkreuz);
		
		wagenThread.start();
		wagen2Thread.start();
		
		drehkreuzThread.start();
	}

}
