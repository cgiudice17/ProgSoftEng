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
 * @brief Gestisce l'intero catalogo dei libri della biblioteca 
 * Utilizza molteplici mappe per indicizzare i libri e permettere ricerche efficienti, basate sui seguenti criteri: ISBN, Autore, Titolo e Anno di pubblicazione.
 * Gestisce inoltre il conteggio delle copie disponibili per ogni libro
 */
public class BooksCollection {
    
    private Map<Book, Integer> books;
    private Map<Author, Set<Book>> authorBooks;
    private Map<String, Book> ISBNBooks;
    private Map<String, Set<Book>> titleBooks;
    private Map<Integer, Set<Book>> yearBooks;
    
    /**
     * @brief Costruttore di default
     * Inizializza la mappa principale
     */
    public BooksCollection() {
        this.books = new TreeMap();
    }
    
    /**
     * @brief Aggiunge un nuovo libro al catalogo e aggiorna tutti gli indici di ricerca 
     * @param b  libro da aggiungere
     * @pre Il libro da aggiungere sia valido 
     * @post Il libro è aggiunto correttamente a tutte le collezioni 
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
     * @brief Rimuove un libro dal catalogo e da tutti gli indici associsti 
     * @param b Il libro da rimuovere.
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
     * @brief Recupera un libro tramite il suo codice ISBN
     * @param ISBN Il codice ISBN del libro da recuperare
     * @return il libro corrispondente al codice ISBN specificato
     */
    public Book getBookByISBN(String ISBN){
        return null;
    }
    
    /**
     * @brief Recupera un libro tramite il titolo specificato 
     * @param[ title Il titolo del libro da recuperare
     * @return Il libro dal titolo specificato
     */
    public List<Book> getBookbyTitle(String title){
        return null;
    }
    
    /**
     * @brief Restituisce una lista di libri scritti dall'autore specificato
     * @param author L'autore del libro da recuperare
     * @return Lista di libri associata all'autore
     */
    public List<Book> getBookbyAuthor(Author author){
        return null;
    }
    
    /**
     * @brief Restituisce una lista di libri con l'anno di pubblicazione specificato
     * @param year Anno di pubblicazione scelto
     * @return Lista di libri corrispondenti  all'anno specificato
     */
    public List<Book> getBookbyYear(int year){
        return null;
    }

    /**
     * @brief Aggiorna il numero di copie disponibili per un libro già presente nel catalogo.
     * Utilizza il metodo replace per sovrascrivere il valore associato alla chiave libro, permettendo di modificare la disponibilità in seguito a prestiti, restituzioni o nuovi acquisti.
     * @param b Il libro di cui si vuole modificare la disponibilità.
     * @param copies Il nuovo numero di copie da impostare.
     * @pre Il libro deve essere già presente nella collezione 'books'.
     * @post Il numero di copie del libro viene aggiornato al nuovo valore specificato.
     */
    public void setCopies(Book b,int copies){
        books.replace(b, books.get(b), copies);
    }
    
    public int getCopies(Book b) {
        return books.getOrDefault(b, 0);
    }
    
    /**
     * @brief Metodo helper per aggiornare la mappa dei libri indicizzata per autore.
     * * Se l'autore non è ancora presente come chiave, viene inizializzata una nuova lista.
     * * @param a L'autore da usare come chiave.
     * @param b Il libro da aggiungere alla lista associata all'autore.
     */
   
    private void addAuthorBooksHelper(Author a, Book b){
        if(!authorBooks.containsKey(a)){
            authorBooks.put(a, new ArrayList());
        }
        
        authorBooks.get(a).add(b);
    }
    
    /**
     * @brief Metodo helper per aggiornare la mappa dei libri indicizzata per titolo.
     * * Se il titolo non è ancora presente come chiave, viene inizializzata una nuova lista.
     * * @param t Il titolo da usare come chiave.
     * @param b Il libro da aggiungere alla lista associata al titolo.
     */
    private void addTitleBooksHelper(String t, Book b){
        if(!titleBooks.containsKey(t)){
            titleBooks.put(t, new ArrayList());
        }
        
        titleBooks.get(t).add(b);
    }
    
    /**
     * @brief Restituisce una rappresentazione in formato stringa dell'intero catalogo
     * @return String stringa con tutti i libri
     */
    public static String printAll(){
        return null;
    }
    
}
