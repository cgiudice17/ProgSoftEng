package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book libro;
    private List<Author> listaAutori;

    @BeforeEach
    public void setUp() {
        // Prepariamo i dati
        listaAutori = new ArrayList<>();
        // Nota: se non hai ancora la classe Author, puoi lasciare la lista vuota o null per ora
        // listaAutori.add(new Author("Mario", "Rossi")); 
        
        // Creiamo il libro
        libro = new Book("Java Manual", listaAutori, "123-456", 2024);
    }

    // --- TEST CHE PASSERANNO (VERDI) ---
    
    @Test
    public void testCostruttoreEGetter() {
        System.out.println("Test 1: Verifica Costruttore");
        assertEquals("Java Manual", libro.getTitle());
        assertEquals("123-456", libro.getISBN());
        assertEquals(2024, libro.getPublishYear());
    }

    @Test
    public void testSetters() {
        System.out.println("Test 2: Verifica Setters");
        libro.setTitle("Nuovo Titolo");
        libro.setPublishYear(1999);
        
        assertEquals("Nuovo Titolo", libro.getTitle());
        assertEquals(1999, libro.getPublishYear());
    }
    
    @Test
    public void testEquals() {
        System.out.println("Test 3: Verifica Uguaglianza (ISBN)");
        Book libroCopia = new Book("Altro Titolo", listaAutori, "123-456", 2000);
        assertEquals(libro, libroCopia, "Devono essere uguali se l'ISBN è uguale");
    }

    // --- IL TEST "TRAPPOLA" CHE FALLIRÀ (ROSSO) ---
    
    @Test
    public void testToString() {
        System.out.println("Test 4: Verifica toString (Caccia all'errore)");
        
        // Eseguiamo il metodo toString del tuo codice
        String descrizione = libro.toString();
        
        // VERIFICA 1: Non deve MAI essere null (questo farà fallire il test!)
        assertNotNull(descrizione, "ERRORE GRAVE: Il metodo toString() ha restituito NULL!");
        
        // VERIFICA 2: Deve contenere i dati del libro
        assertTrue(descrizione.contains("Java Manual"), "La stampa deve contenere il titolo");
        assertTrue(descrizione.contains("123-456"), "La stampa deve contenere l'ISBN");
        assertTrue(descrizione.contains("2024"), "La stampa deve contenere l'anno");
    }
}