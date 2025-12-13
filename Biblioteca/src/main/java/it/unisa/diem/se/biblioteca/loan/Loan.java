package it.unisa.diem.se.biblioteca.loan;

import it.unisa.diem.se.biblioteca.user.User;
import it.unisa.diem.se.biblioteca.book.Book;
import java.time.LocalDate;
import java.io.Serializable;

public class Loan implements Comparable<Loan>, Serializable{
    private static final long serialVersionUID = 1L;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
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
        
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        Loan l = (Loan) o;
        return this.book.equals(l.getBook()) && this.returnDate.equals(l.getReturnDate()) && this.user.equals(l.getUser());
    }

     /**
     * @brief Override del metotdo toString per stampare la classe corrente.
     */
    @Override
    public String toString(){
        return "Prestito: Utente: " + user + ", Libro: " + book.getTitle() + ", Data: " + returnDate;
    }
}
