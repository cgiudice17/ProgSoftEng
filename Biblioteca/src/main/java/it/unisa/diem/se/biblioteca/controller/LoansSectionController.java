/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 */
public class LoansSectionController implements Initializable {

    @FXML
    private TextField LoanStudentLabel;
    @FXML
    private TextField LoanBookLabel;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private Button LoanButton;
    @FXML
    private Button ReturnButton;
    @FXML
    private Button GoBackButton;
    @FXML
    private TableView<?> BookTable;
    @FXML
    private TableColumn<?, ?> LoanNameClm;
    @FXML
    private TableColumn<?, ?> LoanSurnameClm;
    @FXML
    private TableColumn<?, ?> LoanCodeClm;
    @FXML
    private TableColumn<?, ?> LoanBookTitleClm;
    @FXML
    private TableColumn<?, ?> LoanBookCodeClm;
    @FXML
    private TableColumn<?, ?> LoanDateClm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SelectDate(ActionEvent event) {
    }

    @FXML
    private void LoanBook(ActionEvent event) {
    }

    @FXML
    private void ReturnBook(ActionEvent event) {
    }

    @FXML
    private void GoBack(ActionEvent event) {
    }
    
}
