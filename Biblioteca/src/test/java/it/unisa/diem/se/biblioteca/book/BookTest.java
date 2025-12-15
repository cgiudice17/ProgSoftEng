package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** 
 * Test di unità per la classe Book.
 * Questa classe verifica il corretto funzionamento del costruttore, dei getter, dei setter, 
 * e garantisce che i metodi equals, hashCode e compareTo si basino correttamente sull'ISBN (come identificatore unico).
 */
public class BookTest {

    private Book libro;
    private List<Author> listaAutori;

    /** 
     * Metodo eseguito prima di ogni test.
     * Inizializza un oggetto Book valido con dati standard.
     */
    @BeforeEach
    public void setUp() throws Exception {
        listaAutori = new ArrayList<>();
        listaAutori.add(new Author("Mario", "Rossi"));    
        
        // Creazione di un libro base valido
        libro = new Book("Java Manual", listaAutori, "9781234567890", 2024);
    }

    // 1. TEST COSTRUTTORE E GETTERS
    
    // Verifica che il costruttore assegni correttamente tutti i valori iniziali tramite i getter.
    @Test
    public void testCostruttoreEGetter() {
  
        assertEquals("Java Manual", libro.getTitle());
        assertEquals("9781234567890", libro.getISBN());
        assertEquals(2024, libro.getPublishYear());
        assertEquals(listaAutori, libro.getAuthors());
    }

    /**
     * Test delle Eccezioni (InvalidBookException)
     * Verifica che il costruttore lanci l'eccezione se l'ISBN non è valido.
     */
    @Test
    public void testCostruttore_ISBNInvalido() {
        assertThrows(InvalidBookException.class, () -> 
            new Book("Titolo", listaAutori, "123", 2024)
        );
    }

    // Verifica che il costruttore lanci l'eccezione se l'anno è nel futuro (invalido).
    @Test
    public void testCostruttore_AnnoInvalido() {
        assertThrows(InvalidBookException.class, () -> 
            new Book("Titolo", listaAutori, "9781234567890", 3000)
        );
    }

    // Verifica che un anno molto vecchio (come 0) non causi InvalidBookExceptio.
    @Test
    public void testCostruttore_AnnoZero() {
        assertDoesNotThrow(() ->
            new Book("Titolo", listaAutori, "9781234567890", 0),
            "Anno 0 deve essere accettato da ValidYear (se la validazione non impone limiti inferiori)."
        );
    }

    // Verifica che il costruttore lanci l'eccezione se la lista autori contiene dati non validi.
    @Test
    public void testCostruttore_AutoreInvalido() {
        List<Author> autoriSbagliati = new ArrayList<>();
        autoriSbagliati.add(new Author("mario", "rossi")); 
        assertThrows(InvalidBookException.class, () -> 
            new Book("Titolo", autoriSbagliati, "9781234567890", 2024)
        );
    }

    // 2. TEST SETTERS
    
    // Verifica che i metodi setter modifichino correttamente le proprietà mutabili del libro.
    @Test
    public void testSetters() {
        libro.setTitle("Nuovo Titolo");
        assertEquals("Nuovo Titolo", libro.getTitle());
        
        List<Author> nuovaLista = new ArrayList<>();
        nuovaLista.add(new Author("Aldo", "Bianchi"));
        libro.setAuthors(nuovaLista);
        assertEquals(nuovaLista, libro.getAuthors());
    }

    // 3. TEST COMPARE TO
    
    // Verifica che l'ordinamento (compareTo) si basi sul titolo del libro e che un titolo minore restituisca < 0.
    @Test
    public void testCompareTo_Minore() throws Exception {
        Book b1 = new Book("Algoritmi", listaAutori, "9781111111111", 2020);
        Book b2 = new Book("Database", listaAutori, "9782222222222", 2020);
      
        assertTrue(b1.compareTo(b2) < 0, "B1 deve essere minore di B2 (ordine alfabetico del titolo)");
    }
    
    // Verifica che l'ordinamento (compareTo) si basi sul titolo e che un titolo maggiore restituisca > 0.
    @Test
    public void testCompareTo_Maggiore() throws Exception {
        Book b1 = new Book("Algoritmi", listaAutori, "9781111111111", 2020);
        Book b2 = new Book("Database", listaAutori, "9782222222222", 2020);
       
        assertTrue(b2.compareTo(b1) > 0, "B2 deve essere maggiore di B1");
    }

    // Verifica che l'ordinamento (compareTo) ignori ISBN/Anno e che titoli uguali restituiscano 0.
    @Test
    public void testCompareTo_Uguale() throws Exception {
        Book b1 = new Book("Algoritmi", listaAutori, "9781111111111", 2020);
        Book b3 = new Book("Algoritmi", listaAutori, "9783333333333", 2021); 
       
        assertTrue(b1.compareTo(b3) == 0, "Titoli uguali devono dare 0"); 
        assertEquals(0, libro.compareTo(libro), "Il confronto con se stesso deve dare 0.");
    }
    
    // 4. TEST EQUALS

    // Verifica che l'uguaglianza (equals) sia basata UNICAMENTE sull'ISBN.
    @Test
    public void testEquals() throws InvalidBookException {
        Book libroCopia = new Book("Altro Titolo", listaAutori, "9781234567890", 2000); 
        assertEquals(libro, libroCopia, "Libri con lo stesso ISBN devono essere uguali.");
        
        Book libroDiverso = new Book("Titolo", listaAutori, "9790000000000", 2020);
        
        assertNotEquals(libro, libroDiverso, "Libri con ISBN diversi non devono essere uguali.");
        assertEquals(libro, libro, "Uguaglianza riflessiva.");
        assertNotEquals(libro, null, "Confronto con null.");
        assertNotEquals(libro, "Una Stringa", "Confronto con oggetti di classe diversa.");
    }
    
    // 5. TEST HASHCODE 

    // Verifica che HashCode sia coerente con Equals (ovvero, basato sull'ISBN).
    @Test
    public void testHashCode() throws InvalidBookException {
        Book libroCopia = new Book("Altro Titolo", listaAutori, "9781234567890", 2000);
        Book libroDiverso = new Book("Titolo", listaAutori, "9790000000000", 2020);

        assertEquals(libro.hashCode(), libroCopia.hashCode(), "L'HashCode deve essere lo stesso per libri con lo stesso ISBN.");
        assertNotEquals(libro.hashCode(), libroDiverso.hashCode(), "L'HashCode di libri con ISBN diversi dovrebbe essere diverso (probabilistico).");
    }

    // 6. TEST TOSTRING 

    // Verifica che il metodo toString() produca una stringa non vuota e contenente i dati chiave del libro.
    @Test
    public void testToString() {
        String s = libro.toString();
        
        assertNotNull(s, "La stringa non deve essere null");
        assertTrue(s.contains("Java Manual"), "Deve contenere il titolo");
        assertTrue(s.contains("9781234567890"), "Deve contenere l'ISBN");
        assertTrue(s.contains("2024"), "Deve contenere l'anno di pubblicazione");
    }
}
