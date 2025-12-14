/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;

/**
 * @brief Gestisce la collezione di tutti gli utenti registrati nel sistema della biblioteca.
 * Utilizza due strutture dati complementari: un TreeSet per l'ordinamento 
 * e una HashMap per la ricerca veloce tramite matricola.
 */
public class UsersCollection implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Set<User> users;  
    private Map<String, User> codeUsers;
    
    /**
     * @brief Costruttore di default
     * Inizializza l'insieme ordinato TreeSet e la mappa HashMap
     */
    public UsersCollection() {
        this.users = new TreeSet<>();
        this.codeUsers = new HashMap<>();
    }
    
    public Set<User> getUsers() {
        return this.users;
    }

    /**
     * @brief Aggiunge un utente alla collezione.
     * L'utente viene aggiunto sia al Set ordinato che alla Map indice. Se un utente con lo stesso 
     * cognome/nome esiste già, viene gestito dal Set in base alla logica di compareTo.
     * @param u L'utente da aggiungere.
     * @throws NullPointerException Se l'utente passato è null.
     */
    public void addUser(User u) {
        if (u == null) {
            throw new NullPointerException("Impossibile aggiungere un utente nullo.");
        }
        users.add(u);
        codeUsers.put(u.getCode(), u);
    }
    
    /**
     * @brief Rimuove un utente da tutte le collezioni interne (Set ordinato e Map indice).
     * @param u L'utente da rimuovere.
     * @throws NullPointerException Se l'utente passato è  null.
     */
    public void removeUser(User u) {
        if (u == null) {
            throw new NullPointerException("Impossibile rimuovere un utente nullo.");
        }
        users.remove(u);
        codeUsers.remove(u.getCode());
    }
    
    /**
     * @brief Restituisce l'utente associato alla matricola.
     * @param code La matricola dell'utente da cercare
     * @return L'oggetto User se trovato, altrimenti null
     */
    public User getUserByCode(String code) {
        if (code == null) return null;
        return this.codeUsers.get(code);
    }

    /**
     * @brief Genera una stringa contenente l'elenco completo di tutti gli utenti registrati.
     * @return Una stringa formattata con la lista degli utenti 
     */
    public String printAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("Elenco Utenti:\n");
        
        if (users.isEmpty()) {
            sb.append("Nessun utente registrato.");
        } else {
            for (User u : users) {
                sb.append(u.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
