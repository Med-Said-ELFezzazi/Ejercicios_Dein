package controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Persona;

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
    ObservableList<Persona> personas ;
    
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
     * Inicializa la interfaz de usuario y configura la tabla.
     *
     * @param url La ubicación para resolver rutas relativas de la raíz del objeto de clase.
     * @param resourceBundle El recurso de texto localizado.
     */
    public void initialize(URL url, ResourceBundle rb) {
   
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        
        personas = FXCollections.observableArrayList();
        tabla.setItems(personas);
    }
}