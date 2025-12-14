package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @brief Classe di test per l'interfaccia ValidUser.
 * Implementa l'interfaccia stessa per verificare il corretto funzionamento 
 * dei metodi di default per la validazione dei dati utente.
 */
public class ValidUserTest implements ValidUser {

    // 1. TEST VALIDAZIONE MATRICOLA (validCode)

    /**
     * @brief Verifica che una matricola valida (10 cifre) venga accettata correttamente.
     */
    @Test
    public void testValidCode() {
        String validCode = "0123456789";
        assertTrue(validCode(validCode), "Una matricola di 10 cifre numeriche deve essere valida.");
        // CASO LIMITE: Tutti zeri
        assertTrue(validCode("0000000000"), "Una matricola di 10 zeri deve essere valida.");
    }

    /**
     * @brief Verifica che matricole con lunghezza errata (troppo corte o lunghe) vengano rifiutate.
     */
    @Test
    public void testInvalidCodeLength() {
        // Caso: Troppo corta
        assertFalse(validCode("12345"), "Matricola troppo corta (5 cifre) deve fallire.");
        
        // Caso: Troppo lunga
        assertFalse(validCode("012345678912"), "Matricola troppo lunga (12 cifre) deve fallire.");
    }

    /**
     * @brief Verifica che matricole contenenti caratteri non numerici vengano rifiutate.
     */
    @Test
    public void testInvalidCodeFormat() {
        // Caso: Contiene lettere
        assertFalse(validCode("01234abc89"), "Matricola contenente lettere deve fallire.");
        
        // Caso: Contiene simboli
        assertFalse(validCode("01234-6789"), "Matricola contenente simboli deve fallire.");
        
        // Caso: Stringa vuota
        assertFalse(validCode(""), "Matricola vuota deve fallire.");
        
        // CASO LIMITE: Contiene spazi
        assertFalse(validCode("01234 56789"), "Matricola con spazi interni deve fallire.");
        assertFalse(validCode(" 0123456789"), "Matricola con spazio iniziale deve fallire.");
        assertFalse(validCode(null), "Matricola null deve fallire."); // Non lancia più NullPointerException
    }

    // 2. TEST VALIDAZIONE NOME/COGNOME (validName)

    /**
     * @brief Verifica che nomi e cognomi nel formato corretto vengano accettati.
     */
    @Test
    public void testValidName() {
        // Nomi semplici
        assertTrue(validName("Mario"), "Nome semplice con iniziale maiuscola deve essere valido.");
        assertTrue(validName("Rossi"), "Cognome semplice con iniziale maiuscola deve essere valido.");
        
        // Nomi composti
        assertTrue(validName("De Luca"), "Cognome composto con spazio deve essere valido.");
        assertTrue(validName("Gian Marco"), "Nome composto con spazio deve essere valido.");
        
        // Nomi con apostrofo
        assertTrue(validName("D'Angelo"), "Cognome con apostrofo dritto (') deve essere valido.");
        
        // CASO LIMITE: Apostrofo riccio (U+2019)
        assertTrue(validName("O’Malley"), "Cognome con apostrofo riccio (’) deve essere valido."); 
        
        // CASO LIMITE: Nome composto più lungo
        assertTrue(validName("Di Pietro De La Cruz"), "Nome/Cognome composti e lunghi devono essere validi.");
        
        // CASO LIMITE: Lettere accentate (\p{L} le supporta)
        assertTrue(validName("García"), "Cognome con lettere accentate deve essere valido.");
    }

    /**
     * @brief Verifica che nomi con formato errato (minuscole, numeri, simboli) vengano rifiutati.
     */
    @Test
    public void testInvalidName() {
        // Caso: Iniziale minuscola
        assertFalse(validName("mario"), "Nome con iniziale minuscola deve fallire.");
        
        // Caso: Contiene numeri
        assertFalse(validName("Mario1"), "Nome contenente numeri deve fallire.");
        
        // Caso: Contiene simboli non ammessi
        assertFalse(validName("Mario@"), "Nome contenente simboli speciali deve fallire.");
        
        // Caso: Stringa vuota
        assertFalse(validName(""), "Nome vuoto deve fallire.");
        
        // CASO LIMITE: Null e spazi
        assertFalse(validName(null), "Nome null deve fallire."); // Non lancia più NullPointerException
        assertFalse(validName(" "), "Nome contenente solo spazi deve fallire.");
        
        // CASO LIMITE: Spazio iniziale/finale
        assertFalse(validName(" Mario"), "Nome con spazio iniziale deve fallire.");
        assertFalse(validName("Mario "), "Nome con spazio finale deve fallire.");
        
        // CASO LIMITE: Spazio senza successiva maiuscola
        assertFalse(validName("De luca"), "Spazio deve essere seguito da una maiuscola per la regex.");
        
        // CASO LIMITE: Iniziale di parola intermedia non maiuscola
        assertFalse(validName("Gian marco"), "Solo la prima lettera e quelle dopo uno spazio devono essere maiuscole.");
    }

