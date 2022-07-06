package de.main.matrix;

import java.util.Arrays;

/**
 * Klasse zur Repräsentation von Matrizen.
 */
public class Matrix {
    private final int laenge;          // Anzahl Zeilen
    private final int breite;          // Anzahl Spalten
    private final long[][] werte;      // Feld zur Speicherung der Einträge

    /**
     * Konstruktor zur Erzeugung einer Nullmatrix mit vorgegebener Anzahl Zeilen und Spalten.
     *
     * @param laenge Anzahl der Zeilen.
     * @param breite Anzahl der Spalten.
     */
    public Matrix(int laenge, int breite) {

        if (laenge <= 0 || breite <= 0)
            throw new IllegalArgumentException("Ungültige Zeilen- oder Spaltenzahl.");

        werte = new long[laenge][breite];
        this.laenge = laenge;
        this.breite = breite;

    }

    /**
     * Konstruktor zur Erzeugung einer Matrix aus bestehendem zweidimensionalen Feld. Die Einträge des Feldes werden
     * dabei kopiert.
     *
     * @param werte Zweidimensionales Feld, das die Einträge der Matrix enthält.
     */
    public Matrix(long[][] werte) {
        if (verifyMatrix(werte))
            throw new IllegalArgumentException("Ungültiges Feld übergeben.");

        laenge = werte.length;
        breite = werte[0].length;
        this.werte = copyArray(werte);


    }

    /**
     * Prüft ob Matrix korrekt angegeben ist
     *
     * @param werte Zweidimensionales Feld, das die Einträge der Matrix enthält.
     * @return Wenn Matrix alle Ueberpruefungen besteht, false sonst true
     */
    private boolean verifyMatrix(long[][] werte) {
        return werte == null || istZeileNull(werte) || istAllesNull(werte) || hatNichtGleichVieleEintraege(werte);
    }

    /**
     * Kopiert das gegebene Feld
     *
     * @param werte Zweidimensionales Feld, das die Einträge der Matrix enthält.
     * @return Referenz auf kopiertes neues Feld
     */
    private long[][] copyArray(long[][] werte) {

        long[][] copy = new long[werte.length][werte[0].length];

        for (int i = 0; i < laenge; i++) {

            for (int j = 0; j < breite; j++) {

                copy[i][j] = werte[i][j];

            }

        }

        return copy;

    }

    /**
     * Prüft ob eine Zeile der Matrix null ist
     *
     * @param werte Zweidimensionales Feld, das die Einträge der Matrix enthält.
     * @return Wenn eine Zeil null true ansonsten false
     */
    private boolean istZeileNull(long[][] werte) {

        for (int i = 0; i < werte.length; i++) {

            if (werte[i] == null) return true;

        }

        return false;
    }

    /**
     * Gibt zurück ob es Eintrag gibt der nicht null ist
     *
     * @param werte Zweidimensionales Feld, das die Einträge der Matrix enthält.
     * @return Wenn Eintrag nicht null, gibt false aus sonst true
     */
    private boolean istAllesNull(long[][] werte) {

        for (int i = 0; i < werte.length; i++) {
            for (int j = 0; j < werte[i].length; j++) {
                if (werte[i][j] != 0) return false;
            }
        }

        return true;
    }

    /**
     * Prüft ob Matrix quadratisch ist
     *
     * @param werte Zweidimensionales Feld, das die Einträge der Matrix enthält.
     * @return Gibt true aus wenn Matrix quadratisch ansonsten false
     */
    private boolean hatNichtGleichVieleEintraege(long[][] werte) {

        int anzahl = werte[0].length;

        for (int i = 0; i < werte.length; i++) {
            if (werte[i].length != anzahl) return true;
        }

        return false;
    }

    /**
     * Setzt einen Eintrag der Matrix auf einen neuen Wert.
     *
     * @param zeile     Zeilenindex des Eintrags (beginnend bei 0).
     * @param spalte    Spaltenintex des Eintrags (beginnend bei 0).
     * @param neuerWert Neuer Wert des Eintrags.
     */
    public void setEintrag(int zeile, int spalte, long neuerWert) {
        if (checkEingabe(zeile, spalte))
            throw new IllegalArgumentException("Ungültige Indizes übergeben.");

        werte[zeile][spalte] = neuerWert;

    }

    /**
     * Gibt einen Eintrag der Matrix zurück.
     *
     * @param zeile  Zeilenindex des Eintrags (beginnend bei 0).
     * @param spalte Spaltenintex des Eintrags (beginnend bei 0).
     * @return Eintrag an angegebener Position.
     */
    public long getEintrag(int zeile, int spalte) {

        if (checkEingabe(zeile, spalte))
            throw new IllegalArgumentException("Ungültige Indizes übergeben.");
        return werte[zeile][spalte];
    }

    /**
     * Prüft auf korrekte Eingabe
     *
     * @param zeile  Anzahl der Zeilen der Matrix
     * @param spalte Anzahl der Spalten der Matrix
     * @return Gibt zurück ob es eine korrekte Eingabe war
     */
    private boolean checkEingabe(int zeile, int spalte) {
        return zeile < 0 || spalte < 0 || werte.length <= zeile || werte[zeile].length <= spalte;
    }

    /**
     * Prüft, ob Matrix eine untere Dreiecksmatrix ist.
     *
     * @return true, falls untere Dreiecksmatrix, sonst false.
     */
    public boolean isUntereDreiecksmatrix() {
        if (hatNichtGleichVieleEintraege(werte))
            throw new IllegalArgumentException("Ungültige Indizes übergeben.");

        for (int i = 0; i < werte.length; i++) {

            for (int j = 1 + i; j < werte[i].length; j++) {

                if (werte[i][j] != 0) return false;

            }

        }

        return true;
    }

    /**
     * Addiert zwei Matrizen und erzeugt ein neues Matrix-Objekt mit dem Ergebnis.
     *
     * @param andereMatrix Zu addierende Matrix.
     * @return Referenz auf Matrix-Objekt, das die Summe repräsentiert.
     */
    public Matrix addition(Matrix andereMatrix) {
        if (andereMatrix == null || verifyMatrix(andereMatrix.getWerte()) || andereMatrix.breite != breite || andereMatrix.laenge != laenge)
            throw new IllegalArgumentException("Inkompatible Matrix übergeben.");

        Matrix nMatrix = new Matrix(laenge, breite);

        for (int i = 0; i < laenge; i++) {

            for (int j = 0; j < breite; j++) {

                nMatrix.setEintrag(i, j, werte[i][j] + andereMatrix.getWerte()[i][j]);

            }

        }

        return nMatrix;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Ab hier nur Hilfsfunktionen zum Testen.               *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Gibt Länge der Matrix zurück.
     *
     * @return Anzahl der Zeilen der Matrix.
     */
    public int getLaenge() {
        return laenge;
    }

    /**
     * Gibt Breite der Matrix zurück.
     *
     * @return Anzahl der Spalten der Matrix.
     */
    public int getBreite() {
        return breite;
    }

    /**
     * Gibt Feld mit den Einträgen der Matrix zurück.
     *
     * @return Feld mit den Einträgen der Matrix.
     */
    public long[][] getWerte() {
        return werte;
    }

    /**
     * Liefert String-Darstellung der Einträge der Matrix.
     *
     * @return Matrix in String-Darstellung.
     */
    public String toString() {
        return Arrays.deepToString(werte);
    }
}
