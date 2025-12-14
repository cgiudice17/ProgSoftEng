package it.unisa.diem.se.biblioteca.loan;

import it.unisa.diem.se.biblioteca.author.Author;
import it.unisa.diem.se.biblioteca.book.Book;
import it.unisa.diem.se.biblioteca.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    private Loan prestito;
    private User utente;
    private Book libro;
    private LocalDate dataRestituzione;

    @BeforeEach
    public void setUp() throws Exception {
        // CORRETTO: Aggiunto '1' all'email per passare la validazione
        utente = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        
        List<Author> autori = new ArrayList<>();
        autori.add(new Author("Giovanni", "Verga"));
        libro = new Book("I Malavoglia", autori, "9781234567890", 1881);
        
        dataRestituzione = LocalDate.now().plusDays(30);

        prestito = new Loan(utente, libro, dataRestituzione);
    }

    // 1. TEST COSTRUTTORE E GETTERS

    @Test
    public void testCostruttoreEGetters() {
        assertNotNull(prestito, "L'oggetto prestito non deve essere null.");
        assertEquals(utente, prestito.getUser(), "L'utente non corrisponde.");
        assertEquals(libro, prestito.getBook(), "Il libro non corrisponde.");
        assertEquals(dataRestituzione, prestito.getReturnDate(), "La data di restituzione non corrisponde.");
    }

    // 2. TEST SETTERS

    @Test
    public void testSetters() throws Exception{
        LocalDate nuovaData = LocalDate.of(2030, 1, 1);
        prestito.setReturnDate(nuovaData);
        assertEquals(nuovaData, prestito.getReturnDate(), "Il setter della data non ha funzionato.");

        // CORRETTO: Email valida (con numero '1' e dominio corretto)
        User nuovoUtente = new User("Luigi", "Bianchi", "9876543210", "l.bianchi1@studenti.unisa.it");
        prestito.setUser(nuovoUtente);
        assertEquals(nuovoUtente, prestito.getUser(), "Il setter dell'utente non ha funzionato.");
    }
    
    // 3. TEST COMPARE TO 

    @Test
    public void testCompareTo() {
        LocalDate ieri = LocalDate.now().minusDays(1);
        LocalDate domani = LocalDate.now().plusDays(1);
        
        Loan prestitoScaduto = new Loan(utente, libro, ieri);
        Loan prestitoFuturo = new Loan(utente, libro, domani);

        assertTrue(prestitoScaduto.compareTo(prestitoFuturo) < 0, "Una data passata deve essere 'minore' di una data futura.");
        
        assertTrue(prestitoFuturo.compareTo(prestitoScaduto) > 0, "Una data futura deve essere 'maggiore' di una data passata.");
        
        assertEquals(0, prestitoScaduto.compareTo(prestitoScaduto), "Il confronto tra prestiti con la stessa data deve restituire 0.");
    }

    @Test
    public void testCompareTo_DateUguali() {
        LocalDate stessaData = dataRestituzione;
        Loan prestitoUguale = new Loan(utente, libro, stessaData);
        
        // Confronto con se stesso e con prestito con stessa data
        assertEquals(0, prestito.compareTo(prestito), "Il confronto con se stesso deve restituire 0.");
        assertEquals(0, prestito.compareTo(prestitoUguale), "Il confronto tra prestiti con la stessa data deve restituire 0.");
    }

    // 4. TEST EQUALS

    @Test
    public void testEquals() {
        Loan copiaEsatta = new Loan(utente, libro, dataRestituzione);
        assertEquals(prestito, copiaEsatta, "Due prestiti con stessi campi devono essere uguali.");

        Loan prestitoDiverso = new Loan(utente, libro, LocalDate.now());
        assertNotEquals(prestito, prestitoDiverso, "Prestiti con date diverse non devono essere uguali.");
        
        // NUOVO: Test riflessività
        assertEquals(prestito, prestito, "Un oggetto deve essere uguale a se stesso.");
    }

    @Test
    public void testEquals_CasiDiDiversita() throws Exception {
        LocalDate dataDiversa = dataRestituzione.plusDays(1);
        User utenteDiverso = new User("Luigi", "Bianchi", "9876543210", "l.bianchi1@studenti.unisa.it");
        
        List<Author> autoriDiversi = new ArrayList<>();
        autoriDiversi.add(new Author("Italo", "Calvino"));
        Book libroDiverso = new Book("Il barone rampante", autoriDiversi, "9780000000001", 1957);

        Loan diversoSoloData = new Loan(utente, libro, dataDiversa);
        Loan diversoSoloUtente = new Loan(utenteDiverso, libro, dataRestituzione);
        Loan diversoSoloLibro = new Loan(utente, libroDiverso, dataRestituzione);

        assertNotEquals(prestito, diversoSoloData, "I prestiti devono essere diversi se la data è diversa.");
        assertNotEquals(prestito, diversoSoloUtente, "I prestiti devono essere diversi se l'utente è diverso.");
        assertNotEquals(prestito, diversoSoloLibro, "I prestiti devono essere diversi se il libro è diverso.");
        assertNotEquals(prestito, null, "Il confronto con null deve restituire false.");
        assertNotEquals(prestito, "Una stringa", "Il confronto con una classe diversa deve restituire false.");
    }

    // 5. TEST HASHCODE (METODO AGGIUNTO PER COERENZA)

    @Test
    public void testHashCode() throws Exception {
        Loan copiaEsatta = new Loan(utente, libro, dataRestituzione);
        
        // HashCode basato su utente, libro e data.
        assertEquals(prestito.hashCode(), copiaEsatta.hashCode(), "L'HashCode deve essere lo stesso per oggetti uguali.");
        
        Loan prestitoDiverso = new Loan(utente, libro, dataRestituzione.plusDays(1));
        assertNotEquals(prestito.hashCode(), prestitoDiverso.hashCode(), "L'HashCode deve essere diverso per prestiti diversi (data).");
    }
    // 6. TEST TOSTRING
    
    @Test
    public void testToString() {
        String s = prestito.toString();
        assertNotNull(s, "La stringa toString non deve essere null.");
        assertTrue(s.contains("I Malavoglia"), "La stringa deve contenere il titolo del libro.");
        assertTrue(s.contains(dataRestituzione.toString()), "La stringa deve contenere la data di restituzione.");
    }
}