    // 3. TEST VALIDAZIONE EMAIL (validMail)

    /**
     * @brief Verifica che le email nel formato istituzionale corretto vengano accettate.
     * NOTA: La regex richiede obbligatoriamente da 1 a 3 cifre dopo il cognome.
     */
    @Test
    public void testValidMail() {
        // Caso con 1 cifra
        assertTrue(validMail("m.rossi1@studenti.unisa.it"), "Email con 1 cifra finale deve essere valida.");
        
        // Caso con 2 cifre
        assertTrue(validMail("g.verdi12@studenti.unisa.it"), "Email con 2 cifre finali deve essere valida.");
        
        // Caso con 3 cifre
        assertTrue(validMail("a.b123@studenti.unisa.it"), "Email con 3 cifre finali deve essere valida.");
        
        // CASO LIMITE: Cognome di una sola lettera (es. l.r1@...)
        assertTrue(validMail("l.r1@studenti.unisa.it"), "Email con cognome di una sola lettera deve essere valida.");
        
        // CASO LIMITE: Cognome che finisce con cifra
        assertTrue(validMail("c.rossi1@studenti.unisa.it"), "Cognome standard (tutte lettere) seguito da cifre deve essere valido.");
    }

    /**
     * @brief Verifica che la mancanza del numero obbligatorio dopo il cognome causi errore.
     */
    @Test
    public void testInvalidMailMissingNumber() {
        assertFalse(validMail("m.rossi@studenti.unisa.it"), "Email senza numero finale deve fallire (richiesto dalla regex).");
    }

    /**
     * @brief Verifica errori di formato generali (maiuscole, punteggiatura mancante, troppi numeri).
     */
    @Test
    public void testInvalidMailFormat() {
        // Caso: Lettere Maiuscole
        assertFalse(validMail("M.Rossi1@studenti.unisa.it"), "Email con lettere maiuscole deve fallire.");
        
        // Caso: Manca il punto tra nome e cognome
        assertFalse(validMail("mrossi1@studenti.unisa.it"), "Email senza punto separatore deve fallire.");
        
        // Caso: Troppe cifre (più di 3)
        assertFalse(validMail("m.rossi1234@studenti.unisa.it"), "Email con più di 3 cifre finali deve fallire.");
        
        // CASO LIMITE: Più di una lettera iniziale prima del punto (e.g., ma.rossi1@...)
        assertFalse(validMail("ma.rossi1@studenti.unisa.it"), "L'iniziale deve essere una sola lettera minuscola.");
        
        // CASO LIMITE: Iniziale non è una lettera
        assertFalse(validMail("1.rossi1@studenti.unisa.it"), "La prima parte deve iniziare con una lettera minuscola.");
        
        // CASO LIMITE: Stringa vuota o null
        assertFalse(validMail(""), "Email vuota deve fallire.");
        assertFalse(validMail(null), "Email null deve fallire."); // Non lancia più NullPointerException
    }

    /**
     * @brief Verifica che domini diversi da @studenti.unisa.it vengano rifiutati.
     */
    @Test
    public void testInvalidMailDomain() {
        assertFalse(validMail("m.rossi1@gmail.com"), "Dominio generico (@gmail.com) deve fallire.");
        assertFalse(validMail("m.rossi1@unisa.it"), "Dominio incompleto (@unisa.it) deve fallire, serve @studenti.unisa.it.");
        assertFalse(validMail("m.rossi1@studenti.unisa.it."), "Dominio con carattere extra alla fine deve fallire.");
    }
}