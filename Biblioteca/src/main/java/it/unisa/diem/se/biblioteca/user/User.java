package it.unisa.diem.se.biblioteca.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @brief L'utente della biblioteca
 * @invariant
 * Il numero massimo di prestiti attivi non è mai maggiore di maxLoans
 * @invariant
 *
 * 
 */


public class User implements Comparable<User>{
    private String name;
    private String surname;
    private String code;
    private String email;
    private int loanCount;
    

    
    
    
    /**
     * @brief Costruttore di default
     * 
     * @param[in] name Nome dell'utente
     * @param[in] surname Cognome dell'utente
     * @param[in] code Matricola dell'utente
     * @param[in] email Email dell'utente
     * 
     * @post Utente inizializzato correttamente.
     * La lista dei presiti associata è inizializzata.
     * 
     */
    public User(String name, String surname, String code, String email){
        this.name = name;
        this.surname = surname;
        this.code = code;
        this.email = email;
        this.loanCount=0;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(int loanCount) {
        this.loanCount = loanCount;
    }
    
    

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    
    /**
     * @brief Override del metodo compareTo che confronta gli user
     *        in base al cognome e, nel caso fosse uguale, in base al nome.
     * 
     * 
     * @param[in] u L'ogetto user da comparare
     * @return Un intero negativo se l'ogetto chiamate viene prima lessigograficamente dell'argomento,
     *         0 se sono uguali0 e un intero positivo se viene dopo.
     */

    @Override
    public int compareTo(User u) {
        if(this.surname.equals(u.surname)) return this.name.compareTo(u.name);
        
        return this.surname.compareTo(u.surname);
    }


    /**
     * @brief Override del metotdo toString per sstampare la classe corrente.
     * 
     */
    @Override
    public String toString(){
        return null;
    }
    
    @Override
    public int hashCode(){
        return this.code.hashCode();
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null || !this.getClass().equals(o.getClass())) return false;
        if(this == o ) return true;
        User u = (User) o;
        return this.code.equals(u.code);
    }
    
    
}
