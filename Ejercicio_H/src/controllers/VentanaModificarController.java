package controllers;

import dao.PersonaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Persona;

public class VentanaModificarController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtNombre;
    
	private ObservableList<Persona> personas;
	
    public VentanaModificarController() {
        // Inicializa la lista de personas aquí.
        //personas = FXCollections.observableArrayList(); // Otra forma de inicializar la lista.
    }
	
    private Persona persona;
    
    private EscenaPrincipalController escenaPrincipalController;
    /*
    public void initAttributtes(Persona persona) {
        this.persona = persona;
        txtNombre.setText(persona.getNombre());
        txtApellidos.setText(persona.getApellidos());
        txtEdad.setText(Integer.toString(persona.getEdad()));
    }
    */
    
    public void initAttributtes(ObservableList<Persona> personas, Persona p) {
		this.personas = personas;
		persona = p;
		txtNombre.setText(p.getNombre());
		txtApellidos.setText(p.getApellidos());
		txtEdad.setText(p.getEdad() + "");

	}
       

    public void setEscenaPrincipalController(EscenaPrincipalController controller) {
        this.escenaPrincipalController = controller;
    }

    // metodos a utilizar 
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
	void guardar(ActionEvent event) {
	    String nombre = this.txtNombre.getText();
	    String apellidos = this.txtApellidos.getText();
	    String ed = this.txtEdad.getText();

	    if (nombre.isEmpty() && apellidos.isEmpty() && ed.isEmpty()) {
	        mostrarError("Los campos no están rellenados. Por favor, rellene todos los campos.");
	    } else {
	        try {
	            int edad = Integer.parseInt(ed);
	            if (edad <= 0) {
	                mostrarError("La edad debe ser un número mayor que cero.");
	            } else {
	                if (nombre.isEmpty() && apellidos.isEmpty()) {
	                    mostrarError("Los campos nombre y apellidos están vacíos, rellene todos los campos por favor");
	                } else if (nombre.isEmpty()) {
	                    mostrarError("El campo nombre está vacío, rellene todos los campos por favor");
	                } else if (apellidos.isEmpty()) {
	                    mostrarError("El campo apellidos está vacío, rellene todos los campos por favor");
	                } else {
	                    Persona pmodificada = new Persona(nombre, apellidos, edad);
	                    pmodificada.setId(persona.getId());  // Establece el ID correcto

	                    if (personas.contains(pmodificada)) {
	                        mostrarError("La persona ya existe");
	                    } else {
	                        PersonaDao personaDao = new PersonaDao();

	                        boolean exito = personaDao.modificarPersona(pmodificada);

	                        if (exito) {
	                        	mostrarInformacion("Persona modificada en la base de datos correctamente.");

	                            // Actualiza la persona original con los nuevos datos
	                            persona.setNombre(nombre);
	                            persona.setApellidos(apellidos);
	                            persona.setEdad(edad);

	                        } else {
	                            mostrarError("Error al modificar la persona en la base de datos.");
	                        }

	                        mostrarInformacion("Persona modificada correctamente");

	                        limpiarCampos();
	                        cerrarVentanaModal();
	                    }
	                }
	            }
	        } catch (NumberFormatException e) {
	            mostrarError("El campo edad está vacío o no es un número válido.");
	        }
	    }
	}
 
    
    @FXML
    void cancelar(ActionEvent event) {
		// Obtener el Stage actual
		Stage stage = (Stage) btnCancelar.getScene().getWindow();

		// Cerrar la ventana modal
		stage.close();
    }

}