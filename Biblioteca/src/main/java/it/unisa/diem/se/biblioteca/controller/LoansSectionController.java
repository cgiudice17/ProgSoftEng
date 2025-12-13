/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.loan.Loan;
import it.unisa.diem.se.biblioteca.loan.LoansCollection;
import it.unisa.diem.se.biblioteca.user.UsersCollection;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @brief Controller per la gestione della sezione prestiti 
 * Gestisce il ciclo di vita dei prestiti (creazione, visualizzazione e restituzione) verificando la disponibilità delle risorse e associando utenti e libri 
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
    private TableView<Loan> LoanTable;
    @FXML
    private TableColumn<Loan, String> LoanNameClm;
    @FXML
    private TableColumn<Loan, String> LoanSurnameClm;
    @FXML
    private TableColumn<Loan, String> LoanCodeClm;
    @FXML
    private TableColumn<Loan, String> LoanBookTitleClm;
    @FXML
    private TableColumn<Loan, String> LoanBookCodeClm;
    @FXML
    private TableColumn<Loan, LocalDate> LoanDateClm;
    
    private ObservableList<Loan> loanList;
    private LoansCollection loans = new LoansCollection();
    private UsersCollection users = new UsersCollection();

    /**
     * @brief Inizializza il controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loanList = FXCollections.observableArrayList(loans.getLoans());
        LoanTable.setItems(loanList);

        LoanNameClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUser().getName()));
        LoanSurnameClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUser().getSurname()));
        LoanCodeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUser().getCode()));
        LoanBookTitleClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getBook().getTitle()));
        LoanBookCodeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getBook().getISBN()));
        LoanDateClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getReturnDate()));
        
        BooleanBinding b = Bindings.or(LoanStudentLabel.textProperty().isEmpty(), LoanBookLabel.textProperty().isEmpty())
                .or(DatePicker.valueProperty().isNull());
        
        LoanButton.disableProperty().bind(b);
        
    }   
    
    
    /**
     * @brief Gestisce la selezione di uno studente per il prestito.
     * Invocato quando l'utente scrive nel "LoanStudentLabel". 
     * permette di identificare l'utente a cui associare il prestito tramite matricola o nome 
     * @param event L'evento generato dalla scrittura nel campo di testo.
     */
    @FXML
    private void selectStudent(ActionEvent event) {
    }
    
    
    /**
     * @brief Gestisce la selezione di libro per il prestito.
     * Invocato quando l'utente scrive nel "LoanBookLabel". 
     * Questo metodo aggiorna la vista per mostrare il libro di un dato prestito.
     * @param event L'evento generato dalla scrittura nel campo di testo.
     */
    @FXML
    private void selectBook(ActionEvent event) {
    }

    /**
     * @brief Gestisce la selezione di una data di restituzione tramite il DatePicker.
     * Invocato quando l'utente sceglie una data dal calendario. 
     * Questo metodo aggiorna la vista per mostrare la scedenza di un dato prestito.
     * @param event L'evento generato dalla selezione della data.
     */
    @FXML
    private void SelectDate(ActionEvent event) {
    }

    /**
     * @brief Esegue l'operazione di registrazione del prestito.
     * Invocato dal click sul pulsante "Registra prestito".
     * Il metodo verifica che ci siano copie disponibili, registra il nuovo prestito nel sistema.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void LoanBook(ActionEvent event) {
    }

    /**
     * @brief Esegue l'operazione di restituzione di un libro.
     * Invocato dal click sul pulsante "Registra restituzione".
     * Il metodo aggiorna la lista dei  prestiti rimuovendo il prestito e aggiornando il numero di copie dei libri.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void ReturnBook(ActionEvent event) {
    }

    /**
     * @brief Gestisce la navigazione verso la schermata precedente.
     * Invocato dal click sul pulsante "Torna indietro". Torna al menu principale dell'applicazione.
     * @param event L'evento generato dal click sul pulsante.
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
    
}
