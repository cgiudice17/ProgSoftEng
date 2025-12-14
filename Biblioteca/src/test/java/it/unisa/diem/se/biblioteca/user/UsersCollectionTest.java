package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

public class UsersCollectionTest {

    private UsersCollection collection;
    private User user;
    private User altroUtente;
    private User utenteOrdinamento;

    @BeforeEach
    public void setUp() throws Exception {
        collection = new UsersCollection();
        
        // Setup Utenti (con email valide)
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it"); // Rossi
        altroUtente = new User("Luigi", "Verdi", "9876543210", "l.verdi1@studenti.unisa.it"); // Verdi
        utenteOrdinamento = new User("Aldo", "Bianchi", "1111111111", "a.bianchi1@studenti.unisa.it"); // Bianchi
    }

    // 1. TEST AGGIUNTA E RICERCA

    @Test
    public void testAddAndGetUser() {
        collection.addUser(user);
        
        // Verifica presenza nel Set
        assertTrue(collection.getUsers().contains(user), "Il Set deve contenere l'utente appena aggiunto.");
        
        // Verifica recupero tramite codice (Map)
        assertEquals(user, collection.getUserByCode("0123456789"), "getUserByCode deve restituire l'utente corretto tramite matricola.");
        
        // Verifica dimensione
        assertEquals(1, collection.getUsers().size(), "La dimensione della collezione deve essere 1.");
    }

    // 2. TEST DUPLICATI

    @Test
    public void testAddDuplicateUser() throws Exception {
        // CREIAMO UN UTENTE LOGICAMENTE DIVERSO MA CON STESSO NOME/COGNOME
        User userOriginale = user; // Mario Rossi, Matricola 0123456789
        
        // Utente duplicato nel TreeSet (stesso Nome/Cognome), ma con matricola diversa.
        User duplicatoNomeCognome = new User("Mario", "Rossi", "9999999999", "m.rossi2@studenti.unisa.it");
        
        collection.addUser(userOriginale);
        collection.addUser(duplicatoNomeCognome);
        
        // RISULTATO ATTESO DALLA TUA IMPLEMENTAZIONE (compareTo su Nome/Cognome):
        assertEquals(1, collection.getUsers().size(), "Il TreeSet deve contenere 1 elemento perché gli utenti hanno lo stesso Nome/Cognome (comparati come duplicati).");

        // Per testare l'integrità del SET (TreeSet) con la logica compareTo (Nome/Cognome):
        User uDiversoMatricola = new User("Pippo", "Pluto", "0123456789", "p.pluto1@studenti.unisa.it");
        collection.addUser(uDiversoMatricola);

        // Poiché "Pippo Pluto" ha un Nome/Cognome diverso da "Mario Rossi", compareTo != 0.
        assertEquals(2, collection.getUsers().size(), "L'aggiunta di un utente con stessa matricola ma Nome/Cognome diverso DEVE risultare in 2 elementi nel TreeSet.");
    }

    // 3. TEST RIMOZIONE

    @Test
    public void testRemoveUser() {
        collection.addUser(user);
        collection.addUser(altroUtente);
        
        collection.removeUser(user);
        
        // 'user' non deve esserci più
        assertFalse(collection.getUsers().contains(user), "L'utente rimosso non deve essere nel Set.");
        assertNull(collection.getUserByCode("0123456789"), "L'utente rimosso non deve essere nella Map (ricerca per codice).");
        
        // 'altroUtente' deve ancora esserci
        assertTrue(collection.getUsers().contains(altroUtente), "L'altro utente non deve essere rimosso.");
        assertEquals(1, collection.getUsers().size(), "La dimensione deve essere 1 dopo una rimozione.");
    }

    // 4. TEST RICERCA

    @Test
    public void testGetUserByCode() {
        collection.addUser(user);
        collection.addUser(altroUtente);

        // Codici esistenti
        assertEquals(user, collection.getUserByCode("0123456789"), "La ricerca deve trovare Mario Rossi.");
        assertEquals(altroUtente, collection.getUserByCode("9876543210"), "La ricerca deve trovare Luigi Verdi.");
        
        // Codice inesistente
        assertNull(collection.getUserByCode("0000000000"), "La ricerca per codice inesistente deve restituire null.");
        
        // Codice null
        assertNull(collection.getUserByCode(null), "La ricerca con codice null deve restituire null.");
    }

    // 5. TEST ECCEZIONI (Null handling)

    @Test
    public void testAddNullUser() {
        assertThrows(NullPointerException.class, () -> 
            collection.addUser(null), 
            "L'aggiunta di un utente null deve lanciare NullPointerException."
        );
    }

    @Test
    public void testRemoveNullUser() {
        assertThrows(NullPointerException.class, () -> 
            collection.removeUser(null), 
            "La rimozione di un utente null deve lanciare NullPointerException."
        );
    }

    // 6. TEST ORDINAMENTO (TreeSet)
    
    @Test
    public void testOrdering() {
        collection.addUser(user); // Rossi
        collection.addUser(altroUtente); // Verdi
        collection.addUser(utenteOrdinamento); // Bianchi
        
        // Il TreeSet deve ordinare per cognome (Bianchi, Rossi, Verdi)
        Iterator<User> iterator = collection.getUsers().iterator();
        
        // 1. Bianchi
        assertEquals(utenteOrdinamento, iterator.next(), "Il primo utente deve essere Bianchi (ordine alfabetico per cognome).");
        
        // 2. Rossi
        assertEquals(user, iterator.next(), "Il secondo utente deve essere Rossi.");
        
        // 3. Verdi
        assertEquals(altroUtente, iterator.next(), "Il terzo utente deve essere Verdi.");
    }
    
    @Test
    public void testOrdering_StessoCognome() throws Exception {
        User u1 = new User("Aldo", "Rossi", "0000000001", "a.rossi1@studenti.unisa.it");
        User u2 = new User("Carlo", "Rossi", "0000000002", "c.rossi1@studenti.unisa.it");

        collection.addUser(u2); // Carlo Rossi
        collection.addUser(u1); // Aldo Rossi
        
        // Se il cognome è uguale, deve ordinare per nome (Aldo, Carlo)
        Iterator<User> iterator = collection.getUsers().iterator();
        
        // 1. Aldo
        assertEquals(u1, iterator.next(), "Il primo utente deve essere Aldo (ordine alfabetico per nome).");
        
        // 2. Carlo
        assertEquals(u2, iterator.next(), "Il secondo utente deve essere Carlo.");
    }

    // 7. TEST PRINT ALL
    
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
    
    @Test
    public void testPrintAll_CollezioneVuota() {
        String output = collection.printAll();
        
        assertNotNull(output, "La stringa printAll non deve essere null anche se vuota.");
        assertTrue(output.contains("Nessun utente registrato."), "Deve essere mostrato il messaggio per collezione vuota.");
    }

}