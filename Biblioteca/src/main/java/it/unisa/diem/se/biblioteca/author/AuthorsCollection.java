package it.unisa.diem.se.biblioteca.author;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class AuthorsCollection {
    private Set<Author> authors;
    private Map<String, Set<Author>> nameAuthors;
    private Map<String, Set<Author>> surnameAuthors; 
    
    public AuthorsCollection(){
        this.authors = new HashSet<Author>();
        this.nameAuthors = new HashMap<String, Set<Author>>();
        this.surnameAuthors = new HashMap<String, Set<Author>>();
    }
    
    /**
     * @brief Aggiunge un autore a tutte le collezioni.
     * @param[in] a L'autore da aggiungere
     * 
     * @pre L'autoreè valido.
     * @psot L'autore è aggiunto correttamente.
     */
    public void addAuthor(Author a){
        authors.add(a);
    }
    
    
    /**
     * @brief Rimuove un autore da tutte le collezioni.
     * @param[in] a Lìautore da rimuovere.
     * @pre L'autore è valido.
     * @post L'autore è rimosso correttamente.
     */
    public void removeAuthor(Author a){
        
    }
    
    
    /**
     * @brief  Restituisce gli autori che hanno come nome l'argomento passato.
     * @param[in] n Il nome ricercato.
     * @return Il set di autori con il nome cercato.
     */
    public Set<Author> getAuthorbyName(String n){
        return null;
    }
    
    
    /**
     * @brief  Restituisce gli autori che hanno come cognome l'argomento passato.
     * @param[in] n Il cognome ricercato.
     * @return Il set di autori con il cognome cercato.
     *  
     */
    public Set<Author> getAuthorbySurname(String s){
        return null;
    }
    
  
    private void addAuthorNameHelper(Author a){
        
        
    }
    
    private void addAuthorSurnameHelper();
}
