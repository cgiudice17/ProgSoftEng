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
     * @brief Inizializza il controllore.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    
    /**
     * @brief Gestisce la selezione di uno studente per il prestito.
     * * Invocato quando l'utente scrive nel "LoanStudentLabel". 
     * Questo metodo aggiorna la vista per mostrare lo studente di un dato prestito.
     * * @param[in] event L'evento generato dalla scrittura nel campo di testo.
     */
    @FXML
    private void selectStudent(ActionEvent event) {
    }
    
    
    /**
     * @brief Gestisce la selezione di libro per il prestito.
     * * Invocato quando l'utente scrive nel "LoanBookLabel". 
     * Questo metodo aggiorna la vista per mostrare il libro di un dato prestito.
     * * @param[in] event L'evento generato dalla scrittura nel campo di testo.
     */
    @FXML
    private void selectBook(ActionEvent event) {
    }

    /**
     * @brief Gestisce la selezione di una data di restituzione tramite il DatePicker.
     * * Invocato quando l'utente sceglie una data dal calendario. 
     * Questo metodo aggiorna la vista per mostrare la scedenza di un dato prestito.
     * * @param[in] event L'evento generato dalla selezione della data.
     */
    @FXML
    private void SelectDate(ActionEvent event) {
    }

    /**
     * @brief Esegue l'operazione di registrazione del prestito.
     * * Invocato dal click sul pulsante "Registra prestito".
     * Il metodo verifica che ci siano copie disponibili, registra il nuovo prestito nel sistema.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void LoanBook(ActionEvent event) {
    }

    /**
     * @brief Esegue l'operazione di restituzione di un libro.
     * * Invocato dal click sul pulsante "Registra restituzione".
     * Il metodo aggiorna la lista dei  prestiti rimuovendo il prestito e aggiornando il numero di copie dei libri.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void ReturnBook(ActionEvent event) {
    }

    /**
     * @brief Gestisce la navigazione verso la schermata precedente.
     * * Invocato dal click sul pulsante "Torna indietro". Torna al menu principale dell'applicazione.
     * * @param[in] event L'evento  generato dal click sul pulsante.
     */
    @FXML
    private void GoBack(ActionEvent event) {
    }
    
}
