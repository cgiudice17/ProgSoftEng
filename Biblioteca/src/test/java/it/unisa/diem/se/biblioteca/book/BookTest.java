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
        // Usa "Mario" e "Rossi" con maiuscole perché ValidBook lo richiede
        listaAutori.add(new Author("Mario", "Rossi")); 
        
        // Usa un ISBN valido (inizia con 978 e ha 13 cifre)
        libro = new Book("Java Manual", listaAutori, "9781234567890", 2024);
    }

    @Test
    public void testCostruttoreEGetter() {
        System.out.println("Test: Costruttore e Getter");
        assertEquals("Java Manual", libro.getTitle());
        assertEquals("9781234567890", libro.getISBN());
        assertEquals(2024, libro.getPublishYear());
    }

    @Test
    public void testEquals() throws InvalidBookException {
        System.out.println("Test: Equals");
        Book libroCopia = new Book("Altro Titolo", listaAutori, "9781234567890", 2000);
        assertEquals(libro, libroCopia);
    }

    // TEST NEGATIVI (Controllo che lanci InvalidBookException)

    @Test
    public void testISBNInvalido() {
        System.out.println("Test: ISBN non valido");
        // ISBN troppo corto o senza prefisso 978
        assertThrows(InvalidBookException.class, () -> {
            new Book("Titolo", listaAutori, "123", 2024);
        });
    }

    @Test
    public void testAnnoInvalido() {
        System.out.println("Test: Anno futuro");
        // Anno 3000
        assertThrows(InvalidBookException.class, () -> {
            new Book("Titolo", listaAutori, "9781234567890", 3000);
        });
    }

    @Test
    public void testAutoreInvalido() {
        System.out.println("Test: Autore minuscolo");
        List<Author> autoriSbagliati = new ArrayList<>();
        autoriSbagliati.add(new Author("mario", "rossi")); // Minuscolo -> Errore

        assertThrows(InvalidBookException.class, () -> {
            new Book("Titolo", autoriSbagliati, "9781234567890", 2024);
        });
    }
}