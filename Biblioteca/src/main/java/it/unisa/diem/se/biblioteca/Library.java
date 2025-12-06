/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca;

import it.unisa.diem.se.biblioteca.author.AuthorsCollection;
import it.unisa.diem.se.biblioteca.book.BooksCollection;
import it.unisa.diem.se.biblioteca.loan.LoansCollection;
import it.unisa.diem.se.biblioteca.user.UsersCollection;
/**
 *
 * 
 */
public class Library {
    private AuthorsCollection authors;
    private LoansCollection loans;
    private BooksCollection books;
    private UsersCollection users;
    
    /**
     * @brief Salva l'intera biblioteca (libri, studenti e prestiti) in un file locale.
     * @param filename Il nome del file su cui scrivere la biblioteca
     */
    
    private static void writeObj(String filename){
        
    }
    
    /**
     * @brief Legge una biblioteca da un file locale.
     * @param filename Il file da cui leggere la biblioteca
     * @return La biblioteca letta.
     */
    private static Library readObj(String filename){
        return null;
    }

    
}
