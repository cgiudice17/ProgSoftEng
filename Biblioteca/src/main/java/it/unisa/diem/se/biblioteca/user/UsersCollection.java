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
 * @brief Gestisce la collezione degli utenti della biblioteca
 */
public class UsersCollection implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // TreeSet mantiene gli utenti ordinati (usando il compareTo di User)
    private Set<User> users;
    
    // HashMap permette la ricerca veloce tramite matricola
    private Map<String, User> codeUsers;
    
    /**
     * @brief Costruttore di default
     * @post Collezione inizializzata correttamente.
     */
    public UsersCollection() {
        this.users = new TreeSet<>();
        this.codeUsers = new HashMap<>();
    }
    
    /**
     * @brief Aggiunge un utente alla collezione.
     * @param u L'utente da aggiungere
     * @throws NullPointerException se l'utente passato è null
     */
    public void addUser(User u) {
        if (u == null) {
            throw new NullPointerException("Impossibile aggiungere un utente nullo.");
        }
        users.add(u);
        codeUsers.put(u.getCode(), u);
    }
    
    /**
     * @brief Rimuove un utente da tutte le collezioni.
     * @param u L'utente da rimuovere
     * @throws NullPointerException se l'utente passato è null
     */
    public void removeUser(User u) {
        if (u == null) {
            throw new NullPointerException("Impossibile rimuovere un utente nullo.");
        }
        users.remove(u);
        
        // Rimuoviamo anche dalla mappa usando il codice
        // Nota: u.getCode() è sicuro qui perché abbiamo controllato che u non sia null
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
     * @brief Restituisce l'intero set di utenti.
     * @return Il Set degli utenti ordinati
     */
    public Set<User> getUsers() {
        return this.users;
    }
    
    /**
     * @brief Restituisce una stringa contenente l'elenco di tutti gli utenti.
     * Non è statico perché accede ai dati dell'istanza corrente.
     * @return String formattata con l'elenco degli utenti
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