/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.book;

import it.unisa.diem.se.biblioteca.author.Author;

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
    public boolean validISBN(String ISBN);
    
    /**
     * @brief Controlla se l'autore inserito è valido (controlla se è un nome o un cognome valido).
     * Viene utilizzato dal controller quando si inserisce/cerca un libro
     * @param author Il nome o cogome dell'autore da controllare
     * @return true se l'autore è valido, false altrimenti
     */
    public boolean validAuthor(String author);
    
    /**
     * @brief Controlla se l'anno di pubplicazione inserito è valido (minore o uguale dell'anno odierno).
     * Viene utilizzato dal controller quando si inserisce/cerca un libro
     * @param year l'anno da controllare
     * @return true se l'anno è valido, false altrimenti
     */
    public boolean validYear(int year);
}
