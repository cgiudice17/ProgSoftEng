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
    public void testCostruttore_AnnoZero() {
        // Test per anno tecnicamente valido ma molto vecchio
        assertDoesNotThrow(() ->
            new Book("Titolo", listaAutori, "9781234567890", 0),
            "Anno 0 deve essere accettato da ValidYear"
        );
    }

    @Test
    public void testCostruttore_AutoreInvalido() {
        List<Author> autoriSbagliati = new ArrayList<>();
        // Assumiamo che "mario" non sia valido
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
        
        // Rafforzamento: Test su lista autori e ISBN/Anno (se i setter esistono)
        // Nota: Assumo che non ci siano setter per ISBN/Anno
        List<Author> nuovaLista = new ArrayList<>();
        nuovaLista.add(new Author("Aldo", "Bianchi"));
        libro.setAuthors(nuovaLista);
        assertEquals(nuovaLista, libro.getAuthors());
    }

    // 3. TEST COMPARE TO

    @Test
    public void testCompareTo_Minore() throws Exception {
        // "Algoritmi" (A) vs "Database" (D) -> A viene prima, risultato < 0
        Book b1 = new Book("Algoritmi", listaAutori, "9781111111111", 2020);
        Book b2 = new Book("Database", listaAutori, "9782222222222", 2020);
        assertTrue(b1.compareTo(b2) < 0, "B1 deve essere minore di B2 (ordine alfabetico)");
    }
    
    @Test
    public void testCompareTo_Maggiore() throws Exception {
        // "Database" (D) vs "Algoritmi" (A) -> D viene dopo, risultato > 0
        Book b1 = new Book("Algoritmi", listaAutori, "9781111111111", 2020);
        Book b2 = new Book("Database", listaAutori, "9782222222222", 2020);
        assertTrue(b2.compareTo(b1) > 0, "B2 deve essere maggiore di B1");
    }

    @Test
    public void testCompareTo_Uguale() throws Exception {
        // Titoli uguali -> risultato = 0
        Book b1 = new Book("Algoritmi", listaAutori, "9781111111111", 2020);
        Book b3 = new Book("Algoritmi", listaAutori, "9783333333333", 2021); // ISBN/Anno diversi non contano
        assertTrue(b1.compareTo(b3) == 0, "Titoli uguali devono dare 0");
        
        // Rafforzamento
        assertEquals(0, libro.compareTo(libro), "Il confronto con se stesso deve dare 0.");
    }
    // 4. TEST EQUALS

    @Test
    public void testEquals() throws InvalidBookException {
        // Stesso ISBN del libro nel setUp -> Devono essere uguali
        Book libroCopia = new Book("Altro Titolo", listaAutori, "9781234567890", 2000); // Titolo/Anno/Autori diversi, stesso ISBN
        assertEquals(libro, libroCopia, "Libri con lo stesso ISBN devono essere uguali.");
        
        // ISBN Diverso -> Devono essere diversi
        Book libroDiverso = new Book("Titolo", listaAutori, "9790000000000", 2020);
        assertNotEquals(libro, libroDiverso, "Libri con ISBN diversi non devono essere uguali.");
        
        // Rafforzamento: Riflessività, Null e Oggetti diversi
        assertEquals(libro, libro, "Uguaglianza riflessiva.");
        assertNotEquals(libro, null, "Confronto con null.");
        assertNotEquals(libro, "Una Stringa", "Confronto con oggetti di classe diversa.");
    }
    
    // 5. TEST HASHCODE 
    @Test
    public void testHashCode() throws InvalidBookException {
        Book libroCopia = new Book("Altro Titolo", listaAutori, "9781234567890", 2000);
        Book libroDiverso = new Book("Titolo", listaAutori, "9790000000000", 2020);

        // Se due oggetti sono uguali (stesso ISBN), devono avere lo stesso HashCode
        assertEquals(libro.hashCode(), libroCopia.hashCode(), "L'HashCode deve essere lo stesso per libri con lo stesso ISBN.");
        
        // HashCode diverso per oggetti diversi
        assertNotEquals(libro.hashCode(), libroDiverso.hashCode(), "L'HashCode di libri con ISBN diversi dovrebbe essere diverso (probabilistico).");
    }


    // 6. TEST TOSTRING 

    @Test
    public void testToString() {
        String s = libro.toString();
        
        // Verifichiamo solo che non sia null e contenga i dati chiave
        assertNotNull(s, "La stringa non deve essere null");
        assertTrue(s.contains("Java Manual"), "Deve contenere il titolo");
        assertTrue(s.contains("9781234567890"), "Deve contenere l'ISBN");
        assertTrue(s.contains("2024"), "Deve contenere l'anno di pubblicazione");
    }
}