/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.loan.Loan;
import it.unisa.diem.se.biblioteca.loan.LoansCollection;
import it.unisa.diem.se.biblioteca.user.User;
import it.unisa.diem.se.biblioteca.user.UsersCollection;
import it.unisa.diem.se.biblioteca.user.ValidUser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * @brief Controller per la gestione della sezione utenti. 
 * Gestisce le operazioni di gestione degli utenti 
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
    private TableView<User> UsersTable;
    @FXML
    private TableColumn<User, String> NameClm;
    @FXML
    private TableColumn<User, String> SurnameClm;
    @FXML
    private TableColumn<User, String> NumberClm;
    @FXML
    private TableColumn<User, String> EmailClm;
    @FXML
    private TableColumn<User, String> LoanClm;
    
    private ObservableList<User> userList;
    // Da mettere in Library per avere la collezione già inizializzata quando leggi da file
    private UsersCollection users = new UsersCollection();
    private LoansCollection loans = new LoansCollection();

    /**
     * @brief Inizialliza la classe del controller e comfigura la TableView.
     * Imposta la lista osservabile per la tabella, definisce le factory per le celle e collega le colonne alle proprietà dell'oggetto
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userList = FXCollections.observableArrayList(users.getUsers());
        
        UsersTable.setItems(userList);
        NameClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getName()));
        SurnameClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getSurname()));
        NumberClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getCode()));
        EmailClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getEmail()));
        LoanClm.setCellValueFactory(cellData -> {
        User currentUser = cellData.getValue();
        List<Loan> activeLoans = loans.getLoansByUser(currentUser);
        
        if (activeLoans == null || activeLoans.isEmpty()) {
            return new SimpleStringProperty("Nessun prestito");
        }
        
        StringBuilder sb = new StringBuilder();
        for (Loan l : activeLoans) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(l.getBook().getTitle()); 
        }
        
        return new SimpleStringProperty(sb.toString());
    });
        
        NameClm.setCellFactory(TextFieldTableCell.forTableColumn());
        SurnameClm.setCellFactory(TextFieldTableCell.forTableColumn());
        NumberClm.setCellFactory(TextFieldTableCell.forTableColumn());
        EmailClm.setCellFactory(TextFieldTableCell.forTableColumn());
        
        BooleanBinding b = Bindings.or(NameLabel.textProperty().isEmpty(), SurnameLabel.textProperty().isEmpty())
                .or(StudentCodeLabel.textProperty().isEmpty()).or(EmailLabel.textProperty().isEmpty());
        
        AddUserButton.disableProperty().bind(b);
        
    }

    /**
     * @brief Gestisce l'aggiunta di un nuovo utente al sistema.
     * Invocato dal click sul pulsante "Aggiungi utente". Raccoglie i dati inseriti 
     * nei campi di testo (nome, cognome,matricola, email), valida l'input tramite `checkUser` 
     * e registra il nuovo utente nella lista osservabile.
     * * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void AddUser(ActionEvent event) {
        // Nome e cognome
        String name = this.NameLabel.getText().trim();
        String surname = this.SurnameLabel.getText().trim();
        
        // Verifica campi vuoti (controllo extra per sicurezza)
        if (name.isEmpty() || surname.isEmpty()) {
            showError("Errore Input", "Campi mancanti", "Per favore inserisci sia il Nome che il Cognome.");
            return;
        }
        
        // Verifica validità nome e cognome (es. caratteri speciali)
        if (!validName(name) || !validName(surname)){
            showError("Errore Formato", "Nome o Cognome non validi", "Inserire solo lettere valide, senza numeri o simboli speciali e con la prima lettera maiuscola.");
            return;
        }
                
        
        // Verifica la matricola
        String studentCode = this.StudentCodeLabel.getText();
        
        if (!this.validCode(studentCode)){
            showError("Errore Matricola", "Formato Matricola non valido", "La matricola non rispetta il formato richiesto (es. 10 cifre).");
            return;
        }
        
        if (users.getUserByCode(studentCode) != null){
            showError("Errore Duplicato", "Utente già esistente", "Esiste già un utente registrato con la matricola: " + studentCode);
            return;
        }
        
        // Verifica l'e-mail
        String mail = this.EmailLabel.getText();
        if (!this.validMail(mail)){
            showError("Errore Email", "Email non valida", "L'indirizzo email inserito non è valido o non è istituzionale.");
            return;
        }
        
   

        User u = new User(name, surname, studentCode, mail);
  
        users.addUser(u);
        userList.setAll(users.getUsers());
        
        
        this.NameLabel.clear();
        this.SurnameLabel.clear();
        this.StudentCodeLabel.clear();
        this.EmailLabel.clear();
  
    }

    /**
     * @brief Rimuove l'utente selezionato dalla lista.
     * Invocato dal click sul pulsante "Rimuovi utente". Procede all'eliminazione dell'utente selezionato
     * dall'archivio della biblioteca.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void RemoveUser(ActionEvent event) {
        User u = this.UsersTable.getSelectionModel().getSelectedItem();
        
        userList.remove(u);
        users.removeUser(u);
    }

    /**
     * @brief Gestisce la navigazione verso la schermata precedente.
     * Invocato dal click sul pulsante "Torna indietro". Torna al menu principale dell'applicazione.
     * @param event L'evento  generato dal click sul pulsante.
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
     * Invocato dall'edit del campo di testo "UserSearchLabel" . Filtra la TableView mostrando 
     * solo gli utenti che corrispondono ai criteri ( nome o matricola).
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void userSearch(ActionEvent event) {
        
        userList.setAll(users.getUsers());
        FilteredList<User> filteredData = new FilteredList<>(userList, p -> true);
        UserSearchLabel.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(user -> {
            // Se il campo di ricerca è vuoto, mostra tutti i libri
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            // Controllo nome
            if (user.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }

            // Controllo cognome
            // (Assumendo che getAuthors() ritorni una lista o una stringa)
            if (user.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            // Controllo matricola
            if (user.getCode().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            // Controllo email
            if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            // Se nessuna delle condizioni sopra è vera, nascondi il libro
            return false; 
            });
        });


        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(UsersTable.comparatorProperty());
        UsersTable.setItems(sortedData);
    }
    
    /**
     * @brief Aggiorna il nome dell'utente dopo la modifica in tabella.
     * Viene chiamato quando viene modificata la cella "Nome".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * @param event L'evento di modifica contenente il nuovo nome.
     */
    @FXML
    private void updateName(TableColumn.CellEditEvent<User, String> event) {
        User u = event.getRowValue();
        u.setName(event.getNewValue());
        
    }

    /**
     * @brief Aggiorna il cognome dell'utente dopo la modifica in tabella.
     * Viene chiamato quando viene modificata la cella "Cognome".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * @param event L'evento di modifica contenente il nuovo cognome.
     */
    @FXML
    private void updateSurname(TableColumn.CellEditEvent<User , String> event) {
        User u = event.getRowValue();
        u.setSurname(event.getNewValue());
    }

    /**
     * @brief Aggiorna la matricola dell'utente dopo la modifica in tabella.
     * Viene chiamato quando viene modificata la cella "Matricola".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * @param event L'evento di modifica contenente il nuovo codice.
     */
    @FXML
    private void updateCode(TableColumn.CellEditEvent<User, String> event) {
        User u = event.getRowValue();
        u.setCode(event.getNewValue());
    }

    /**
     * @brief Aggiorna l'e-mail dell'utente dopo la modifica in tabella.
     * Viene chiamato quando viene modificata la cella "E-mail istituzionale".
     * Salva il nuovo valore nell'oggetto User corrispondente.
     * @param event L'evento di modifica contenente la nuova email.
     */
    @FXML
    private void updateEmail(TableColumn.CellEditEvent<User, String> event) {
        User u = event.getRowValue();
        u.setEmail(event.getNewValue());
    }    
    
    private void showError(String titolo, String intestazione, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(intestazione);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}

