package de.main.duplikatenentferner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für uebungen.Duplikatentferner.
 */
public class DuplikatentfernerTest {

    private static Duplikatentferner de;

    @BeforeAll
    static void init() {
        de = new Duplikatentferner();
    }

    @DisplayName("containsInRange: Fehlerbehandlung")
    @ParameterizedTest
    @MethodSource("provideContainsException")
    void testContainsInRangeException(int[] feld, int links, int rechts) {
        assertThrows(IllegalArgumentException.class, () -> de.containsInRange(42, feld, links, rechts));
    }

    // Ungültige Testfälle für containsInRange
    private static Stream<Arguments> provideContainsException() {
        return Stream.of(
                Arguments.of(null, 0, 1),
                Arguments.of(new int[]{1, 2, 3, 4}, -1, 2),
                Arguments.of(new int[]{1, 2, 3, 4}, 2, 1),
                Arguments.of(new int[]{1, 2, 3, 4}, 0, 4));
    }

    @DisplayName("containsInRange")
    @ParameterizedTest
    @MethodSource("provideContains")
    void testContainsInRange(int kandidat, int[] feld, int links, int rechts, boolean erwartet) {
        assertEquals(erwartet, de.containsInRange(kandidat, feld, links, rechts));
    }

    // Testfälle für containsInRange
    private static Stream<Arguments> provideContains() {
        return Stream.of(
                Arguments.of(9, new int[]{1, 9, 2, 7, 3, 2, 7, 5, 0, 0}, 0, 9, true),
                Arguments.of(2, new int[]{2, 3, 9, 1, 2, 5, 2, 4, 4, 6}, 6, 6, true),
                Arguments.of(4, new int[]{3, 7, 9, 1, 4, 4, 9, 2, 8, 0}, 2, 7, true),
                Arguments.of(4, new int[]{0, 0, 5, 5, 0, 5, 9, 2, 9, 7}, 0, 9, false),
                Arguments.of(5, new int[]{9, 1, 2, 7, 4, 1, 3, 7, 5, 9}, 0, 7, false),
                Arguments.of(4, new int[]{6, 4, 8, 9, 2, 5, 0, 1, 5, 3}, 2, 9, false));
    }

    @DisplayName("removeDuplicates: Fehlerbehandlung")
    @Test
    void testRemoveDuplicatesException() {
        assertThrows(IllegalArgumentException.class, () -> de.removeDuplicates(null));
    }

    @DisplayName("removeDuplicates")
    @ParameterizedTest
    @MethodSource("provideDuplicates")
    void testRemoveDuplicates(int[] feld, int[] erwartet) {
        assertArrayEquals(erwartet, de.removeDuplicates(feld));
    }

    // Testfälle für containsInRange
    private static Stream<Arguments> provideDuplicates() {
        return Stream.of(
                Arguments.of(new int[]{}, new int[]{}),
                Arguments.of(new int[]{0}, new int[]{0}),
                Arguments.of(new int[]{0, 0}, new int[]{0}),
                Arguments.of(new int[]{0, 9, 1, 8, 2, 7, 3, 6, 4, 5}, new int[]{0, 9, 1, 8, 2, 7, 3, 6, 4, 5}),
                Arguments.of(new int[]{1, 9, 2, 7, 3, 2, 7, 5, 0, 0}, new int[]{1, 9, 2, 7, 3, 5, 0}),
                Arguments.of(new int[]{2, 3, 9, 1, 2, 5, 2, 4, 4, 6}, new int[]{2, 3, 9, 1, 5, 4, 6}),
                Arguments.of(new int[]{3, 7, 9, 1, 4, 4, 9, 2, 8, 0}, new int[]{3, 7, 9, 1, 4, 2, 8, 0}),
                Arguments.of(new int[]{0, 0, 5, 5, 0, 5, 9, 2, 9, 7}, new int[]{0, 5, 9, 2, 7}),
                Arguments.of(new int[]{9, 1, 2, 7, 4, 1, 3, 7, 5, 9}, new int[]{9, 1, 2, 7, 4, 3, 5}),
                Arguments.of(new int[]{6, 4, 8, 9, 2, 5, 0, 1, 5, 3}, new int[]{6, 4, 8, 9, 2, 5, 0, 1, 3}));
    }
}
