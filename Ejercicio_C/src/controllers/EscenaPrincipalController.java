package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Persona;
/**
 * Controlador para la interfaz de la escena principal.
 */
public class EscenaPrincipalController implements Initializable{

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldApellidos;

    @FXML
    private TextField txtFieldEdad;

    @FXML
    private Button btnAgregar;

    @FXML
    private TableView<Persona> tabla;

    @FXML
    private TableColumn<Persona, String> colApellidos;

    @FXML
    private TableColumn<Persona, String> colEdad;

    @FXML
    private TableColumn<Persona, String> colNombre;
    
    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;
       
   
    // lista de persona que van a estar en la tabla
    ObservableList<Persona> Personas = FXCollections.observableArrayList();
    
    // metodo para verificar si la entrada es un numero
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Maneja el evento de agregar una nueva persona.
     *
     * @param event Evento de acción.
     */
    @FXML
    void agregarPersona(ActionEvent event) {
    	
    	String nombre = txtFieldNombre.getText();
    	String apellidos = txtFieldApellidos.getText();
    	String txtEdad = txtFieldEdad.getText();
    	int edad = 0; // Inicializa la edad en 0

    	// Verificar si el campo de edad no esta vacio y si que contiene un número
    	if (!txtEdad.isEmpty() && isNumeric(txtEdad)) {
    	        edad = Integer.parseInt(txtEdad);
    	}
    	
    	    // Verificar si los campos obligatorios están vacios
    	if (nombre.isEmpty() || apellidos.isEmpty() || edad == 0) {
    		String mensajeError = "";
    		if (nombre.isEmpty()) {
    	        mensajeError += "El campo nombre es obligatorio \n";
    	        }
    		if (apellidos.isEmpty()) {
    			mensajeError += "El campo apellidos es obligatorio \n";
    	        }
    		if (edad == 0) {
    	        mensajeError += "El campo edad es obligatorio ";
    	        }

    	        // Mostrar la ventana de error
	            Alert alerta = new Alert(Alert.AlertType.ERROR);
	            alerta.setTitle("ERROR");
	            alerta.setHeaderText(null);
	            alerta.setContentText(mensajeError);
	            alerta.showAndWait();
	            
    	    } else {
    	        // Comprobar si la persona ya existe en la tabla
    	    	boolean Pexiste = false;
    	        for (Persona elemento : Personas) {
    		        if (nombre.equals(elemento.getNombre()) && apellidos.equals(elemento.getApellidos()) && edad == elemento.getEdad()) {
    		        Pexiste = true;
    		        // Si la persona ya existe en la table no hacemos nada si 
    		        break;
    		        }
    	        }
    	        // Si la persona no existe 
    	        if (!Pexiste) {
    	   
    	            Persona p1 = new Persona(nombre, apellidos, edad);
    	            Personas.add(p1);

    	            // Mostrar la ventana modal INFORMATION
                    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertaInfo.setTitle("Info");
                    alertaInfo.setHeaderText(null);
                    alertaInfo.setContentText("Persona añadida correctamente");
                    alertaInfo.showAndWait();

    	            // Limpiar los campos de entrada
    	            txtFieldNombre.clear();
    	            txtFieldApellidos.clear();
    	            txtFieldEdad.clear();
    	            
    	            // Deseleccionar el registro en la tabla
    	            tabla.getSelectionModel().clearSelection();
    	        }
    	    }

    	}
    	
    private Persona personaSeleccionada;
    //private boolean Pexiste = false;
    
