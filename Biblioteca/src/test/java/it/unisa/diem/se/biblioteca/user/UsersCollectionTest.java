package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

/**
 * Test di unità per la classe UsersCollection.
 * Questa classe verifica la corretta gestione del registro degli utenti, 
 * inclusa l'aggiunta, la rimozione, la ricerca per matricola e la garanzia dell'ordinamento 
 * tramite TreeSet basato su Nome e Cognome.
 */
public class UsersCollectionTest {

    private UsersCollection collection;
    private User user;
    private User altroUtente;
    private User utenteOrdinamento;

    /**
     * Metodo eseguito prima di ogni test.
     * Inizializza la collezione e crea oggetti User validi con matricole diverse e nomi/cognomi per l'ordinamento.
     */
    @BeforeEach
    public void setUp() throws Exception {
        collection = new UsersCollection();
        
        // Setup Utenti (con cognomi per testare l'ordinamento: Bianchi, Rossi, Verdi)
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it"); // Rossi
        altroUtente = new User("Luigi", "Verdi", "9876543210", "l.verdi1@studenti.unisa.it"); // Verdi
        utenteOrdinamento = new User("Aldo", "Bianchi", "1111111111", "a.bianchi1@studenti.unisa.it"); // Bianchi
    }

    // 1. TEST AGGIUNTA E RICERCA

    // Verifica la corretta aggiunta di un utente nel TreeSet e l'indicizzazione nella mappa per matricola.
    @Test
    public void testAddAndGetUser() {
        collection.addUser(user);
        
        assertTrue(collection.getUsers().contains(user), "Il Set deve contenere l'utente appena aggiunto.");
        assertEquals(user, collection.getUserByCode("0123456789"), "getUserByCode deve restituire l'utente corretto tramite matricola.");
        assertEquals(1, collection.getUsers().size(), "La dimensione della collezione deve essere 1.");
    }

    // 2. TEST DUPLICATI

    /**
     * Verifica come la collezione gestisce utenti con Nome/Cognome uguali ma Matricola diversa.
     * Poiché TreeSet usa compareTo (basato su Nome/Cognome), solo il primo elemento viene mantenuto.
     */ 
    @Test
    public void testAddDuplicateUser() throws Exception {
        User userOriginale = user; 
        User duplicatoNomeCognome = new User("Mario", "Rossi", "9999999999", "m.rossi2@studenti.unisa.it");
        
        collection.addUser(userOriginale);
        collection.addUser(duplicatoNomeCognome);
        
        assertEquals(1, collection.getUsers().size(), "Il TreeSet deve contenere 1 elemento perché compareTo (Nome/Cognome) è zero, trattandoli come duplicati.");

        User uDiversoMatricola = new User("Pippo", "Pluto", "0123456789", "p.pluto1@studenti.unisa.it");
        collection.addUser(uDiversoMatricola);

        assertEquals(2, collection.getUsers().size(), "L'aggiunta di un utente con Nome/Cognome diverso DEVE risultare in 2 elementi nel TreeSet.");
    }

    // 3. TEST RIMOZIONE

    // Verifica la corretta rimozione di un utente sia dal TreeSet che dalla mappa di ricerca.
    @Test
    public void testRemoveUser() {
        collection.addUser(user);
        collection.addUser(altroUtente);
        
        collection.removeUser(user);
        
        assertFalse(collection.getUsers().contains(user), "L'utente rimosso non deve essere nel Set.");
        assertNull(collection.getUserByCode("0123456789"), "L'utente rimosso non deve essere nella Map (ricerca per codice).");
        assertTrue(collection.getUsers().contains(altroUtente), "L'altro utente non deve essere rimosso.");
        assertEquals(1, collection.getUsers().size(), "La dimensione deve essere 1 dopo una rimozione.");
    }

    // 4. TEST RICERCA

