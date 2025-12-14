package it.unisa.diem.se.biblioteca.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * @brief Rappresenta un utente registrato del sistema biblioteca.
 * La classe è comparabile per l'ordinamento per cognome e nome e implementa ValidUser 
 * per la validazione dei dati anagrafici e della matricola.
 * L'identificatore univoco è la matricola.
 */
public class User implements Comparable<User>, Serializable, ValidUser {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String surname;
    private String code;
    private String email;
    private int loanCount;

    /**
     * @brief Costruttore dell'utente che esegue la validazione di tutti i campi.
     * Vengono eseguite le validazioni tramite i metodi dell'interfaccia ValidUser: nome, cognome, matricola ed email.
     * @param name Il nome dell'utente.
     * @param surname Il cognome dell'utente.
     * @param code La matricola (10 cifre).
     * @param email L'indirizzo email.
     * @throws InvalidUserException Se uno dei dati forniti non rispetta il formato di validazione.
     */
    public User(String name, String surname, String code, String email) throws InvalidUserException {
        
        if (!validName(name)) {
            throw new InvalidUserException("Nome utente non valido: " + name);
        }
        if (!validName(surname)) {
            throw new InvalidUserException("Cognome utente non valido: " + surname);
        }
        if (!validCode(code)) {
            throw new InvalidUserException("Matricola non valida (devono essere 10 cifre): " + code);
        }
        if (!validMail(email)) {
            throw new InvalidUserException("Email non valida (formato unisa richiesto): " + email);
        }

        this.name = name;
        this.surname = surname;
        this.code = code;
        this.email = email;
        this.loanCount = 0;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getLoanCount() { return loanCount; }
    public void setLoanCount(int loanCount) { this.loanCount = loanCount; }


    /**
     * @brief Confronta due utenti per l'ordinamento.
     * L'ordinamento primario è basato sul cognome; se i cognomi sono uguali, l'ordinamento secondario è basato sul nome.
     * @param u L'oggetto tente} con cui confrontare.
     * @return Un intero negativo, zero o positivo se l'utente corrente è rispettivamente 
     * minore, uguale o maggiore dell'utente specificato nell'ordinamento.
     */
    @Override
    public int compareTo(User u) {
        if(this.surname.equals(u.surname)) return this.name.compareTo(u.name);
        return this.surname.compareTo(u.surname);
    }
    
    /**
     * @brief Genera un codice hash per l'utente.
     * L'hash è basato sulla matricola.
     * Viene utilizzato java.util.Objects.hash(Object) per garantire un calcolo dell'hashcode robusto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
    
    /**
     * @brief Confronta questo utente con l'oggetto specificato.
     * L'identità dell'utente è determinata univocamente dalla sua matricola.
     * Due utenti sono considerati uguali se possiedono la stessa matricola.
     * @param o L'oggetto da confrontare con l'istanza corrente.
     * @return true se l'oggetto passato è un Utente e ha lo stesso codice, false altrimenti.
    */
    @Override
    public boolean equals(Object o) {
        if (o == null || !this.getClass().equals(o.getClass())) return false;
        if (this == o) return true;
        User u = (User) o;
        return this.code.equals(u.code);
    }

    /**
     * @brief Restituisce una stringa dell'utente contenente il nome, il cognome, la matricola e l'email 
     */
    @Override
    public String toString() {
        return "Utente: " + name + " " + surname + " | Matricola: " + code + " | Email: " + email;
    }
}

