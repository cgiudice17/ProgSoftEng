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
import it.unisa.diem.se.biblioteca.book.InvalidBookException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
        bookList = FXCollections.observableArrayList(books.getBooks());
        
        
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
        if (title == null || title.isEmpty()) {
            showError("Errore Input", "Titolo mancante", "Per favore inserisci il titolo del libro.");
            return;
        }
        
        // Verifica nome e cognome di ogni autore
        String authorsText = this.AuthorLabel.getText();
        if (authorsText == null || authorsText.isEmpty()) {
             showError("Errore Input", "Autore mancante", "Per favore inserisci almeno un autore.");
             return;
        }

        String[] names = authorsText.split(",");
        List<Author> authorsList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            names[i] = names[i].trim();
            
            // Controllo preventivo per evitare crash se manca il cognome
            String[] nameParts = names[i].split(" ");
            if (nameParts.length < 2) {
                showError("Errore Formato Autore", "Formato non valido per: " + names[i], "Inserire 'Nome Cognome' separati da spazio.");
                return;
            }

            String name = nameParts[0];
            String surname = nameParts[1];
                    
            if (!this.validAuthor(name)){
                showError("Errore Autore", "Nome autore non valido", "Il nome dell'autore '" + name + "' contiene caratteri non validi.");
                return;
            }
            authorsList.add(new Author(name , surname));
        }
        
        // Verifica l'ISBN
        String ISBN = this.CodeLabel.getText();
        if (!this.validISBN(ISBN)){
            showError("Errore ISBN", "Codice ISBN non valido", "Il formato dell'ISBN inserito non è corretto.");
            return;
        }
        
        if (books.getBookByISBN(ISBN) != null){
            showError("Errore Duplicato", "Libro già presente", "Esiste già un libro in catalogo con questo codice ISBN.");
            return;
        }
        
        // Verifica anno di publicazione
        String year = this.YearLabel.getText();
        if (!this.validYear(year)){
            showError("Errore Anno", "Anno non valido", "Inserire un anno numerico valido (es. 2024).");
            return;
        }
        
        // Verifica numero di copie
        String copies = this.CopiesLabel.getText();
        if (!this.validCopies(copies)){
            showError("Errore Copie", "Numero copie non valido", "Inserire un numero intero positivo.");
            return;
        }
   
        // Creazione e aggiunta libro
        try {
            Book b = new Book(title, authorsList, ISBN, Integer.parseInt(year));
            books.addBook(b, Integer.parseInt(copies));
            bookList.setAll(books.getBooks());
            
            // Pulizia campi
            this.TitleLabel.clear();
            this.AuthorLabel.clear();
            this.CodeLabel.clear();
            this.YearLabel.clear();
            this.CopiesLabel.clear();
            
        } catch (InvalidBookException e) {
            showError("Errore nella creazione del libro", "Errore di creazione", "Controllare i parametri passati");
        }
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
      
        bookList.setAll(books.getBooks());

        FilteredList<Book> filteredData = new FilteredList<>(bookList, p -> true);

        BookSearchLabel.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(book -> {
            // Se il campo di ricerca è vuoto, mostra tutti i libri
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            
            String lowerCaseFilter = newValue.toLowerCase();

            // Controllo sul titolo
            if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }

            // Controllo sugli autori
            if (book.getAuthors().toString().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            // Controllo ISBN
            if (book.getISBN().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            // Controllo anno 
            if (String.valueOf(book.getPublishYear()).contains(lowerCaseFilter)) {
                return true;
            }

            // Se nessuna delle condizioni sopra è vera, nascondi il libro
            return false; 
            });
        });

    
        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(BookTable.comparatorProperty());
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
    private void updateCopies(TableColumn.CellEditEvent<Book, Integer> event) throws InvalidBookException {
        Book b = event.getRowValue();
        books.setCopies(b, event.getNewValue());
    }
    
    
    private void showError(String titolo, String intestazione, String messaggio) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(intestazione);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
