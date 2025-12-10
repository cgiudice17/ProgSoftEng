package it.unisa.diem.se.biblioteca.author;

import java.util.Objects;


public class Author {
    private String name;
    private String surname;
    
    /**
     * @brief Costruttore di default
     * 
     * @param[in] name Nome autore
     * @param[in] surname Cognome autore
     * 
     * @post Autore inizializzato correttamente
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
     * @brief Override del metotdo toString per sstampare la classe corrente.
     * 
     */
    @Override
    public String toString(){
        return "Nome: " + name + "\nCognome: " + surname;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(name, surname) * 31;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass()){
            return false;
        }
        if (this == o){
            return true;
        }
        
        Author a = (Author) o;
        return (this.name == a.name) && (this.surname == a.surname); 
    }
}
