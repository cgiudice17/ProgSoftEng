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
            
    public int addLoan(Loan l){
       if(l.getUser().getLoanCount() >= maxLoans){
           return 1; // Errore: Limite raggiunto
       }
       
       if(!userLoans.containsKey(l.getUser())){
           userLoans.put(l.getUser(), new ArrayList<>());
       }
       userLoans.get(l.getUser()).add(l);
       
       loans.add(l);
       
       l.getUser().setLoanCount(l.getUser().getLoanCount() + 1);
             
       return 0; // Successo
    }
    
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
    
    public List<Loan> getLoansByUser(User u){
        return this.userLoans.getOrDefault(u, new ArrayList<>());
    }
    
    public Set<Loan> getLoans(){
        return this.loans;
    }
}