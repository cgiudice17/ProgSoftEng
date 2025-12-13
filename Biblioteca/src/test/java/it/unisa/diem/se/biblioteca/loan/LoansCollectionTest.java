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
    private User user;
    private User altroUtente; // Aggiunto per i test multi-utente
    private Book book;
    private Loan loan;

    @BeforeEach
    public void setUp() throws Exception {
        collection = new LoansCollection();
        
        // Setup Utenti
        user = new User("Mario", "Rossi", "0123456789", "m.rossi@test.it");
        altroUtente = new User("Luigi", "Verdi", "9876543210", "l.verdi@test.it");
        
        // Setup Libro
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Autore", "Test"));
        book = new Book("Titolo Test", authors, "9781234567890", 2020);
        
        // Setup Prestito base
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
        // IMPORTANTE: Usiamo date diverse per ogni prestito.
        // Poiché Loan usa compareTo basato sulla data, se usassimo sempre LocalDate.now(),
        // il TreeSet li considererebbe duplicati e il test fallirebbe.
        
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(1)));
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(2)));
        collection.addLoan(new Loan(user, book, LocalDate.now().plusDays(3)));
        
        assertEquals(3, user.getLoanCount());

        // 4° prestito -> Deve fallire (usiamo una data molto diversa per sicurezza)
        Loan loanExtra = new Loan(user, book, LocalDate.now().plusDays(100));
        int result = collection.addLoan(loanExtra);
        
        assertEquals(1, result); // 1 = Errore (Limite raggiunto)
        assertEquals(3, user.getLoanCount()); // Non deve aumentare
        assertFalse(collection.getLoans().contains(loanExtra));
    }

    // 3. TEST RIMOZIONE (STANDARD E CASI LIMITE)

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