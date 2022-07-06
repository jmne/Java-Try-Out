package de.main.duplikatenentferner;

/**
 * Klasse zum Entfernen von Duplikaten aus Feldern.
 */
public class Duplikatentferner {
    /**
     * Prüft, ob ein bestimmter Wert in einem bestimmten Bereich eines Feldes vorkommt. Das übergebene Feld darf nicht
     * null sein, die linke Grenze des Suchbereichs darf nicht negativ sein, die rechte Grenze des Suchbereichs muss
     * mindestens so groß sein wie die linke und innerhalb des Feldes liegen.
     *
     * @param kandidat Zu suchender Wert.
     * @param feld     Zu durchsuchendes Feld.
     * @param links    Linke Grenze des Suchbereichs (inklusive).
     * @param rechts   Rechte Grenze des Suchbereichs (inklusive).
     * @return true, falls Wert im Suchbereich enthalten, sonst false.
     */


    public boolean containsInRange(int kandidat, int[] feld, int links, int rechts) {
        // Prüfe Parameter auf Gültigkeit.
        if (feld == null || feld.length < rechts)
            throw new IllegalArgumentException("Ungültiges Feld übergeben.");
        if (links < 0 || (!(kandidat >= links && kandidat <= rechts)))
            throw new IllegalArgumentException("Ungültige Indizes übergeben.");

            for (int i = links; i < rechts; i++) {
                if (feld[i] == kandidat) return true;
            }

        return false;
    }

    /**
     * Entfernt alle Duplikate aus einem Feld. Das übergebene Feld darf nicht null sein.
     *
     * @param feld Feld, aus dem Duplikate entfernt werden sollen.
     * @return Kopie des übergebenen Feldes ohne Duplikate.
     */


    public int[] removeDuplicates(int[] feld) {
        // Prüfe Feld auf Gültigkeit.
        if (feld == null || feld.length == 0)
            throw new IllegalArgumentException("Ungültiges Feld übergeben.");

        int[] newFeld = new int[feld.length];
        int index = 0;
        boolean zero = false;

        for (int i = 0; i < feld.length; i++) {

            for (int j = 0; j < newFeld.length; j++) {

                if(feld[i] == 0 && !zero){  // Teste ob Zahl 0 ist, da diese in newFeld mehrfach vorkommt
                    newFeld[index] = feld[i];
                    index++;
                    zero = true;
                    break;
                }

                if (feld[i] == newFeld[j]) break; // Wenn Zahl bereits aufgenommen, überspring.

                if (j == newFeld.length - 1) { // Wenn Zahl nicht doppelt, schreib in neue Liste
                    newFeld[index] = feld[i];
                    index++;
                }

            }

        }

        return verkuerzeFeld(newFeld, index);

    }


    /**
     * Kürze gegebenes Feld auf gegebene Laenge von links
     *
     * @param feld   Feld, was reduziert werden soll
     * @param laenge Länge des neuen Feldes
     * @return Verkürztes Feld auf bestimmte Laenge
     */
    private int[] verkuerzeFeld(int[] feld, int laenge) {
        int[] ausgabe = new int[laenge];

        for (int i = 0; i < laenge; i++) { // Schreibe alte Liste in neue Liste und kürze um laenge
            ausgabe[i] = feld[i];
        }

        return ausgabe;

    }

    /**
     *
     * Aufgabe c)
     *
     * Der Code ist nicht kompilierbar, da wir waehrend des durchgehens der Liste ein neues Object vom Typ Feld erzeugen muessen.
     * Bei Generics in Java ist es nicht möglich neue Objekte dieser zu erzeugen. Dies ist eine Restriktion
     * innerhalb der Programmiersprache. Dementsprechend können wir unsere Funktion, ohne weitere
     * Programmierung nicht von int auf "T" umschreiben.
     *
     */


}
