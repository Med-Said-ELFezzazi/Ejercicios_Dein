package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 * Clase principal de la aplicación que hereda de {@code Application} en JavaFX.
 * Permite la creación de aplicaciones con interfaz gráfica.
 */
public class Main extends Application {
    /**
     * Método principal que se llama al iniciar la aplicación.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxml/Personas.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

			Image imagen = new Image(getClass().getResourceAsStream("/images/agenda.png"));
			primaryStage.getIcons().add(imagen);
			
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
