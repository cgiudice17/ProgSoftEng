package it.unisa.diem.se.biblioteca.user;

/**
 * @brief Eccezione lanciata quando si tenta di creare un Utente con dati non validi
 */
public class InvalidUserException extends Exception {
    
    /**
     * @brief Costruttore senza messaggi
     */
    public InvalidUserException() {
        super();
    }

    /**
     * @brief Costruttore con messaggio di errore
     * @param msg Il messaggio che spiega l'errore
     */
    public InvalidUserException(String msg) {
        super(msg);
    }
}