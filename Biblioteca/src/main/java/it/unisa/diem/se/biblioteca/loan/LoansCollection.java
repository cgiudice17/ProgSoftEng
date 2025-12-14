package it.unisa.diem.se.biblioteca.loan;

import it.unisa.diem.se.biblioteca.Library;
import it.unisa.diem.se.biblioteca.book.InvalidBookException;
import it.unisa.diem.se.biblioteca.user.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief Gestisce l'intera collezione di prestiti attivi all'interno della biblioteca.
 * La classe indicizza i prestiti in un insieme globale (ordinato per data di restituzione) 
 * e li mappa per singolo utente.
 */
public class LoansCollection implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static int maxLoans = 3; 
    private Set<Loan> loans;
    private Map<User, List<Loan>> userLoans; 
    
    /**
     * @brief Costruttore di default.
     * Inizializza l'insieme globale dei prestiti (TreeSet) e la mappa per utente (HashMap).
     */
    public LoansCollection(){
        this.loans = new TreeSet<>();
        this.userLoans = new HashMap<>();
    }

    public Set<Loan> getLoans(){
        return this.loans;
    }

    public int getMaxLoans(){
        return maxLoans;
    }
      
   /**
     * @brief Aggiunge un nuovo prestito alla collezione, verificando prima se l'utente ha raggiunto il limite massimo.
     * Se il limite non è raggiunto, aggiunge il prestito all'insieme globale, alla lista dell'utente e incrementa il contatore dei prestiti dell'utente.
     * @param l Il  prestito da aggiungere.
     * @return Ritorna 0 se l'inserimento ha avuto successo, 1 se l'utente ha raggiunto il limite massimo di prestiti, 
     * oppure 2 se il prestito (stesso utente/libro) è già presente.
     * @throws NullPointerException Se il prestito passato è null.
     */           
    public int addLoan(Loan l){
        if (l == null) {
            throw new NullPointerException("Il prestito non può essere null.");
        }

        if(l.getUser().getLoanCount() >= maxLoans){
            return 1; 
        }
        
        List<Loan> userLoanList = userLoans.computeIfAbsent(l.getUser(), k -> new ArrayList<>());

        if(userLoanList.contains(l)){
            return 2;
        }

        userLoanList.add(l);
        loans.add(l);
        
        l.getUser().setLoanCount(l.getUser().getLoanCount() + 1);
        return 0; 
    }

    /**
     * @brief Rimuove un prestito dall'insieme globale e dalla lista personale dell'utente.
     * Inoltre decrementa il contatore dei prestiti attivi dell'utente.
     * @param l Il prestito da rimuovere.
     * @post Il prestito viene rimosso e il contatore dell'utente viene decrementato
     */    
    public void removeLoan(Loan l){
        if (l == null) return;
        loans.remove(l);
        
        User u = l.getUser();
        if (userLoans.containsKey(u)) {
            userLoans.get(u).remove(l);
        }
        if (u.getLoanCount() > 0) {
            u.setLoanCount(u.getLoanCount() - 1);
        }
    }

    /**
     * @brief Restituisce la lista dei prestiti associati a uno specifico utente.
     * @param u L'utente di cui cercare i prestiti.
     * @return Una lista di oggetti associati all'utente.
     * Se l'utente non ha prestiti, restituisce una lista vuota (non null).
     */
    public List<Loan> getLoansByUser(User u){
        return this.userLoans.getOrDefault(u, new ArrayList<>());
    }
}
