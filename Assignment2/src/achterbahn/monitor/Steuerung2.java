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
            while (w1Passagiere >= MaxPassagiere && w2Passagiere >= MaxPassagiere) 
            {
                wait();
            }
               

            if(java.lang.Math.random() < 0.5 ){           
                ++w1Passagiere;
			    notifyAll();
            }
            else
            {
                ++w2Passagiere;
                notifyAll();
            }
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