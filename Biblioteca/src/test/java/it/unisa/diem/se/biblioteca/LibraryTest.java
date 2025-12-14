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

/**
 * Test di unità per la classe Library.
 * Questa classe verifica il corretto funzionamento del pattern Singleton e i meccanismi 
 * di persistenza dei dati (serializzazione e deserializzazione).
 */
public class LibraryTest {

    // Nome del file temporaneo utilizzato per il salvataggio/caricamento nei test.
    private static final String TEST_FILE = "test_biblioteca.bin";

    /**
     * Metodo eseguito prima di ogni singolo test.
     * Garantisce che l'ambiente sia pulito e che una nuova istanza Library sia disponibile.
     */
    @BeforeEach
    public void setUp() {
        // Resettiamo l'istanza Singleton per isolare i test.
        Library.resetInstance();
        
        // Inizializziamo una nuova libreria vuota, associandola al file di test.
        Library.createNewLibrary(TEST_FILE);
    }

    /**
     * Metodo eseguito dopo ogni singolo test.
     * Si occupa della pulizia delle risorse, eliminando il file temporaneo.
     */    
    @AfterEach
    public void tearDown() {
        // Cancelliamo il file fisico generato durante il test.
        File f = new File(TEST_FILE);
        if (f.exists()) {
            f.delete();
        }

        // Resettiamo l'istanza Singleton in memoria.
        Library.resetInstance();
    }

    // 1. TEST INIZIALIZZAZIONE

    // Verifica che createNewLibrary crei un'istanza non nulla e che tutte le collezioni siano inizializzate.
    @Test
    public void testCreateNewLibrary() {
        Library lib = Library.getInstance();
        
        assertNotNull(lib, "L'istanza non dovrebbe essere null dopo la creazione.");
        assertNotNull(lib.getBooks(), "La collezione libri deve esistere ed essere inizializzata.");
        assertNotNull(lib.getUsers(), "La collezione utenti deve esistere ed essere inizializzata.");
        assertNotNull(lib.getLoans(), "La collezione prestiti deve esistere ed essere inizializzata.");
    }

    // 2. TEST SINGLETON PATTERN

    // Verifica che due chiamate consecutive a getInstance() restituiscano lo stesso oggetto in memoria.
    @Test
    public void testSingletonPattern() {
        Library lib1 = Library.getInstance();
        Library lib2 = Library.getInstance();

        assertSame(lib1, lib2, "Le due chiamate a getInstance() devono restituire la stessa istanza.");
    }

    // 3. TEST ECCEZIONE (Accesso senza inizializzazione)

    // Verifica che l'accesso a getInstance() prima che la libreria sia stata creata o caricata lanci un'eccezione.
    @Test
    public void testGetInstanceWithoutLoad() {
        Library.resetInstance();

        assertThrows(IllegalStateException.class, () -> {
            Library.getInstance();
        }, "L'accesso a getInstance() senza aver prima chiamato createNewLibrary o loadFromFile deve lanciare IllegalStateException.");
    }

    // 4. TEST SALVATAGGIO E CARICAMENTO 

    // Verifica che il salvataggio crei un file e che il caricamento riesca a deserializzare l'oggetto.
    @Test
    public void testSaveAndLoadFileCreation() throws IOException, ClassNotFoundException {
        Library libOriginale = Library.getInstance();
        libOriginale.save();

        File f = new File(TEST_FILE);
        assertTrue(f.exists(), "Il file di serializzazione dovrebbe essere stato creato dopo il salvataggio.");

        Library.resetInstance();

        Library.loadFromFile(TEST_FILE);
        assertNotNull(Library.getInstance(), "L'istanza della libreria deve essere ricaricata correttamente dopo il load.");
    }
    
    // 5. TEST CARICAMENTO FILE INESISTENTE

    // Verifica che il tentativo di caricare un file inesistente lanci una IOException.
    @Test
    public void testLoadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            Library.loadFromFile("file_inesistente_12345.bin");
        }, "Il caricamento da un file inesistente deve lanciare IOException.");
    }

    // 6. TEST SETTERS (Nuovi Casi aggiunti)

    // Verifica che i metodi setter permettano di sostituire le collezioni interne con nuove istanze.
    @Test
    public void testSetters() {
        Library lib = Library.getInstance();
        
        // Creiamo nuove collezioni vuote
        BooksCollection newBooks = new BooksCollection();
        UsersCollection newUsers = new UsersCollection();
        LoansCollection newLoans = new LoansCollection();
        
        lib.setBooks(newBooks);
        lib.setUsers(newUsers);
        lib.setLoans(newLoans);
        
        // Verifichiamo che i riferimenti siano corretti
        assertSame(newBooks, lib.getBooks(), "Il setter per BooksCollection non ha funzionato correttamente.");
        assertSame(newUsers, lib.getUsers(), "Il setter per UsersCollection non ha funzionato correttamente.");
        assertSame(newLoans, lib.getLoans(), "Il setter per LoansCollection non ha funzionato correttamente.");
    }

    // 7. TEST PERSISTENZA DATI REALE 

    // Verifica che i dati aggiunti (un utente), salvati e ricaricati siano effettivamente persistenti e corretti.
    @Test
    public void testDataPersistence() throws Exception {

        Library lib = Library.getInstance();
        
        User u = new User("Mario", "Rossi", "0123456789", "m.rossi1@studenti.unisa.it");
        lib.getUsers().addUser(u);
        
        assertEquals(1, lib.getUsers().getUsers().size(), "Prima del salvataggio, deve esserci 1 utente.");
        
        lib.save();
        
        Library.resetInstance();
        
        Library.loadFromFile(TEST_FILE);
        Library libCaricata = Library.getInstance();
        
        assertNotNull(libCaricata.getUsers().getUserByCode("0123456789"), "L'utente salvato deve essere ritrovato dopo il caricamento tramite matricola.");
        assertEquals("Mario", libCaricata.getUsers().getUserByCode("0123456789").getName(), "Il nome dell'utente ricaricato deve corrispondere all'originale.");
    }
}