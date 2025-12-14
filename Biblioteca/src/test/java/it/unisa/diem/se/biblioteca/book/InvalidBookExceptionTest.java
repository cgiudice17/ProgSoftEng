package it.unisa.diem.se.biblioteca.book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** 
 * Test di unità per la classe InvalidBookException.
 * Questa classe verifica il corretto funzionamento dei costruttori dell'eccezione 
 * personalizzata e conferma la sua corretta ereditarietà.
 */
public class InvalidBookExceptionTest {

    // 1. TEST COSTRUTTORI

    // Verifica il costruttore di default senza argomenti.
    @Test
    public void testCostruttoreVuoto() {
        InvalidBookException exception = new InvalidBookException();
        
        assertNotNull(exception, "L'oggetto eccezione non deve essere null.");
        assertNull(exception.getMessage(), "Il messaggio dell'eccezione vuota deve essere null.");
    }

    // Verifica il costruttore che accetta un messaggio come stringa.
    @Test
    public void testCostruttoreConMessaggio() {
        String msg = "ISBN non valido";
        InvalidBookException exception = new InvalidBookException(msg);
        
        assertEquals(msg, exception.getMessage(), "Il messaggio dell'eccezione deve corrispondere a quello fornito.");
    }
    
    // 2. TEST EREDITARIETÀ

    // Verifica che l'eccezione personalizzata InvalidBookException estenda correttamente la classe Exception (checked exception).
    @Test
    public void testEreditarieta() {
        InvalidBookException exception = new InvalidBookException();
     
        assertTrue(exception instanceof Exception, "InvalidBookException deve ereditare da Exception.");
    }
}
