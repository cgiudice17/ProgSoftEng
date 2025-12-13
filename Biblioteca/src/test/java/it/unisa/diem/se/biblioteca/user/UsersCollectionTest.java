package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsersCollectionTest {

    private UsersCollection collection;
    private User user;
    private User altroUtente;

    @BeforeEach
    public void setUp() throws Exception {
        collection = new UsersCollection();
        
        // Setup Utenti (con email valide)
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        altroUtente = new User("Luigi", "Verdi", "9876543210", "l.verdi1@studenti.unisa.it");
    }

    // 1. TEST AGGIUNTA E RICERCA

    @Test
    public void testAddAndGetUser() {
        collection.addUser(user);
        
        // Verifica presenza nel Set
        assertTrue(collection.getUsers().contains(user));
        
        // Verifica recupero tramite codice (Map)
        assertEquals(user, collection.getUserByCode("0123456789"));
        
        // Verifica dimensione
        assertEquals(1, collection.getUsers().size());
    }

    // 2. TEST DUPLICATI

    @Test
    public void testAddDuplicateUser() {
        collection.addUser(user);
        collection.addUser(user); // Aggiungo lo stesso utente una seconda volta
        
        // I Set non ammettono duplicati, la dimensione deve rimanere 1
        assertEquals(1, collection.getUsers().size());
    }

    // 3. TEST RIMOZIONE

    @Test
    public void testRemoveUser() {
        collection.addUser(user);
        collection.addUser(altroUtente);
        
        collection.removeUser(user);
        
        // 'user' non deve esserci più
        assertFalse(collection.getUsers().contains(user));
        assertNull(collection.getUserByCode("0123456789"));
        
        // 'altroUtente' deve ancora esserci
        assertTrue(collection.getUsers().contains(altroUtente));
        assertEquals(1, collection.getUsers().size());
    }

    @Test
    public void testRemoveNonExistentUser() {
        collection.addUser(user);
        
        // Provo a rimuovere un utente mai aggiunto
        collection.removeUser(altroUtente);
        
        // Non deve succedere nulla, 'user' deve rimanere
        assertEquals(1, collection.getUsers().size());
        assertTrue(collection.getUsers().contains(user));
    }

    // 4. TEST RICERCA

    @Test
    public void testGetUserByCode() {
        collection.addUser(user);
        collection.addUser(altroUtente);

        // Codici esistenti
        assertEquals(user, collection.getUserByCode("0123456789"));
        assertEquals(altroUtente, collection.getUserByCode("9876543210"));
        
        // Codice inesistente
        assertNull(collection.getUserByCode("0000000000"));
        
        // Codice null
        assertNull(collection.getUserByCode(null));
    }

    // 5. TEST ECCEZIONI (Null handling)

    @Test
    public void testAddNullUser() {
        assertThrows(NullPointerException.class, () -> collection.addUser(null));
    }

    @Test
    public void testRemoveNullUser() {
        assertThrows(NullPointerException.class, () -> collection.removeUser(null));
    }

    // 6. TEST PRINT ALL
    
    @Test
    public void testPrintAll() {
        collection.addUser(user);
        collection.addUser(altroUtente);
        
        String output = collection.printAll();
        
        assertNotNull(output, "La stringa non deve essere null");
        assertFalse(output.isEmpty(), "La stringa non deve essere vuota");
        assertTrue(output.contains("Mario"), "Deve contenere il nome del primo utente");
        assertTrue(output.contains("Luigi"), "Deve contenere il nome dell'altro utente");
    }
}