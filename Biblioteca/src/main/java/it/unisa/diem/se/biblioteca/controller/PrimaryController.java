package it.unisa.diem.se.biblioteca.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

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
    private void OpenBookSection(ActionEvent event) {
    }

    /**
     * @brief Apre la sezione dedicata alla gestione degli utenti.
     * * Metodo invocato dal click sul pulsante "Sezione utenti" nel menu principale.
     * Carica la vista FXML relativa agli utenti.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenUsersSection(ActionEvent event) {
    }

    /**
     * @brief Apre la sezione dedicata alla gestione dei prestiti.
     * * Metodo invocato dal click sul pulsante "Sezione prestiti" nel menu principale.
     * Carica la vista FXML relativa ai prestiti.
     * * @param[in] event L'evento generato dal click sul pulsante.
     */
    @FXML
    private void OpenLoansSection(ActionEvent event) {
    }

    
}
