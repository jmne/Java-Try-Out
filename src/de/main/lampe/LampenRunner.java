package de.main.lampe;

/**
 * Runner zur Lampen-Aufgabe (Blatt 11)
 */
public class LampenRunner {

    /**
     * Feld zur Aufnahme aller Lampen(-Referenzen).
     */
    Lampe[][] feld;

    public static void main(String[] args) {
        LampenRunner r = new LampenRunner();
        r.run();
    }

    /**
     * Initialisiere das Lampen-Feld.
     */
    private void erzeugeLampen() {

        feld = new Lampe[10][5];

        // Erzeuge 50 Lampen und speichere diese in 'feld'

        for (int i = 0; i < feld.length; i++) {

            for (int j = 0; j < feld[i].length; j++) {

                feld[i][j] = new Lampe();

            }

        }

        // Setze Verbindungen zu den maximal vier existierenden
        // Nachbarn.

        for (int i = 0; i < feld.length; i++) {

            for (int j = 0; j < feld[i].length; j++) {

                if (i - 1 >= 0 && feld[i - 1][j] != null)              feld[i][j].setNachbarn(feld[i - 1][j], Richtung.NORD);
                if (i + 1 < feld.length && feld[i + 1][j] != null)     feld[i][j].setNachbarn(feld[i + 1][j], Richtung.SUED);
                if (j - 1 >= 0 && feld[i][j - 1] != null)              feld[i][j].setNachbarn(feld[i][j - 1], Richtung.WEST);
                if (j + 1 < feld[i].length && feld[i][j + 1] != null)  feld[i][j].setNachbarn(feld[i][j + 1], Richtung.OST);

            }

        }


    }

    /**
     * Zeige den Status aller Lampen an.
     *
     * @param s Zusaetzlicher Informationstext.
     */
    private void zeigeStatus(String s) {
        System.out.println("Aktueller Status (" + s + "):");
        for (int i = 0; i < feld.length; i++) {
            for (int j = 0; j < feld[i].length; j++) {
                System.out.print(feld[i][j] + " ");
            }
            System.out.println(feld[i] + " ");
        }
        System.out.println("==== Fertig. ====");
    }

    /**
     * Hier werden die einzelnen Aktionen ausgefuehrt.
     */
    private void run() {

        // Erzeuge Lampen-Feld und verkuepfe Lampen

        erzeugeLampen();
        zeigeStatus("nach Erzeugung aller Lampen");

        // Schalte eine Lampe per Bewegungsmelder an
        // und lasse weiter melden.

        feld[3][4].meldeBewegung();
        zeigeStatus("nach Bewegungsmeldung an Stelle (3,4)");

        // Schalte alle Lampen aus.

        for (int i = 0; i < feld.length; i++) {

            for (int j = 0; j < feld[i].length; j++) {

                if (feld[i][j] != null) feld[i][j].schalteLampe(Lampenstatus.OFF);

            }

        }

        zeigeStatus("nach Ausschalten aller Lampen");

        // Schalte Lampen in der mittlere Reihe an.

        int mittlereReihe = Math.round(feld.length / 2);


        for (int i = 0; i < feld[mittlereReihe].length; i++) {

            if (feld[mittlereReihe][i] != null) feld[mittlereReihe][i].schalteLampe(Lampenstatus.ON);

        }

        zeigeStatus("nach Anschalten aller Lampen in der mittleren Reihe");

        // Schalte eine Lampe per Bewegungsmelder an
        // und lasse weiter melden.
        feld[0][0].meldeBewegung();
        zeigeStatus("nach Bewegungsmeldung an Stelle (0,0)");

    }
}
