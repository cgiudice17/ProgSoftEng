package it.unisa.diem.se.biblioteca.loan;

import it.unisa.diem.se.biblioteca.user.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LoansCollection implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static int maxLoans = 3; 
    
    private Set<Loan> loans;
    private Map<User, List<Loan>> userLoans; 
    
    public LoansCollection(){
        this.loans = new TreeSet<>();
        this.userLoans = new HashMap<>();
    }
        /**
     * @brief Permette di aggiungere nuovi prestiti alla collezione
     *Inoltre verifica se l'utente ha raggiunto il limite massimo di prestiti consentiti.
     * Se il limite non è raggiunto, aggiunge il prestito sia alla collezione globale che alla lista dell'utente,
     * e incrementa il contatore dei prestiti dell'utente.
     *
     * @param l      Il prestito da aggiungere.
     * @return ritorna 0 se l'inserimento ha avuto successo, mentre 1 se l'utente ha raggiunto il limite massimo di prestiti.
     * @post Il prestito è aggiunto alla collezione, e il contatore prestiti dell'utente è incrementato.
     */        
    public int addLoan(Loan l){
       if(l.getUser().getLoanCount() >= maxLoans){
           return 1;
       }
       if(userLoans.containsKey(l.getUser())){
           return 2;
       }
       
       if(!userLoans.containsKey(l.getUser())){
           userLoans.put(l.getUser(), new ArrayList<>());
       }
       userLoans.get(l.getUser()).add(l);
       
       loans.add(l);
       
       l.getUser().setLoanCount(l.getUser().getLoanCount() + 1);
             
       return 0; 
    }
    /**
     * @brief Rimuove un prestito dall'insieme globale e dalla lista personale dell'utente, decrementa
     * il contatore dei prestiti attivi dell'utente.
     * @param l      Il prestito da rimuovere.
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
     *
     * @param u L'utente di cui cercare i prestiti.
     * @return Una lista di oggetti {@link Loan} associati all'utente.
     * Se l'utente non ha prestiti, restituisce una lista vuota (non null).
     */
    public List<Loan> getLoansByUser(User u){
        return this.userLoans.getOrDefault(u, new ArrayList<>());
    }
    
    public Set<Loan> getLoans(){
        return this.loans;
    }
    
    /**
     * 
     * @return 
     */
    
    public int getMaxLoans(){
        return maxLoans;
    }
}
