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
    public void setUp() throws Exception {
        listaAutori = new ArrayList<>();
        listaAutori.add(new Author("Mario", "Rossi")); 
        
        libro = new Book("Java Manual", listaAutori, "9781234567890", 2024);
    }

    // 1. TEST COSTRUTTORE E GETTERS
    
    @Test
    public void testCostruttoreEGetter() {
        // Verifica immediata dei valori inseriti
        assertEquals("Java Manual", libro.getTitle());
        assertEquals("9781234567890", libro.getISBN());
        assertEquals(2024, libro.getPublishYear());
        assertEquals(listaAutori, libro.getAuthors());
    }

    // Test delle Eccezioni (InvalidBookException)
    // Utilizziamo assertThrows perchè è la modalità migliore in JUnit 5
    @Test
    public void testCostruttore_ISBNInvalido() {
        assertThrows(InvalidBookException.class, () -> 
            new Book("Titolo", listaAutori, "123", 2024)
        );
    }

    @Test
    public void testCostruttore_AnnoInvalido() {
        assertThrows(InvalidBookException.class, () -> 
            new Book("Titolo", listaAutori, "9781234567890", 3000)
        );
    }

    @Test
    public void testCostruttore_AutoreInvalido() {
        List<Author> autoriSbagliati = new ArrayList<>();
        autoriSbagliati.add(new Author("mario", "rossi")); 

        assertThrows(InvalidBookException.class, () -> 
            new Book("Titolo", autoriSbagliati, "9781234567890", 2024)
        );
    }

    // 2. TEST SETTERS
    
    @Test
    public void testSetters() {
        libro.setTitle("Nuovo Titolo");
        assertEquals("Nuovo Titolo", libro.getTitle());
    }

    // 3. TEST COMPARE TO

    @Test
    public void testCompareTo() throws Exception {
        // Creiamo due libri  per questo confronto
        // "Algoritmi" (A) vs "Database" (D)
        Book b1 = new Book("Algoritmi", listaAutori, "9781111111111", 2020);
        Book b2 = new Book("Database", listaAutori, "9782222222222", 2020);

        // A viene prima di D, quindi il risultato deve essere negativo (< 0)
        assertTrue(b1.compareTo(b2) < 0);
    }

    // 4. TEST EQUALS

    @Test
    public void testEquals() throws InvalidBookException {
        // Stesso ISBN del libro nel setUp -> Devono essere uguali
        Book libroCopia = new Book("Altro Titolo", listaAutori, "9781234567890", 2000);
        assertEquals(libro, libroCopia);
        
        // ISBN Diverso -> Devono essere diversi
        Book libroDiverso = new Book("Titolo", listaAutori, "9790000000000", 2020);
        assertNotEquals(libro, libroDiverso);
    }

    // 5. TEST TOSTRING

    @Test
    public void testToString() {
        String s = libro.toString();
        
        // Verifichiamo solo che non sia null e contenga i dati chiave
        assertNotNull(s);
        assertTrue(s.contains("Java Manual"));
        assertTrue(s.contains("9781234567890"));
    }
}