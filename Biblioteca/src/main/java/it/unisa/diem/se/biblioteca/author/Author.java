package it.unisa.diem.se.biblioteca.author;

import java.util.Objects;
import java.io.Serializable;

/**
 * @brief rappresenta un autore di un libro all'interno del sistema.
 * Questa classe serve a memorizzare i dati anagrafici dell'autore.
 * Fondamentale per l'indicizzazione dei libri, la ricerca e la gestione del catalo.
 */
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;    
    private String name;
    private String surname;
    
/**
 * @brief Costruisce un nuovo oggetto Autore
 * @param name  il nome dell'autore
 * @param surname il cognome autore
 */
    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }   
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * @brief Genera un codice hash per l'autore.
     * L'hash è basato sulla combinazione di nome e cognome.
     */
    @Override
    public int hashCode(){
        return Objects.hash(name, surname) * 31;
    }
    
    /**
     * @brief verifica l'uguaglianza tra 2 autori 
     * Due autori sono considerati uguali se hanno lo stesso nome e lo stesso cognome 
     * @param l'ogetto con cui confrontare l'autore 
     * @return true se gli oggetti confrontati sono uguali, false altrimenti 
     */
    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass()){
            return false;
        }
        if (this == o){
            return true;
        }
        
        Author a = (Author) o;
        return this.name.equals(a.name) && this.surname.equals(a.surname); 
    }

    @Override
    public String toString(){
        return "Nome: " + name + "\nCognome: " + surname;
    }
}

// FAI ULTIMO


