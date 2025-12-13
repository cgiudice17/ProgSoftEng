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
import java.io.*;

public class Library implements Serializable {
    
    private static Library instance;
    private static final long serialVersionUID = 1L;

    // Questo campo serve a ricordare su quale file stiamo lavorando per i salvataggi futuri
    // 'transient' perché non vogliamo salvarlo DENTRO il file binario, è solo per la sessione corrente
    private transient String currentFilePath = "file"; 

    private LoansCollection loans;
    private BooksCollection books;
    private UsersCollection users;

    private Library() {
        this.loans = new LoansCollection();
        this.books = new BooksCollection();
        this.users = new UsersCollection();
    }
    
    /**
     * @brief Legge l'intero stato della biblioteca da un file locale.
     * @param filepath Il file da cui leggere i dati della biblioteca.
     * @return Un'istanza della classe Library contenente i dati caricati, oppure una nuova Library vuota se il file non esiste.
     */
    public static void loadFromFile(String filepath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            instance = (Library) ois.readObject();
        }
        
        instance.currentFilePath = filepath;
    }

    
    public static void createNewLibrary(String filepath) {
        instance = new Library();
        instance.currentFilePath = filepath;
    }

    
    public static Library getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ERRORE: La libreria non è stata ancora caricata! Chiama loadFromFile prima.");
        }
        return instance;
    }

    /**
     * @brief Salva l'intero stato della biblioteca (libri, studenti e prestiti) su un file locale.
     * 
     * @throws IOException Se ci sono problemi nella scrittura del file.
     */
    public void save() {
        if (currentFilePath == null) {
            System.err.println("Errore: Nessun file specificato per il salvataggio.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentFilePath))) {
            oos.writeObject(this);
            System.out.println("Salvato con successo su: " + currentFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}