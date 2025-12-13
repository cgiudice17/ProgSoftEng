package it.unisa.diem.se.biblioteca.book;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidBookTest implements ValidBook {

    // 1. TEST ISBN

    @Test
    public void testValidISBN() {
        // --- CASI VALIDI ---
        assertTrue(validISBN("9781234567890"), "ISBN 978 standard");
        assertTrue(validISBN("9790000000000"), "ISBN 979 standard");

        // --- CASI NON VALIDI
        assertFalse(validISBN("123"), "Troppo corto");
        assertFalse(validISBN("9771234567890"), "Prefisso errato (977)");
        assertFalse(validISBN("978-1234567890"), "Trattini non ammessi dalla tua regex");
        assertFalse(validISBN(" 9781234567890"), "Spazio iniziale non ammesso");
        assertFalse(validISBN("9781234567890 "), "Spazio finale non ammesso");
        
        // --- CASI LIMITE ---
        assertFalse(validISBN(""), "Stringa vuota");
        assertFalse(validISBN(null), "Null deve restituire false");
    }

    // 2. TEST AUTORE

    @Test
    public void testValidAuthor() {
        // --- CASI VALIDI STANDARD ---
        assertTrue(validAuthor("Mario Rossi")); 
        assertTrue(validAuthor("Niccolò Ammaniti"), "Accenti");
        assertTrue(validAuthor("D'Annunzio"), "Apostrofo");
        assertTrue(validAuthor("De Luca"), "Particella con maiuscola");
        assertTrue(validAuthor("Renzo"), "Nome singolo");

        // --- CASI NON VALIDI ---
        assertFalse(validAuthor("mario rossi"), "Tutto minuscolo");
        assertFalse(validAuthor("Mario123"), "Numeri nel nome");
        assertFalse(validAuthor("Mario @Rossi"), "Caratteri speciali");
        assertFalse(validAuthor(" Mario Rossi"), "Spazio iniziale");
        
        // --- CASI LIMITE ---
        assertFalse(validAuthor(""), "Stringa vuota");
        assertFalse(validAuthor((String)null), "Null");
    }

    // 3. TEST ANNO 

    @Test
    public void testValidYear() {
        int annoCorrente = LocalDate.now().getYear();

        // --- CASI VALIDI ---
        assertTrue(validYear("2000"));
        assertTrue(validYear("0"), "Anno 0 è tecnicamente un numero valido");
        assertTrue(validYear(String.valueOf(annoCorrente)));

        // --- CASI NON VALIDI ---
        assertFalse(validYear("3000"), "Futuro");
        assertFalse(validYear("-100"), "Negativo (la regex vuole solo digit)");
        assertFalse(validYear("2020a"), "Alfanumerico");
        assertFalse(validYear(" 2020"), "Spazio iniziale");

        // --- CASI LIMITE ---
        assertFalse(validYear(""), "Stringa vuota");
        assertFalse(validYear(null), "Null");
        
        //int
        assertTrue(validYear(2000));
        assertFalse(validYear(3000));
    }

    // 4. TEST COPIE 

    @Test
    public void testValidCopies() {
        // --- CASI VALIDI ---
        assertTrue(validCopies("1"));
        assertTrue(validCopies("1000"));

        // --- CASI NON VALIDI ---
        assertFalse(validCopies("0"), "Zero copie non ammesso");
        assertFalse(validCopies("-5"), "Negativo");
        assertFalse(validCopies("10 "), "Spazio finale");
        assertFalse(validCopies("10a"), "Alfanumerico");

        // --- CASI LIMITE ---
        assertFalse(validCopies(""), "Stringa vuota");
        assertFalse(validCopies(null), "Null");
        
        //int
        assertTrue(validCopies(10));
        assertFalse(validCopies(0));
    }
}