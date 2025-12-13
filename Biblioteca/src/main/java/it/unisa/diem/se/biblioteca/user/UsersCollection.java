/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * 
 */
public class UsersCollection {
    
    private Set<User> users;
    private Map<String, User> codeUsers;
    
    

    
    /**
     * @brief Costruttore di default
     * 
     * @post Collezione inizializzata correttamente.
     */
    public UsersCollection() {
        this.users = new TreeSet();
        this.codeUsers = new HashMap();
    }
    
    /**
     * @brief Aggiunge un utente alla mappa degli utenti.
     * @param u L'utente da aggingere
     * @pre L'utente è valido
     */
    
    public void addUser(User u){
        
        users.add(u);
        codeUsers.put(u.getCode(), u);
        
    }
    
    /**
     * 
     * @brief Rimuove un utente da tutte le collezioni
     * 
     * @param[in] u 
     * @pre L'utente da rimuovere sia valido e si trovi nelle collezioni
     * @post L'utente è stato rimosso correttamente da tutte le collezioni
     */
    
    public void removeUser(User u){
        users.remove(u);
        codeUsers.remove(u.getCode());
    }
    
    /**
     * 
     * @brief Restituisce l'utente associato alla matricola
     * 
     * @param[in] matricola
     * @return L'utente associato alla matricola
     * @pre  La matricola sia valida
     * @post L'utente è stato restituito 
     */
    
    public User getUserByCode(String code){
        return this.codeUsers.get(code);
    }
    
    public Set<User> getUsers(){
        return this.users;
    }
    


    
    /**
     * @brief Restituisce una stringa contenente tutti gli utenti 
     * 
     * @return String tutti gli utenti
     */
    public static String printAll(){
        return null;
    }
}
