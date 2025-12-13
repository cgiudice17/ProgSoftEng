package it.unisa.diem.se.biblioteca.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {

    private Author autore;

    @BeforeEach
    public void setUp() {
        // Inizializziamo un autore base per i test
        autore = new Author("Mario", "Rossi");
    }

    // 1. TEST COSTRUTTORE E GETTERS

    @Test
    public void testCostruttoreEGetters() {
        assertNotNull(autore);
        assertEquals("Mario", autore.getName());
        assertEquals("Rossi", autore.getSurname());
    }

    // 2. TEST SETTERS

    @Test
    public void testSetters() {
        autore.setName("Luigi");
        autore.setSurname("Bianchi");

        assertEquals("Luigi", autore.getName());
        assertEquals("Bianchi", autore.getSurname());
    }

    // 3. TEST EQUALS

    @Test
    public void testEquals() {
        Author stessoAutore = new Author("Mario", "Rossi");
        Author autoreDiversoNome = new Author("Luigi", "Rossi");
        Author autoreDiversoCognome = new Author("Mario", "Verdi");

        // Riflessività e Simmetria
        assertEquals(autore, autore); // Se stesso
        assertEquals(autore, stessoAutore); // Stessi dati
        
        // Diversità
        assertNotEquals(autore, autoreDiversoNome);
        assertNotEquals(autore, autoreDiversoCognome);
        assertNotEquals(autore, null);
        assertNotEquals(autore, "Una Stringa");
    }

    // 4. TEST HASHCODE

    @Test
    public void testHashCode() {
        Author copiaAutore = new Author("Mario", "Rossi");

        // Se due oggetti sono uguali (equals true), devono avere lo stesso HashCode
        assertEquals(autore.hashCode(), copiaAutore.hashCode());
        
        // Verifica che l'hashcode non sia zero (probabilisticamente)
        assertNotEquals(0, autore.hashCode());
    }

    // 5. TEST TOSTRING

    @Test
    public void testToString() {
        String descrizione = autore.toString();

        assertNotNull(descrizione);
        assertTrue(descrizione.contains("Mario"));
        assertTrue(descrizione.contains("Rossi"));
    }
}