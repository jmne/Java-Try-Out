package de.main.lampe;

import java.util.Random;

/**
 * Klasse zur Realisierung von Lampen mit Bewegungsmeldern.
 */
public class Lampe {
    /**
     * Globale Konstante für die Ausfallwahrscheinlichkeit beim Schalten einer Lampe.
     */
    public static final double ausfallwahrscheinlichkeit = 0.01;

    /**
     * Zustand der Lampe.
     *
     * @see Lampenstatus
     */
    private Lampenstatus status;
    private final Lampe[][] nachbarn;

    /**
     * Konstruktor zur Erzeugung einer Lampe.
     * Die Lampe ist zu Beginn im ausgeschalteten Zustand.
     */
    public Lampe() {

        status = Lampenstatus.OFF;
        nachbarn = new Lampe[4][1];

    }

    /**
     * Schaltet die Lampe ein oder aus. Bei einem Wechsel des Zustands wird besteht die Gefahr, dass die Lampe
     * verschleißt. Zurückgegeben wird der neue Zustand der Lampe.
     *
     * @param neuerStatus Neuer Zustand der Lampe. Darf nicht BROKEN sein.
     * @return Neuer Zustand der Lampe.
     */
    public Lampenstatus schalteLampe(Lampenstatus neuerStatus) {

        if (neuerStatus == Lampenstatus.BROKEN)
            throw new IllegalArgumentException("Lampe kann nicht auf 'BROKEN' gesetzt werden.");
        if (status != neuerStatus && status != Lampenstatus.BROKEN) {
            status = neuerStatus;
            geheVielleichtKaputt();
        }

        return status;
    }


    /**
     * Eine nicht-angeschaltete und nicht-defekte Lampe wird angeschaltet.
     * Ist zudem der Bewegungsmelder angeschaltet, wird die Bewegung an
     * eventuell benachbarte Lampen weitergemeldet.
     */
    public void meldeBewegung() {

        status = Lampenstatus.ON;

        for (Richtung richtung : Richtung.values()) {
            if (getNachbarn(richtung) != null) getNachbarn(richtung).schalteLampe(Lampenstatus.ON);
        }

    }

    /**
     * Methode zur Simulation des Lampenverschleißes.
     */
    private void geheVielleichtKaputt() {
        double zufallswert = new Random().nextDouble();
        if (zufallswert <= ausfallwahrscheinlichkeit)
            status = Lampenstatus.BROKEN;
    }

    /**
     * Liefert aktuellen Zustand der Lampe zurück.
     *
     * @return Aktueller Zustand der Lampe.
     */
    public Lampenstatus getStatus() {
        return status;
    }


    /**
     * Setzt einen neuen Nachbarn
     *
     * @param wert     Uebergebene Lampe die als Nachbar gesetzt werden soll
     * @param richtung Wo der Nachbar liegt
     */
    public void setNachbarn(Lampe wert, Richtung richtung) {

        if (wert != null && richtung != null) {
            switch (richtung) {
                case NORD -> nachbarn[0][0] = wert;
                case OST -> nachbarn[1][0] = wert;
                case SUED -> nachbarn[2][0] = wert;
                case WEST -> nachbarn[3][0] = wert;
            }

        } else throw new IllegalArgumentException("Falscher Parameter übergeben.");

    }


    /**
     * Gibt Nachbarn der speziellen Himmelsrichtung zurueck
     *
     * @param richtung Welcher Nachbar abgefragt werden soll
     * @return Den in der angegebenen Himmelsrichtung liegenden Nachbarn
     */
    public Lampe getNachbarn(Richtung richtung) {

        if (richtung != null) {
            switch (richtung) {
                case NORD:
                    return nachbarn[0][0];
                case OST:
                    return nachbarn[1][0];
                case SUED:
                    return nachbarn[2][0];
                case WEST:
                    return nachbarn[3][0];
            }
        }

        return null;

    }

    /**
     * Gib Status der Lampe zurück als Zeichenkette
     *
     * @return Status der Lampe als Zeichenkette
     */
    @Override
    public String toString() {
        String s = null;

        switch (status) {
            case ON -> s = "*";
            case OFF -> s = ".";
            case BROKEN -> s = "!";
        }

        return s;
    }
}
