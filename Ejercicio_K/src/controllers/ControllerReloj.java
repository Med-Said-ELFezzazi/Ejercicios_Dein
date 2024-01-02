package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.Timer;
import java.util.TimerTask;


public class ControllerReloj implements Initializable{
	
	
    @FXML
    private ImageView h1;

    @FXML
    private ImageView h2;

    @FXML
    private ImageView min1;

    @FXML
    private ImageView min2;

    @FXML
    private ImageView s1;

    @FXML
    private ImageView s2;
    
    @FXML
    private Text hora;
    
    @FXML
    private TextArea txtArea;

    public String obtenerHoraActual() {
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = horaActual.format(formatter);
        return horaFormateada;
    }
	
	String n0 = "/images/ZERO.png";
	String n1 = "/images/ONE.png";
	String n2 = "/images/TWO.png";
	String n3 = "/images/THREE.png";
	String n4 = "/images/FOUR.png";
	String n5 = "/images/FIVE.png";
	String n6 = "/images/SIX.png";
	String n7 = "/images/SEVEN.png";
	String n8 = "/images/EIGHT.png";
	String n9 = "/images/NINE.png";
	    
    String[] digitImages = {
            "/images/ZERO.png", "/images/ONE.png", "/images/TWO.png",
            "/images/THREE.png", "/images/FOUR.png", "/images/FIVE.png",
            "/images/SIX.png", "/images/SEVEN.png", "/images/EIGHT.png",
            "/images/NINE.png"};	
    

    private void updateClock() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String formattedTime = now.format(formatter);

        // Make sure you handle the exception if the image cannot be loaded
        try {
            setDigitImage(h1, formattedTime.charAt(0));
            setDigitImage(h2, formattedTime.charAt(1));
            setDigitImage(min1, formattedTime.charAt(2));
            setDigitImage(min2, formattedTime.charAt(3));
            setDigitImage(s1, formattedTime.charAt(4));
            setDigitImage(s2, formattedTime.charAt(5));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error, possibly update the UI to indicate the error
        }
    }

    private void setDigitImage(ImageView imageView, char digit) {
        int index = Character.getNumericValue(digit);
        // Make sure the path starts with a slash if it's in the resources directory
        Image image = new Image(getClass().getResourceAsStream(digitImages[index]));
        imageView.setImage(image);
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
    	
    	updateClock(); // Initial clock update
    	Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
    		@Override
    		public void run() {
    			// Use Platform.runLater when modifying the JavaFX UI from another thread
    			javafx.application.Platform.runLater(new Runnable() {
    				@Override
    				public void run() {
    					updateClock();
    				}
    			});
    		}
    	};
    	timer.scheduleAtFixedRate(task, 0, 1000); // Update every second
    	
    }
    
}