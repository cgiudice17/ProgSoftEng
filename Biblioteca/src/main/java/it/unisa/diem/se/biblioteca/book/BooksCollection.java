/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * 
 */
public class BooksCollection {
    
    private Map<Book, Integer> books;
    private Map<Author, Set<Book>> authorBooks;
    private Map<String, Book> ISBNBooks;
    private Map<String, Set<Book>> titleBooks;
    private Map<Integer, Set<Book>> yearBooks;
    
    /**
     * @brief Costruttore di default
     * 
     * @post Collezione inizializzata correttamente.
     */
    public BooksCollection() {
        this.books = new TreeMap();
    }
    
    
    /**
     * @brief Aggiunge un libro a tutte le collezioni 
     * @param[in] b il libro da aggiungere
     * 
     * @pre Il libro da aggiungere sia valido 
     * @post Il libro è aggiunto correttamente a tutte le collezioni 
     * 
     */
    public void addBook(Book b, int copies){
        
        books.put(b, copies);
        for(Author aut: b.getAuthors()){
            addAuthorBooksHelper(aut, b);
        } 
        ISBNBooks.put(b.getISBN(), b);
        addTitleBooksHelper(b.getTitle(),b);
    }
    
    
    /**
     * @brief Rimuove un libro da tutte le collezioni
     * 
     * @param[in] b Il libro da rimuovere.
     * @pre Il libro da rimuovere sia valido e si trovi nelle collezioni
     * @post Il libro è rimosso correttamente a tutte le collezioni 
     * 
     */
    
    public void removeBook(Book b){
        books.remove(b);
        
        ISBNBooks.remove(b.getISBN());
        
        titleBooks.get(b.getTitle()).remove(b);
        
        for(Author aut: b.getAuthors()){
            authorBooks.get(aut).remove(b);
        } 
    }
    
    /**
     * @brief Recupera un libro dato il suo codice ISBN
     * @param[in] ISBN Il codice ISBN del libro da recuperare
     * 
     * @return il libro del codice ISBN specificato
     */
    public Book getBookByISBN(String ISBN){
        return null;
    }
    
    
    /**
     * @brief Recupera un libro dato il suo titolo
     * @param[in] tile Il titolo del libro da recuperare
     * 
     * @return Il libro dal titolo specificato
     */
    public List<Book> getBookbyTitle(String title){
        return null;
    }
    
    
    /**
     * @brief Restituisce una lista di libri scritti dall'autore passato
     * @param[in] author L'autore del libro da recuperare
     * 
     * @return Lista di libri
     */
    public List<Book> getBookbyAuthor(Author author){
        return null;
    }
    
    /**
     * @brief Restituisce una lista di libri con l'anno di pubblicazione passato
     * 
     * @param year Anno di pubblicazione scelto
     * @return Lista di libri 
     */
    
    public List<Book> getBookbyYear(int year){
        return null;
    }
    
    public void setCopies(Book b,int copies){
        books.replace(b, books.get(b), copies);
    }
    public int getCopies(Book b) {
        
        return books.getOrDefault(b, 0);
    }
    
    
    /**
     * @brief Metodo helper per aggiungere un libro alla lista dei libri dell'autore.
     * @param a L'autore del libro
     * @param b Il libro da aggiungere
     */
   
    private void addAuthorBooksHelper(Author a, Book b){
        if(!authorBooks.containsKey(a)){
            authorBooks.put(a, new ArrayList());
        }
        
        authorBooks.get(a).add(b);
    }
    
    /**
     * @brief Metodo helper per aggiungere un libro alla lista dei libri con quel titolo.
     * @param t Titolo del libro
     * @param b Il libro da aggiungere
     */
   
    private void addTitleBooksHelper(String t, Book b){
        if(!titleBooks.containsKey(t)){
            titleBooks.put(t, new ArrayList());
        }
        
        titleBooks.get(t).add(b);
    }
    
    /**
     * @brief Restituisce una stringa contenente tutti i libri
     * 
     * @return String tutti i libri
     */
    public static String printAll(){
        return null;
    }
    
}
