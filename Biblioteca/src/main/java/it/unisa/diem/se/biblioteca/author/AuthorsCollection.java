package it.unisa.diem.se.biblioteca.author;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class AuthorsCollection {
    private static Set<Author> authors;
    private static Map<String, Set<Author>> nameAuthors;
    private static Map<String, Set<Author>> surnameAuthors; 
    
    public AuthorsCollection(){
        this.authors = new HashSet<Author>();
        this.nameAuthors = new HashMap<String, Set<Author>>();
        this.surnameAuthors = new HashMap<String, Set<Author>>();
    }
    
    
    public void addAuthor(Author a){
        
    }
    
    public void removeAuthor(Author a){
        
    }
    
    public Set<Author> getAuthorbyName(String n){
        return null;
    }
    
    public Set<Author> getAuthorbySurname(String s){
        return null;
    }
}
