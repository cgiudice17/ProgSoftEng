package it.unisa.diem.se.biblioteca.prova;

import java.time.LocalDate;

/**
 *
 * @author danilocarratu
 */
public class Loan {
    private Book book;
    private LocalDate returnDate;
    
    /**
     * @brief Costruttore di default
     * 
     * @param[in] book
     * @param[in] returnDate 
     * 
     * @post Prestito inizializzato correttamente
     */
    
    public Loan(Book book, LocalDate returnDate){
        this.book = book;
        this.returnDate = returnDate;
    }

}
