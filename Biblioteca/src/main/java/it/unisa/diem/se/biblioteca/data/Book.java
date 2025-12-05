package it.unisa.diem.se.biblioteca.data;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author danilocarratu
 */
public class Book implements Comparable<Book>{
    private String title;
    private List<Author> authors;
    private String ISBN;
    private LocalDate publishDate;
    
    
    
    /**
     * @brief Costruttore di default
     * 
     * @param[in] title Titolo del libro
     * @param[in] authors Autori
     * @param[in] ISBN ISBN
     * @param[in] publishDate Data di pubblicazione 
     * 
     * @post Libro inizializzato correttamente
     */

    public Book(String title, List<Author> authors, String ISBN, LocalDate publishDate) {
        this.title = title;
        this.authors = authors;
        this.ISBN = ISBN;
        this.publishDate = publishDate;
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

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
    
    
    /**
     * @brief Override del metodo compareTo che confronta i libri
     *        in base al titolo
     * 
     * 
     * @param[in] b L'ogetto book da comparare
     * @return Un intero negativo se l'ogetto chiamate viene prima lessigograficamente dell'argomento,
     *         0 se sono uguali e un intero positivo se viene dopo.
     */
    
    @Override
    public int compareTo(Book b){
        return this.title.compareTo(b.title);
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        Book b = (Book) o;
        return this.ISBN.equals(b.ISBN);
    }
    
    /**
     * @brief Override del metotdo toString per sstampare la classe corrente.
     * 
     */
    @Override
    public String toString(){
        return null;
    }
}
