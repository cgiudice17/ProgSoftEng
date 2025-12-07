package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.time.LocalDate;
import java.util.List;


public class Book implements Comparable<Book>{
    private String title;
    private List<Author> authors;
    private String ISBN;
    private int publishYear;
    
    
    
    /**
     * @brief Costruttore di default
     * 
     * @param[in] title Titolo del libro
     * @param[in] authors Autori
     * @param[in] ISBN ISBN
     * @param[in] publishYear Anno di pubblicazione 
     * 
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

    public void setPublishDate(int publishYear) {
        this.publishYear = publishYear;
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
    public int hashCode(){
        return 0;
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
