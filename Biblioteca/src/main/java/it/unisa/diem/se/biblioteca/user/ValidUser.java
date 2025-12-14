package it.unisa.diem.se.biblioteca.user;

/**
 * @brief Interfaccia che definisce i contratti per la validazione dei dati di un utente.
 * Implementa metodi di default per eseguire controlli standard su matricola, nomi e formato email.
 */
public interface ValidUser {
    
    /**
     * @brief Controlla se la matricola è valida (10 interi)
     * La matricola deve essere composta esattamente da 10 cifre numeriche.
     * @param code La matricola da controllare
     * @return true se la matricola è valida, false altrimenti
     */
    public default boolean validCode(String code){
        if (code == null) return false;
        return code.matches("^\\d{10}$");
    }
    
    /**
     * @brief Controlla se una stringa (usata per nome o cognome) è valida.
     * Richiede che la stringa inizi con una lettera maiuscola e supporti lettere Unicod e caratteri come l'apostrofo  
     * Supporta nomi composti separati da spazi, dove ogni parte inizia con una maiuscola.
     * @param name Il nome o cognome da controllare
     * @return true se il nome/cognome è valido, false altrimenti
     */
    public default boolean validName(String name){
        if (name == null) return false;
        // ^\p{Lu} perchè la prima lettera del nome/cognome deve essere maiuscola
        // [\p{L}'’]+ perchè i caratteri seguenti possono essere lettere Unicode, apostrofi o virgolette singole tipografiche
        // (?: \p{Lu}[\p{L}'’]+)* significa che se è presente uno spazio, la parte successiva (es. il secondo nome)
        return name.matches("^\\p{Lu}[\\p{L}'’]+(?: \\p{Lu}[\\p{L}'’]+)*$");
    }
    
    /**
     * @brief Controlla se l'indirizzo email è nel formato standard UNISA per gli studenti.
     * Il formato richiesto è: {@code [iniziale_nome].[cognome][cifre]@studenti.unisa.it}.
     * @param mail L'indirizzo email (String) da controllare.
     * @return {@code true} se la mail è nel formato UNISA richiesto, {@code false} altrimenti.
     */
    public default boolean validMail(String mail){
        if (mail == null) return false;
        // ^\p{Ll}\.\p{Ll}+[\d]{1,3}@studenti\.unisa\.it$
        // ^\p{Ll}\.\p{Ll}+ perchè l'email deve iniziare con una minuscola seguita da punto, e poi almeno una minuscola
        // [\d]{1,3} per 1 a 3 cifre numeriche finali (come 8, 08 o 008)
        // @studenti\.unisa\.it$ per il dominio fisso e la fine della stringa
        return mail.matches("^\\p{Ll}\\.\\p{Ll}+[\\d]{1,3}@studenti\\.unisa\\.it$");
    }
            
}