    /**
     * Maneja el evento de modificar una persona existente.
     *
     * @param event Evento de acción.
     */
    @FXML
    void modificarPersona(ActionEvent event) {

        if (personaSeleccionada != null) {
            // Obtener los nuevos valores de los campos
            String nombre = txtFieldNombre.getText();
            String apellidos = txtFieldApellidos.getText();
            String ed = txtFieldEdad.getText();
            int edad = 0; // inciar el valor de edad
        	if (!ed.isEmpty() && isNumeric(ed)) {
    	        edad = Integer.parseInt(ed);
        	}
        	
        	boolean PersonaExiste = false;
            
        	for (Persona elemento : Personas) {
        		if (nombre.equals(elemento.getNombre()) && apellidos.equals(elemento.getApellidos()) && edad == elemento.getEdad()) {
    		        PersonaExiste = true;
    		        // Ventana modal ERROR que ya existe una persona con los datos nuevos
    	            Alert alerta = new Alert(Alert.AlertType.ERROR);
    	            alerta.setTitle("ERROR");
    	            alerta.setHeaderText(null);
    	            alerta.setContentText("No se puede realizar el cambio\n Ya existe una persona con los datos modificados");
    	            alerta.showAndWait();
    	            break;
    		        }
    	        }
        	if (!PersonaExiste) {	
	           if (nombre.isEmpty() || apellidos.isEmpty()|| edad == 0) {
	            	String mensajeError = "";
	        		if (nombre.isEmpty()) {
	        	        mensajeError += "El campo nombre es obligatorio \n";
	        	        }
	        		if (apellidos.isEmpty()) {
	        			mensajeError += "El campo apellidos es obligatorio \n";
	        	        }
	        		if (edad == 0) {
	        	        mensajeError += "El campo edad es obligatorio ";
	        	        }
	
	        	        // En una ventana modal tipo error mostramos el mensaje
	    	            Alert alerta = new Alert(Alert.AlertType.ERROR);
	    	            alerta.setTitle("ERROR");
	    	            alerta.setHeaderText(null);
	    	            alerta.setContentText("No se puede realizar el cambio! \n"+mensajeError);
	    	            alerta.showAndWait();
	
	            } else {
	                // Actualiza los datos de la persona seleccionada
	            	personaSeleccionada.setNombre(nombre);
	            	personaSeleccionada.setApellidos(apellidos);
	            	personaSeleccionada.setEdad(edad);
	                	
	            	// Actualiza la tabla
	                tabla.refresh();
	                	          	
	                // Mostrar una ventana de Information de que los cambios han sido realizados correctamente
	                Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
	                alertaInfo.setTitle("Info");
	                alertaInfo.setHeaderText(null);
	                alertaInfo.setContentText("Los datos han cambiado correctamente");
	                alertaInfo.showAndWait();
	
	                // Limpia los campos de texto
	                txtFieldNombre.clear();
	                txtFieldApellidos.clear();
	                txtFieldEdad.clear();
	                
	                // Deseleccionar el registro en la tabla
	                tabla.getSelectionModel().clearSelection();
	            }
        	}
        }
    }
    /**
     * Maneja el evento de eliminar una persona existente.
     *
     * @param event Evento de acción.
     */
    @FXML
    void eliminarPersona(ActionEvent event) {
    	
        if (personaSeleccionada != null) {
            // Elimina la persona seleccionada de la lista
            Personas.remove(personaSeleccionada);
            
            // Ventana para informar 
            Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
            alertaInfo.setTitle("Info");
            alertaInfo.setHeaderText(null);
            alertaInfo.setContentText("Se ha eliminado el registro correctamente");
            alertaInfo.showAndWait();
            
            // Actualiza la tabla
            tabla.refresh();

            // Limpia los campos de texto
            txtFieldNombre.clear();
            txtFieldApellidos.clear();
            txtFieldEdad.clear();
            
            // Deseleccionar el registro en la tabla
            tabla.getSelectionModel().clearSelection();

        }
    }
    /**
     * Inicializa la interfaz de usuario y configura la tabla.
     *
     * @param url La ubicación para resolver rutas relativas de la raíz del objeto de clase.
     * @param resourceBundle El recurso de texto localizado.
     */
    public void initialize(URL url, ResourceBundle rb) {
    	
    	tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    	    if (newSelection != null) {
    	        // Cuando se selecciona una fila, obtén el elemento seleccionado
    	        personaSeleccionada = newSelection;

    	        // Muestra los datos del elemento seleccionado en los campos de texto de izquierda
    	        txtFieldNombre.setText(personaSeleccionada.getNombre());
    	        txtFieldApellidos.setText(personaSeleccionada.getApellidos());
    	        txtFieldEdad.setText(String.valueOf(personaSeleccionada.getEdad()));
    	    }
    	});

   
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
       
        tabla.setItems(Personas);
    }
}