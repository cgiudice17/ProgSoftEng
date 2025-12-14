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

/**  
 * Test di unità per la classe LoansCollection.
 * Questa classe verifica la gestione della collezione di prestiti, inclusi l'aggiunta, 
 * la rimozione, la ricerca per utente e la corretta applicazione del limite massimo di prestiti.
 */
public class LoansCollectionTest {

    private LoansCollection collection;
    private User altroUtente;
    private User user;
    private Book book;
    private Book book2;
    private Loan loan;

    /**
     * Metodo eseguito prima di ogni test.
     * Inizializza la collezione e crea oggetti User, Book e Loan validi.
     */ 
    @BeforeEach
    public void setUp() throws Exception {
        collection = new LoansCollection();
        
        // Creazione di Utenti validi
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        altroUtente = new User("Luigi", "Verdi", "9876543210", "l.verdi1@studenti.unisa.it");
        
        // Assicuriamo che i contatori siano resettati
        user.setLoanCount(0);
        altroUtente.setLoanCount(0);
        
        // Creazione di Libri validi
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Autore", "Test"));
        book = new Book("Titolo Test", authors, "9781234567890", 2020);
        
        List<Author> authors2 = new ArrayList<>();
        authors2.add(new Author("Autore", "Due"));
        book2 = new Book("Titolo Due", authors2, "9781111111111", 2021);
        
        // Creazione del prestito base
        loan = new Loan(user, book, LocalDate.now().plusDays(30));
    }

    // 1. TEST AGGIUNTA PRESTITO
    
    // Verifica l'aggiunta di un prestito unico e il corretto aggiornamento del contatore prestiti dell'utente.
    @Test
    public void testAddLoan_Successo() {
        int result = collection.addLoan(loan);
        
        assertEquals(0, result, "Il risultato dell'aggiunta deve essere 0 (Successo).");
        assertTrue(collection.getLoans().contains(loan), "Il prestito deve essere nella collezione globale.");
        assertTrue(collection.getLoansByUser(user).contains(loan), "Il prestito deve essere nella lista dell'utente.");
        assertEquals(1, user.getLoanCount(), "Il contatore prestiti dell'utente deve essere 1.");
    }
    
    // Verifica l'aggiunta di un prestito che è un duplicato (stesso Utente + stesso Libro), 
    // che dovrebbe essere respinto e restituire il codice 2.
    @Test
    public void testAddLoan_Duplicato() {
        collection.addLoan(loan);
        
        Loan loanDuplicato = new Loan(user, book, LocalDate.now().plusDays(60)); 
        int result = collection.addLoan(loanDuplicato);
        
        assertEquals(2, result, "L'aggiunta di un prestito duplicato (stesso Utente/Libro) deve restituire 2."); 
        assertEquals(1, user.getLoanCount(), "Il contatore non deve aumentare dopo l'inserimento duplicato.");
        assertEquals(1, collection.getLoans().size(), "La collezione globale non deve contenere duplicati (1 elemento).");
    }

    // Verifica che l'aggiunta di un prestito nullo lanci NullPointerException.
    @Test
    public void testAddNullLoan() {
        assertThrows(NullPointerException.class, () -> 
            collection.addLoan(null),
            "L'aggiunta di un prestito nullo deve lanciare NullPointerException."
        );
    }

    // 2. TEST LIMITE PRESTITI (MAX 3)
    
    // Verifica l'aggiunta di 3 prestiti unici e il tentativo fallito di aggiungere il quarto, che deve restituire il codice 1.
    @Test
    public void testMaxLoansLimit() throws Exception { 
        Loan loan1 = new Loan(user, book, LocalDate.now().plusDays(1)); 
        collection.addLoan(loan1); 
        
        Loan loan2 = new Loan(user, book2, LocalDate.now().plusDays(2));
        collection.addLoan(loan2); 
        
        List<Author> authors3 = new ArrayList<>();
        authors3.add(new Author("Autore", "Tre"));
        Book book3 = new Book("Titolo Tre", authors3, "9782222222222", 2022);
        Loan loan3 = new Loan(user, book3, LocalDate.now().plusDays(3));
        collection.addLoan(loan3); 
        
        assertEquals(3, user.getLoanCount(), "Il contatore deve essere 3 (limite massimo).");

        List<Author> authorsExtra = new ArrayList<>();
        authorsExtra.add(new Author("Autore", "Extra"));
        Book bookExtra = new Book("Titolo Extra", authorsExtra, "9780000000000", 2023);
        Loan loanExtra = new Loan(user, bookExtra, LocalDate.now().plusDays(100)); 
        
        int result = collection.addLoan(loanExtra);
        
        assertEquals(1, result, "L'aggiunta deve restituire 1 (Limite raggiunto)."); // 1 = Limite raggiunto
        assertEquals(3, user.getLoanCount(), "Il contatore non deve aumentare oltre il limite."); 
        
        assertFalse(collection.getLoans().contains(loanExtra), "Il prestito extra non deve essere aggiunto alla collezione globale.");
        assertEquals(3, collection.getLoansByUser(user).size(), "La lista utente non deve avere più di 3 elementi.");
    }

