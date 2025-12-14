package it.unisa.diem.se.biblioteca.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test di unità per la classe Author.
 * Questa classe verifica il corretto funzionamento dei metodi fondamentali (costruttore, getter/setter) 
 * e garantisce l'implementazione corretta di equals e hashCode, basata su nome e cognome.
 */
public class AuthorTest {

    private Author autore;

    // Metodo eseguito prima di ogni test.
    // Inizializza un'istanza base dell'Autore per i test.
    @BeforeEach
    public void setUp() {
        autore = new Author("Mario", "Rossi");
    }

    // 1. TEST COSTRUTTORE E GETTERS
    
    // Verifica che il costruttore inizializzi correttamente l'oggetto e che i getter restituiscano i valori attesi.
    @Test
    public void testCostruttoreEGetters() {
        assertNotNull(autore, "L'oggetto Autore non dovrebbe essere null dopo l'inizializzazione.");
        assertEquals("Mario", autore.getName(), "Il nome recuperato non corrisponde a quello fornito nel costruttore.");
        assertEquals("Rossi", autore.getSurname(), "Il cognome recuperato non corrisponde a quello fornito nel costruttore.");
    }

    // 2. TEST SETTERS

    // Verifica che i metodi setter modifichino correttamente le proprietà 'name' e 'surname'.
    @Test
    public void testSetters() {
        autore.setName("Luigi");
        autore.setSurname("Bianchi");

        assertEquals("Luigi", autore.getName(), "Il setter setName non ha aggiornato il nome correttamente.");
        assertEquals("Bianchi", autore.getSurname(), "Il setter setSurname non ha aggiornato il cognome correttamente.");
    }

    // 3. TEST EQUALS

    // Verifica che l'uguaglianza (equals) sia basata su nome e cognome.
    @Test
    public void testEquals() {
        Author stessoAutore = new Author("Mario", "Rossi");
        Author autoreDiversoNome = new Author("Luigi", "Rossi");
        Author autoreDiversoCognome = new Author("Mario", "Verdi");


        assertEquals(autore, autore, "Uguaglianza riflessiva: un oggetto deve essere uguale a se stesso.");
        assertEquals(autore, stessoAutore, "Due autori con nome e cognome identici devono essere uguali.");
        
        assertNotEquals(autore, autoreDiversoNome, "Autori con nomi diversi non devono essere uguali.");
        assertNotEquals(autore, autoreDiversoCognome, "Autori con cognomi diversi non devono essere uguali.");
        assertNotEquals(autore, null, "Il confronto con null deve restituire false.");
        assertNotEquals(autore, "Una Stringa", "Il confronto con oggetti di classi diverse deve restituire false.");
    }

    // 4. TEST HASHCODE

    // Verifica che l'implementazione di hashCode sia coerente con equals.
    @Test
    public void testHashCode() {
        Author copiaAutore = new Author("Mario", "Rossi");
        Author autoreDiverso = new Author("Luigi", "Verdi");

        assertEquals(autore.hashCode(), copiaAutore.hashCode(), "L'HashCode deve essere lo stesso per oggetti uguali.");
        assertNotEquals(autore.hashCode(), autoreDiverso.hashCode(), "L'HashCode di oggetti diversi dovrebbe essere diverso (probabilistico).");        
        assertNotEquals(0, autore.hashCode(), "L'HashCode non dovrebbe essere 0 per valori validi.");
    }

    // 5. TEST TOSTRING

    // Verifica che il metodo toString() produca una stringa non vuota contenente nome e cognome.
    @Test
    public void testToString() {
        String descrizione = autore.toString();

        assertNotNull(descrizione, "La stringa toString non deve essere null.");
        assertTrue(descrizione.contains("Mario"), "La descrizione toString deve contenere il nome.");
        assertTrue(descrizione.contains("Rossi"), "La descrizione toString deve contenere il cognome.");
    }
}
