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
 * Controlador para la escena principal de la aplicación.
 * Gestiona la lógica de la interfaz de usuario y la interacción con la tabla de personas.
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
   
    // lista de persona que van a estar en la tabla
    ObservableList<Persona> Personas = FXCollections.observableArrayList();
    
    /**
     * Método para verificar si una cadena es un número.
     *
     * @param str La cadena a verificar.
     * @return true si la cadena es un número, false de lo contrario.
     */
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    /**
     * Método llamado al hacer clic en el botón "Agregar Persona".
     * Agrega una nueva persona a la tabla.
     *
     * @param event Evento de clic.
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
    		        break;
    		        // Si la persona ya existe en la table no hacemos nada si 
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
    	        }
    	    }

    	}
    /**
     * Inicializa la Estructura de datos y configura la tabla al cargar la escena.
     *
     * @param url       La ubicación para resolver rutas relativas para el objeto raíz.
     * @param resourceBundle Los recursos específicos del idioma para esta clase.
     */
    public void initialize(URL url, ResourceBundle rb) {
   
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
       
        tabla.setItems(Personas);
    }
}