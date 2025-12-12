package it.unisa.diem.se.biblioteca;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaTest {

    // TEST 1: Test singolo
    @Test
    public void testSomma() {
        System.out.println("--- NUOVO CODICE: Test Singolo ---");
        assertEquals(10, 5 + 5);
    }

    // TEST 2: Parametrizzato (Deve girare 5 volte)
    @ParameterizedTest
    @ValueSource(ints = { 2, 4, 6, 8, 100 })
    public void testNumeriPari(int numero) {
        System.out.println("Test Parametrizzato con numero: " + numero);
        assertTrue(numero % 2 == 0, "Deve essere pari");
    }
}