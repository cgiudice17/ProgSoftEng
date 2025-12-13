package it.unisa.diem.se.biblioteca.book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidBookExceptionTest {

    @Test
    public void testCostruttoreVuoto() {
        // Verifica che possiamo creare l'eccezione senza messaggio
        InvalidBookException exception = new InvalidBookException();
        
        // Verifica che l'oggetto non sia null
        assertNotNull(exception, "L'eccezione deve essere creata correttamente");
        
        // Il messaggio dovrebbe essere null (o vuoto)
        assertNull(exception.getMessage(), "Il messaggio dovrebbe essere null nel costruttore vuoto");
    }

    @Test
    public void testCostruttoreConMessaggio() {
        String messaggioDiErrore = "ISBN non valido";
        
        // Creiamo l'eccezione passando un messaggio
        InvalidBookException exception = new InvalidBookException(messaggioDiErrore);
        
        // Verifichiamo che getMessage() restituisca esattamente quello che abbiamo passato
        assertEquals(messaggioDiErrore, exception.getMessage(), "Il messaggio dell'eccezione deve corrispondere");
    }
    
    @Test
    public void testEreditarieta() {
        // Verifica che InvalidBookException sia effettivamente figlia di Exception
        InvalidBookException exception = new InvalidBookException();
        assertTrue(exception instanceof Exception, "Deve estendere la classe Exception");
    }
}