package it.unisa.diem.se.biblioteca;

import it.unisa.diem.se.biblioteca.author.Author;
import it.unisa.diem.se.biblioteca.book.Book;
import it.unisa.diem.se.biblioteca.book.BooksCollection;
import it.unisa.diem.se.biblioteca.loan.LoansCollection;
import it.unisa.diem.se.biblioteca.user.UsersCollection;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private final String TEST_FILENAME = "test_library_backup.bin";
    private Library lib;

    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    // 1. TEST COSTRUTTORE E GETTERS

    @Test
    public void testCostruttoreEGetters() {
        lib = new Library();

        assertNotNull(lib);
        assertNotNull(lib.getBooks());
        assertNotNull(lib.getUsers());
        assertNotNull(lib.getLoans());
        
        assertTrue(lib.getBooks().getBooks().isEmpty());
    }

    // 2. TEST SETTERS

    @Test
    public void testSetters() {
        lib = new Library();
        
        BooksCollection newBooks = new BooksCollection();
        UsersCollection newUsers = new UsersCollection();
        LoansCollection newLoans = new LoansCollection();

        lib.setBooks(newBooks);
        lib.setUsers(newUsers);
        lib.setLoans(newLoans);

        assertSame(newBooks, lib.getBooks());
        assertSame(newUsers, lib.getUsers());
        assertSame(newLoans, lib.getLoans());
    }

    // 3. SALVATAGGIO E CARICAMENTO

    @Test
    public void testSalvataggioECaricamento() throws Exception {
        Library libOriginale = new Library();
        
        // Creazione dati
        List<Author> autori = new ArrayList<>();
        autori.add(new Author("Mario", "Rossi"));
        Book libroTest = new Book("Libro Test", autori, "9781234567890", 2020);
        libOriginale.getBooks().addBook(libroTest, 5);

        // Salvataggio
        libOriginale.writeObj(TEST_FILENAME);
        assertTrue(new File(TEST_FILENAME).exists());

        // Caricamento e Verifica
        Library libCaricata = Library.readObj(TEST_FILENAME);
        
        assertNotNull(libCaricata);
        assertTrue(libCaricata.getBooks().getBooks().contains(libroTest));
        assertEquals(5, libCaricata.getBooks().getCopies(libroTest));
    }

    // 4. TEST CASI LIMITE (

    @Test
    public void testCaricamentoFileInesistente() {
        Library libVuota = Library.readObj("file_inesistente.bin");

        assertNotNull(libVuota);
        assertNotNull(libVuota.getBooks());
        assertTrue(libVuota.getBooks().getBooks().isEmpty());
    }
}