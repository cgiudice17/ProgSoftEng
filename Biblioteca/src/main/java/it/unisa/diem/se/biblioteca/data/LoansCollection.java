/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 
 */
public class LoansCollection {
    
    /**
     * @brief Costante usata per definire il numero massimo di possibili prestiti attivi
     */
    private final static int maxLoans = 3; 
    
    private Map<User, List<Loan>> userLoans; 
    private Map<Book, List<Loan>> bookLoans;
    
    /**
     * @brief Costruttore di default
     * 
     * @post Collezione inizializzata correttamente.
     */
    public LoansCollection(){
        this.userLoans = new HashMap();
        this.bookLoans = new HashMap();
    }
            
            
            
    /**
     * @brief Aggiunge un prestito alla lista dei prestiti dell'utente
     * 
     * @pre Il numero di prestiti attivi deve essere minore di maxLoans
     * @post Il prestito è stato inserito alla lista 
     * 
     * @param[in] l Un prestito valido 
     * @return 1 se l'utente ha già maxLoans prestiti, 0 se è stato aggiunto il prestito.
     */
    public int addLoan(Loan l){
       
       if((l.getUser().getLoanCount()) >= maxLoans){
           return 1;
       }
       
       addUserLoanHelper(l);
        
       addBookLoanHelper(l);
       
       
       return 0;
    }
    
    /**
     * @brief Rimuove un prestito dalla collezione dei prestiti dell'utente
     * 
     * @pre Il prestito deve essere nella collezione
     * @post Il prestito è stato riomosso dalla collezione 
     * 
     * @param[in] l Un prestito valido 
     *  
     */
    public void removeLoan(Loan l){
        
    }
    
    /**
     * @brief Metodo helper per aggiungere un prestito alla mappa dei prestiti con chiave utente.
     * @param[in] l il prestito da inserire
     */
   
    private void addUserLoanHelper(Loan l){
        User u = l.getUser();
        if(!userLoans.containsKey(u)){
            userLoans.put(u, new ArrayList());
        }
        
        userLoans.get(u).add(l);
    }
    
    /**
     * @brief Metodo helper per aggiungere un prestito alla mappa dei prestiti con chiave libro.
     * @param[in] l il prestito da inserire
     */
   
    private void addBookLoanHelper(Loan l){
        Book b = l.getBook();
        if(!bookLoans.containsKey(b)){
            bookLoans.put(b, new ArrayList());
        }
        
        bookLoans.get(b).add(l);
    }
    
    /**
     * @brief Restituisce una lista di prestiti dell' utente passato.
     * @param[in] u L'utente di cui si vuole i prestiti 
     * 
     * @pre L'utente è valido
     * 
     * @return lista dei prestiti
     */
    
    public List<Loan> getLoansByUser(User u){
        return null;
    }
    
    /**
     * @brief Restituisce una lista di prestiti del libro passato.
     * @param[in] b Il libro di cui si vuole i prestiti 
     * 
     * @pre Il libro è valido
     * 
     * @return lista dei prestiti
     */
    
    public List<Loan> getLoansByBook(Book b){
        return null;
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
