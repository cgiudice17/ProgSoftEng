package it.unisa.diem.se.biblioteca.prova;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author danilocarratu
 */


public class User {
    private String name;
    private String surname;
    private String code;
    private String email;
    private List<Loan> loans;
    

    private final static int maxLoans = 3;
    
    
    
    public User(String name, String surname, String code, String email){
        this.name = name;
        this.surname = surname;
        this.code = code;
        this.email = email;
        
        this.loans = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public int addLoan(Loan l){
       if(loans.size() >= maxLoans){
           System.out.println("Numero massimo di prestiti raggiunto");
           return 1;
       }
       
       loans.add(l);
       return 0;
    }
    
    
    
    
}
