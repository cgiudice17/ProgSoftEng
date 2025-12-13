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
    public default boolean validCode(String code){
        return code.matches("^\\d{10}$");
    }
    
    
    /**
     * @brief Controlla se il cognome è valido (solo caratteri)
     * 
     * Viene utilizzato dal controller quando si inserisce/cerca un utente
     * 
     * @param name Il cognome da controllare
     * @return true se il cognome è valido, false altrimenti
     */
    public default boolean validName(String name){
        return name.matches("^\\p{Lu}[\\p{L}'’]+(?: \\p{Lu}[\\p{L}'’]+)*$");
    }
    
    
    /**
     * @brief Controlla se la mail è valida (es. m.rossi8@studenti.unisa.it)
     * 
     * Viene utilizzato dal controller quando si inserisce/cerca un utente
     * 
     * @param mail Mail da controllare
     * @return true se la mail è valida, false altrimenti
     */
    public default boolean validMail(String mail){
        return mail.matches("^\\p{Ll}\\.\\p{Ll}+[\\d]{1,3}@studenti\\.unisa\\.it$");
    }
            
}
