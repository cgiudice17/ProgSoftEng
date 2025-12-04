/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.data;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * 
 */
public class UsersCollection {
    
    private Set<User> users;
    

    
    /**
     * @brief Costruttore di default
     * 
     * @post Collezione inizializzata correttamente.
     */
    public UsersCollection() {
        this.users = new TreeSet();
    }
    
    public void addUser(User u){
        
        users.add(u);
        
    }
    
    public void removeUser(User u){
        users.remove(u);
    }
    
    public User getUserByCode(String matricola){
        return null;
    }
    
    public List<User> getUserbySurname(String surname){
        return null;
    }
    
    
    
    
}
