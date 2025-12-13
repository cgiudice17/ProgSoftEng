package it.unisa.diem.se.biblioteca;

import it.unisa.diem.se.biblioteca.book.BooksCollection;
import it.unisa.diem.se.biblioteca.loan.LoansCollection;
import it.unisa.diem.se.biblioteca.user.User;
import it.unisa.diem.se.biblioteca.user.UsersCollection;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private static final String TEST_FILE = "test_biblioteca.bin";

    @BeforeEach
    public void setUp() {
        // 1. Resettiamo il Singleton
        Library.resetInstance();
        
        // 2. Inizializziamo una nuova libreria di test
        Library.createNewLibrary(TEST_FILE);
    }

    @AfterEach
    public void tearDown() {
        // 1. Cancelliamo il file fisico
        File f = new File(TEST_FILE);
        if (f.exists()) {
            f.delete();
        }

        // 2. Resettiamo di nuovo la memoria
        Library.resetInstance();
    }

    // 1. TEST INIZIALIZZAZIONE

    @Test
    public void testCreateNewLibrary() {
        Library lib = Library.getInstance();
        
        assertNotNull(lib, "L'istanza non dovrebbe essere null");
        assertNotNull(lib.getBooks(), "La lista libri deve esistere");
        assertNotNull(lib.getUsers(), "La lista utenti deve esistere");
        assertNotNull(lib.getLoans(), "La lista prestiti deve esistere");
    }

    // 2. TEST SINGLETON PATTERN

    @Test
    public void testSingletonPattern() {
        Library lib1 = Library.getInstance();
        Library lib2 = Library.getInstance();

        assertSame(lib1, lib2, "Deve restituire sempre la stessa istanza");
    }

    // 3. TEST ECCEZIONE (Accesso senza inizializzazione)

    @Test
    public void testGetInstanceWithoutLoad() {
        Library.resetInstance();

        assertThrows(IllegalStateException.class, () -> {
            Library.getInstance();
        });
    }

    // 4. TEST SALVATAGGIO E CARICAMENTO (File fisico)

    @Test
    public void testSaveAndLoadFileCreation() throws IOException, ClassNotFoundException {
        Library libOriginale = Library.getInstance();
        libOriginale.save();

        File f = new File(TEST_FILE);
        assertTrue(f.exists(), "Il file dovrebbe essere stato creato");

        Library.resetInstance();

        Library.loadFromFile(TEST_FILE);
        assertNotNull(Library.getInstance());
    }
    
    // 5. TEST CARICAMENTO FILE INESISTENTE
    
    @Test
    public void testLoadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            Library.loadFromFile("file_inesistente_12345.bin");
        });
    }

    // 6. TEST SETTERS (Nuovi Casi aggiunti)
    
    @Test
    public void testSetters() {
        Library lib = Library.getInstance();
        
        // Creiamo nuove collezioni vuote
        BooksCollection newBooks = new BooksCollection();
        UsersCollection newUsers = new UsersCollection();
        LoansCollection newLoans = new LoansCollection();
        
        // Usiamo i setter
        lib.setBooks(newBooks);
        lib.setUsers(newUsers);
        lib.setLoans(newLoans);
        
        // Verifichiamo che siano stati impostati correttamente
        assertSame(newBooks, lib.getBooks());
        assertSame(newUsers, lib.getUsers());
        assertSame(newLoans, lib.getLoans());
    }

    // 7. TEST PERSISTENZA DATI REALE (Nuovo Caso aggiunto)
    // Verifica che se aggiungo un utente, salvo e ricarico, l'utente c'è ancora.
    
    @Test
    public void testDataPersistence() throws Exception {
        // FASE A: Popolamento
        Library lib = Library.getInstance();
        // Attenzione: Usiamo un utente valido per non far fallire il test
        User u = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        lib.getUsers().addUser(u);
        
        // Verifica pre-salvataggio
        assertEquals(1, lib.getUsers().getUsers().size());
        
        // FASE B: Salvataggio
        lib.save();
        
        // FASE C: Reset totale (simulo chiusura app)
        Library.resetInstance();
        
        // FASE D: Ricaricamento
        Library.loadFromFile(TEST_FILE);
        Library libCaricata = Library.getInstance();
        
        // Verifica post-caricamento: L'utente deve esserci!
        assertNotNull(libCaricata.getUsers().getUserByCode("0123456789"));
        assertEquals("Mario", libCaricata.getUsers().getUserByCode("0123456789").getName());
    }
}