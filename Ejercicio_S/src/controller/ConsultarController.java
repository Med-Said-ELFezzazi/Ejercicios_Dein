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

    /**
     * Maneja el evento de acción cuando se hace clic en el botón "Consultar".
     * Obtiene el código de la interfaz de usuario, realiza una consulta en la base de datos y muestra el resultado.
     * @param event El evento de acción desencadenado al hacer clic en el botón "Consultar".
     */
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
    
    /**
     * Muestra una alerta de error con el mensaje proporcionado.
     * @param mensaje El mensaje de error que se mostrará.
     */
    // Ventanas de INFO y ERROR
	private void mostrarError(String mensaje) {
	    Alert alertaError = new Alert(Alert.AlertType.ERROR);
	    alertaError.setTitle("ERROR");
	    alertaError.setHeaderText(null);
	    alertaError.setContentText(mensaje);
	    alertaError.showAndWait();
	}
	
    /**
     * Muestra una alerta de información con el mensaje proporcionado.
     * @param mensaje El mensaje de información que se mostrará.
     */
	private void mostrarInformacion(String mensaje) {
	    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
	    alertaInfo.setTitle("INFO");
	    alertaInfo.setHeaderText(null);
	    alertaInfo.setContentText(mensaje);
	    alertaInfo.showAndWait();
	}   
}