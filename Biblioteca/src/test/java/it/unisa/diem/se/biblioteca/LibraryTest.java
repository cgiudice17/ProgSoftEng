package it.unisa.diem.se.biblioteca;

import it.unisa.diem.se.biblioteca.author.Author;
import it.unisa.diem.se.biblioteca.book.Book;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private final String TEST_FILENAME = "test_library_backup.bin";

    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    // 1. TEST INIZIALIZZAZIONE

    @Test
    public void testCostruttore() {
        Library lib = new Library();
        
        // Verifica che le collezioni siano state create e non siano null
        assertNotNull(lib.getBooks(), "La collezione libri non deve essere null");
        assertNotNull(lib.getUsers(), "La collezione utenti non deve essere null");
        assertNotNull(lib.getLoans(), "La collezione prestiti non deve essere null");
    }

    // 2. TEST SALVATAGGIO E CARICAMENTO

    @Test
    public void testSalvataggioECaricamento() throws Exception {

        Library libOriginale = new Library();
        
        // Creiamo un libro e aggiungiamolo alla libreria originale
        List<Author> autori = new ArrayList<>();
        autori.add(new Author("Mario", "Rossi"));
        Book libro = new Book("Libro Test", autori, "9781234567890", 2020);
        
        libOriginale.getBooks().addBook(libro, 5);
        
        // Salviamo lo stato su file
        libOriginale.writeObj(TEST_FILENAME);
        
        // Verifica che il file sia stato creato fisicamente
        File file = new File(TEST_FILENAME);
        assertTrue(file.exists(), "Il file di salvataggio deve esistere");
        
        // Creiamo una NUOVA istanza caricando dal file
        Library libCaricata = Library.readObj(TEST_FILENAME);
        
        // Verifica
        assertNotNull(libCaricata, "La libreria caricata non deve essere null");
        
        // Controlliamo che il libro ci sia ancora
        assertTrue(libCaricata.getBooks().getBooks().contains(libro), 
                   "Il libro salvato deve essere presente dopo il caricamento");
                   
        // Controlliamo anche i dettagli (es. copie)
        assertEquals(5, libCaricata.getBooks().getCopies(libro), 
                     "Il numero di copie deve essere preservato");
    }

    // 3. TEST CASI LIMITE (FILE MANCANTE)

    @Test
    public void testCaricamentoFileInesistente() {
        // File che non esiste
        Library lib = Library.readObj("file_che_non_esiste.bin");
        
        assertNotNull(lib);
        assertNotNull(lib.getBooks());
        // Deve essere vuota
        assertTrue(lib.getBooks().getBooks().isEmpty());
    }
}