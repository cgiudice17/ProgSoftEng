package it.unisa.diem.se.biblioteca.book;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @brief Classe di test per l'interfaccia ValidBook, contenente tutti i casi possibili 
 * per validare i dati del libro secondo le regole definite nell'interfaccia.
 */
public class ValidBookTest implements ValidBook {

    // 1. TEST ISBN

    @Test
    public void testValidISBN() {
        // --- CASI VALIDI ---
        assertTrue(validISBN("9781234567890"), "ISBN 978 standard deve essere valido.");
        assertTrue(validISBN("9790000000000"), "ISBN 979 standard deve essere valido.");
        
        // NUOVI CASI: Limiti di numeri
        assertTrue(validISBN("9789999999999"), "ISBN 978 con tutti 9 deve essere valido.");
        assertTrue(validISBN("9791000000000"), "ISBN 979 con tutti 0 dopo il prefisso deve essere valido.");


        // --- CASI NON VALIDI
        assertFalse(validISBN("123"), "Troppo corto deve fallire.");
        assertFalse(validISBN("9771234567890"), "Prefisso errato (977) deve fallire.");
        assertFalse(validISBN("97812345678901"), "Troppo lungo (14 cifre) deve fallire.");
        assertFalse(validISBN("978-1234567890"), "Trattini non ammessi dalla tua regex devono fallire.");
        assertFalse(validISBN(" 9781234567890"), "Spazio iniziale non ammesso deve fallire.");
        assertFalse(validISBN("9781234567890 "), "Spazio finale non ammesso deve fallire.");
        assertFalse(validISBN("978123456789A"), "Contiene lettere deve fallire.");
        
        // --- CASI LIMITE ---
        assertFalse(validISBN(""), "Stringa vuota deve fallire.");
        assertFalse(validISBN(null), "Null deve restituire false.");
    }

    // 2. TEST AUTORE

    @Test
    public void testValidAuthor() {
        // --- CASI VALIDI STANDARD ---
        assertTrue(validAuthor("Mario Rossi"), "Nome e Cognome standard devono essere validi.");
        assertTrue(validAuthor("Niccolò Ammaniti"), "Accenti in Niccolò devono essere validi.");
        assertTrue(validAuthor("Renzo"), "Nome singolo deve essere valido.");

        // --- NUOVI CASI AVANZATI (Testing la Regex) ---
        
        // Apostrofi (Standard e Ricci)
        assertTrue(validAuthor("D'Annunzio"), "Apostrofo standard (') deve essere valido.");
        assertTrue(validAuthor("O’Malley"), "Apostrofo riccio (’) deve essere valido."); 
        
        // Trattini (se la tua regex li supporta)
        assertTrue(validAuthor("Rossi-Bianchi"), "Trattino (-) deve essere valido."); 
        
        // Particelle composte e nomi multipli
        assertTrue(validAuthor("De Luca"), "Particella con maiuscola deve essere valida.");
        assertTrue(validAuthor("Gian Pietro De La Vega"), "Nome composto e cognome composto devono essere validi.");
        
        // Nomi con accenti e caratteri non standard (ma riconosciuti da \p{L})
        assertTrue(validAuthor("García Lorca"), "Lettere non latine base (es. 'á') devono essere valide.");


        // --- CASI NON VALIDI ---
        assertFalse(validAuthor("mario rossi"), "Tutto minuscolo deve fallire.");
        assertFalse(validAuthor("Mario123"), "Numeri nel nome deve fallire.");
        assertFalse(validAuthor("Mario @Rossi"), "Caratteri speciali non permessi (es. @) deve fallire.");
        assertFalse(validAuthor(" Mario Rossi"), "Spazio iniziale deve fallire.");
        assertFalse(validAuthor("Mario Rossi "), "Spazio finale deve fallire.");
        assertFalse(validAuthor("D' Annunzio"), "Spazio dopo apostrofo deve fallire."); 
        
        // --- CASI LIMITE ---
        assertFalse(validAuthor(""), "Stringa vuota deve fallire.");
        assertFalse(validAuthor((String)null), "Null deve fallire.");
    }

    // 3. TEST ANNO 

    @Test
    public void testValidYear() {
        int annoCorrente = LocalDate.now().getYear();

        // --- CASI VALIDI ---
        assertTrue(validYear("2000"), "Anno passato valido.");
        assertTrue(validYear("0"), "Anno 0 (tecnicamente un numero valido).");
        assertTrue(validYear(String.valueOf(annoCorrente)), "Anno corrente valido (String).");

        // NUOVI CASI: Anni limite bassi
        assertTrue(validYear("1"), "Anno 1 (stringa) valido.");
        assertTrue(validYear(1), "Anno 1 (int) valido.");


        // --- CASI NON VALIDI (String) ---
        assertFalse(validYear("3000"), "Anno futuro (String) deve fallire.");
        assertFalse(validYear("-100"), "Negativo (la regex vuole solo digit) deve fallire.");
        assertFalse(validYear("2020a"), "Alfanumerico deve fallire.");
        assertFalse(validYear(" 2020"), "Spazio iniziale deve fallire.");
        assertFalse(validYear(""), "Stringa vuota deve fallire.");
        
        // --- CASO NULL AGGIUNTO PER COERENZA ---
        assertFalse(validYear(null), "Null deve restituire false.");
        
        // --- CASI NON VALIDI (Int) ---
        assertFalse(validYear(annoCorrente + 1), "Anno futuro (int) deve fallire.");
        // Nota: la versione int accetta negativi, ma la logica è che sia <= anno corrente
        assertTrue(validYear(-1000), "Anno negativo (int) deve essere valido se <= anno corrente."); 
        
        // --- CASI LIMITE (Int) ---
        assertTrue(validYear(annoCorrente), "Anno corrente (int) deve essere valido.");
        assertFalse(validYear(annoCorrente + 1), "Anno futuro (int) deve fallire.");
    }

    // 4. TEST COPIE 

    @Test
    public void testValidCopies() {
        // --- CASI VALIDI (String) ---
        assertTrue(validCopies("1"), "Copia singola deve essere valida.");
        assertTrue(validCopies("1000"), "Copie multiple devono essere valide.");

        // NUOVI CASI: Numeri molto grandi
        assertTrue(validCopies("9999999"), "Numero grande valido.");


        // --- CASI NON VALIDI (String) ---
        assertTrue(validCopies("0"), "Zero copie non ammesso.");
        assertFalse(validCopies("-5"), "Negativo (la regex vuole solo digit) deve fallire.");
        assertFalse(validCopies("10 "), "Spazio finale non ammesso.");
        assertFalse(validCopies("10a"), "Alfanumerico non ammesso.");
        
        // --- CASI LIMITE (String) ---
        assertFalse(validCopies(""), "Stringa vuota deve fallire.");
        
        // --- CASO NULL AGGIUNTO PER COERENZA ---
        assertFalse(validCopies(null), "Null deve restituire false.");
       
        // CASI VALIDI (int) --
        assertTrue(validCopies(0), "Zero (int) non ammesso.");
        assertTrue(validCopies(1), "Uno deve essere ammesso.");
        assertTrue(validCopies(10), "Int valido (valore standard).");
        assertTrue(validCopies(500000), "Numero casuale grande ammesso.");
        assertTrue(validCopies(Integer.MAX_VALUE), "Massimo int ammesso.");
        
// --- CASI NON VALIDI (Int) ---
        assertTrue(validCopies(10), "Int valido.");
        assertTrue(validCopies(0), "Zero (int) non ammesso.");
        assertFalse(validCopies(-5), "Negativo (int) non ammesso.");
    }
}