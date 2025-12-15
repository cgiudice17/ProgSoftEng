package it.unisa.diem.se.biblioteca.loan;

import it.unisa.diem.se.biblioteca.user.User;
import it.unisa.diem.se.biblioteca.book.Book;
import java.util.Objects;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * @brief Rappresenta un prestito attivo di un libro ad un utente.
 * La classe è comparabile in base alla data di restituzione.
 */
public class Loan implements Comparable<Loan>, Serializable{
    private static final long serialVersionUID = 1L;
    private User user;
    private Book book;
    private LocalDate returnDate;
    
    /**
     * @brief Costruttore parametrico della classe Loan.
     * @param user L'utente che ha richiesto il prestito.
     * @param book Il libro oggetto del prestito.
     * @param returnDate La data prevista per la restituzione.
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
     * I prestiti vengono ordinati cronologicamente in base alla data prevista di restituzione.
     * @param l Il prestito da confrontare.
     * @return Un intero negativo se l'ogetto chiamate viene prima cronologicamente dell'argomento,
     * 0 se le date sono uguali, e un intero positivo se viene dopo
     */
    @Override
    public int compareTo(Loan l) {
        return this.returnDate.compareTo(l.returnDate);
    }
    
    /**
     * @brief Genera un codice hash per il prestito.
     * l'hash si basa sui 3 campi che definiscono l'unicità del prestito: utente, libro e data di restituzione 
     * Uso di Objects.hash per un calcolo dell'hashcode robusto basato su tutti i campi di equals.   
     */
    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }
   
    /**
     * @brief Confronta questo prestito con l'oggetto specificato.
     * Verifica l'uguaglianza basandosi sulla combinazione unica di Utente e Libro.
     * Due prestiti sono considerati identici se si riferiscono allo stesso libro concesso allo stesso utente.
     * @param o L'oggetto da confrontare con l'istanza corrente.
     * @return true se l'oggetto passato è un Prestito e coincide per Utente e Libro, false altrimenti.
     */
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        Loan l = (Loan) o;
        return this.book.equals(l.getBook()) && this.user.equals(l.getUser());
    }

    /**
     * @brief Restituisce una stringa del prestito contenente il Utente, il Libro e la data per la restituzione
     */
    @Override
    public String toString(){
        return "Prestito: Utente: " + user + ", Libro: " + book.getTitle() + ", Data: " + returnDate;
    }
}

