/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import java.io.Serializable;

 /**
 * @brief Gestisce l'intero catalogo dei libri della biblioteca.
 * Indicizza i libri tramite mappe e ne permettere le ricerche basate sui 
 * seguenti criteri: ISBN, Autore, Titolo e Anno di pubblicazione.
 * Inoltre gestisce il conteggio delle copie disponibili per ogni libro
 * Implementa l'interfaccia {@link it.unisa.diem.se.biblioteca.book.ValidBook ValidBook} per la validazione delle copie.
 */
public class BooksCollection implements ValidBook, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Book, Integer> books;
    private Map<String, Book> booksISBN;

    /**
     * @brief Costruttore di default.
     * Inizializza la mappa principale per la gestione dei libri e degli indici
     */
    public BooksCollection() {
        this.books = new TreeMap<>();    
        this.booksISBN = new HashMap<>();
    }
    
     /**
     * @brief Aggiunge un nuovo libro al catalogo e aggiorna gli indici.
     * Se il libro esiste già, il numero di copie viene semplicemente sovrascritto.
     * @param b Il libro da aggiungere.
     * @param copies Il numero di copie da aggiungere.
     * @throws NullPointerException Se il libro passato è null.
     */
    public void addBook(Book b, int copies){
        if(b == null){
            throw new NullPointerException("Invalid Pointer to book");
        }
        books.put(b, copies);
        booksISBN.put(b.getISBN(), b);
    }
    
    /**
     * @brief Rimuove un libro dal catalogo e da tutti gli indici associsti 
     * @param b Il libro da rimuovere.
     * @throws NullPointerException Se il libro passato è null.
     */
    public void removeBook(Book b){
        if(b == null){
            throw new NullPointerException("Invalid Pointer to book");
        }
        books.remove(b);
        booksISBN.remove(b.getISBN());
    }

    /**
     * @brief Restituisce l'insieme di tutti i libri presenti nel catalogo
     * L'ordine dei libri è dato dall'ordinamento naturale della chiave Book per titolo.
     * @return Set<Book> insieme di tutti i libri presenti nel catalogo
     */
    public Set<Book> getBooks(){
        return books.keySet();
    }

    /**
    * @brief Cerca e restituisce un libro dato il suo codice ISBN
    * @param ISBN Il codice ISBN del libro da cercare
    * @return il libro del codice ISBN specificato
    */
    public Book getBookByISBN(String ISBN){
        return booksISBN.get(ISBN);
    }
    
    /**
     * @brief Aggiorna il numero di copie disponibili per un libro già presente nel catalogo.
     * Utilizza il metodo replace per sovrascrivere il valore associato alla chiave libro, 
     * permettendo di modificare la disponibilità in seguito a prestiti, restituzioni o nuovi acquisti.
     * @param b Il libro di cui si vuole modificare la disponibilità.
     * @param copies Il nuovo numero di copie da impostare.
     * @throws NullPointerException Se il libro passato è {@code null}.
     * @throws InvalidBookException Se il numero di copie specificato non è valido (ad esempio, minore o uguale a zero).
     */
    public void setCopies(Book b,int copies) throws InvalidBookException{
        if(b == null){
            throw new NullPointerException("Invalid Pointer to book");
        }
        if (!validCopies(copies)){
            throw new InvalidBookException("Invalid number of copies");
        }
        books.replace(b, books.get(b), copies);
    }
    
    public int getCopies(Book b) {
        if(b == null){
            throw new NullPointerException("Invalid Pointer to book");
        }
        return books.getOrDefault(b, 0);
    }

    public String printAll() {
        if (books.isEmpty()) {
            return "Il catalogo è vuoto.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("--- Catalogo Libri ---\n");
        
        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            Book b = entry.getKey();
            Integer copie = entry.getValue();
            
            sb.append(b.toString())
              .append(" | Copie disponibili: ")
              .append(copie)
              .append("\n----------------------\n");
        }
        
        return sb.toString();
    }
}


//FAI LE ULTIME 2 