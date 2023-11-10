package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * La clase principal de la aplicación que hereda de {@code Application} en JavaFX.
 * Permite la creación de aplicaciones con interfaz gráfica.
 */
public class Main extends Application {
    /**
     * El método principal que se llama al iniciar la aplicación.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML (definición de la interfaz gráfica)
            AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxml/encuesta.fxml"));

            // Crear una nueva escena con el diseño cargado desde el archivo FXML
            Scene scene = new Scene(root);

            // Agregar una hoja de estilo CSS a la escena
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

            // Configurar la ventana principal (escenario)
            primaryStage.setTitle("ENCUESTA");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Manejar cualquier excepción que pueda ocurrir al cargar la interfaz gráfica
            e.printStackTrace();
        }
    }

    /**
     * El método principal para iniciar la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
