package it.unisa.diem.se.biblioteca.author;

import java.util.Objects;

/**
 *@brief rappresenta un autore di un libro all'interno del sistema.
 *Questa classe serve a memorizzare i dati anagrafici dell'autore. Fondamentale per l'indicizzazione dei liobri e la gestione del catalo.
 */
public class Author {
    private String name;
    private String surname;
    
/**
 * @brief Costruisce un nuovo oggetto Autore
 * @param name  il nome dell'autore
 * @param surname il cognome autore
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
     * @brief Resituisce una rappresentazione in formato stringa della classe 
     * @return stringa contenente nomee cognome dell'autore
     */
    @Override
    public String toString(){
        return "Nome: " + name + "\nCognome: " + surname;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(name, surname) * 31;
    }
    
    /**
     *@brief verifica l'uguaglianza tra 2 autori 
     * Due autori sono considerati uguali se hanno lo stesso nome o lo stesso cognome 
     *@param l'ogetto con cui confrontare l'autore 
     *@return true se gli oggetti confrontati sono uguali, false altrimenti 
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
        return (this.name == a.name) && (this.surname == a.surname); 
    }
}




