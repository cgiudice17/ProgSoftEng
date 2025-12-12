package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.time.LocalDate;
import java.util.List;

/**
 * @brief rappresenta un libro all'interno del sistema bibliotecario.
 * Questa classe funge da entità principale per la gestione del catalogo. 
 */

public class Book implements Comparable<Book>{
    private String title;
    private List<Author> authors;
    private String ISBN;
    private int publishYear;
    
    
    
    /**
     * @brief Costruttore di default. Costruisce un nuovo oggetto book
     * @param title  il titolo del libro
     * @param authors lista di autori 
     * @param ISBN codice ISBN
     * @param [publishYear anno di pubblicazione 
     * @post Libro inizializzato correttamente
     */
    public Book(String title, List<Author> authors, String ISBN, int publishYear) {
        this.title = title;
        this.authors = authors;
        this.ISBN = ISBN;
        this.publishYear = publishYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
    
    
    /**
     * @brief metodo compareTo che confronta questo libro con un altro in base al titolo.
     * @param b l'ogetto book da comparare
     * @return Un intero negativo se l'ogetto chiamate viene prima lessicograficamente dell'argomento, 0 se sono uguali e un intero positivo se viene dopo.
     */
    @Override
    public int compareTo(Book b){
        return this.title.compareTo(b.title);
    }
    
    
    /**
     *@brief verifica l'uguaglianza tra 2 libri 
     * Due libei sono considerati uguali se hanno lo stesso codice ISBN 
     *@param l'ogetto con cui confrontare l'autore 
     *@return true se gli oggetti confrontati sono uguali, false altrimenti 
     */
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        Book b = (Book) o;
        return this.ISBN.equals(b.ISBN);
    }
    
     /**
     * @brief Resituisce una rappresentazione in formato stringa della classe 
     * @return stringa contenente le informazioni che descrivono il libro
     */
    @Override
    public String toString(){
        return null;
    }
}
