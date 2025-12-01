package it.unisa.diem.se.biblioteca.prova;

/**
 *
 * @author danilocarratu
 */


public class User {
    private String name;
    private String surname;
    private String code;
    private String email;
    private Loan[] loans;
    
    
    public User(String name, String surname, String code, String email){
        this.name = name;
        this.surname = surname;
        this.code = code;
        this.email = email;
        
        this.loans = new Loan[3];
    }
    
    
    
    
    
}
