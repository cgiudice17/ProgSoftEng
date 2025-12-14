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

public class LoansCollectionTest {

    private LoansCollection collection;
    private User altroUtente;
    private User user;
    private Book book;
    private Loan loan;

    @BeforeEach
    public void setUp() throws Exception {
        collection = new LoansCollection();
        
        // CORRETTO: Email valide per entrambi gli utenti
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        altroUtente = new User("Luigi", "Verdi", "9876543210", "l.verdi1@studenti.unisa.it");
        
        // Assicuriamoci che gli utenti partano con 0 prestiti (per sicurezza)
        user.setLoanCount(0);
        altroUtente.setLoanCount(0);
        
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Autore", "Test"));
        book = new Book("Titolo Test", authors, "9781234567890", 2020);
        
        loan = new Loan(user, book, LocalDate.now().plusDays(30));
    }

    // 1. TEST AGGIUNTA PRESTITO

    @Test
    public void testAddLoan() {
        int result = collection.addLoan(loan);
        
        assertEquals(0, result, "Il risultato dell'aggiunta deve essere 0 (Successo)."); // 0 = Successo
        assertTrue(collection.getLoans().contains(loan), "Il prestito deve essere nella collezione globale.");
        assertTrue(collection.getLoansByUser(user).contains(loan), "Il prestito deve essere nella lista dell'utente.");
        assertEquals(1, user.getLoanCount(), "Il contatore prestiti dell'utente deve essere 1."); // Il contatore utente deve aumentare
    }
    
    @Test
    public void testAddNullLoan() {
        // Dato che addLoan chiama l.getUser(), un Loan nullo deve lanciare NullPointerException
        assertThrows(NullPointerException.class, () -> 
            collection.addLoan(null),
            "L'aggiunta di un prestito nullo deve lanciare NullPointerException."
        );
    }


    // 2. TEST LIMITE PRESTITI (MAX 3)

    @Test
    public void testMaxLoansLimit() {
        
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(1)));
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(2)));
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(3)));
        
        assertEquals(3, user.getLoanCount(), "Il contatore deve essere 3 (limite massimo).");

        Loan loanExtra = new Loan(user, book, LocalDate.now().plusDays(100));
        int result = collection.addLoan(loanExtra);
        
        assertEquals(1, result, "L'aggiunta deve restituire 1 (Limite raggiunto)."); // 1 = Errore (Limite raggiunto)
        assertEquals(3, user.getLoanCount(), "Il contatore non deve aumentare oltre il limite."); 
        assertFalse(collection.getLoans().contains(loanExtra), "Il prestito extra non deve essere aggiunto alla collezione globale.");
    }

    // 3. TEST RIMOZIONE

    @Test
    public void testRemoveLoan() {
        collection.addLoan(loan);
        assertEquals(1, user.getLoanCount(), "Il contatore deve essere 1 prima della rimozione.");

        collection.removeLoan(loan);
        
        assertFalse(collection.getLoans().contains(loan), "Il prestito deve essere rimosso dalla collezione globale.");
        assertFalse(collection.getLoansByUser(user).contains(loan), "Il prestito deve essere rimosso dalla lista dell'utente.");
        assertEquals(0, user.getLoanCount(), "Il contatore deve scendere a 0."); 
    }
    
    @Test
    public void testRemoveNullLoan() {
        // La tua implementazione ha un 'if (l == null) return;' quindi non lancia eccezioni.
        assertDoesNotThrow(() -> 
            collection.removeLoan(null),
            "La rimozione di un prestito nullo non deve lanciare eccezioni."
        );
    }
    
    @Test
    public void testRemoveLoan_PrestitiMultipli() {
        // Aggiungo due prestiti
        Loan loan2 = new Loan(user, book, LocalDate.now().plusDays(40));
        collection.addLoan(loan);
        collection.addLoan(loan2);
        assertEquals(2, user.getLoanCount(), "Il contatore deve essere 2.");

        // Rimuovo il primo
        collection.removeLoan(loan);
        
        assertEquals(1, user.getLoanCount(), "Il contatore deve essere 1 dopo la rimozione del primo prestito."); 
        assertTrue(collection.getLoans().contains(loan2), "Il secondo prestito deve rimanere nella collezione.");
        assertTrue(collection.getLoansByUser(user).contains(loan2), "Il secondo prestito deve rimanere nella lista utente.");
    }
    
    @Test
    public void testRemoveLoan_UtenteSenzaPrestiti() {
        // Test di sicurezza: rimuovo un prestito da un utente con count=0
        user.setLoanCount(0);
        collection.removeLoan(loan);
        
        assertEquals(0, user.getLoanCount(), "Il contatore non deve diventare negativo se era già zero.");
    }

    // 5. TEST GET LOANS BY USER

    @Test
    public void testGetLoansByUser() {
        // Caso utente con prestiti
        collection.addLoan(loan);
        List<Loan> userLoans = collection.getLoansByUser(user);
        assertNotNull(userLoans, "La lista prestiti utente non deve essere null.");
        assertEquals(1, userLoans.size(), "La lista prestiti utente deve contenere 1 elemento.");

        // Caso utente senza prestiti (usiamo l'altro utente inizializzato nel setup)
        List<Loan> emptyLoans = collection.getLoansByUser(altroUtente);
        assertNotNull(emptyLoans, "La lista restituita per utente senza prestiti non deve essere null.");
        assertTrue(emptyLoans.isEmpty(), "La lista restituita per utente senza prestiti deve essere vuota.");
    }
    
    // 6. TEST METODI ACCESSORI (getLoans)
    
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
}