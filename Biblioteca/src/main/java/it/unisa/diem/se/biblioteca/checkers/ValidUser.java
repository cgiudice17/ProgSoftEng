/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.checkers;

import it.unisa.diem.se.biblioteca.data.User;


public interface ValidUser {
    
    /**
     * @brief Controlla se un utente è valido, ovvero se la sua matricola è di 13 cifre.
     * @param u l'utente da controllare.
     * @return True se l'utente è valido, false altrimenti.
     */
    public boolean checkUser(User u);
    
}
