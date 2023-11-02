package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ColorController {
	
    @FXML
    private ImageView foto;
    
    @FXML
    private ImageView luz;
    
    @FXML
    private ImageView imgLuzOff;

    @FXML
    private ImageView imgLuzOn;
    
    
    @FXML
    void miniRojo(MouseEvent event) {
    	Image cocheRojo = new Image(getClass().getResourceAsStream("/images/miniBlazingRed.png"));
    	foto.setImage(cocheRojo);
    }


    @FXML
    void miniAzul(MouseEvent event) {
    	
    	Image cocheAzul = new Image(getClass().getResourceAsStream("/images/miniElectricBlue.png"));
    	foto.setImage(cocheAzul);
    }

    @FXML
    void miniBlueMarin(MouseEvent event) {
    	Image cocheMarin = new Image(getClass().getResourceAsStream("/images/miniLapisluxuryBlue.png"));
    	foto.setImage(cocheMarin);
    }
    
	@FXML
	void miniNegro(MouseEvent event) {
    	Image cocheNegro = new Image(getClass().getResourceAsStream("/images/miniMidnightBlack.png"));
    	foto.setImage(cocheNegro);
	}
	
	@FXML
	void miniGrey(MouseEvent event) {
    	Image cocheGrey = new Image(getClass().getResourceAsStream("/images/miniMoonwalkGrey.png"));
    	foto.setImage(cocheGrey);
	}

    @FXML
	void miniBiege(MouseEvent event) {
    	Image cocheBeige = new Image(getClass().getResourceAsStream("/images/miniPepperWhite.png"));
    	foto.setImage(cocheBeige);
	}
	
	@FXML
	void miniMedioNegro(MouseEvent event) {
    	Image cocheMedio = new Image(getClass().getResourceAsStream("/images/miniThunderGray.png"));
    	foto.setImage(cocheMedio);
	}
	
	@FXML
	void miniNaranja(MouseEvent event) {
    	Image cocheNaranja = new Image(getClass().getResourceAsStream("/images/miniVolcaninOrange.png"));
    	foto.setImage(cocheNaranja);
	}
	
	@FXML
	void imgLuzOnClicked(MouseEvent event) {
		// Quitar la foto pinchada y mostrar la otra 
		imgLuzOn.setVisible(false);
		imgLuzOff.setVisible(true);
		
		//Encender la luz
    	Image encenderLuz = new Image(getClass().getResourceAsStream("/logos/autoLuz.png"));
    	luz.setImage(encenderLuz);
    	
    	// Volver a poner la luz porque en el metodo anterior se apaga la luz
    	luz.setVisible(true);
		
	}

	@FXML
	void imgLuzOffClicked(MouseEvent event) {
		// Quitar la foto pinchada y mostrar la otra 
		imgLuzOff.setVisible(false);
		imgLuzOn.setVisible(true);
		
		//Apagar la luz
		luz.setVisible(false);
	}

}