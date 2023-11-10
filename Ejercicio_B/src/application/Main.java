package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;

public class Main extends Application {
	  /**
     * Método principal que se llama al iniciar la aplicación.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
	@Override
	public void start(Stage primaryStage) {
		try {
            // Cargar el archivo FXML (definición de la interfaz gráfica)
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxml/Personas.fxml"));
			Scene scene = new Scene(root);
            // Agregar una hoja de estilo CSS a la escena
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			// Añadimos el icono de la ventana
			Image imagen = new Image(getClass().getResourceAsStream("/images/agenda.png"));
			primaryStage.getIcons().add(imagen);
			// Configurar la ventana principal (escenario)
			primaryStage.setScene(scene);
			primaryStage.setTitle("PERSONAS");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * Método principal para iniciar la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
	public static void main(String[] args) {
		launch(args);
	}
}
