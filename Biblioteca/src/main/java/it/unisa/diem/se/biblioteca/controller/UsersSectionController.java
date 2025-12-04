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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lucon
 */
public class UsersSectionController implements Initializable {

    @FXML
    private TextField NameLabel;
    @FXML
    private TextField SurnameLabel;
    @FXML
    private TextField StudentCodeLabel;
    @FXML
    private TextField EmailLabel;
    @FXML
    private Button AddUserButton;
    @FXML
    private Button RemoveUserButton;
    @FXML
    private Button GoBackButton;
    @FXML
    private TextField UserSearchLabel;
    @FXML
    private TableView<?> UsersTable;
    @FXML
    private TableColumn<?, ?> NameClm;
    @FXML
    private TableColumn<?, ?> SurnameClm;
    @FXML
    private TableColumn<?, ?> NumberClm;
    @FXML
    private TableColumn<?, ?> EmailClm;
    @FXML
    private TableColumn<?, ?> LoanClm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddUser(ActionEvent event) {
    }

    @FXML
    private void RemoveUser(ActionEvent event) {
    }

    @FXML
    private void GoBack(ActionEvent event) {
    }

    @FXML
    private void userSearch(ActionEvent event) {
    }

    @FXML
    private void updateName(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateSurname(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateCode(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateEmail(TableColumn.CellEditEvent<S, T> event) {
    }
    
}
