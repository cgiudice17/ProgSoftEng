/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;
import java.time.LocalDate;

/**
 * @brief Interfaccia che definisce i contratti per la validazione dei dati di un libro 
 * Le classi che implemantano questa interfaccia (o i controller che la utilizzano) devono garantire che i dati inseriti nel sistema rispettino i formati standard richiesti  
*/
public interface ValidBook {
    
    /**
     * @brief Controlla se l'ISBN inserito è valido.
     * Viene utilizzato dal controller quando si inserisce/cerca un libro
     * @param ISBN stringa di cifre rappresentante l'ISBN da controllare
     * @return true se l'ISBN è valido, false altrimenti
     */
    public default boolean validISBN(String ISBN){
        if (ISBN == null) return false; // Protezione contro NullPointer
        return ISBN.matches("^978\\d{10}") || ISBN.matches("^979\\d{10}");
    }
    
    /**
     * @brief Controlla se l'autore inserito è valido (controlla se è un nome o un cognome valido).
     * Viene utilizzato dal controller quando si inserisce/cerca un libro
     * @param name Il nome e cogome dell'autore da controllare
     * @return true se l'autore è valido, false altrimenti
     */
    public default boolean validAuthor(String name){
        if (name == null) return false; // Protezione contro NullPointer
        // ^p{Lu} perchè la prima lettera deve essere maiuscola
        // [\\p{L}'’]+ perchè i caratteri seguenti possono avere anche apostrofo
        // (?: \\p{Lu}[\\p{L}'’]+)* Significa che se ci sta uno spazio, il secondo nome deve essere maiuscolo e conforme al primo
        return name.matches("^\\p{Lu}[\\p{Ll}]*(?:['’\\-]\\p{Lu}\\p{Ll}+)*(?:['’\\-]\\p{Ll}+)*(?: \\p{Lu}[\\p{L}'’\\-]+)*$");
    }
    
    
    // Da vedere bene non ne sono sicuro, penso abbia più senso mettere la verifica
    // quando si inizializza un nuovo author
    public default boolean validAuthor(Author a){
        if (a == null) return false; // Protezione contro NullPointer
        return validAuthor(a.getName() + " " + a.getSurname());
    }
    
    /**
     * @brief Controlla se l'anno di pubplicazione inserito è valido (minore o uguale dell'anno odierno).
     * Viene utilizzato dal controller quando si inserisce/cerca un libro
     * @param year l'anno da controllare
     * @return true se l'anno è valido, false altrimenti
     */
    public default boolean validYear(String year){  
        if (year == null) return false; // Protezione contro NullPointer
        return year.matches("^\\d+$") && Integer.parseInt(year) <= LocalDate.now().getYear();
    }
    
    public default boolean validYear(int year){    
        return year <= LocalDate.now().getYear();
    }
    
    /**
     * @brief Controlla se il numero di copie inserito è valido (maggiore di 0).
     * Viene utilizzato dal controller quando si inserisce
     * @param copies le copie da inserire
     * @return true se le copie sono valide, false altrimenti
     */
    public default boolean validCopies(String copies){
        if (copies == null) return false; // Protezione contro NullPointer
        return copies.matches("^\\d+$") && Integer.parseInt(copies) > 0;
    }
    
    public default boolean validCopies(int copies){
        return copies > 0;
    }
}