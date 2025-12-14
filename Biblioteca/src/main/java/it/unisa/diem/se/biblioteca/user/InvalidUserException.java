package it.unisa.diem.se.biblioteca.user;

/**
 * @brief Eccezione lanciata per indicare dati non validi.
 * Se uno o più dati di un utente (es. nome,cognome,matricola o email) non sono validi secondo le 
 * regole di validazione del sistema, viene lanciata l'eccezione.
 * Questa è un'eccezione controllata (Checked Exception).
 */
public class InvalidUserException extends Exception {
    
    /**
     * @brief Costruttore senza argomenti.
     * Crea un'istanza vuota di InvaliduserException
     */
    public InvalidUserException() {
        super();
    }

    /**
     * @brief Costruttore con messaggio di errore.
     * @param msg Il messaggio di dettaglio (String) che descrive la natura dell'errore di validazione.
     */
    public InvalidUserException(String msg) {
        super(msg);
    }
}