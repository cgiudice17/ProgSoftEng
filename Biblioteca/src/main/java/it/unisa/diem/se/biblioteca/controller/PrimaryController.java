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
     * * Metodo invocato dal click sul pulsante "Libri" (o icona corrispondente) nel menu principale.
     * Carica la vista FXML relativa all'inventario, permettendo l'aggiunta,
     * la rimozione e la ricerca dei volumi.
     * * @param event L'evento ActionEvent che ha scatenato la navigazione.
     */
    @FXML
    private void OpenBookSection(ActionEvent event) {
    }

    /**
     * @brief Apre la sezione dedicata alla gestione degli utenti.
     * * Metodo invocato dal click sul pulsante "Utenti" nel menu principale.
     * Effettua il cambio di scena verso l'interfaccia di anagrafica, dove è possibile
     * visualizzare, iscrivere o cancellare gli utenti della biblioteca.
     * * @param event L'evento ActionEvent che ha scatenato la navigazione.
     */
    @FXML
    private void OpenUsersSection(ActionEvent event) {
    }

    /**
     * @brief Apre la sezione dedicata alla gestione dei prestiti.
     * * Metodo invocato dal click sul pulsante "Prestiti" nel menu principale.
     * Reindirizza l'operatore alla schermata per registrare nuovi prestiti,
     * effettuare restituzioni e controllare le scadenze dei libri.
     * * @param event L'evento ActionEvent che ha scatenato la navigazione.
     */
    @FXML
    private void OpenLoansSection(ActionEvent event) {
    }

    
}