    // Verifica la corretta funzionalità di ricerca per matricola (getUserByCode) per casi esistenti e inesistenti.
    @Test
    public void testGetUserByCode() {
        collection.addUser(user);
        collection.addUser(altroUtente);

        assertEquals(user, collection.getUserByCode("0123456789"), "La ricerca deve trovare Mario Rossi.");
        assertEquals(altroUtente, collection.getUserByCode("9876543210"), "La ricerca deve trovare Luigi Verdi.");
        assertNull(collection.getUserByCode("0000000000"), "La ricerca per codice inesistente deve restituire null.");
        assertNull(collection.getUserByCode(null), "La ricerca con codice null deve restituire null.");
    }

    // 5. TEST ECCEZIONI (Null handling)

    // Verifica che l'aggiunta di un utente nullo lanci NullPointerException.
    @Test
    public void testAddNullUser() {
        assertThrows(NullPointerException.class, () -> 
            collection.addUser(null), 
            "L'aggiunta di un utente null deve lanciare NullPointerException."
        );
    }

    // Verifica che la rimozione di un utente nullo lanci NullPointerException.
    @Test
    public void testRemoveNullUser() {
        assertThrows(NullPointerException.class, () -> 
            collection.removeUser(null), 
            "La rimozione di un utente null deve lanciare NullPointerException."
        );
    }

    // 6. TEST ORDINAMENTO (TreeSet)
    
    // Verifica che il TreeSet ordini gli utenti correttamente in base al cognome e poi al nome.
    @Test
    public void testOrdering() {
        collection.addUser(user); // Rossi
        collection.addUser(altroUtente); // Verdi
        collection.addUser(utenteOrdinamento); // Bianchi
        
        // Il TreeSet deve ordinare per cognome (Bianchi, Rossi, Verdi)
        Iterator<User> iterator = collection.getUsers().iterator();
        
        assertEquals(utenteOrdinamento, iterator.next(), "Il primo utente deve essere Bianchi (ordine alfabetico per cognome).");
        assertEquals(user, iterator.next(), "Il secondo utente deve essere Rossi.");
        assertEquals(altroUtente, iterator.next(), "Il terzo utente deve essere Verdi.");
    }
    
    // Verifica l'ordinamento secondario per nome quando i cognomi sono uguali.
    @Test
    public void testOrdering_StessoCognome() throws Exception {
        User u1 = new User("Aldo", "Rossi", "0000000001", "a.rossi1@studenti.unisa.it");
        User u2 = new User("Carlo", "Rossi", "0000000002", "c.rossi1@studenti.unisa.it");

        collection.addUser(u2); // Carlo Rossi
        collection.addUser(u1); // Aldo Rossi
        
        Iterator<User> iterator = collection.getUsers().iterator();
        
        assertEquals(u1, iterator.next(), "Il primo utente deve essere Aldo (ordine alfabetico per nome).");
        assertEquals(u2, iterator.next(), "Il secondo utente deve essere Carlo.");
    }

    // 7. TEST PRINT ALL
    
    // Verifica che il metodo printAll() restituisca una stringa formattata contenente tutti gli utenti.
    @Test
    public void testPrintAll() {
        collection.addUser(user);
        collection.addUser(altroUtente);
        
        String output = collection.printAll();
        
        assertNotNull(output, "La stringa printAll non deve essere null.");
        assertFalse(output.isEmpty(), "La stringa printAll non deve essere vuota.");
        assertTrue(output.contains("Mario"), "La stringa deve contenere il nome del primo utente.");
        assertTrue(output.contains("Luigi"), "La stringa deve contenere il nome dell'altro utente.");
        assertTrue(output.contains("Elenco Utenti:\n"), "La stringa deve iniziare con l'intestazione.");
    }
    
    // Verifica che printAll() restituisca il messaggio corretto se la collezione è vuota.
    @Test
    public void testPrintAll_CollezioneVuota() {
        String output = collection.printAll();
        
        assertNotNull(output, "La stringa printAll non deve essere null anche se vuota.");
        assertTrue(output.contains("Nessun utente registrato."), "Deve essere mostrato il messaggio per collezione vuota.");
    }
}
