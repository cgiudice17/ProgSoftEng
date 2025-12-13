package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsersCollectionTest {

    private UsersCollection collection;
    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        collection = new UsersCollection();
        // CORRETTO: Aggiunto '1' all'email per rispettare la validazione
        user = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
    }

    // 1. TEST AGGIUNTA E RICERCA

    @Test
    public void testAddAndGetUser() {
        collection.addUser(user);
        
        assertTrue(collection.getUsers().contains(user));
        assertEquals(user, collection.getUserByCode("0123456789"));
    }

    // 2. TEST RIMOZIONE

    @Test
    public void testRemoveUser() {
        collection.addUser(user);
        collection.removeUser(user);
        
        assertFalse(collection.getUsers().contains(user));
        assertNull(collection.getUserByCode("0123456789"));
    }

    @Test
    public void testRemoveNonExistentUser() {
        collection.removeUser(user);
        assertTrue(collection.getUsers().isEmpty());
    }

    // 3. TEST NULL

    @Test
    public void testAddNullUser() {
        assertThrows(NullPointerException.class, () -> collection.addUser(null));
    }

    @Test
    public void testRemoveNullUser() {
        assertThrows(NullPointerException.class, () -> collection.removeUser(null));
    }
}