package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxml/MiniCooper.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

			Image imagen = new Image(getClass().getResourceAsStream("/logos/Cooper.png"));
			primaryStage.getIcons().add(imagen);
			
			// Cargar la imagen que deseas usar como fondo
	        Image backgroundImage = new Image(getClass().getResource("/images/fondoNegro.png").toExternalForm());
	        
	        // Crear un BackgroundImage
	        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
	        
	        // Crear un Background
	        Background backgroundWithImage = new Background(background);
	        
	        // Establecer el fondo del AnchorPane
	        root.setBackground(backgroundWithImage);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("MINI COOPER");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
