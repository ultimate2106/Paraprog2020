package achterbahn.monitor;

import achterbahn.interfaces.Steuerung;

public class Steuerung2 implements Steuerung {
    private int w1Passagiere = 0;
    private int w2Passagiere = 0;
    private boolean w1Vorne = false;
    private boolean w2Vorne = false;
    private final int MaxPassagiere = 5;

    // Als Konstruktor umsetzten?
    public synchronized void init() {
        if (java.lang.Math.random() < 0.5) {
            w1Vorne = true;
        } else {
            w2Vorne = true;
        }
    }

    @Override
    public synchronized void passagier() {
        try {
            while (!(w1Passagiere < MaxPassagiere) && !(w2Passagiere < MaxPassagiere)) {
                wait();
            }

            if (w1Passagiere < MaxPassagiere && w2Passagiere < MaxPassagiere) {
                if (java.lang.Math.random() < 0.5) {
                    ++w1Passagiere;

                    System.out.println("Ein Passagier ist in Wagen 1 eingestiegen. Aktuelle Anzahl " + w1Passagiere);
			
			        if(w1Passagiere >= MaxPassagiere) {
				        System.out.println("Alle Sitzplaetze in Wagen 1 sind belegt.");
			        } else {
				        System.out.println("Noch sind Sitzplaetze frei. Wagen 1 wartet!");
			        }
                } else {
                    ++w2Passagiere;

                    System.out.println("Ein Passagier ist in Wagen 2 eingestiegen. Aktuelle Anzahl " + w2Passagiere);
			
			        if(w2Passagiere >= MaxPassagiere) {
				        System.out.println("Alle Sitzplaetze in Wagen 2 sind belegt.");
			        } else {
				        System.out.println("Noch sind Sitzplaetze frei. Wagen 2 wartet!");
			        }
                }
            } else if (w1Passagiere < MaxPassagiere) {
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
    public synchronized void abfahrt(boolean wagen) {
            if (wagen) {
                abfahrt(wagen, w1Passagiere, w1Vorne);
            } else {
                abfahrt(wagen, w2Passagiere, w2Vorne);
            }
            notifyAll();
    }

    public synchronized void abfahrt(boolean wagen, int passagiere, boolean vorne) {
        while (!(passagiere >= MaxPassagiere & vorne)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Abfahrt! :)");

        for (int i = 0; i < 5; ++i) {
            System.out.println(i + 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}

        aussteigen(wagen);
    }

    @Override
    public synchronized void aussteigen(boolean wagen) {
        if(wagen)
        {
            System.out.println("Fahrt in Wagen 1  ist zu Ende. Alles bitte aussteigen!");
            w1Vorne = false;
            w2Vorne = true;

        }
        else
        {
            System.out.println("Fahrt in Wagen 2  ist zu Ende. Alles bitte aussteigen!");
            w2Vorne = false;
            w1Vorne = true;
        }
    }
    
}
