package it.unisa.diem.se.biblioteca.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * @brief L'utente della biblioteca
 */
public class User implements Comparable<User>, Serializable, ValidUser {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String surname;
    private String code;
    private String email;
    private int loanCount;

    /**
     * @brief Costruttore con validazione
     * @throws InvalidUserException se i dati non rispettano il formato
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



    @Override
    public int compareTo(User u) {
        if(this.surname.equals(u.surname)) return this.name.compareTo(u.name);
        return this.surname.compareTo(u.surname);
    }

    @Override
    public String toString() {
        return "Utente: " + name + " " + surname + " | Matricola: " + code + " | Email: " + email;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !this.getClass().equals(o.getClass())) return false;
        if (this == o) return true;
        User u = (User) o;
        return this.code.equals(u.code);
    }
}