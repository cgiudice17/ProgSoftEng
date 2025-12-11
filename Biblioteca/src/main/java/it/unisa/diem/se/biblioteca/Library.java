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
 * La classe Library è il punto di accesso centrale dell'applicazione. Aggrega le collezioni (Utenti, Libri, Prestiti, Autori) e si occupa del salvataggio dei dati su file e del caricamento dell'intero sistema  
 */
public class Library {
    private AuthorsCollection authors;
    private LoansCollection loans;
    private BooksCollection books;
    private UsersCollection users;
    
    /**
     * @brief Salva l'intero stato della biblioteca (libri, studenti e prestiti) su un file locale.
     * @param filename Il nome del file su cui scrivere i dati della biblioteca
     */
    private static void writeObj(String filename){
    }
    
    /**
     * @brief Legge l'intero stato della biblioteca da un file locale.
     * @param filename Il file da cui leggere i dati della biblioteca
     * @return un istanza della classe Library contenente i dati caricati 
     */
    private static Library readObj(String filename){
        return null;
    }

    
} 