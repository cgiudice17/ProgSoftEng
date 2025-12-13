package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        // CORRETTO: Aggiunto '1' dopo il cognome per rispettare la regex
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
    }

    // 1. TEST COSTRUTTORE E GETTERS

    @Test
    public void testCostruttoreEGetter() {
        assertEquals("Mario", user.getName());
        assertEquals("Rossi", user.getSurname());
        assertEquals("0123456789", user.getCode());
        assertEquals("m.rossi1@studenti.unisa.it", user.getEmail());
        assertEquals(0, user.getLoanCount());
    }

    @Test
    public void testCostruttore_MatricolaInvalida() {
        assertThrows(InvalidUserException.class, () -> 
            new User("Mario", "Rossi", "123", "m.rossi1@studenti.unisa.it")
        );
    }

    @Test
    public void testCostruttore_EmailInvalida() {
        // Email non istituzionale o formato errato
        assertThrows(InvalidUserException.class, () -> 
            new User("Mario", "Rossi", "0123456789", "email.sbagliata@gmail.com")
        );
    }

    @Test
    public void testCostruttore_NomeInvalido() {
        assertThrows(InvalidUserException.class, () -> 
            new User("mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it")
        );
    }

    // 2. TEST SETTERS

    @Test
    public void testSetters() {
        user.setName("Luigi");
        user.setSurname("Bianchi");
        user.setCode("9876543210");
        
        // CORRETTO: Email valida con numero
        String newMail = "l.bianchi1@studenti.unisa.it";
        user.setEmail(newMail); 
        user.setLoanCount(2);

        assertEquals("Luigi", user.getName());
        assertEquals("Bianchi", user.getSurname());
        assertEquals("9876543210", user.getCode());
        assertEquals(newMail, user.getEmail());
        assertEquals(2, user.getLoanCount());
    }

    // 3. TEST COMPARE TO

    @Test
    public void testCompareTo() throws Exception {
        // CORRETTO: Tutte le email hanno il numero '1'
        User u1 = new User("Aldo", "Bianchi", "0000000001", "a.bianchi1@studenti.unisa.it");
        User u2 = new User("Bruno", "Verdi", "0000000002", "b.verdi1@studenti.unisa.it");
        
        // Minore (< 0): Bianchi viene prima di Verdi
        assertTrue(u1.compareTo(u2) < 0);
        
        // Maggiore (> 0): Verdi viene dopo Bianchi
        assertTrue(u2.compareTo(u1) > 0);

        // Stesso Cognome, Nomi diversi ("Aldo" prima di "Carlo")
        User u3 = new User("Carlo", "Bianchi", "0000000003", "c.bianchi1@studenti.unisa.it");
        assertTrue(u1.compareTo(u3) < 0);

        // Uguale (== 0): Stesso Nome e Cognome
        User u4 = new User("Aldo", "Bianchi", "0000000009", "a.bianchi2@studenti.unisa.it");
        assertEquals(0, u1.compareTo(u4));
    }

    // 4. TEST EQUALS E HASHCODE

    @Test
    public void testEquals() throws Exception {
        // Stessa matricola -> Uguali (Email corretta con '1')
        User copia = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        assertEquals(user, copia);

        // Matricola diversa -> Diversi
        User diverso = new User("Mario", "Rossi", "9999999999", "m.rossi1@studenti.unisa.it");
        assertNotEquals(user, diverso);
    }

    @Test
    public void testHashCode() throws Exception {
        User copia = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        
        assertEquals(user.hashCode(), copia.hashCode());
        
        User diverso = new User("Luigi", "Verdi", "9999999999", "l.verdi1@studenti.unisa.it");
        assertNotEquals(user.hashCode(), diverso.hashCode());
    }

    // 5. TEST TOSTRING

    @Test
    public void testToString() {
        String s = user.toString();
        
        assertNotNull(s);
        assertTrue(s.contains("Mario"));
        assertTrue(s.contains("0123456789"));
    }
}