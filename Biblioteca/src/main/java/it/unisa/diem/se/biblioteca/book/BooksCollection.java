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
 * @brief Gestisce l'intero catalogo dei libri della biblioteca 
 * Utilizza molteplici mappe per indicizzare i libri e permettere ricerche efficienti, basate sui seguenti criteri: ISBN, Autore, Titolo e Anno di pubblicazione.
 * Gestisce inoltre il conteggio delle copie disponibili per ogni libro
 */
public class BooksCollection implements ValidBook, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Book, Integer> books;
    private Map<String, Book> booksISBN;

    /**
     * @brief Costruttore di default
     * Inizializza la mappa principale
     */
    public BooksCollection() {
        this.books = new TreeMap<>();    
        this.booksISBN = new HashMap<>();
    }
    
    /**
     * @brief Aggiunge un nuovo libro al catalogo e aggiorna tutti gli indici di ricerca 
     * @param b  libro da aggiungere
     * @param copies numero di copie del libro da aggiungere
     * @pre Il libro da aggiungere sia valido 
     * @post Il libro è aggiunto correttamente a tutte le collezioni 
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
     * @pre Il libro da rimuovere sia valido e si trovi nelle collezioni
     * @post Il libro è rimosso correttamente a tutte le collezioni 
     * 
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
     * @return Set<Book> insieme di tutti i libri presenti nel catalogo
     * @post Viene restituito l'insieme di tutti i libri presenti nel catalogo
     */
    
    public Set<Book> getBooks(){
        return books.keySet();
    }
    
    public Book getBookByISBN(String ISBN){
        return booksISBN.get(ISBN);
    }
    
    /**
     * @throws it.unisa.diem.se.biblioteca.book.InvalidBookException
     * @brief Aggiorna il numero di copie disponibili per un libro già presente nel catalogo.
     * Utilizza il metodo replace per sovrascrivere il valore associato alla chiave libro, permettendo di modificare la disponibilità in seguito a prestiti, restituzioni o nuovi acquisti.
     * @param b Il libro di cui si vuole modificare la disponibilità.
     * @param copies Il nuovo numero di copie da impostare.
     * @pre Il libro deve essere già presente nella collezione 'books'.
     * @post Il numero di copie del libro viene aggiornato al nuovo valore specificato.
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

    /**
     * @brief Restituisce una rappresentazione in formato stringa dell'intero catalogo
     * @return String stringa con tutti i libri
     */
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
