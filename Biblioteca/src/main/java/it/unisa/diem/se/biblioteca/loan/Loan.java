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
     * @brief Costruttore parametrico della classe Loan.
     *
     * @param user          L'utente che ha richiesto il prestito.
     * @param book          Il libro oggetto del prestito.
     * @param returnDate    La data prevista per la restituzione.
     * @post Il prestito è inizializzato correttamente con i dati forniti.
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
     * @brief Confronta due prestiti in base alla data di restituzione
     * @param l       Il prestito da confrontare.
     * @return Un intero negativo se l'ogetto chiamate viene prima cronologicamente dell'argomento, 0 se le date sono uguali, e un intero positivo se viene dopo
     */
    @Override
    public int compareTo(Loan l) {
        return this.returnDate.compareTo(l.returnDate);
    }
    /**
     * @brief Verifica l'uguaglianza tra questo prestito e un altro oggetto.
     * Due prestiti sono considerati uguali se coinvolgono lo stesso utente,
     * lo stesso libro e hanno la stessa data di restituzione prevista.
     *
     * @param o      L'oggetto con cui confrontare il prestito
     * @return ritorna true se i prestiti sono uguali, altrimenti false
     */
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        Loan l = (Loan) o;
        return this.book.equals(l.getBook()) && this.returnDate.equals(l.getReturnDate()) && this.user.equals(l.getUser());
    }
    /**
     * @brief Restituisce una rappresentazione in formato stringa del prestito
     *@return ritorna una stringa formattata con Utente, Titolo del Libro e Data di restituzione
     **/
    @Override
    public String toString(){
        return "Prestito: Utente: " + user + ", Libro: " + book.getTitle() + ", Data: " + returnDate;
    }
}
