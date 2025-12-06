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
    
    private static Set<User> users;
    private static Map<String, User> codeUsers;
    private static Map<String, List<User>> surnameUsers;
    
    

    
    /**
     * @brief Costruttore di default
     * 
     * @post Collezione inizializzata correttamente.
     */
    public UsersCollection() {
        this.users = new TreeSet();
        this.codeUsers = new HashMap();
        this.surnameUsers = new HashMap();
    }
    
    /**
     * @brief Aggiunge un utente alla mappa degli utenti.
     * @param u L'utente da aggingere
     * @pre L'utente è valido
     */
    
    public void addUser(User u){
        
        users.add(u);
        codeUsers.put(u.getCode(), u);
        this.addSurnameUsersHelper(u.getSurname(), u);
        
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
        surnameUsers.get(u.getSurname()).remove(u); 
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
    
    /**
     * 
     * @brief Restituisce gli utenti col cognome passato
     * 
     * @param[in] surname
     * @return La lista degli utenti con quel cognome
     * @pre  Il cognome sia valido
     * @post Gli utenti sono stati restituiti 
     */
    
    public List<User> getUserbySurname(String surname){
        return this.surnameUsers.get(surname);
    }
    
    private void addSurnameUsersHelper(String s, User u){
        if(!surnameUsers.containsKey(s)){
            surnameUsers.put(s, new ArrayList());
        }
        
        surnameUsers.get(s).add(u);
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
