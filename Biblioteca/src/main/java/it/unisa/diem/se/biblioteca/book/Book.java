package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.util.List;
import java.io.Serializable;

public class Book implements Comparable<Book>, ValidBook, Serializable {
    private static final long serialVersionUID = 1L;   
    private String title;
    private List<Author> authors;
    private String ISBN;
    private int publishYear;
    
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
    
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        Book b = (Book) o;
        return this.ISBN.equals(b.ISBN);
    }
    
    @Override
    public String toString() {
        return "Titolo: " + title + ", Autore: " + authors + ", ISBN: " + ISBN +", Anno: " + publishYear;
    }
}