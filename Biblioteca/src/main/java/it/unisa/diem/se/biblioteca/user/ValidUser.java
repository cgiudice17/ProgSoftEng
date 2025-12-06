/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.user;


public interface ValidUser {
    
    /**
     * @brief Controlla se la matricola è valida (10 interi)
     * 
     * Viene utilizzato dal controller quando si inserisce/cerca un utente
     * 
     * @param code La matricola da controllare
     * @return true se la matricola è valida, false altrimenti
     */
    public boolean validCode(String code);
    
    
    /**
     * @brief Controlla se il cognome è valido (solo caratteri)
     * 
     * Viene utilizzato dal controller quando si inserisce/cerca un utente
     * 
     * @param surname Il cognome da controllare
     * @return true se il cognome è valido, false altrimenti
     */
    public boolean validSurname(String surname);
    
}
