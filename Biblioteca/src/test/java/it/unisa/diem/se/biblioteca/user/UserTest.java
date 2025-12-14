package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test di unità per la classe User.
 * Questa classe verifica la corretta inizializzazione, la mutazione dei dati, 
 * il rispetto delle regole di validazione nel costruttore e la logica di equals/hashCode 
 * basata sulla matricola (code) come identificatore unico.
 */
public class UserTest {

    private User user;

    /**
     * Metodo eseguito prima di ogni test.
     * Inizializza un oggetto User valido per i test.
     */
    @BeforeEach
    public void setUp() throws Exception {
        // La validazione (che si presume esista) richiede che i dati siano formattati correttamente.
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
    }

    // 1. TEST COSTRUTTORE E GETTERS

    // Verifica che il costruttore assegni correttamente tutti i campi iniziali.
    @Test
    public void testCostruttoreEGetter() {
        assertEquals("Mario", user.getName(), "Il nome non corrisponde.");
        assertEquals("Rossi", user.getSurname(), "Il cognome non corrisponde.");
        assertEquals("0123456789", user.getCode(), "La matricola non corrisponde.");
        assertEquals("m.rossi1@studenti.unisa.it", user.getEmail(), "L'email non corrisponde.");
        assertEquals(0, user.getLoanCount(), "Il contatore prestiti deve essere inizializzato a 0.");
    }

    // Verifica che il costruttore lanci InvalidUserException per matricola non valida (es. lunghezza errata).
    @Test
    public void testCostruttore_MatricolaInvalida() {
        assertThrows(InvalidUserException.class, () -> 
            new User("Mario", "Rossi", "123", "m.rossi1@studenti.unisa.it"),
            "Il costruttore deve lanciare un'eccezione per matricola troppo corta."
        );
    }

    // Verifica che il costruttore lanci InvalidUserException per email non istituzionale o formattata male.
    @Test
    public void testCostruttore_EmailInvalida() {
        assertThrows(InvalidUserException.class, () -> 
            new User("Mario", "Rossi", "0123456789", "email.sbagliata@gmail.com"),
            "Il costruttore deve lanciare un'eccezione per email non UNISA o errata."
        );
    }

    // Verifica che il costruttore lanci InvalidUserException per nome non valido (es. minuscolo iniziale).
    @Test
    public void testCostruttore_NomeInvalido() {
        assertThrows(InvalidUserException.class, () -> 
            new User("mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it"),
            "Il costruttore deve lanciare un'eccezione per nome che inizia con minuscola."
        );
    }

    // Verifica che il costruttore lanci InvalidUserException per cognome non valido (es. minuscolo iniziale).
    @Test
    public void testCostruttore_CognomeInvalido() {
        assertThrows(InvalidUserException.class, () -> 
            new User("Mario", "rossi", "0123456789", "m.rossi1@studenti.unisa.it"),
            "Il costruttore deve lanciare un'eccezione per cognome che inizia con minuscola."
        );
    }

    // 2. TEST SETTERS

    // Verifica che tutti i metodi setter aggiornino correttamente i rispettivi campi.
    @Test
    public void testSetters() {
        user.setName("Luigi");
        user.setSurname("Bianchi");
        user.setCode("9876543210");
        
        String newMail = "l.bianchi1@studenti.unisa.it";
        user.setEmail(newMail); 
        user.setLoanCount(2);

        assertEquals("Luigi", user.getName(), "Il nome non è stato aggiornato.");
        assertEquals("Bianchi", user.getSurname(), "Il cognome non è stato aggiornato.");
        assertEquals("9876543210", user.getCode(), "La matricola non è stata aggiornata.");
        assertEquals(newMail, user.getEmail(), "L'email non è stata aggiornata.");
        assertEquals(2, user.getLoanCount(), "Il contatore prestiti non è stato aggiornato.");
    }
    
