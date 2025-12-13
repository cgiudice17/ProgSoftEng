package it.unisa.diem.se.biblioteca.book;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidBookTest implements ValidBook {

    @Test
    public void testValidISBN() {
        System.out.println("Test: Validazione ISBN (Regex)");

        // --- CASI VALIDI ---
        assertTrue(validISBN("9781234567890"), "ISBN 978 valido");
        assertTrue(validISBN("9790000000000"), "ISBN 979 valido");

        // --- CASI INVALIDI ---
        assertFalse(validISBN("123"), "Troppo corto");
        assertFalse(validISBN("9771234567890"), "Prefisso errato (977)");
        assertFalse(validISBN("978123456789X"), "Contiene lettere");
        assertFalse(validISBN(""), "Stringa vuota");
        assertFalse(validISBN(null), "Null dovrebbe essere gestito (o crashare se non gestito, ma qui testiamo la regex)"); 
    }

    @Test
    public void testValidAuthor() {
        System.out.println("Test: Validazione Autore");

        // --- CASI VALIDI ---
        assertTrue(validAuthor("Mario Rossi"));
        assertTrue(validAuthor("D'Annunzio"), "Apostrofo");
        assertTrue(validAuthor("De Luca"), "Spazio nel cognome");

        // --- CASI INVALIDI ---
        assertFalse(validAuthor("mario rossi"), "Minuscolo");
        assertFalse(validAuthor("Mario123"), "Numeri");
        assertFalse(validAuthor(""), "Vuoto");
    }

    @Test
    public void testValidYear() {
        System.out.println("Test: Validazione Anno");

        int annoCorrente = LocalDate.now().getYear();

        // Test versione String
        assertTrue(validYear("2000"));
        assertTrue(validYear(String.valueOf(annoCorrente)));
        
        assertFalse(validYear("3000"), "Futuro");
        assertFalse(validYear("abc"), "Lettere");
        assertFalse(validYear("-100"), "Negativo");

        // Test versione int
        assertTrue(validYear(2000));
        assertFalse(validYear(3000));
    }

    @Test
    public void testValidCopies() {
        System.out.println("Test: Validazione Copie");

        // Test versione String
        assertTrue(validCopies("1"));
        assertTrue(validCopies("100"));
        
        assertFalse(validCopies("0"), "Zero copie non ha senso");
        assertFalse(validCopies("-5"), "Negativo");
        assertFalse(validCopies("abc"), "Lettere");

        // Test versione int
        assertTrue(validCopies(10));
        assertFalse(validCopies(0));
    }
}