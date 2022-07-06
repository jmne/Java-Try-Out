package de.main.duplikatenentferner;

import java.util.Arrays;  // für Feldausgabe benötigt

public class DuplikatentfernerRunner {

    public void run() {
        // Beispielhaftes Entfernen von Duplikaten und Ausgabe auf der Konsole.
        Duplikatentferner de = new Duplikatentferner();
        int[] feld = new int[]{0, 0, 5, 5, 0, 5, 9, 2, 9, 7};
        System.out.println("Feld " + Arrays.toString(feld) + " ohne Duplikate:");
        System.out.println(Arrays.toString(de.removeDuplicates(feld)));
    }

    public static void main(String[] args) {
        DuplikatentfernerRunner r = new DuplikatentfernerRunner();
        r.run();
    }
}
