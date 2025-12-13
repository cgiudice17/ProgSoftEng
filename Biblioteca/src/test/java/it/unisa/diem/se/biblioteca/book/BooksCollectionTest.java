package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BooksCollectionTest {
    
    private BooksCollection collezione;
    private Book libro;
    private List<Author> listaAutori;

    @BeforeEach
    public void setUp() throws Exception {
        collezione = new BooksCollection();
        listaAutori = new ArrayList<>();
        listaAutori.add(new Author("Mario", "Rossi"));
        
        // Creiamo un libro valido base per i test
        libro = new Book("Libro Test", listaAutori, "9781234567890", 2020);
    }

    // 1. TEST GESTIONE LIBRI (AGGIUNTA, RIMOZIONE, RICERCA)

    @Test
    public void testAggiuntaLibro() {
        collezione.addBook(libro, 5);
        
        // Verifichiamo presenza nel Set, numero copie e mappa ISBN
        assertTrue(collezione.getBooks().contains(libro));
        assertEquals(5, collezione.getCopies(libro));
        assertEquals(libro, collezione.getBookByISBN("9781234567890"));
    }

    @Test
    public void testRimozioneLibro() {
        collezione.addBook(libro, 3);
        collezione.removeBook(libro);
        
        // Verifichiamo che sia sparito da tutto
        assertFalse(collezione.getBooks().contains(libro));
        assertNull(collezione.getBookByISBN("9781234567890"));
    }

    @Test
    public void testRemoveNullBook() {
        assertThrows(NullPointerException.class, () -> collezione.removeBook(null));
    }

    // 2. TEST GESTIONE COPIE

    @Test
    public void testAggiornamentoCopie() throws InvalidBookException {
        collezione.addBook(libro, 1);
        collezione.setCopies(libro, 10);
        
        assertEquals(10, collezione.getCopies(libro));
    }

    // 4. TEST ERRORI ED ECCEZIONI 

    @Test
    public void testAggiuntaNull() {
        assertThrows(NullPointerException.class, () -> 
            collezione.addBook(null, 5)
        );
    }

    @Test
    public void testRimozioneNull() {
        assertThrows(NullPointerException.class, () -> 
            collezione.removeBook(null)
        );
    }

    @Test
    public void testSetCopies_Negativo() {
        collezione.addBook(libro, 5);
        // Deve lanciare InvalidBookException (perché ValidBook rifiuta numeri <= 0)
        assertThrows(InvalidBookException.class, () -> 
            collezione.setCopies(libro, -5)
        );
    }
    
    @Test
    public void testSetCopies_NullBook() {
        assertThrows(NullPointerException.class, () -> 
            collezione.setCopies(null, 5)
        );
    }

    // 3. TEST STAMPA (PRINT ALL)

    @Test
    public void testPrintAll() {
        // Caso 1: Vuoto
        assertEquals("Il catalogo è vuoto.", collezione.printAll());
        
        // Caso 2: Pieno
        collezione.addBook(libro, 3);
        String risultato = collezione.printAll();
        
        assertNotNull(risultato);
        assertTrue(risultato.contains("Libro Test"));
        assertTrue(risultato.contains("9781234567890"));
        assertTrue(risultato.contains("Copie disponibili: 3"));
    }
}