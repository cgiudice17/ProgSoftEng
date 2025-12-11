package it.unisa.diem.se.biblioteca.author;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @brief Gestisce la collezione degli autori registrati nel sistema.
 * La classe mantiene un elenco principale di tutti gli autori e utilizza strutture dati (mappe) per permettere rapide ricerche basate sul nome o sul cognome dell'autore 
 */
public class AuthorsCollection {
    private Set<Author> authors;
    private Map<String, Set<Author>> nameAuthors;
    private Map<String, Set<Author>> surnameAuthors; 
    
/**
 * @brief Costruttore di default. 
 * Inizializza le mappe di indicizzazione
 */
    public AuthorsCollection(){
        this.authors = new HashSet<Author>();
        this.nameAuthors = new HashMap<String, Set<Author>>();
        this.surnameAuthors = new HashMap<String, Set<Author>>();
    }
    
    /**
     * @brief Aggiunge un autore a tutte le collezioni.
     * @param a L'autore da aggiungere 
     * @pre L'autore è valido.
     * @psot L'autore è aggiunto correttamente nelle strutture dati.
     */
    public void addAuthor(Author a){
        authors.add(a);
    }
    
    
    /**
     * @brief Rimuove un autore da tutte le collezioni.
     * @param a L'autore da rimuovere.
     * @pre L'autore è valido.
     * @post L'autore è rimosso correttamente dal sistema .
     */
    public void removeAuthor(Author a){
        
    }
    
    
    /**
     * @brief  Restituisce gli autori che hanno come nome l'argomento passato.
     * @param n Il nome ricercato.
     * @return Il set di autori con il nome cercato.
     */
    public Set<Author> getAuthorbyName(String n){
        return null;
    }
    
    
    /**
     * @brief  Restituisce gli autori che hanno come cognome l'argomento passato.
     * @param Il cognome ricercato.
     * @return Il set di autori con il cognome cercato.
     *  
     */
    public Set<Author> getAuthorbySurname(String s){
        return null;
    }
    
  
    /**
     * @brief Metodo helper per aggiornare la mappa degli autori indicizzata per nome.
     * Se il nome dell'autore non è ancora presente come chiave, viene inizializzato un nuovo Set.
     * @param a L'autore da aggiungere all'indice per nome.
     */
    private void addAuthorNameHelper(Author a){
        
        
    }
    
    /**
     * @brief Metodo helper per aggiornare la mappa degli autori indicizzata per cognome.
     * Se il cognome dell'autore non è ancora presente come chiave, viene inizializzato un nuovo Set.
     * @param a L'autore da aggiungere all'indice per cognome.
     */
    private void addAuthorSurnameHelper(Author a){
       
    }
}