    // 3. TEST RIMOZIONE
    
    // Verifica la corretta rimozione di un prestito dalla collezione e il decremento del contatore utente.
    @Test
    public void testRemoveLoan() {
        collection.addLoan(loan);
        assertEquals(1, user.getLoanCount(), "Il contatore deve essere 1 prima della rimozione.");

        collection.removeLoan(loan);
        
        assertFalse(collection.getLoans().contains(loan), "Il prestito deve essere rimosso dalla collezione globale.");
        assertFalse(collection.getLoansByUser(user).contains(loan), "Il prestito deve essere rimosso dalla lista dell'utente.");
        assertEquals(0, user.getLoanCount(), "Il contatore deve scendere a 0."); 
    }
    
    // Verifica che la rimozione di un prestito nullo non causi errori.
    @Test
    public void testRemoveNullLoan() {
        assertDoesNotThrow(() -> 
            collection.removeLoan(null),
            "La rimozione di un prestito nullo non deve lanciare eccezioni."
        );
    }
    
    // Verifica che la rimozione di un prestito in una lista con più elementi funzioni correttamente.
    @Test
    public void testRemoveLoan_PrestitiMultipli() {
        Loan loan2 = new Loan(user, book2, LocalDate.now().plusDays(40));
        collection.addLoan(loan);
        collection.addLoan(loan2);
        assertEquals(2, user.getLoanCount(), "Il contatore deve essere 2.");

        collection.removeLoan(loan);
        
        assertEquals(1, user.getLoanCount(), "Il contatore deve essere 1 dopo la rimozione del primo prestito."); 
        assertTrue(collection.getLoans().contains(loan2), "Il secondo prestito deve rimanere nella collezione.");
        assertTrue(collection.getLoansByUser(user).contains(loan2), "Il secondo prestito deve rimanere nella lista utente.");
    }
    
    // Verifica che il contatore non diventi negativo se il prestito rimosso è già assente o se il contatore è già a zero.
    @Test
    public void testRemoveLoan_UtenteSenzaPrestiti() {
        collection.removeLoan(loan);
        
        assertEquals(0, user.getLoanCount(), "Il contatore non deve diventare negativo se era già zero (o non è stato incrementato).");
    }

    // 5. TEST GET LOANS BY USER
    
    // Verifica che la ricerca dei prestiti per utente restituisca la lista corretta o una lista vuota.
    @Test
    public void testGetLoansByUser() {
        collection.addLoan(loan);
        List<Loan> userLoans = collection.getLoansByUser(user);
        assertNotNull(userLoans, "La lista prestiti utente non deve essere null.");
        assertEquals(1, userLoans.size(), "La lista prestiti utente deve contenere 1 elemento.");

        List<Loan> emptyLoans = collection.getLoansByUser(altroUtente);
        assertNotNull(emptyLoans, "La lista restituita per utente senza prestiti non deve essere null.");
        assertTrue(emptyLoans.isEmpty(), "La lista restituita per utente senza prestiti deve essere vuota.");
    }
    
    // 6. TEST METODI ACCESSORI (getLoans)
    
    // Verifica che il getter per la collezione globale restituisca tutti i prestiti attivi.
    @Test
    public void testGetLoans() {
        assertTrue(collection.getLoans().isEmpty(), "La collezione globale deve essere vuota inizialmente.");
        
        Loan loan2 = new Loan(altroUtente, book, LocalDate.now().plusDays(10));
        collection.addLoan(loan);
        collection.addLoan(loan2);
        
        assertEquals(2, collection.getLoans().size(), "La collezione globale deve contenere 2 prestiti.");
        assertTrue(collection.getLoans().contains(loan), "La collezione globale deve contenere il primo prestito.");
        assertTrue(collection.getLoans().contains(loan2), "La collezione globale deve contenere il secondo prestito.");
    }
    
    // 7. TEST METODI ACCESSORI (getMaxLoans)
    
    // Verifica che la costante MAX_LOANS sia impostata correttamente (uguale a 3).
    @Test
    public void testGetMaxLoans() {
        assertEquals(3, collection.getMaxLoans(), "Il limite massimo di prestiti deve essere 3.");
    }
}
