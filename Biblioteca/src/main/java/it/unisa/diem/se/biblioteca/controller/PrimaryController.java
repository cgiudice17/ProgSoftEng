package it.unisa.diem.se.biblioteca.controller;

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

public class PrimaryController {

    private static final String PERCORSO_BASE = "/it/unisa/diem/se/biblioteca/";
    
    @FXML
    private Button BookButton;
    @FXML
    private Button UsersButton;
    @FXML
    private Button LoansButton;

    /**
     * @brief Apre la sezione dedicata alla gestione dei libri.
     * * Metodo invocato dal click sul pulsante "Sezione libri" nel menu principale.
     * Carica la vista FXML relativa ai libri.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenBookSection(ActionEvent event) throws IOException {
        
            
        this.changeScene("booksSection.fxml", event);
        
    }

    /**
     * @brief Apre la sezione dedicata alla gestione degli utenti.
     * * Metodo invocato dal click sul pulsante "Sezione utenti" nel menu principale.
     * Carica la vista FXML relativa agli utenti.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenUsersSection(ActionEvent event) throws IOException {
        this.changeScene("usersSection.fxml", event);
    }

    /**
     * @brief Apre la sezione dedicata alla gestione dei prestiti.
     * * Metodo invocato dal click sul pulsante "Sezione prestiti" nel menu principale.
     * Carica la vista FXML relativa ai prestiti.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenLoansSection(ActionEvent event) throws IOException {
        this.changeScene("loansSection.fxml", event);
    }

    
    private void changeScene(String nomeFileFXML, ActionEvent event) throws IOException {
    
        // Costruisce il percorso completo: /it/unisa/.../nomeFileFXML
        String percorsoCompleto = PERCORSO_BASE + nomeFileFXML;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(percorsoCompleto));
        Parent root = loader.load();

        // Cambia la scena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(800);  // Larghezza in pixel
        stage.setHeight(550); // Altezza in pixel
        stage.show();

    
    }
    
}
