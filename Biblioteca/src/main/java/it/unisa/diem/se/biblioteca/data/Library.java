/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.data;

import it.unisa.diem.se.biblioteca.collections.AuthorsCollection;
import it.unisa.diem.se.biblioteca.collections.BooksCollection;
import it.unisa.diem.se.biblioteca.collections.LoansCollection;
import it.unisa.diem.se.biblioteca.collections.UsersCollection;

/**
 *
 * 
 */
public class Library {
    private AuthorsCollection authors;
    private LoansCollection loans;
    private BooksCollection books;
    private UsersCollection users;
    
    
    
    private static void writeObj(String filename){
        
    }
    
    private static Library readObj(String filename){
        return null;
    }

    
}