    // Verifica che il setter setLoanCount accetti valori negativi (se non gestiti altrove dalla logica di business).
    @Test
    public void testSetLoanCount_Negative() {
        user.setLoanCount(-5);
        assertEquals(-5, user.getLoanCount(), "Il contatore prestiti deve accettare valori negativi (se non gestiti altrove).");
    }

    // 3. TEST COMPARE TO

    // Verifica che l'ordinamento (compareTo) sia basato primariamente sul cognome e secondariamente sul nome.
    @Test
    public void testCompareTo() throws Exception {
        User u1 = new User("Aldo", "Bianchi", "0000000001", "a.bianchi1@studenti.unisa.it");
        User u2 = new User("Bruno", "Verdi", "0000000002", "b.verdi1@studenti.unisa.it");
        
        assertTrue(u1.compareTo(u2) < 0, "Bianchi deve essere minore di Verdi.");
        assertTrue(u2.compareTo(u1) > 0, "Verdi deve essere maggiore di Bianchi.");

        User u3 = new User("Carlo", "Bianchi", "0000000003", "c.bianchi1@studenti.unisa.it");
        assertTrue(u1.compareTo(u3) < 0, "Aldo Bianchi deve essere minore di Carlo Bianchi.");

        User u4 = new User("Aldo", "Bianchi", "0000000009", "a.bianchi2@studenti.unisa.it");
        assertEquals(0, u1.compareTo(u4), "Utenti con stesso nome e cognome devono essere uguali nel compareTo.");
        assertEquals(0, user.compareTo(user), "Il confronto con se stesso deve essere 0.");
    }

    // 4. TEST EQUALS 

    // Verifica che l'uguaglianza (equals) sia basata UNICAMENTE sulla matricola (code).
    @Test
    public void testEquals() throws Exception {
        User copia = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        assertEquals(user, copia, "Utenti con stessa matricola devono essere uguali.");

        User diverso = new User("Mario", "Rossi", "9999999999", "m.rossi1@studenti.unisa.it");
        assertNotEquals(user, diverso, "Utenti con matricola diversa devono essere diversi.");
        
        User stessaMatricolaDatiDiversi = new User("Pippo", "Pluto", "0123456789", "p.pluto1@studenti.unisa.it");
        assertEquals(user, stessaMatricolaDatiDiversi, "Utenti con stessa matricola ma altri dati diversi devono essere uguali.");
        
        assertNotEquals(user, null, "Il confronto con null deve restituire false.");
        assertNotEquals(user, "Una Stringa", "Il confronto con classe diversa deve restituire false.");
    }

    // 5. TEST HASHCODE
    
    // Verifica che l'HashCode sia coerente con Equals (ovvero, basato sulla matricola).
    @Test
    public void testHashCode() throws Exception {
        User copia = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        assertEquals(user.hashCode(), copia.hashCode(), "HashCode deve essere lo stesso per oggetti uguali (stessa matricola).");
        
        User diverso = new User("Luigi", "Verdi", "9999999999", "l.verdi1@studenti.unisa.it");
        assertNotEquals(user.hashCode(), diverso.hashCode(), "HashCode deve essere diverso per matricole diverse.");
    }

    // 6. TEST TOSTRING

    // Verifica che il metodo toString() contenga tutti i dati chiave e segua il formato atteso.
    @Test
    public void testToString() {
        String s = user.toString();
        
        assertNotNull(s, "La stringa toString non deve essere null.");
        assertTrue(s.contains("Mario"), "La stringa deve contenere il nome.");
        assertTrue(s.contains("Rossi"), "La stringa deve contenere il cognome.");
        assertTrue(s.contains("0123456789"), "La stringa deve contenere la matricola.");
        assertTrue(s.contains("m.rossi1@studenti.unisa.it"), "La stringa deve contenere l'email.");
        assertTrue(s.contains("Utente: Mario Rossi | Matricola: 0123456789 | Email: m.rossi1@studenti.unisa.it"), "Il formato della stringa non è quello atteso.");
    }
}
