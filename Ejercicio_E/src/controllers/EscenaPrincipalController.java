package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableView<Persona> tabla;

    @FXML
    private TableColumn<Persona, String> colApellidos;

    @FXML
    private TableColumn<Persona, String> colEdad;

    @FXML
    private TableColumn<Persona, String> colNombre;
    
   
    // lista de persona que van a estar en la tabla
    ObservableList<Persona> personas ;
    
    // metodo para verificar si la entrada es un numero
   /* public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);  NO HACE FALTA
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    } */
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
     * Maneja el evento de agregar una nueva persona.
     *
     * @param event Evento de acción.
     */    
    @FXML
    public void agregarPersona(ActionEvent event) throws IOException {
       
            // Cargar el archivo FXML de la ventana modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarPersonaModal.fxml"));
            Parent root = loader.load();

            // Crear una instancia del controlador de la ventana modal
            VentanaModalController modalController = loader.getController();
            modalController.initAttributtes(personas);
            
            // Configurar el controlador de la ventana modal
            modalController.setEscenaPrincipalController(this);

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear y configurar la ventana
            Stage stage = new Stage();
            
            // ventana con tamaño fijo
            stage.setResizable(false);

            // Hacer que la ventana sea modal
            stage.initModality(Modality.APPLICATION_MODAL);


            // Establecer el título de la ventana
            stage.setTitle("Nueva Persona");

            // Establecer la escena en el escenario
            stage.setScene(scene);

            // Mostrar la ventana modal y esperar hasta que se cierre
            stage.showAndWait();

            // Actualizar la tabla
            tabla.refresh();
    }
    /**
     * Elimina la persona seleccionada de la tabla.
     *
     * @param event Evento de acción.
     */
    @FXML
    void eliminarPersona(ActionEvent event) {
        Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        
        if (personaSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Seguro que quieres eliminar esta persona?");
            alert.setContentText(personaSeleccionada.getNombre() + " " + personaSeleccionada.getApellidos());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            	 // Elimina la persona de la lista
                personas.remove(personaSeleccionada);
                
                mostrarInformacion("Persona eliminada correctamente");
                
             // Actualiza la tabla
                tabla.refresh(); 
            }
        } else {
            mostrarError("Selecciona una persona para eliminar");
        }
    }

    /**
     * Abre la ventana modal para modificar la información de la persona seleccionada.
     * Version mejorada double click en registro se abre la ventana para modificar
     * @param personaSeleccionada Persona seleccionada para modificar.
     */
    @FXML
    public void abrirVentanaModificar(Persona personaSeleccionada) {
        try {
            // Cargar el archivo FXML de la ventana modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarPersonaModal.fxml"));
            Parent root = loader.load();

            // Crear una instancia del controlador de la ventana modal
            VentanaModificarController modalController = loader.getController();
            modalController.initAttributtes(personas, personaSeleccionada);

            // Configurar el controlador de la ventana modal
            modalController.setEscenaPrincipalController(this);

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear y configurar la ventana
            Stage stage = new Stage();
            
            // ventana con tamaño fijo
            stage.setResizable(false);

            // Hacer que la ventana sea modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Establecer el título de la ventana
            stage.setTitle("Editar Persona");

            // Establecer la escena en el escenario
            stage.setScene(scene);

            // Mostrar la ventana modal y esperar hasta que se cierre
            stage.showAndWait();

            // Actualizar la tabla
            tabla.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Abre la ventana modal de modificación cuando se selecciona la opción de modificar.
     *
     * @param event Evento de acción.
     * @throws IOException Excepción de entrada/salida.
     */
    @FXML
    public void modificarPersona(ActionEvent event) throws IOException {
        // Obtén la persona seleccionada en la tabla
        Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (personaSeleccionada != null) {
            abrirVentanaModificar(personaSeleccionada);
        } else {
            mostrarError("Selecciona una persona para modificar");
        }
    }

    /**
     * Inicializa la estructura de datos y configura la tabla al inicio de la aplicación.
     *
     * @param url       Ubicación utilizada para resolver rutas relativas de la raíz del proyecto.
     * @param rb        Archivo de propiedades que contiene localizaciones específicas del idioma.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // Configura las columnas de la tabla y la lista de personas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        
        // Inicializa la lista observable de personas y asigna la lista
        personas = FXCollections.observableArrayList();
        tabla.setItems(personas);
        
        // Configura el evento de doble clic en la tabla
        tabla.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
                if (personaSeleccionada != null) {
                    abrirVentanaModificar(personaSeleccionada);
                }
            }
        });
    }
}