package it.unisa.diem.se.biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @brief JavaFX: classe principale dell'applicazione. 
 * Questa classe estende Application e gestisce il ciclo di vita dell'interfaccia grafica, l'inizializzazione dello stage e il caricamente dei file FXML
 */
public class App extends Application {

    private static Scene scene;
    /** @brief Metodo di avvio dell'applicazione JavaFX. Inizializza lo stage primario 
     *  @param stage fornito dall piattaforma JavaFX
    */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    
    /**@brief Sostituisce il contenuto della scena attuale caricando un nuovo file FXML.
     * Questo permette di passare facilmente da una sezione all'altra all'interno dell'applicazione.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**@brief Carica un file FXML e restituisce il nodo genitore
     *@param nome del file FXML da caricare 
     *@return il nodo parent 
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}