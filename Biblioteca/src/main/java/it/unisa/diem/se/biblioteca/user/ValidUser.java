package it.unisa.diem.se.biblioteca.user;


public interface ValidUser {
    
    /**
     * @brief Controlla se la matricola è valida (10 interi)
     * @param code La matricola da controllare
     * @return true se la matricola è valida, false altrimenti
     */
    public default boolean validCode(String code){
        if (code == null) return false;
        return code.matches("^\\d{10}$");
    }
    
    
    /**
     * @brief Controlla se il nome/cognome è valido
     * @param name Il nome/cognome da controllare
     * @return true se il nome/cognome è valido, false altrimenti
     */
    public default boolean validName(String name){
        if (name == null) return false;
        return name.matches("^\\p{Lu}[\\p{L}'’]+(?: \\p{Lu}[\\p{L}'’]+)*$");
    }
    
    
    /**
     * @brief Controlla se la mail è valida (es. m.rossi8@studenti.unisa.it)
     * @param mail Mail da controllare
     * @return true se la mail è valida, false altrimenti
     */
    public default boolean validMail(String mail){
        if (mail == null) return false;
        return mail.matches("^\\p{Ll}\\.\\p{Ll}+[\\d]{1,3}@studenti\\.unisa\\.it$");
    }
            
}