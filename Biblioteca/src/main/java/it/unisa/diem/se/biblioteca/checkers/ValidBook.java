/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.checkers;

import it.unisa.diem.se.biblioteca.data.Book;


public interface ValidBook {
    
    /**
     * @brief Controlla se un libro è valido, ovvero se il suo ISBN è di 13 cifre.
     * @param b il libro da controllare.
     * @return True se il libro è valido, false altrimenti.
     */
    public boolean checkBook(Book b);
}
