package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test di unità per la classe InvalidUserException.
 * Questa classe verifica che i costruttori dell'eccezione personalizzata funzionino correttamente 
 * e che la classe estenda la classe base {@link Exception} (eccezione checked).
 */
public class InvalidUserExceptionTest {

    // 1. TEST COSTRUTTORE SENZA ARGOMENTI

    // Verifica che il costruttore di default crei un oggetto non nullo e con messaggio nullo.
    @Test
    public void testConstructorNoArgs() {
        InvalidUserException exception = new InvalidUserException();
        
        assertNotNull(exception, "L'oggetto eccezione non deve essere null.");
        assertNull(exception.getMessage(), "Il messaggio dell'eccezione senza argomenti deve essere null.");
    }

    // 2. TEST COSTRUTTORE CON MESSAGGIO

    // Verifica che il costruttore con messaggio memorizzi correttamente il messaggio di errore.
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Errore: Dati utente non validi";
        InvalidUserException exception = new InvalidUserException(errorMessage);
        
        assertNotNull(exception, "L'oggetto eccezione non deve essere null.");
        assertEquals(errorMessage, exception.getMessage(), "Il messaggio dell'eccezione deve corrispondere a quello fornito.");
    }
}
