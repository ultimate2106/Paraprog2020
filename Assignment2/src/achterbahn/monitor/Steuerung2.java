package achterbahn.monitor;

import achterbahn.interfaces.Steuerung;

public class Steuerung2 implements Steuerung {
    private int w1Passagiere = 0;
    private int w2Passagiere = 0;
    private boolean w1Vorne = true;
    private boolean w2Vorne = false;
    private final int MaxPassagiere = 5;

    @Override
    public synchronized void passagier() {
        try {
            while (w1Passagiere >= MaxPassagiere && w2Passagiere >= MaxPassagiere) {
                wait();
            }
<<<<<<< HEAD
               
            if(java.lang.Math.random() < 0.5 ){           
                ++w1Passagiere;
			    notifyAll();
=======

            if(w1Passagiere < 5 && w2Passagiere < 5){
            	if(java.lang.Math.random() < 0.5) {
            		++w1Passagiere;
            	} else {
            		++w2Passagiere;            		
            	}
>>>>>>> e0f2053c409797e510f3af4b2c73ff8a3aac80e7
            }
            else if(w1Passagiere < 5){
            	++w1Passagiere;
            } else {
            	++w2Passagiere;
            }
            
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void abfahrt() {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void aussteigen() {
        // TODO Auto-generated method stub

    }
    
}