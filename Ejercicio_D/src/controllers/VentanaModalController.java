package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Persona;
/**
 * Controlador para la ventana modal de edición/agregación de persona.
 */
public class VentanaModalController {

	// lista de persona que van a estar en la tabla
	ObservableList<Persona> personas;
	private Persona persona;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtApellidos;

	@FXML
	private TextField txtEdad;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnGuardar;

    /**
     * Inicializa los atributos de la ventana modal.
     *
     * @param personas Lista de personas en la tabla.
     * @param p Persona para la edición o nueva.
     */
	public void initAttributtes(ObservableList<Persona> personas, Persona p) {
		this.personas = personas;
		persona = p;
		txtNombre.setText(p.getNombre());
		txtApellidos.setText(p.getApellidos());
		txtEdad.setText(p.getEdad() + "");

	}
	  /**
     * Inicializa los atributos de la ventana modal sin persona.
     *
     * @param personas Lista de personas en la tabla.
     */
	public void initAttributtes(ObservableList<Persona> personas) {
		this.personas = personas;
	}

	public Persona getPersona() {
		return persona;
	}

	public Persona getNuevaPersona() {
		return persona;
	}

	private EscenaPrincipalController escenaPrincipalController; // Agrega un campo para el controlador de la escena principal
	
	 /**
     * Establece el controlador de la escena principal.
     *
     * @param controller Controlador de la escena principal.
     */
	public void setEscenaPrincipalController(EscenaPrincipalController controller) {
		this.escenaPrincipalController = controller;
	}
	
    /**
     * Maneja el evento de guardar una persona.
     *
     * @param event Evento de acción.
     */
	@FXML
	void guardar(ActionEvent event) {
		String nombre = this.txtNombre.getText();
		String apellidos = this.txtApellidos.getText();
		String ed = this.txtEdad.getText();
	//	int edad = Integer.parseInt(ed);
		//Persona pnew = new Persona(nombre, apellidos, edad);
		
		//String mensaje = "";
		
		if (nombre.isEmpty() && apellidos.isEmpty() && ed.isEmpty()) {
		        mostrarError("Los campos no estan rellenados. Por favor, rellene todos los campos.");
		    } else {
		        try {
		            int edad = Integer.parseInt(ed);
		            if (edad <= 0) {
		                mostrarError("La edad debe ser un número mayor que cero.");
		            } else {
		                Persona pnew = new Persona(nombre, apellidos, edad);
		                if (personas.contains(pnew)) {
		                    mostrarError("La persona ya existe");
		                    limpiarCampos();
		                } else if (nombre.isEmpty() && apellidos.isEmpty()) {
		                	mostrarError("Los campos nombre y apellidos están vacios , rellene todos los campos por favor");
		                } else if(nombre.isEmpty()) {
		                	mostrarError("El campo nombre esta vacio , rellene todos los campos por favor");
		                } else if (apellidos.isEmpty()) {
		                	mostrarError("El campo apellidos esta vacio , rellene todos los campos por favor");
		                } else {
		                    mostrarInformacion("Persona añadida correctamente");
		                    personas.add(pnew);
		                    limpiarCampos();
		                    cerrarVentanaModal();
		                }
		            }
		        } catch (NumberFormatException e) {
		            mostrarError("El campo edad esta vacio o no es un número válido.");
		        }
		    }
				/*
		if (personas.contains(pnew)) {		
			// Si la persona ya existe en la tabla, mostrar la ventana de error
			mensaje = "La persona ya existe";
			mostrarError(mensaje);
			limpiarCampos();
			cerrarVentanaModal();

		} else if (nombre.isEmpty() && apellidos.isEmpty() && ed.isEmpty()) {
			mensaje = "Los campos estan vacios , hay que rellenar los datos!";
			
			// Mostrar la ventana de error
			Alert alertaError2 = new Alert(Alert.AlertType.ERROR);
			alertaError2.setTitle("ERROR");
			alertaError2.setHeaderText(null);
			alertaError2.setContentText(mensaje);
			alertaError2.showAndWait();

			// Cerrar la ventana modal
			Stage stage = (Stage) btnGuardar.getScene().getWindow();
			stage.close();

			if (nombre.isEmpty()) {
				mensaje += "El campo nombre es obligatorio\n";
			}
			if (apellidos.isEmpty()) {
				mensaje += "El campo apellidos es obligatorio\n";
			}
			if (edad <= 0) {
				mensaje += "El campo edad es obligatorio";
			}

			// Mostrar la ventana de error
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("ERROR");
			alerta.setHeaderText(null);
			alerta.setContentText(mensaje);
			alerta.showAndWait();

			limpiarCampos();
			cerrarVentanaModal();

		} else {
			mensaje = "Persona añadida correctamente";
			// Mostrar la ventana modal INFORMATION
			Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
			alertaInfo.setTitle("Info");
			alertaInfo.setHeaderText(null);
			alertaInfo.setContentText(mensaje);
			personas.add(pnew);
			
			limpiarCampos();
			cerrarVentanaModal();
			
		}*/
	}
	
	/**
	 * Muestra una ventana emergente de tipo error con el mensaje proporcionado.
	 *
	 * @param mensaje Mensaje de error a mostrar en la ventana.
	 */
	private void mostrarError(String mensaje) {
	    Alert alertaError = new Alert(Alert.AlertType.ERROR);
	    alertaError.setTitle("ERROR");
	    alertaError.setHeaderText(null);
	    alertaError.setContentText(mensaje);
	    alertaError.showAndWait();
	}
	/**
	 * Muestra una ventana informativa de tipo INFORMACIÓN con el mensaje proporcionado.
	 *
	 * @param mensaje Mensaje de INFORMACION a mostrar en la ventana.
	 */
	private void mostrarInformacion(String mensaje) {
	    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
	    alertaInfo.setTitle("INFO");
	    alertaInfo.setHeaderText(null);
	    alertaInfo.setContentText(mensaje);
	    alertaInfo.showAndWait();
	}
	/**
	 * Limpia los campos de texto de la ventana modal.
	 */
	private void limpiarCampos() {
	    txtNombre.clear();
	    txtApellidos.clear();
	    txtEdad.clear();
	}
	/**
	 * Funcion de cerrar la ventana modal actual.
	 */
	private void cerrarVentanaModal() {
	    Stage stage = (Stage) btnGuardar.getScene().getWindow();
	    stage.close();
	}
	
    /**
     * Maneja el evento de cancelar la edición/agregación de persona.
     *
     * @param event Evento de acción.
     */
	@FXML
	void cancelar(ActionEvent event) {
		// Obtener el Stage actual
		Stage stage = (Stage) btnCancelar.getScene().getWindow();

		// Cerrar la ventana modal
		stage.close();
	}

}
