package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidUserExceptionTest {

    // 1. TEST COSTRUTTORE SENZA ARGOMENTI

    @Test
    public void testConstructorNoArgs() {
        InvalidUserException exception = new InvalidUserException();
        
        assertNotNull(exception);
        // Verifica che l'oggetto creato sia un'istanza valida
        assertTrue(exception instanceof Exception);
    }

    // 2. TEST COSTRUTTORE CON MESSAGGIO

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Errore: Dati utente non validi";
        InvalidUserException exception = new InvalidUserException(errorMessage);
        
        assertNotNull(exception);
        // Verifica che il messaggio passato sia stato memorizzato correttamente
        assertEquals(errorMessage, exception.getMessage());
    }
}