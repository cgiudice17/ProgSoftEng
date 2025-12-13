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
        
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Autore", "Test"));
        book = new Book("Titolo Test", authors, "9781234567890", 2020);
        
        loan = new Loan(user, book, LocalDate.now().plusDays(30));
    }

    // 1. TEST AGGIUNTA PRESTITO

    @Test
    public void testAddLoan() {
        int result = collection.addLoan(loan);
        
        assertEquals(0, result); // 0 = Successo
        assertTrue(collection.getLoans().contains(loan));
        assertTrue(collection.getLoansByUser(user).contains(loan));
        assertEquals(1, user.getLoanCount()); // Il contatore utente deve aumentare
    }

    // 2. TEST LIMITE PRESTITI (MAX 3)

    @Test
    public void testMaxLoansLimit() {
        
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(1)));
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(2)));
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(3)));
        
        assertEquals(3, user.getLoanCount());

        Loan loanExtra = new Loan(user, book, LocalDate.now().plusDays(100));
        int result = collection.addLoan(loanExtra);
        
        assertEquals(1, result); // 1 = Errore (Limite raggiunto)
        assertEquals(3, user.getLoanCount()); // Non deve aumentare
        assertFalse(collection.getLoans().contains(loanExtra));
    }

    // 3. TEST RIMOZIONE

    @Test
    public void testRemoveLoan() {
        collection.addLoan(loan);
        assertEquals(1, user.getLoanCount());

        collection.removeLoan(loan);
        
        assertFalse(collection.getLoans().contains(loan));
        assertFalse(collection.getLoansByUser(user).contains(loan));
        assertEquals(0, user.getLoanCount()); // Il contatore deve scendere
    }
    
    @Test
    public void testRemoveNullLoan() {
        // Non deve lanciare eccezioni
        collection.removeLoan(null);
        assertTrue(collection.getLoans().isEmpty());
    }

    // 5. TEST GET LOANS BY USER

    @Test
    public void testGetLoansByUser() {
        // Caso utente con prestiti
        collection.addLoan(loan);
        List<Loan> userLoans = collection.getLoansByUser(user);
        assertNotNull(userLoans);
        assertEquals(1, userLoans.size());

        // Caso utente senza prestiti (usiamo l'altro utente inizializzato nel setup)
        List<Loan> emptyLoans = collection.getLoansByUser(altroUtente);
        assertNotNull(emptyLoans);
        assertTrue(emptyLoans.isEmpty());
    }
}