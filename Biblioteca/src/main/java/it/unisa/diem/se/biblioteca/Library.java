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
    
    private static Library instance;
    private static final long serialVersionUID = 1L;

   /**
     * @brief Percorso del file corrente utilizzato per il salvataggio/caricamento.
     * È marcato come 'transient' in quanto non deve essere serializzato insieme all'oggetto Library.
     */
    private transient String currentFilePath = "file"; 

    private LoansCollection loans;
    private BooksCollection books;
    private UsersCollection users;

    /**
     * @brief Costruttore privato (tipico del pattern Singleton).
     * Inizializza le collezioni interne: {LoansCollection}, {BooksCollection} e {UsersCollection}.
     */
    private Library() {
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
     * @brief Legge l'intero stato della biblioteca da un file locale.
     * Questo metodo imposta l'istanza Singleton 
     * @param filepath Il file da cui leggere i dati della biblioteca.
     */
    public static void loadFromFile(String filepath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            instance = (Library) ois.readObject();
        }
        
        instance.currentFilePath = filepath;
    }

    /**
     * @brief Crea una nuova istanza vuota della Library (Singleton).
     * * Utilizzato quando non è possibile caricare i dati esistenti.
     * * @param filepath Il percorso del file che sarà utilizzato per i futuri salvataggi.
     */
    public static void createNewLibrary(String filepath) {
        instance = new Library();
        instance.currentFilePath = filepath;
    }

    /**
     * @brief Restituisce l'unica istanza (Singleton) della Library.
     * Questo metodo deve essere chiamato solo dopo loadFromFile(String)
     * @return L'istanza Singleton della Library.
     * @throws IllegalStateException Se l'istanza non è stata ancora caricata o creata.
     */
    public static Library getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ERRORE: La libreria non è stata ancora caricata! Chiama loadFromFile prima.");
        }
        return instance;
    }

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

    public static void resetInstance() {
        instance = null;
    }
}


// FAI GLI ULTIMI 2 