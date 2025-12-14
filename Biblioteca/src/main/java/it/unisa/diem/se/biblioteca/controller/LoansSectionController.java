/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.Library;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
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
    private LoansCollection loans = Library.getInstance().getLoans();
    private UsersCollection users = Library.getInstance().getUsers();
    private BooksCollection books = Library.getInstance().getBooks();

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
        
        loanDateClm.setCellFactory(column -> {
            return new TableCell<Loan, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle(""); // Pulisce lo stile
                    } else {
                        // Imposta il testo della data (puoi formattarlo se vuoi)
                        setText(item.toString());

                        // Logica per il colore
                        if (item.isBefore(LocalDate.now())) {
                            // Testo Rosso e in Grassetto se scaduto
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                        } else {
                            // Stile standard (nero) altrimenti
                            setStyle("-fx-text-fill: black;");
                        }
                    }
                }
            };
        });
        
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
        int v = loans.addLoan(new Loan(u, b, returnDate));
        if (v==1){
            showError("Errore prestiti", "Superato il numero massimo di prestiti", "Il numero massimo di prestiti attivi è: " + loans.getMaxLoans());
            return;
        }
        if (v==2){
            showError("Errore prestiti", "Libro già preso in prestito dall'utente", "Per favore seleziona un altro utente o libro");
            return;
        }
        loanList.setAll(loans.getLoans());
        
        this.loanStudentLabel.clear();
        this.loanBookLabel.clear();
        this.datePicker.setValue(null);
    }

    /**
     * @brief Esegue l'operazione di restituzione di un libro.
     * Invocato dal click sul pulsante "Registra restituzione".
     * Il metodo aggiorna la lista dei  prestiti rimuovendo il prestito e aggiornando il numero di copie dei libri.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void returnLoan(ActionEvent event) {
        Loan l = this.loanTable.getSelectionModel().getSelectedItem();
        
        loanList.remove(l);
        loans.removeLoan(l);
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
        loanList.setAll(loans.getLoans());

        FilteredList<Loan> filteredData = new FilteredList<>(loanList, p -> true);

        loanSearchLabel.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(loan -> {
 
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            
            String lowerCaseFilter = newValue.toLowerCase();
            
            // Controllo sul nome
            if (loan.getUser().getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }
            
            // Controllo sul cognome
            if (loan.getUser().getSurname().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }
            
            // Controllo su matricola
            if (loan.getUser().getCode().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }
            
            // Controllo su Email
            if (loan.getUser().getEmail().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }

            // Controllo sul titolo
            if (loan.getBook().getTitle().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }

            // Controllo ISBN
            if (loan.getBook().getISBN().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            
            // Controllo per data
            if (loan.getReturnDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }
            
  
            return false; 
            });
        });

    
        SortedList<Loan> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(loanTable.comparatorProperty());
        loanTable.setItems(sortedData);  
    }
        


    private void showError(String titolo, String intestazione, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(intestazione);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
    
}
