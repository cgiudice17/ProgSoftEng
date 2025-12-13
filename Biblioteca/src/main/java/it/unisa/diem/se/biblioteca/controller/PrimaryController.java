package it.unisa.diem.se.biblioteca.controller;

import it.unisa.diem.se.biblioteca.Library;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * @brief Controller principale dell'applicazione 
 * Gestisce la navigazione tra le diverse sezioni del sistema (Libri, Utenti, Prestiti)
 */
public class PrimaryController {

    private static final String PERCORSO_BASE = "/it/unisa/diem/se/biblioteca/";
    
    @FXML
    private Button BookButton;
    @FXML
    private Button UsersButton;
    @FXML
    private Button LoansButton;
    @FXML
    private Button loadFileButton;
    @FXML
    private Button CreateNewButton;

    /**
     * @brief Apre la sezione dedicata alla gestione dei libri.
     * Metodo invocato dal click sul pulsante "Sezione libri" nel menu principale.
     * Carica la vista FXML relativa ai libri.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenBookSection(ActionEvent event) throws IOException {
        
            
        this.changeScene("booksSection.fxml", event);
        
    }

    /**
     * @brief Apre la sezione dedicata alla gestione degli utenti.
     * Metodo invocato dal click sul pulsante "Sezione utenti" nel menu principale.
     * Carica la vista FXML relativa agli utenti.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenUsersSection(ActionEvent event) throws IOException {
        this.changeScene("usersSection.fxml", event);
    }

    /**
     * @brief Apre la sezione dedicata alla gestione dei prestiti.
     * Metodo invocato dal click sul pulsante "Sezione prestiti" nel menu principale.
     * Carica la vista FXML relativa ai prestiti.
     * @param event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenLoansSection(ActionEvent event) throws IOException {
        this.changeScene("loansSection.fxml", event);
    }
    
    @FXML
    private void loadFile(ActionEvent event){
        Library.loadFromFile();
    }
    
    @FXML
    private void createNewLibrary(ActionEvent event){
        Library.createNewLibrary();
    }

    /**
     * @brief Metodo helper per il csmbio della scena.
     * Carica il file FXML specificsto, imposta la nuova root e ridimensiona lo stage 
     * @param nomeFileFXML nome del file FXML da caricare 
     * @param event l'evento che ha scatenato il cambio di scena, usato per recuperare lo stage corrente 
     */
    private void changeScene(String nomeFileFXML, ActionEvent event) throws IOException {
    
        String percorsoCompleto = PERCORSO_BASE + nomeFileFXML;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(percorsoCompleto));
        Parent root = loader.load();

        
        
        Stage stage = (Stage) BookButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1000);  
        stage.setHeight(800); 
        stage.centerOnScreen();
        stage.show();

    
    }
    
}
