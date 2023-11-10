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
 * Controlador para la ventana modal de ingreso de datos de una nueva persona.
 * Permite la creación y validación de una nueva entrada en la tabla de personas.
 */
public class VentanaModalController {

	// lista de persona que van a estar en la tabla
	private ObservableList<Persona> personas;
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
     * Inicializa los atributos de la ventana modal con una persona existente.
     * Se utiliza cuando se desea modificar una persona existente.
     *
     * @param personas Lista observable de personas en la tabla.
     * @param p        Persona a modificar.
     */
	public void initAttributtes(ObservableList<Persona> personas, Persona p) {
		this.personas = personas;
		persona = p;
		txtNombre.setText(p.getNombre());
		txtApellidos.setText(p.getApellidos());
		txtEdad.setText(p.getEdad() + "");

	}
	 /**
     * Inicializa los atributos de la ventana modal sin una persona existente.
     * Se utiliza cuando se desea agregar una nueva persona.
     *
     * @param personas Lista observable de personas en la tabla.
     */
	public void initAttributtes(ObservableList<Persona> personas) {
		this.personas = personas;
	}
	/**
     * Obtiene la persona actualmente en edición o creación.
     *
     * @return Persona actual.
     */
	public Persona getPersona() {
		return persona;
	}
	 /**
     * Obtiene la nueva persona creada o modificada.
     *
     * @return Nueva persona.
     */
	public Persona getNuevaPersona() {
		return persona;
	}

	private EscenaPrincipalController escenaPrincipalController; // Agrega un campo para el controlador de la escena principal

    /**
     * Establece el controlador de la escena principal para la comunicación entre controladores.
     *
     * @param controller Controlador de la escena principal.
     */
	public void setEscenaPrincipalController(EscenaPrincipalController controller) {
		this.escenaPrincipalController = controller;
	}

    /**
     * Maneja el evento de guardar, valida y agrega la nueva persona a la tabla.
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
	}
	 /**
     * Muestra un mensaje de error en una ventana emergente.
     *
     * @param mensaje Mensaje de error.
     */
	private void mostrarError(String mensaje) {
	    Alert alertaError = new Alert(Alert.AlertType.ERROR);
	    alertaError.setTitle("ERROR");
	    alertaError.setHeaderText(null);
	    alertaError.setContentText(mensaje);
	    alertaError.showAndWait();
	}
    /**
     * Muestra un mensaje de información en una ventana emergente.
     *
     * @param mensaje Mensaje de información.
     */
	private void mostrarInformacion(String mensaje) {
	    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
	    alertaInfo.setTitle("INFO");
	    alertaInfo.setHeaderText(null);
	    alertaInfo.setContentText(mensaje);
	    alertaInfo.showAndWait();
	}
    /**
     * Limpia los campos de entrada en la ventana modal.
     */	
	private void limpiarCampos() {
	    txtNombre.clear();
	    txtApellidos.clear();
	    txtEdad.clear();
	}
    /**
     * Cierra la ventana modal actual.
     */	
	private void cerrarVentanaModal() {
	    Stage stage = (Stage) btnGuardar.getScene().getWindow();
	    stage.close();
	}
    /**
     * Maneja el evento de cancelar, cierra la ventana modal actual.
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
