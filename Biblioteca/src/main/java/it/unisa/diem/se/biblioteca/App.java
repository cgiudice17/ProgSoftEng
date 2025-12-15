package it.unisa.diem.se.biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @brief JavaFX: classe principale dell'applicazione. 
 * Questa classe estende javafx.application.Application; e gestisce il ciclo di vita dell'interfaccia grafica, 
 * l'inizializzazione dello stage e il caricamento dei file FXML e dei DATI.
 */
public class App extends Application {

    private static Scene scene;
    private static final String DATA_FILE = "biblioteca.bin";

    /**
     * @brief Metodo di avvio dell'applicazione JavaFX. 
     * Inizializza lo stage primario e imposta la scena iniziale 
     * @param stage fornito dalla piattaforma JavaFX
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Library.loadFromFile(DATA_FILE);
        } catch (Exception e) {
            
            System.out.println("File dati non trovato o errore di lettura. Creazione nuova libreria.");
            Library.createNewLibrary(DATA_FILE);
        }
        // Caricamento interfaccia 
        scene = new Scene(loadFXML("primary"), 660, 500);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @brief Metodo chiamato automaticamente quando l'applicazione viene chiusa (stop).
     * Qui eseguiamo il salvataggio dei dati su file.
     */
    @Override
    public void stop() {
        System.out.println("Chiusura applicazione in corso...");
        
        try {
            Library.getInstance().save();
        } catch (Exception e) {
            System.err.println("Impossibile salvare i dati: " + e.getMessage());
        }
    }

    /**
     * @brief Sostituisce la radice (root) della scena corrente con il contenuto di un nuovo file FXML.
     * @param fxml il nome del file FXML da caricare 
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * @brief Carica un file FXML e restituisce il nodo radice (Parent) della scena associata.
     * Questo metodo è un helper statico per caricare file FXML a partire dal percorso base della classe App.
     * @param fxml Il nome del file FXML da caricare (escludendo l'estensione ".fxml" e il percorso base).
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
