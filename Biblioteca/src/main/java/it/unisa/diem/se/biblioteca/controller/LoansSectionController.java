/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.book.Book;
import it.unisa.diem.se.biblioteca.book.BooksCollection;
import it.unisa.diem.se.biblioteca.book.ValidBook;
import it.unisa.diem.se.biblioteca.loan.Loan;
import it.unisa.diem.se.biblioteca.loan.LoansCollection;
import it.unisa.diem.se.biblioteca.user.User;
import it.unisa.diem.se.biblioteca.user.UsersCollection;
import it.unisa.diem.se.biblioteca.user.ValidUser;
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
import javafx.scene.control.Alert;
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
public class LoansSectionController implements Initializable, ValidUser, ValidBook {

    @FXML
    private TextField loanSearchLabel;
    @FXML
    private TextField loanStudentLabel;
    @FXML
    private TextField loanBookLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button loanButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button goBackButton;
    @FXML
    private TableView<Loan> loanTable;
    @FXML
    private TableColumn<Loan, String> loanNameClm;
    @FXML
    private TableColumn<Loan, String> loanSurnameClm;
    @FXML
    private TableColumn<Loan, String> loanCodeClm;
    @FXML
    private TableColumn<Loan, String> loanBookTitleClm;
    @FXML
    private TableColumn<Loan, String> loanBookCodeClm;
    @FXML
    private TableColumn<Loan, LocalDate> loanDateClm;
    
    private ObservableList<Loan> loanList;
    private LoansCollection loans = new LoansCollection();
    private UsersCollection users = new UsersCollection();
    private BooksCollection books = new BooksCollection();

    /**
     * @brief Inizializza il controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loanList = FXCollections.observableArrayList(loans.getLoans());
        loanTable.setItems(loanList);

        loanNameClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUser().getName()));
        loanSurnameClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUser().getSurname()));
        loanCodeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUser().getCode()));
        loanBookTitleClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getBook().getTitle()));
        loanBookCodeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getBook().getISBN()));
        loanDateClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getReturnDate()));
        
        BooleanBinding b = Bindings.or(loanStudentLabel.textProperty().isEmpty(), loanBookLabel.textProperty().isEmpty())
                .or(datePicker.valueProperty().isNull());
        
        loanButton.disableProperty().bind(b);
        
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
    private void selectDate(ActionEvent event) {
    }

    /**
     * @brief Esegue l'operazione di registrazione del prestito.
     * Invocato dal click sul pulsante "Registra prestito".
     * Il metodo verifica che ci siano copie disponibili, registra il nuovo prestito nel sistema.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void addLoan(ActionEvent event) {
        String code = this.loanStudentLabel.getText();
        String ISBN = this.loanBookLabel.getText();
        
        if (!this.validCode(code)){
            showError("Errore Matricola", "Formato Matricola non valido", "La matricola non rispetta il formato richiesto (es. 10 cifre).");
            return;
        }
        
        if (users.getUserByCode(code) == null){
            showError("Errore utente non registrato", "Utente non è registrato", "Non esiste un utente registrato con la matricola: " + code);
            return;
        }
        
        if (!this.validISBN(ISBN)){
            showError("Errore ISBN", "Codice ISBN non valido", "Il formato dell'ISBN inserito non è corretto.");
            return;
        }
        
        if (books.getBookByISBN(ISBN) == null){
            showError("Errore libro non esistente", "Libro non esiste", "Non esiste un libro in catalogo con questo codice ISBN.");
            return;
        }
        
        
        
        User u = users.getUserByCode(code);
        Book b = books.getBookByISBN(ISBN);
        LocalDate returnDate = this.datePicker.getValue();
        loans.addLoan(new Loan(u, b, returnDate));
        loanList.setAll(loans.getLoans());
        
        this.loanStudentLabel.clear();
        this.loanBookLabel.clear();
        
    }

    /**
     * @brief Esegue l'operazione di restituzione di un libro.
     * Invocato dal click sul pulsante "Registra restituzione".
     * Il metodo aggiorna la lista dei  prestiti rimuovendo il prestito e aggiornando il numero di copie dei libri.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void returnLoan(ActionEvent event) {
        
    }

    /**
     * @brief Gestisce la navigazione verso la schermata precedente.
     * Invocato dal click sul pulsante "Torna indietro". Torna al menu principale dell'applicazione.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/se/biblioteca/primary.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) goBackButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(800);  
        stage.setHeight(550); 
        stage.show();
    }
    
    @FXML
    private void searchLoan(ActionEvent event){
        
    }
    
    private void showError(String titolo, String intestazione, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(intestazione);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
    
}
