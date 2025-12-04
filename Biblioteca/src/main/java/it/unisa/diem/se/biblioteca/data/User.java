package it.unisa.diem.se.biblioteca.data;

import java.util.ArrayList;
import java.util.List;


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
    private List<Loan> loans;
    

    /**
     * @brief Costante usata per definire il numero massimo di possibili prestiti attivi
     */
    private final static int maxLoans = 3; 
    
    
    
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
        
        this.loans = new ArrayList();
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
    
    public List<Loan> getLoans(){
        return loans;
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
    
    
    
    /* DA SPOSTARE DENTRO LOANS */
    
    /**
     * @brief Aggiunge un prestito alla lista dei prestiti dell'utente
     * 
     * @pre Il numero di prestiti attivi deve essere minore di maxLoans
     * @post Il prestito è stato inserito alla lista 
     * 
     * @param[in] l Un prestito valido 
     * @return 
     */
    public int addLoan(Loan l){
       if (l==null){
           return 1;
       }
       if(loans.size() >= maxLoans){
           return 2;
       }
       
       loans.add(l);
       return 0;
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


    
    
    
    
}
