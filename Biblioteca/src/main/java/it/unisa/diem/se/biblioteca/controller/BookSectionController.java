/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.book.ValidBook;
import it.unisa.diem.se.biblioteca.author.Author;
import it.unisa.diem.se.biblioteca.book.Book;
import it.unisa.diem.se.biblioteca.book.BooksCollection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * @brief Controller per la gestione della sezioe libri (catalogo)
 * Gestisce l'inserimento, la modifica e la rimozione dei libri, si occupa inoltre della gestione delle copie disponibili 
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
    private TableView<Book> BookTable;
    @FXML
    private TableColumn<Book, String> TitleClm;
    @FXML
    private TableColumn<Book, String> AuthorsClm;
    @FXML
    private TableColumn<Book, String> CodeClm;
    @FXML
    private TableColumn<Book, Integer> YearClm;
    @FXML
    private TableColumn<Book, Integer> CopiesClm;
    
    private ObservableList<Book> bookList;
    
    private BooksCollection books = new BooksCollection();

    /**
     * @brief Inizializza il controller e la TableView 
     * Configura il binding tra le colonne e le proprietà dell' oggetto  
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookList = FXCollections.observableArrayList();
        
        
        BookTable.setItems(bookList);
        TitleClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTitle()));
        AuthorsClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAuthors().toString()));
        CodeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getISBN()));
        YearClm.setCellValueFactory(r -> new SimpleIntegerProperty(r.getValue().getPublishYear()).asObject());
        CopiesClm.setCellValueFactory(r -> new SimpleObjectProperty<>(books.getCopies(r.getValue())));
        
        TitleClm.setCellFactory(TextFieldTableCell.forTableColumn());
        AuthorsClm.setCellFactory(TextFieldTableCell.forTableColumn());
        CodeClm.setCellFactory(TextFieldTableCell.forTableColumn());
        YearClm.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        CopiesClm.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        BooleanBinding b = Bindings.or(TitleLabel.textProperty().isEmpty(), AuthorLabel.textProperty().isEmpty())
                .or(CodeLabel.textProperty().isEmpty()).or(YearLabel.textProperty().isEmpty()).or(CopiesLabel.textProperty().isEmpty());
        
        AddBookButton.disableProperty().bind(b);
        
    }    

    /**
     * @brief Gestisce l'aggiunta di un nuovo libro alla collezione.
     * Invocato dal click sul pulsante "Aggiungi libro". Questo metodo raccoglie i dati 
     * dai campi di input, valida le informazioni e, se corrette, crea un nuovo 
     * oggetto Libro inserendolo nella lista osservabile.
     * * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void AddBook(ActionEvent event) {
        
        // Titolo
        String title = this.TitleLabel.getText();
        
        // Verifica nome e cognome di ogni autore
        String[] names = this.AuthorLabel.getText().split(",");
        List<Author> authorsList = new ArrayList();

        for (int i = 0; i < names.length; i++) {
            names[i] = names[i].trim();
            String name = names[i].split(" ")[0];
            String surname = names[i].split(" ")[1];
                    
            if (! this.validAuthor(name)){
                // Da implementare il popup
                return;
            }
            authorsList.add(new Author(name , surname));
        }
        
        // Verifica l'ISBN
        String ISBN = this.CodeLabel.getText();
        if (!this.validISBN(ISBN)){
            // da implementare popup
            return;
        }
        
        // Verifica anno di publicazione
        String year = this.YearLabel.getText();
        if (!this.validYear(year)){
            // Da implementare popup
            return;
        }
        
        // Verifica numero di copie
        String copies = this.CopiesLabel.getText();
        if (!this.validCopies(copies)){
            // Da implementare popup
            return;
        }
        Book b = new Book(title, authorsList, ISBN, Integer.parseInt(year));
        books.addBook(b, Integer.parseInt(copies));
        bookList.setAll(books.getAllBooks());
        
    }

    /**
     * @brief Rimuove il libro selezionato dalla tabella.
     * Invocato dal click sul pulsante "Rimuovi libro". Procede alla sua eliminazione 
     * dalla lista e dal sistema.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void RemoveBook(ActionEvent event) {
        Book b = this.BookTable.getSelectionModel().getSelectedItem();
        
        bookList.remove(b);
        books.removeBook(b);
        
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
     * @brief Esegue la ricerca di libri in base all'input del "BookSearchLabel".
     * Invocato dall'edit del campo di testo "BookSearchLabel". Filtra la lista dei libri visibili all'utente.
     * @param event L'evento generato dalla scrittura sul campo di testo.
     */
    @FXML
    private void BookSearch(ActionEvent event) {
        // 1. Crea la Master List (contiene TUTTI i dati)
ObservableList<Book> masterData = FXCollections.observableArrayList(books.getAllBooks());

// 2. Crea una FilteredList avvolgendo la Master List
// All'inizio p -> true significa "mostra tutto"
FilteredList<Book> filteredData = new FilteredList<>(masterData, p -> true);

// 3. Aggiungi un Listener al TextField di ricerca
BookSearchLabel.textProperty().addListener((observable, oldValue, newValue) -> {
    filteredData.setPredicate(book -> {
        // Se il campo di ricerca è vuoto, mostra tutti i libri
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }

        // Converti il testo cercato in minuscolo per ignorare maiuscole/minuscole
        String lowerCaseFilter = newValue.toLowerCase();

        // --- INIZIO LOGICA DI RICERCA ---
        
        // Controlla se il TITOLO contiene la parola cercata
        if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
            return true; 
        }
        
        // Controlla se gli AUTORI contengono la parola cercata
        // (Assumendo che getAuthors() ritorni una lista o una stringa)
        if (book.getAuthors().toString().toLowerCase().contains(lowerCaseFilter)) {
            return true;
        }
        
        // Controlla ISBN
        if (book.getISBN().toLowerCase().contains(lowerCaseFilter)) {
            return true;
        }

        // Controlla ANNO (convertendo l'int in stringa)
        if (String.valueOf(book.getPublishYear()).contains(lowerCaseFilter)) {
            return true;
        }

        // Se nessuna delle condizioni sopra è vera, nascondi il libro
        return false; 
    });
});

