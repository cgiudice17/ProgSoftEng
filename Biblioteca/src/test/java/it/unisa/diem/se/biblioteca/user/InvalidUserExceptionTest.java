package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidUserExceptionTest {

    // 1. TEST COSTRUTTORE SENZA ARGOMENTI

    @Test
    public void testConstructorNoArgs() {
        InvalidUserException exception = new InvalidUserException();
        
        assertNotNull(exception, "L'oggetto eccezione non deve essere null.");
        // Verifica che l'oggetto creato sia un'istanza valida
        assertTrue(exception instanceof Exception, "InvalidUserException deve ereditare da Exception.");
        assertNull(exception.getMessage(), "Il messaggio dell'eccezione senza argomenti deve essere null.");
    }

    // 2. TEST COSTRUTTORE CON MESSAGGIO

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Errore: Dati utente non validi";
        InvalidUserException exception = new InvalidUserException(errorMessage);
        
        assertNotNull(exception, "L'oggetto eccezione non deve essere null.");
        // Verifica che il messaggio passato sia stato memorizzato correttamente
        assertEquals(errorMessage, exception.getMessage(), "Il messaggio dell'eccezione deve corrispondere a quello fornito.");
    }
}