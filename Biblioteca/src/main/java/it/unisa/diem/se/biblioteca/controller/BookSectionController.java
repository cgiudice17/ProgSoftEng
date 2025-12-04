/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.checkers.ValidBook;
import it.unisa.diem.se.biblioteca.data.Book;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lucon
 */
public class BookSectionController implements Initializable, ValidBook {

    @FXML
    private TextField TitleLabel;
    @FXML
    private TextField AuthorLabel;
    @FXML
    private TextField CodeLabel;
    @FXML
    private TextField YearLabel;
    @FXML
    private TextField CopiesLabel;
    @FXML
    private Button AddBookButton;
    @FXML
    private Button RemoveButton;
    @FXML
    private Button GoBackButton;
    @FXML
    private TextField BookSearchLabel;
    @FXML
    private TableView<?> BookTable;
    @FXML
    private TableColumn<?, ?> TitleClm;
    @FXML
    private TableColumn<?, ?> AuthorsClm;
    @FXML
    private TableColumn<?, ?> CodeClm;
    @FXML
    private TableColumn<?, ?> YearClm;
    @FXML
    private TableColumn<?, ?> CopiesClm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddBook(ActionEvent event) {
    }

    @FXML
    private void RemoveBook(ActionEvent event) {
    }

    @FXML
    private void GoBack(ActionEvent event) {
    }

    @FXML
    private void BookSearch(ActionEvent event) {
    }

    @FXML
    private void updateTitle(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateAuthors(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateBookCode(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateYear(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateCopies(TableColumn.CellEditEvent<S, T> event) {
    }

    @Override
    public boolean checkBook(Book b) {
        
    }
    
}
