package controllers;

import dao.PersonaDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Persona;

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
	/*
	 */

	public void initAttributtes(ObservableList<Persona> personas, Persona p) {
		this.personas = personas;
		persona = p;
		txtNombre.setText(p.getNombre());
		txtApellidos.setText(p.getApellidos());
		txtEdad.setText(p.getEdad() + "");

	}

	public void initAttributtes(ObservableList<Persona> personas) {
		this.personas = personas;
	}

	public Persona getPersona() {
		return persona;
	}

	public Persona getNuevaPersona() {
		return persona;
	}

	// Agrega un campo para el controlador de la escena principal
	private EscenaPrincipalController escenaPrincipalController; 

	public void setEscenaPrincipalController(EscenaPrincipalController controller) {
		this.escenaPrincipalController = controller;
	}
	
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
		                	// Llamar al método para agregar persona en la base de datos
		                    PersonaDao personaDao = new PersonaDao();
		                    boolean exito = personaDao.agregarPersona(pnew);

		                    if (exito == true) {
		                        mostrarInformacion("Persona agregada correctamente.");
		                    } else {
		                        mostrarError("Error al agregar la persona en la base de datos.");
		                    }

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
	
	private void limpiarCampos() {
	    txtNombre.clear();
	    txtApellidos.clear();
	    txtEdad.clear();
	}
	
	private void cerrarVentanaModal() {
	    Stage stage = (Stage) btnGuardar.getScene().getWindow();
	    stage.close();
	}

	@FXML
	void cancelar(ActionEvent event) {
		// Obtener el Stage actual
		Stage stage = (Stage) btnCancelar.getScene().getWindow();

		// Cerrar la ventana modal
		stage.close();
	}

}
