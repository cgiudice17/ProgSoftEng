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
     * Viene utilizzato java.util.Objects.hash(Object) per garantire un calcolo dell'hashcode robusto.
     */
    @Override
    public int hashCode(){
        return java.util.Objects.hash(name, surname) * 31;
    }

    /**
     * @brief Confronta questo autore con l'oggetto specificato.
     * La verifica dell'uguaglianza e data dal confronto delle stringhe name e surname.
     * @param o L'oggetto da confrontare con questo autore.
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

    /**
     * @brief Restituisce una stringa dell'autore contenente il nome e il cognome su due righe separate.
     */
    @Override
    public String toString(){
        return name + " " + surname;
    }
}



