package it.unisa.diem.se.biblioteca.book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidBookExceptionTest {

    // 1. TEST COSTRUTTORI

    @Test
    public void testCostruttoreVuoto() {
        InvalidBookException exception = new InvalidBookException();
        
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testCostruttoreConMessaggio() {
        String msg = "ISBN non valido";
        InvalidBookException exception = new InvalidBookException(msg);
        
        // Il messaggio deve essere memorizzato correttamente
        assertEquals(msg, exception.getMessage());
    }
    
    // 2. TEST EREDITARIETÀ

    @Test
    public void testEreditarieta() {
        InvalidBookException exception = new InvalidBookException();
        
        assertTrue(exception instanceof Exception);
    }
}