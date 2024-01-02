package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxml/Reloj.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

			Image imagen = new Image(getClass().getResourceAsStream("/images/Icono.ico"));
			primaryStage.getIcons().add(imagen);
			
			// Cargar la imagen que deseas usar como fondo
	        Image backgroundImage = new Image(getClass().getResource("/images/fondoReloj.png").toExternalForm());
	        
	        // Crear un BackgroundImage
	        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
	        
	        // Crear un Background
	        Background backgroundWithImage = new Background(background);
	        
	        // Establecer el fondo del AnchorPane
	        root.setBackground(backgroundWithImage);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("RELOJ");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
