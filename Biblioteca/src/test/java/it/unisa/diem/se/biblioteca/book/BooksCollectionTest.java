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
        
        // Creiamo un libro valido
        libro = new Book("Libro Test", listaAutori, "9781234567890", 2020);
    }

    @Test
    public void testAggiuntaLibro() {
        collezione.addBook(libro, 5);
        assertTrue(collezione.getBooks().contains(libro));
        assertEquals(5, collezione.getCopies(libro));
        assertEquals(libro, collezione.getBookByISBN("9781234567890"));
    }

    @Test
    public void testRimozioneLibro() {
        collezione.addBook(libro, 3);
        collezione.removeBook(libro);
        assertFalse(collezione.getBooks().contains(libro));
        assertNull(collezione.getBookByISBN("9781234567890"));
    }

    @Test
    public void testAggiornamentoCopie() throws InvalidBookException {
        collezione.addBook(libro, 1);
        collezione.setCopies(libro, 10);
        assertEquals(10, collezione.getCopies(libro));
    }

    @Test
    public void testAggiuntaNull() {
        assertThrows(NullPointerException.class, () -> collezione.addBook(null, 5));
    }

    @Test
    public void testSetCopiesNegativo() {
        collezione.addBook(libro, 5);
        assertThrows(InvalidBookException.class, () -> collezione.setCopies(libro, -5));
    }

    @Test
    public void testPrintAll() {
        System.out.println("Test: Verifica stampa catalogo");
        
        // Caso 1: Catalogo vuoto
        assertEquals("Il catalogo è vuoto.", collezione.printAll());
        
        // Caso 2: Catalogo con libri
        collezione.addBook(libro, 3);
        String risultato = collezione.printAll();
        
        // Verifichiamo che la stringa non sia null e contenga le parti richieste
        assertNotNull(risultato);
        assertTrue(risultato.contains("Libro Test"), "La stampa deve contenere il titolo");
        assertTrue(risultato.contains("9781234567890"), "La stampa deve contenere l'ISBN");
        assertTrue(risultato.contains("Copie disponibili: 3"), "La stampa deve contenere il numero di copie");
        
        System.out.println(risultato);
    }
}