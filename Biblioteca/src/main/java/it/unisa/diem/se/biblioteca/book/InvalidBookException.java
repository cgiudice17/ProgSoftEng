package it.unisa.diem.se.biblioteca.book;

/**
 * @brief Eccezione lanciata per indicare dati non validi.
 * Se uno o più dati di un libro o di una sua proprietà (es. ISBN, Anno, Autore/i) non sono validi secondo le 
 * regole di validazione del sistema, viene lanciata l'eccezione.
 * Questa è un'eccezione controllata (Checked Exception).
 */
public class InvalidBookException extends Exception {

    /**
     * @brief Costruttore senza argomenti.
     * Crea un'istanza vuota di InvalidBookException.
     */
    public InvalidBookException() {
    }

    /**
     * @brief Costruisce un'istanza di InvalidBookException con il messaggio di dettaglio specificato.
     * @param msg Il messaggio di dettaglio che descrive la natura dell'errore di validazione.
     */
    public InvalidBookException(String msg) {
        super(msg);
    }
}