// 4. (Opzionale ma consigliato) Avvolgi in una SortedList
// Questo serve per far funzionare ancora l'ordinamento cliccando sulle colonne
SortedList<Book> sortedData = new SortedList<>(filteredData);

// 5. Collega il comparatore della SortedList a quello della TableView
sortedData.comparatorProperty().bind(BookTable.comparatorProperty());

// 6. Infine, metti la lista ordinata e filtrata nella tabella
BookTable.setItems(sortedData);
        
    }
    
    /**
     * @brief Aggiorna il titolo del libro in seguito alla modifica nella tabella.
     * Invocato quando l'utente conferma la modifica (premendo Invio) nella colonna "Titolo".
     * Aggiorna la proprietà corrispondente all'oggetto con il nuovo valore inserito.
     * @param[in] event L'evento contenente il nuovo valore e l'oggetto modificato.
     */
    @FXML
    private void updateTitle(TableColumn.CellEditEvent<Book, String> event) {
        Book b = event.getRowValue();
        b.setTitle(event.getNewValue());
    }

    /**
     * @brief Aggiorna l'elenco degli autori del libro.
     * Invocato al termine della modifica della cella nella colonna "Autori".
     * Sincronizza il cambiamento visivo con il modello dati sottostante.
     * @param event L'evento contenente il nuovo valore e l'oggetto modificato.
     */
    @FXML
    private void updateAuthors(TableColumn.CellEditEvent<Book, List<Author>> event) {
        Book b = event.getRowValue();
        b.setAuthors(event.getNewValue());
    }

    /**
     * @brief Aggiorna il codice ISBN del libro.
     * Invocato al termine della modifica della cella nella colonna "ISBN".
     * @param event L'evento di modifica della cella contenente il nuovo codice.
     */
    @FXML
    private void updateBookCode(TableColumn.CellEditEvent<Book, String> event) {
        Book b = event.getRowValue();
        b.setISBN(event.getNewValue());
    }

    /**
     * @brief Aggiorna l'anno di pubblicazione del libro.
     * Invocato al termine della modifica della cella nella colonna "Anno di pubblicazione".
     * @param event L'evento di modifica della cella contenente il nuovo anno.
     */
    @FXML
    private void updateYear(TableColumn.CellEditEvent<Book, Integer> event) {
        Book b = event.getRowValue();
        b.setPublishYear(event.getNewValue());
    }

    /**
     * @brief Aggiorna il numero di copie disponibili.
     * Invocato al termine della modifica della cella nella colonna " Numero copie".
     * @param event L'evento di modifica della cella contenente il nuovo numero di copie.
     */
    @FXML
    private void updateCopies(TableColumn.CellEditEvent<Book, Integer> event) {
        Book b = event.getRowValue();
        books.setCopies(b, event.getNewValue());
    }
}
