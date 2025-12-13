/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca;

import it.unisa.diem.se.biblioteca.book.BooksCollection;
import it.unisa.diem.se.biblioteca.loan.LoansCollection;
import it.unisa.diem.se.biblioteca.user.UsersCollection;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @brief La classe Library è il punto di accesso centrale dell'applicazione. 
 * Aggrega le collezioni (Utenti, Libri, Prestiti) e si occupa del salvataggio 
 * dei dati su file e del caricamento dell'intero sistema.
 */
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private LoansCollection loans;
    private BooksCollection books;
    private UsersCollection users;
    
    /**
     * @brief Costruttore di default.
     * Inizializza tutte le collezioni vuote per evitare NullPointerException.
     */
    public Library() {
        this.loans = new LoansCollection();
        this.books = new BooksCollection();
        this.users = new UsersCollection();
    }
    

    public BooksCollection getBooks() {
        return books;
    }

    public LoansCollection getLoans() {
        return loans;
    }

    public UsersCollection getUsers() {
        return users;
    }


    public void setBooks(BooksCollection books) {
        this.books = books;
    }

    public void setLoans(LoansCollection loans) {
        this.loans = loans;
    }

    public void setUsers(UsersCollection users) {
        this.users = users;
    }


    /**
     * @brief Salva l'intero stato della biblioteca (libri, studenti e prestiti) su un file locale.
     * @param filename Il nome del file su cui scrivere i dati della biblioteca.
     * @throws IOException Se ci sono problemi nella scrittura del file.
     */
    public void writeObj(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }
    
    /**
     * @brief Legge l'intero stato della biblioteca da un file locale.
     * @param filename Il file da cui leggere i dati della biblioteca.
     * @return Un'istanza della classe Library contenente i dati caricati, oppure una nuova Library vuota se il file non esiste.
     */
    public static Library readObj(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Library) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {

            System.err.println("Errore nel caricamento (o file non trovato): " + e.getMessage());
            return new Library();
        }
    }
}