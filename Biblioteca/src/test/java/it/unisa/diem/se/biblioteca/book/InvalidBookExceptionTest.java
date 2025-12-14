package it.unisa.diem.se.biblioteca.book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidBookExceptionTest {

    // 1. TEST COSTRUTTORI

    @Test
    public void testCostruttoreVuoto() {
        InvalidBookException exception = new InvalidBookException();
        
        assertNotNull(exception, "L'oggetto eccezione non deve essere null.");
        assertNull(exception.getMessage(), "Il messaggio dell'eccezione vuota deve essere null.");
    }

    @Test
    public void testCostruttoreConMessaggio() {
        String msg = "ISBN non valido";
        InvalidBookException exception = new InvalidBookException(msg);
        
        // Il messaggio deve essere memorizzato correttamente
        assertEquals(msg, exception.getMessage(), "Il messaggio dell'eccezione deve corrispondere a quello fornito.");
    }
    
    // 2. TEST EREDITARIETÀ

    @Test
    public void testEreditarieta() {
        InvalidBookException exception = new InvalidBookException();
        
        assertTrue(exception instanceof Exception, "InvalidBookException deve ereditare da Exception.");
    }
}