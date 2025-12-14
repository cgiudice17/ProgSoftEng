package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.util.List;
import java.io.Serializable;

/**
 * @brief Rappresenta un libro nel catalogo della biblioteca.
 * La classe implementa le interfacce java.lang.Comparable  Comparable per l'ordinamento per titolo e 
 * it.unisa.diem.se.biblioteca.book.ValidBook ValidBook per la validazione dei dati.
 */
public class Book implements Comparable<Book>, ValidBook, Serializable {
    private static final long serialVersionUID = 1L;   
    private String title;
    private List<Author> authors;
    private String ISBN;
    private int publishYear;

    /**
     * @brief Costruisce un nuovo oggetto Book e ne valida i dati.
     * Vengono eseguite le seguenti validazioni tramite i metodi dell'interfaccia ValidBook:
     * 1. Validazione dell'ISBN.
     * 2. Validazione di ogni Autore nella lista.
     * 3. Validazione dell'anno di pubblicazione.
     * @param title Il titolo del libro.
     * @param authors La lista degli autori.
     * @param ISBN Il codice ISBN univoco.
     * @param publishYear L'anno di pubblicazione.
     */
    public Book(String title, List<Author> authors, String ISBN, int publishYear) throws InvalidBookException {
        
        if (!validISBN(ISBN)){
            throw new InvalidBookException("Invalid ISBN");
        }
        for (Author a : authors){
            String nomeCompleto = a.getName() + " " + a.getSurname();
            if (!validAuthor(nomeCompleto)){ 
                throw new InvalidBookException("Invalid Author: " + nomeCompleto);
            }
        }
        if (!validYear(String.valueOf(publishYear))){
            throw new InvalidBookException("Invalid Year");
        }
        this.title = title;
        this.authors = authors;
        this.ISBN = ISBN;
        this.publishYear = publishYear;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }

    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }
    
    @Override
    public int compareTo(Book b){
        return this.title.compareTo(b.title);
    }
    
    /**
     * @brief Genera un codice hash per il libro.
     * L'hash code si basa sul codice ISBN, in coerenza con il metodo equals(Object) equals.
     * Viene utilizzato java.util.Objects.hash(Object) per garantire un calcolo dell'hashcode robusto.
     * @return Il valore di hashcode generato dall'ISBN.
     */
    @Override
    public int hashCode() {
    return java.util.Objects.hash(ISBN);
    }
    
    /**
     * @brief Confronta il libro con l'oggetto specificato.
     * La verifica dell'uguaglianza è determinata dal codice ISBN. 
     * @param o L'oggetto da confrontare con questo libro.
     */
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        Book b = (Book) o;
        return this.ISBN.equals(b.ISBN);
    }

    /**
     * @brief Restituisce una stringa del libro contenente il Titolo, l'Autore, ISBN e l'anno di publicazione
     */
    @Override
    public String toString() {
        return "Titolo: " + title + ", Autore: " + authors + ", ISBN: " + ISBN +", Anno: " + publishYear;
    }
}

