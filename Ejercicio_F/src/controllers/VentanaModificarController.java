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
 * Controlador para la ventana de modificación de persona.
 * Permite editar los detalles de una persona existente.
 */
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
    /**
     * Inicializa los atributos del controlador cuando se utiliza para modificar una persona existente.
     *
     * @param personas Lista de personas.
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
     * Configura el controlador de la ventana principal.
     *
     * @param controller Controlador de la ventana principal.
     */
    public void setEscenaPrincipalController(EscenaPrincipalController controller) {
        this.escenaPrincipalController = controller;
    }
    
    // Métodos auxiliares para mostrar mensajes de error e información
    /**
     * Muestra una ventana de error con el mensaje proporcionado.
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

	private void mostrarInformacion(String mensaje) {
	    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
	    alertaInfo.setTitle("INFO");
	    alertaInfo.setHeaderText(null);
	    alertaInfo.setContentText(mensaje);
	    alertaInfo.showAndWait();
	}
	
    /**
     * Limpia los campos de entrada.
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
     * Maneja el evento de guardar. Valida los campos y guarda la persona si los datos son válidos.
     *
     * @param event Evento de acción.
     */
    @FXML
    void guardar(ActionEvent event) {
		String nombre = this.txtNombre.getText();
		String apellidos = this.txtApellidos.getText();
		String ed = this.txtEdad.getText();
		
    	if (nombre.isEmpty() && apellidos.isEmpty() && ed.isEmpty()) {
	        mostrarError("Los campos no estan rellenados. Por favor, rellene todos los campos.");
	    } else {
	        try {
	            int edad = Integer.parseInt(ed);
	            if (edad <= 0) {
	                mostrarError("La edad debe ser un número mayor que cero.");
	            } else {
	                Persona pmodificada = new Persona(nombre, apellidos, edad);

	                if (nombre.isEmpty() && apellidos.isEmpty()) {
	                	mostrarError("Los campos nombre y apellidos están vacios , rellene todos los campos por favor");
	                } else if(nombre.isEmpty()) {
	                	mostrarError("El campo nombre esta vacio , rellene todos los campos por favor");
	                } else if (apellidos.isEmpty()) {
	                	mostrarError("El campo apellidos esta vacio , rellene todos los campos por favor");
	                } else if (personas.contains(pmodificada)) {
	                		 mostrarError("La persona ya existe");
	                		 //todo ok
	                	} else {
		                      /*  Buscar la persona seleccionada en la lista y remplazar la persona con los nuevos datos
		                		int indice = personas.indexOf(persona);	                		
		                        if (indice >= 0) {
		                            personas.set(indice, pmodificada); */

	                		//mostrarError("La persona a modificar");
	                		persona.setNombre(nombre);
	                		persona.setApellidos(apellidos);
	                		persona.setEdad(edad);
	                		
    	                    mostrarInformacion("Persona modificada correctamente");
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
     * Maneja el evento de cancelar. Cierra la ventana modal.
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