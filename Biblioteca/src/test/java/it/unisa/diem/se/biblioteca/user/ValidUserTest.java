package it.unisa.diem.se.biblioteca.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test di unità per l'interfaccia ValidUser.
 * Implementa l'interfaccia stessa per verificare il corretto funzionamento 
 * dei metodi di default per la validazione dei dati utente (Matricola, Nome/Cognome, Email).
 */
public class ValidUserTest implements ValidUser {

    // 1. TEST VALIDAZIONE MATRICOLA (validCode)

    // Verifica che una matricola valida (10 cifre) venga accettata correttamente.
    @Test
    public void testValidCode() {
        String validCode = "0123456789";
        // CASI VALIDI:
        assertTrue(validCode(validCode), "Una matricola di 10 cifre numeriche deve essere valida.");
        assertTrue(validCode("0000000000"), "Una matricola di 10 zeri deve essere valida.");
    }

    // Verifica che matricole con lunghezza errata (troppo corte o lunghe) vengano rifiutate.
    @Test
    public void testInvalidCodeLength() {
        // CASI INVALIDI:
        assertFalse(validCode("12345"), "Matricola troppo corta (5 cifre) deve fallire.");        
        assertFalse(validCode("012345678912"), "Matricola troppo lunga (12 cifre) deve fallire.");
    }

    // Verifica che matricole contenenti caratteri non numerici, spazi o null vengano rifiutate.
    @Test
    public void testInvalidCodeFormat() {
        //CASI INVALIDI:
        assertFalse(validCode("01234abc89"), "Matricola contenente lettere deve fallire.");
        assertFalse(validCode("01234-6789"), "Matricola contenente simboli deve fallire.");
        assertFalse(validCode(""), "Matricola vuota deve fallire.");
        
        // CASI LIMITE:
        assertFalse(validCode("01234 56789"), "Matricola con spazi interni deve fallire.");
        assertFalse(validCode(" 0123456789"), "Matricola con spazio iniziale deve fallire.");
        assertFalse(validCode(null), "Matricola null deve fallire.");
    }

    // 2. TEST VALIDAZIONE NOME/COGNOME (validName)

    // Verifica che nomi e cognomi nel formato corretto (iniziale maiuscola, spazi, apostrofi) vengano accettati.
    @Test
    public void testValidName() {
        //CASI INVALIDI:
        assertTrue(validName("Mario"), "Nome semplice con iniziale maiuscola deve essere valido.");
        assertTrue(validName("Rossi"), "Cognome semplice con iniziale maiuscola deve essere valido.");
        assertTrue(validName("De Luca"), "Cognome composto con spazio deve essere valido (seconda parola maiuscola).");
        assertTrue(validName("Gian Marco"), "Nome composto con spazio deve essere valido.");
        assertTrue(validName("D'Angelo"), "Cognome con apostrofo dritto (') deve essere valido.");
        assertTrue(validName("L'Aquila"), "Nome: inizia con L' deve essere valido.");

        // CASI LIMITE: 
        assertTrue(validName("O’Malley"), "Cognome con apostrofo riccio (’) deve essere valido."); 
        assertTrue(validName("Di Pietro De La Cruz"), "Nome/Cognome composti e lunghi devono essere validi.");
        assertTrue(validName("García"), "Cognome con lettere accentate deve essere valido.");
        
       
    }

    // Verifica che nomi con formato errato (minuscole, numeri, simboli, spazi) vengano rifiutati.
    @Test
    public void testInvalidName() {
        // CASI INVALIDI;
        assertFalse(validName("mario"), "Nome con iniziale minuscola deve fallire.");
        assertFalse(validName("Mario1"), "Nome contenente numeri deve fallire.");
        assertFalse(validName("Mario@"), "Nome contenente simboli speciali deve fallire.");
        assertFalse(validName(""), "Nome vuoto deve fallire.");
        assertFalse(validName("Rossi-Bianchi"), "Cognome: trattino (-) non è supportato e deve fallire.");

        // CASI LIMITE:
        assertFalse(validName(null), "Nome null deve fallire.");
        assertFalse(validName(" "), "Nome contenente solo spazi deve fallire.");
        assertFalse(validName(" Mario"), "Nome con spazio iniziale deve fallire.");
        assertFalse(validName("Mario "), "Nome con spazio finale deve fallire.");
        assertFalse(validName("De luca"), "Spazio deve essere seguito da una maiuscola per la regex.");
        assertFalse(validName("Gian marco"), "Solo la prima lettera e quelle dopo uno spazio devono essere maiuscole.");
        
    }

    // 3. TEST VALIDAZIONE EMAIL (validMail)

    // Verifica che le email nel formato istituzionale corretto (dominio @studenti.unisa.it e cifre finali) vengano accettate.
    @Test
    public void testValidMail() {
        // CASI VALIDI
        assertTrue(validMail("m.rossi1@studenti.unisa.it"), "Email con 1 cifra finale deve essere valida.");
        assertTrue(validMail("g.verdi12@studenti.unisa.it"), "Email con 2 cifre finali deve essere valida.");
        assertTrue(validMail("a.b123@studenti.unisa.it"), "Email con 3 cifre finali deve essere valida.");

        // CASI LIMITE: 
        assertTrue(validMail("l.r1@studenti.unisa.it"), "Email con cognome di una sola lettera deve essere valida.");
        assertTrue(validMail("c.rossi1@studenti.unisa.it"), "Cognome standard (tutte lettere) seguito da cifre deve essere valido.");
    }

    // Verifica che la mancanza del numero obbligatorio dopo il cognome causi errore.
    @Test
    public void testInvalidMailMissingNumber() {
        assertFalse(validMail("m.rossi@studenti.unisa.it"), "Email senza numero finale deve fallire (richiesto dalla regex).");
    }

    // Verifica errori di formato generali (maiuscole, punteggiatura mancante, limiti di cifre).
    @Test
    public void testInvalidMailFormat() {
        // CASI INVALIDI:
        assertFalse(validMail("M.Rossi1@studenti.unisa.it"), "Email con lettere maiuscole deve fallire.");
        assertFalse(validMail("mrossi1@studenti.unisa.it"), "Email senza punto separatore deve fallire.");
        assertFalse(validMail("m.rossi1234@studenti.unisa.it"), "Email con più di 3 cifre finali deve fallire.");
        assertFalse(validMail("m.rossi-bianchi1@studenti.unisa.it"), "Trattino nell'alias deve fallire.");
        assertFalse(validMail("m.rossi'1@studenti.unisa.it"), "Apostrofo nell'alias deve fallire.");
        
        // CASI LIMITE:
        assertFalse(validMail("ma.rossi1@studenti.unisa.it"), "L'iniziale deve essere una sola lettera minuscola.");
        assertFalse(validMail("1.rossi1@studenti.unisa.it"), "La prima parte deve iniziare con una lettera minuscola.");
        assertFalse(validMail(""), "Email vuota deve fallire.");
        assertFalse(validMail(null), "Email null deve fallire.");
    }

    // Verifica che domini diversi da @studenti.unisa.it vengano rifiutati.
    @Test
    public void testInvalidMailDomain() {
        assertFalse(validMail("m.rossi1@gmail.com"), "Dominio generico (@gmail.com) deve fallire.");
        assertFalse(validMail("m.rossi1@unisa.it"), "Dominio incompleto (@unisa.it) deve fallire, serve @studenti.unisa.it.");
        assertFalse(validMail("m.rossi1@studenti.unisa.it."), "Dominio con carattere extra alla fine deve fallire.");
    }
}