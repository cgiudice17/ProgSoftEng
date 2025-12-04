package it.unisa.diem.se.biblioteca.data;

import java.time.LocalDate;

/**
 *
 * @author danilocarratu
 */
public class Loan implements Comparable<Loan>{
    
    private User user;
    private Book book;
    private LocalDate returnDate;
    
    /**
     * @brief Costruttore di default
     * 
     * @param[in] user
     * @param[in] book
     * @param[in] returnDate 
     * 
     * 
     * @post Prestito inizializzato correttamente
     */
    
    public Loan(User user, Book book, LocalDate returnDate){
        this.user = user;
        this.book = book;
        this.returnDate = returnDate;
    }
    
    /**
     * @brief Override del metodo compareTo che confronta i prestiti 
     *        in base al data di restituzione
     * @param l
     * @return Un intero negativo se l'ogetto chiamate viene prima cronologicamente dell'argomento,
     *         0 se sono uguali e un intero positivo se viene dopo.
     */

    @Override
    public int compareTo(Loan l) {
        return this.returnDate.compareTo(l.returnDate);
    }
    
    

}
