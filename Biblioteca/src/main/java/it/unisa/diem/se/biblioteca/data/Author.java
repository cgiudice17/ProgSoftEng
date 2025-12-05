package it.unisa.diem.se.biblioteca.data;

/**
 *
 * @author danilocarratu
 */
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
    
    
    /**
     * @brief Override del metotdo toString per sstampare la classe corrente.
     * 
     */
    @Override
    public String toString(){
        return null;
    }
}
