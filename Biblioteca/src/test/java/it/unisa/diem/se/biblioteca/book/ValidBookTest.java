package it.unisa.diem.se.biblioteca.book;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Test di unità per l'interfaccia ValidBook.
 * Contiene tutti i casi possibili per validare i dati di un libro (ISBN, Autore, Anno, Copie) 
 * secondo le regole definite nei metodi di default dell'interfaccia.
 */
public class ValidBookTest implements ValidBook {

    // 1. TEST ISBN

    // Verifica la validazione dell'ISBN (tipicamente 13 cifre che iniziano con 978 o 979).
    @Test
    public void testValidISBN() {
        // --- CASI VALIDI ---
        assertTrue(validISBN("9781234567890"), "ISBN 978 standard deve essere valido.");
        assertTrue(validISBN("9790000000000"), "ISBN 979 standard deve essere valido.");
        assertTrue(validISBN("9789999999999"), "ISBN 978 con tutti 9 deve essere valido.");
        assertTrue(validISBN("9791000000000"), "ISBN 979 con tutti 0 dopo il prefisso deve essere valido.");

        // --- CASI NON VALIDI ---
        assertFalse(validISBN("123"), "Troppo corto deve fallire.");
        assertFalse(validISBN("9771234567890"), "Prefisso errato (977) deve fallire.");
        assertFalse(validISBN("97812345678901"), "Troppo lungo (14 cifre) deve fallire.");
        assertFalse(validISBN("978-1234567890"), "Trattini non ammessi devono fallire.");
        assertFalse(validISBN(" 9781234567890"), "Spazio iniziale non ammesso deve fallire.");
        assertFalse(validISBN("9781234567890 "), "Spazio finale non ammesso deve fallire.");
        assertFalse(validISBN("978123456789A"), "Contiene lettere deve fallire.");
        
        // --- CASI LIMITE ---
        assertFalse(validISBN(""), "Stringa vuota deve fallire.");
        assertFalse(validISBN(null), "Null deve restituire false.");
    }

    // 2. TEST AUTORE

    // Verifica la validazione del nome/cognome di un autore (tipicamente caratteri alfabetici, apostrofi, trattini, accenti).
    @Test
    public void testValidAuthor() {
        // --- CASI VALIDI ---
        assertTrue(validAuthor("Mario Rossi"), "Nome e Cognome standard devono essere validi.");
        assertTrue(validAuthor("Niccolò Ammaniti"), "Accenti ('ò') devono essere validi.");
        assertTrue(validAuthor("Renzo"), "Nome singolo deve essere valido.");
        assertTrue(validAuthor("D'Annunzio"), "Apostrofo standard (') deve essere valido.");
        assertTrue(validAuthor("O’Malley"), "Apostrofo riccio (’) deve essere valido."); 
        assertTrue(validAuthor("Rossi-Bianchi"), "Trattino (-) deve essere valido."); 
        assertTrue(validAuthor("De Luca"), "Particella con maiuscola deve essere valida.");
        assertTrue(validAuthor("Gian Pietro De La Vega"), "Nome composto e cognome composto devono essere validi.");
        assertTrue(validAuthor("García Lorca"), "Lettere non latine base (es. 'á') devono essere valide.");

        // --- CASI NON VALIDI ---
        assertFalse(validAuthor("mario rossi"), "Tutto minuscolo deve fallire (se la regola richiede Maiuscola iniziale).");
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

    // Verifica la validazione dell'anno di pubblicazione (deve essere numerico, non futuro, e non nullo).
    @Test
    public void testValidYear() {
        int annoCorrente = LocalDate.now().getYear();

        // --- CASI VALIDI ---
        assertTrue(validYear("2000"), "Anno passato valido (String).");
        assertTrue(validYear("0"), "Anno 0 (String) è tecnicamente un numero valido.");
        assertTrue(validYear(String.valueOf(annoCorrente)), "Anno corrente valido (String).");
        assertTrue(validYear("1"), "Anno 1 (stringa) valido.");
        assertTrue(validYear(1), "Anno 1 (int) valido.");

        // --- CASI NON VALIDI (String) ---
        assertFalse(validYear("3000"), "Anno futuro (String) deve fallire.");
        assertFalse(validYear("-100"), "Negativo (String) deve fallire (se la regex non ammette il segno).");
        assertFalse(validYear("2020a"), "Alfanumerico deve fallire.");
        assertFalse(validYear(" 2020"), "Spazio iniziale deve fallire.");
        assertFalse(validYear(""), "Stringa vuota deve fallire.");
        assertFalse(validYear(null), "Null deve restituire false.");
        
        // --- CASI NON VALIDI (Int) ---
        assertFalse(validYear(annoCorrente + 1), "Anno futuro (int) deve fallire.");
        assertTrue(validYear(-1000), "Anno negativo (int) deve essere valido se <= anno corrente."); 
        
        // --- CASI LIMITE (Int) ---
        assertTrue(validYear(annoCorrente), "Anno corrente (int) deve essere valido.");
        assertFalse(validYear(annoCorrente + 1), "Anno futuro (int) deve fallire.");
    }

    // 4. TEST COPIE 

    // Verifica la validazione del numero di copie (deve essere numerico, positivo o zero).
    @Test
    public void testValidCopies() {
        // --- CASI VALIDI (String) ---
        assertTrue(validCopies("1"), "Copia singola deve essere valida.");
        assertTrue(validCopies("1000"), "Copie multiple devono essere valide.");
        assertTrue(validCopies("9999999"), "Numero grande valido.");
        assertTrue(validCopies("0"), "Zero copie deve essere accettato dalla logica (sebbene non ammesso come requisito)."); 
        
        // --- CASI NON VALIDI (String) ---
        assertFalse(validCopies("-5"), "Negativo (String) deve fallire.");
        assertFalse(validCopies("10 "), "Spazio finale non ammesso.");
        assertFalse(validCopies("10a"), "Alfanumerico non ammesso.");
        
        // --- CASI LIMITE (String) ---
        assertFalse(validCopies(""), "Stringa vuota deve fallire.");
        
        // --- CASO NULL AGGIUNTO PER COERENZA ---
        assertFalse(validCopies(null), "Null deve restituire false.");
        
        // --- CASI VALIDI (int) ---
        assertTrue(validCopies(0), "Zero (int) deve essere accettato dalla logica della classe sorgente.");
        assertTrue(validCopies(1), "Uno deve essere ammesso.");
        assertTrue(validCopies(10), "Int valido (valore standard).");
        assertTrue(validCopies(500000), "Numero casuale grande ammesso.");
        assertTrue(validCopies(Integer.MAX_VALUE), "Massimo int ammesso.");
        
        // --- CASI NON VALIDI (Int) ---
        assertFalse(validCopies(-5), "Negativo (int) non ammesso.");
    }
}
