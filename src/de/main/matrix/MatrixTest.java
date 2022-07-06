package de.main.matrix;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Testklasse für Matrix-Klasse.
 */
public class MatrixTest {

    @DisplayName("Konstruktor für Nullmatrix")
    @ParameterizedTest
    @CsvSource({"3,2", "1,1", "4,5"})
    void testMatrixNullmatrix(int i, int j) {
        Matrix m = new Matrix(i, j);
        assertEquals(i, m.getLaenge());      // Teste Attribut laenge
        assertEquals(j, m.getBreite());      // Teste Attribut breite
        long[][] werte = m.getWerte();
        assertEquals(i, werte.length);       // Teste Feld auf passende Zeilenzahl
        for (long[] zeile : werte) {
            assertEquals(j, zeile.length);  // Teste Zeile auf passende Spaltenzahl
            for (long wert : zeile)
                assertEquals(0, wert);  // Teste, ob alle Werte 0
        }
    }

    @DisplayName("Nullmatrix-Konstruktor: Fehlerbehandlung")
    @ParameterizedTest
    @CsvSource({"1,0", "0,1", "-1,1"})
    void testMatrixNullmatrixException(int i, int j) {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(i, j));
    }


    @DisplayName("Feld-Konstruktor")
    @ParameterizedTest
    @MethodSource("provideValidArrays")
    void testMatrixFeld(long[][] werte, int laenge, int breite) {
        Matrix m = new Matrix(werte);
        assertEquals(laenge, m.getLaenge());                 // Teste Attribut laenge
        assertEquals(breite, m.getBreite());                 // Teste Attribut breite
        assertTrue(Arrays.deepEquals(werte, m.getWerte()));  // Prüfe, ob korrekt kopiert
        assertNotSame(werte, m.getWerte());                  // Prüfe, ob echte Kopie
    }

    // Testfälle für gültige Matrizen
    private static Stream<Arguments> provideValidArrays() {
        return Stream.of(
                Arguments.of(new long[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 3, 3),
                Arguments.of(new long[][]{{4, 5, 6}, {7, 8, 9}}, 2, 3),
                Arguments.of(new long[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}, 4, 2));
    }

    @DisplayName("Feld-Konstruktor: Fehlerbehandlung")
    @ParameterizedTest
    @MethodSource("provideMalformedArrays")
    void testMatrixFeldNull(long[][] werte) {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(werte));
    }

    // Ungültige Felder für den Feld-Konstruktor
    private static Stream<long[][]> provideMalformedArrays() {
        return Stream.of(
                null,
                new long[][]{},
                new long[][]{{1, 2, 3}, {4, 5}, {6, 7, 8}},
                new long[][]{{1, 2, 3}, {4, 5, 6}, {}},
                new long[][]{{1, 2, 3}, null, {4, 5, 6}},
                new long[][]{{1, 2, 3}, {4, 5, 6}, {}},
                new long[][]{{}, {}, {}});
    }

    @DisplayName("getEintrag/setEintrag")
    @ParameterizedTest
    @MethodSource("provideGetSetEintragValid")
    void testGetSetEintragValid(Matrix m, int zeile, int spalte, int wert) {
        assertEquals(wert, m.getEintrag(zeile, spalte));
        assertDoesNotThrow(() -> m.setEintrag(zeile, spalte, 42));
        assertEquals(42, m.getEintrag(zeile, spalte));
    }

    // Testfälle für getEintrag/setEintrag
    private static Stream<Arguments> provideGetSetEintragValid() {
        return Stream.of(
                Arguments.of(new Matrix(new long[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}), 1, 1, 5),
                Arguments.of(new Matrix(new long[][]{{4, 5, 6}, {7, 8, 9}}), 0, 2, 6),
                Arguments.of(new Matrix(new long[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}), 3, 0, 7));
    }

    @DisplayName("getEintrag/setEintrag: Fehlerbehandlung")
    @ParameterizedTest
    @MethodSource("provideGetSetEintragException")
    void testGetSetEintragException(Matrix m, int zeile, int spalte) {
        assertThrows(IllegalArgumentException.class, () -> m.getEintrag(zeile, spalte));
        assertThrows(IllegalArgumentException.class, () -> m.setEintrag(zeile, spalte, 42));
    }

    // Testfälle für getSetEintragException
    private static Stream<Arguments> provideGetSetEintragException() {
        return Stream.of(
                Arguments.of(new Matrix(new long[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}), 3, 2),
                Arguments.of(new Matrix(new long[][]{{4, 5, 6}, {7, 8, 9}}), 1, 3),
                Arguments.of(new Matrix(new long[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}), 4, 2));
    }

    @DisplayName("isUntereDreiecksmatrix")
    @ParameterizedTest
    @MethodSource("provideUntereDreiecksmatrix")
    void testUntereDreiecksmatrix(Matrix m, boolean erwartet) {
        assertEquals(erwartet, m.isUntereDreiecksmatrix());
    }

    // Testfälle für isUntereDreiecksmatrix
    private static Stream<Arguments> provideUntereDreiecksmatrix() {
        return Stream.of(
                Arguments.of(new Matrix(new long[][]{{1, 0, 0, 0}, {2, 3, 0, 1}, {4, 5, 6, 0}, {7, 8, 9, 0}}), false),
                Arguments.of(new Matrix(new long[][]{{4, 5, 6}, {7, 8, 9}}), false),
                Arguments.of(new Matrix(new long[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}), false),
                Arguments.of(new Matrix(10, 10), true),
                Arguments.of(new Matrix(new long[][]{{1, 0, 0}, {4, 5, 0}, {7, 8, 9}}), true),
                Arguments.of(new Matrix(new long[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}), true));
    }

    @DisplayName("addition")
    @ParameterizedTest
    @MethodSource("provideAdditionValid")
    void testAdditionValid(Matrix m1, Matrix m2, Matrix erwartet) {
        Matrix summe = m1.addition(m2);
        assertTrue(Arrays.deepEquals(erwartet.getWerte(), summe.getWerte()));
        assertEquals(erwartet.getLaenge(), summe.getLaenge());
        assertEquals(erwartet.getBreite(), summe.getBreite());

        summe = m2.addition(m1);
        assertTrue(Arrays.deepEquals(erwartet.getWerte(), summe.getWerte()));
        assertEquals(erwartet.getLaenge(), summe.getLaenge());
        assertEquals(erwartet.getBreite(), summe.getBreite());
    }

    // Gültige Testfälle für addition
    private static Stream<Arguments> provideAdditionValid() {
        return Stream.of(
                Arguments.of(new Matrix(new long[][]{{-3, 0, 5}, { 3,-3, 4}}),
                             new Matrix(new long[][]{{ 5,-5,-1}, { 0, 0, 2}}),
                             new Matrix(new long[][]{{ 2,-5, 4}, { 3,-3, 6}})),
                Arguments.of(new Matrix(new long[][]{{-4, 0}, {-2, 3}, { 3,-1}, { 1, 2}}),
                             new Matrix(new long[][]{{-2, 0}, { 2,-3}, { 3,-1}, {-2,-4}}),
                             new Matrix(new long[][]{{-6, 0}, { 0, 0}, { 6,-2}, {-1,-2}})),
                Arguments.of(new Matrix(new long[][]{{-1, 3,-4}, { 3, 2, 3}, { 0,-5, 3}}),
                             new Matrix(new long[][]{{-3,-4, 5}, {-1, 5, 0}, { 4, 0,-2}}),
                             new Matrix(new long[][]{{-4,-1, 1}, { 2, 7, 3}, { 4,-5, 1}})));
    }

    @DisplayName("addition: Fehlerbehandlung")
    @ParameterizedTest
    @MethodSource("provideAdditionException")
    void testAdditionException(Matrix m1, Matrix m2) {
        assertThrows(IllegalArgumentException.class, () -> m1.addition(m2));
    }

    // Testfälle für addition-Fehlerbehandlung
    private static Stream<Arguments> provideAdditionException() {
        return Stream.of(
                Arguments.of(new Matrix(new long[][]{{-3, 0, 5}, { 3, -3, 4}}), null),
                Arguments.of(new Matrix(new long[][]{{-4, 0}, {-2, 3}, { 3,-1}, { 1, 2}}),
                             new Matrix(new long[][]{{-2, 0}, { 2,-3}, { 3,-1}})),
                Arguments.of(new Matrix(new long[][]{{-1, 3, -4}, { 3, 2, 3}, { 0,-5, 3}}),
                             new Matrix(new long[][]{{-3,-4}, {-1, 5}, { 4, 0}})));
    }
}
