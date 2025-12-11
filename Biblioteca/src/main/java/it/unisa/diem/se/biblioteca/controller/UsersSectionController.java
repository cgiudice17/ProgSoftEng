/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.user.User;
import it.unisa.diem.se.biblioteca.user.ValidUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * 
 */
public class UsersSectionController implements Initializable, ValidUser {

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
     * @brief Inizialliza la classe del controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * @brief Gestisce l'aggiunta di un nuovo utente al sistema.
     * * Invocato dal click sul pulsante "Aggiungi utente". Raccoglie i dati inseriti 
     * nei campi di testo (nome, cognome,matricola, email), valida l'input tramite `checkUser` 
     * e registra il nuovo utente nella lista osservabile.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void AddUser(ActionEvent event) {
    }

    /**
     * @brief Rimuove l'utente selezionato dalla lista.
     * * Invocato dal click sul pulsante "Rimuovi utente". Procede all'eliminazione dell'utente selezionato
     * dall'archivio della biblioteca.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void RemoveUser(ActionEvent event) {
    }

    /**
     * @brief Gestisce la navigazione verso la schermata precedente.
     * * Invocato dal click sul pulsante "Torna indietro". Torna al menu principale dell'applicazione.
     * * @param[in] event L'evento  generato dal click sul pulsante.
     */
    @FXML
    private void GoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/se/biblioteca/primary.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) GoBackButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(800);  
        stage.setHeight(550); 
        stage.show();
    }

    /**
     * @brief Esegue la ricerca degli utenti.
     * * Invocato dall'edit del campo di testo "UserSearchLabel" . Filtra la TableView mostrando 
     * solo gli utenti che corrispondono ai criteri ( nome o matricola).
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void userSearch(ActionEvent event) {
    }
    
    /**
     * @brief Aggiorna il nome dell'utente dopo la modifica in tabella.
     * * Viene chiamato quando viene modificata la cella "Nome".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * * @param[in] event L'evento di modifica contenente il nuovo nome.
     */
    @FXML
    private void updateName(TableColumn.CellEditEvent<User, String> event) {
    }

    /**
     * @brief Aggiorna il cognome dell'utente dopo la modifica in tabella.
     * * Viene chiamato quando viene modificata la cella "Cognome".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * * @param[in] event L'evento di modifica contenente il nuovo cognome.
     */
    @FXML
    private void updateSurname(TableColumn.CellEditEvent<User , String> event) {
    }

    /**
     * @brief Aggiorna la matricola dell'utente dopo la modifica in tabella.
     * * Viene chiamato quando viene modificata la cella "Matricola".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * * @param[in] event L'evento di modifica contenente il nuovo codice.
     */
    @FXML
    private void updateCode(TableColumn.CellEditEvent<User, String> event) {
    }

    /**
     * @brief Aggiorna l'e-mail dell'utente dopo la modifica in tabella.
     * * Viene chiamato quando viene modificata la cella "E-mail istituzionale".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * * @param event L'evento di modifica contenente la nuova email.
     */
    @FXML
    private void updateEmail(TableColumn.CellEditEvent<User, String> event) {
    }

    @Override
    public boolean validCode(String code) {
        return true;
    }

    @Override
    public boolean validSurname(String surname) {
        return true;
    }
    
    
    
}

