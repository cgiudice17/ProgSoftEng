package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** 
 *  Test di unità per la classe BooksCollection.
 * Questa classe verifica la corretta gestione del catalogo dei libri, 
 * inclusa l'aggiunta, la rimozione, la gestione delle copie e la ricerca per ISBN.
 */
public class BooksCollectionTest {
    
    private BooksCollection collezione;
    private Book libro;
    private List<Author> listaAutori;

    // Metodo eseguito prima di ogni test.
    // Inizializza una BooksCollection vuota e un oggetto Book valido per i test.
    @BeforeEach
    public void setUp() throws Exception {
        collezione = new BooksCollection();
        listaAutori = new ArrayList<>();
        listaAutori.add(new Author("Mario", "Rossi"));
        
        // Creiamo un libro valido base per i test
        libro = new Book("Libro Test", listaAutori, "9781234567890", 2020);
    }

    // 1. TEST GESTIONE LIBRI (AGGIUNTA, RIMOZIONE, RICERCA)

    /** 
     * Verifica che l'aggiunta di un libro lo inserisca correttamente nel Set, nella mappa ISBN 
     * e imposti il numero iniziale di copie.
     */
    @Test
    public void testAggiuntaLibro() {
        collezione.addBook(libro, 5);
        
        assertTrue(collezione.getBooks().contains(libro), "Il libro deve essere presente nella collezione globale (Set).");
        assertEquals(5, collezione.getCopies(libro), "Il numero di copie deve corrispondere a quello aggiunto.");
        assertEquals(libro, collezione.getBookByISBN("9781234567890"), "Il libro deve essere recuperabile tramite ISBN.");
    }

    // Verifica che la rimozione di un libro lo elimini da tutte le strutture dati interne.
    @Test
    public void testRimozioneLibro() {
        collezione.addBook(libro, 3);
        collezione.removeBook(libro);
        
        assertFalse(collezione.getBooks().contains(libro), "Il libro deve essere rimosso dalla collezione globale.");
        assertNull(collezione.getBookByISBN("9781234567890"), "Il libro non deve essere più recuperabile tramite ISBN.");
    }

    // 2. TEST GESTIONE COPIE

    // Verifica che il metodo setCopies aggiorni correttamente il numero di copie di un libro esistente.
    @Test
    public void testAggiornamentoCopie() throws InvalidBookException {
        collezione.addBook(libro, 1);
        collezione.setCopies(libro, 10);
        
        assertEquals(10, collezione.getCopies(libro), "Il numero di copie deve essere aggiornato a 10.");
    }

    // 4. TEST ERRORI ED ECCEZIONI 

    // Verifica che l'aggiunta di un oggetto Book nullo causi una NullPointerException.
    @Test
    public void testAggiuntaNull() {
        assertThrows(NullPointerException.class, () -> 
            collezione.addBook(null, 5),
            "L'aggiunta di un libro nullo deve lanciare NullPointerException."
        );
    }

    // Verifica che la rimozione di un oggetto Book nullo causi una NullPointerException.
    @Test
    public void testRimozioneNull() {
        assertThrows(NullPointerException.class, () -> 
            collezione.removeBook(null),
            "La rimozione di un libro nullo deve lanciare NullPointerException."
        );
    }

    // Verifica che il tentativo di impostare un numero negativo di copie (non valido) lanci InvalidBookException.
    @Test
    public void testSetCopies_Negativo() {
        collezione.addBook(libro, 5);
        assertThrows(InvalidBookException.class, () -> 
            collezione.setCopies(libro, -5),
            "Il tentativo di impostare copie negative (-5) deve lanciare InvalidBookException."
        );
    }
    
    // Verifica che il tentativo di impostare copie per un libro non esistente/nullo lanci NullPointerException.
    @Test
    public void testSetCopies_NullBook() {
        assertThrows(NullPointerException.class, () -> 
            collezione.setCopies(null, 5),
            "Il tentativo di impostare copie per un libro nullo deve lanciare NullPointerException."
        );
    }

    // 3. TEST STAMPA (PRINT ALL)

    // Verifica che il metodo printAll() restituisca la rappresentazione corretta del catalogo.
    @Test
    public void testPrintAll() {
        assertEquals("Il catalogo è vuoto.", collezione.printAll(), "La collezione vuota deve restituire il messaggio di catalogo vuoto.");
        
        collezione.addBook(libro, 3);
        String risultato = collezione.printAll();
        
        assertNotNull(risultato, "La stringa di stampa non deve essere null.");
        assertTrue(risultato.contains("Libro Test"), "La stampa deve contenere il titolo del libro.");
        assertTrue(risultato.contains("9781234567890"), "La stampa deve contenere l'ISBN del libro.");
        assertTrue(risultato.contains("Copie disponibili: 3"), "La stampa deve indicare le copie disponibili.");
    }
}
