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
        utente = new User("Mario", "Rossi", "0123456789", "m.rossi@studenti.unisa.it");
        
        List<Author> autori = new ArrayList<>();
        autori.add(new Author("Giovanni", "Verga"));
        libro = new Book("I Malavoglia", autori, "9781234567890", 1881);
        
        dataRestituzione = LocalDate.now().plusDays(30);

        prestito = new Loan(utente, libro, dataRestituzione);
    }

    // 1. TEST COSTRUTTORE E GETTERS

    @Test
    public void testCostruttoreEGetters() {
        assertNotNull(prestito);
        assertEquals(utente, prestito.getUser());
        assertEquals(libro, prestito.getBook());
        assertEquals(dataRestituzione, prestito.getReturnDate());
    }

    // 2. TEST SETTERS

    @Test
    public void testSetters() {
        LocalDate nuovaData = LocalDate.of(2030, 1, 1);
        prestito.setReturnDate(nuovaData);
        assertEquals(nuovaData, prestito.getReturnDate());

        User nuovoUtente = new User("Luigi", "Bianchi", "9876543210", "l.bianchi@test.it");
        prestito.setUser(nuovoUtente);
        assertEquals(nuovoUtente, prestito.getUser());
    }

    // 3. TEST COMPARE TO 

    @Test
    public void testCompareTo() {
        LocalDate ieri = LocalDate.now().minusDays(1);
        LocalDate domani = LocalDate.now().plusDays(1);

        Loan prestitoScaduto = new Loan(utente, libro, ieri);
        Loan prestitoFuturo = new Loan(utente, libro, domani);

        assertTrue(prestitoScaduto.compareTo(prestitoFuturo) < 0);
        
        assertTrue(prestitoFuturo.compareTo(prestitoScaduto) > 0);
        
        assertEquals(0, prestitoScaduto.compareTo(prestitoScaduto));
    }

    // 4. TEST EQUALS

    @Test
    public void testEquals() {
        Loan copiaEsatta = new Loan(utente, libro, dataRestituzione);
        assertEquals(prestito, copiaEsatta);

        Loan prestitoDiverso = new Loan(utente, libro, LocalDate.now());
        assertNotEquals(prestito, prestitoDiverso);
    }

    // 5. TEST TOSTRING
    
    @Test
    public void testToString() {
        String s = prestito.toString();
        assertNotNull(s);
        assertTrue(s.contains("I Malavoglia"));
        assertTrue(s.contains(dataRestituzione.toString()));
    }
}