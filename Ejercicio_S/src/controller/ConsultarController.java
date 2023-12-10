package controller;

import dao.AnimalDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConsultarController {

    @FXML
    private Button btnConsultar;

    @FXML
    private TextField txtCodigo;

    @FXML
    void consultar(ActionEvent event) {
    	String cod = txtCodigo.getText().toString();
    	AnimalDao animalDao = new AnimalDao();
    	String con = animalDao.consultar(cod);
    	if (con == null || con.isEmpty()) {
    		mostrarError("Animal con el codigo introducido no existe!");
    	} else {
    		mostrarInformacion(con);
    	}
    }
    
    // Ventanas de INFO y ERROR
	private void mostrarError(String mensaje) {
	    Alert alertaError = new Alert(Alert.AlertType.ERROR);
	    alertaError.setTitle("ERROR");
	    alertaError.setHeaderText(null);
	    alertaError.setContentText(mensaje);
	    alertaError.showAndWait();
	}
	private void mostrarInformacion(String mensaje) {
	    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
	    alertaInfo.setTitle("INFO");
	    alertaInfo.setHeaderText(null);
	    alertaInfo.setContentText(mensaje);
	    alertaInfo.showAndWait();
	}   
}