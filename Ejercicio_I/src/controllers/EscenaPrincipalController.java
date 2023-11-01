package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.PersonaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Persona;



public class EscenaPrincipalController implements Initializable{

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldApellidos;

    @FXML
    private TextField txtFieldEdad;
    
    @FXML
    private TextField txtFieldFiltrar;

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
    
    
    // Debes inyectar la referencia a la escena para aplicar el estilo CSS
    @FXML
    private AnchorPane scene;
    
    // Añadir el Menu contextual
    private ContextMenu tablaContextMenu;
   
    // lista de persona que van a estar en la tabla
    ObservableList<Persona> personas ;
    
    // Ventanas de INFO y ERROR
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
           
    
    
    // version mejorada double click en registro se abre la ventana para modificar
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
    
    // Eso es el metodo para modificar el registro y usa el metodo arriba
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
    
    @FXML
    void eliminarPersona(ActionEvent event) {
        Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        
        // Si algun registro esta seleccionado , saldra una ventana para confirmar con el usuario
        if (personaSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Seguro que quieres eliminar esta persona?");
            alert.setContentText(personaSeleccionada.getNombre() + " " + personaSeleccionada.getApellidos());

            Optional<ButtonType> result = alert.showAndWait();
            
            // A la hora de click OK se elimina la persona de la tabla
            if (result.get() == ButtonType.OK) {
            	
            	PersonaDao personaDao = new PersonaDao();      	
            	
            	boolean exito = personaDao.eliminarPersona(personaSeleccionada);
            	
            	if (exito) {
            		mostrarInformacion("Persona bien eliminada de la base de datos");
            	} else {
            		mostrarError("No se ha podido eliminar la persona de la base de datos");
            	}
            	
            	// Elimina la persona de la lista
                personas.remove(personaSeleccionada);
                
                // Mostrar la ventana informativa
                mostrarInformacion("Persona eliminada correctamente");
                
                // Actualiza la tabla
                tabla.refresh(); 
            }
        } else {
            // En el caso de no haya ningun registro seleccionado , saldra una ventana ERROR
            mostrarError("Selecciona una persona para eliminar");
        }
    }
    
    // Metodo de filtrar los nombres de personas
    @FXML
    void filtrarRegistros(KeyEvent event) {
    	String filtro = txtFieldFiltrar.getText().toLowerCase();
    	
    	// Iterar a través de la lista de personas y agregar las que coincidan con el filtro
    	ObservableList<Persona> resultadosFiltrados = FXCollections.observableArrayList();
    	
        for (Persona persona : personas) {
            if (persona.getNombre().toLowerCase().contains(filtro)) {
                resultadosFiltrados.add(persona);
            }
        }

        // Actualizar la tabla con los resultados filtrados
        tabla.setItems(resultadosFiltrados);
    }
    
    // Metodo de selecciona un idioma

    // Estructura datos
    public void initialize(URL url, ResourceBundle rb) {
    	
    	// Asignación de los atributos a las columnas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        
        // Agregar la lista de personas en la tabla
        personas = FXCollections.observableArrayList();
        //tabla.setItems(personas);
        
        // Crear una instancia de PersonaDao para acceder a la base de datos
        PersonaDao personaDao = new PersonaDao();

        // Recuperar la lista de personas desde la base de datos
        ObservableList<Persona> personasDesdeBD = personaDao.cargarPersonas();

		// Agregar las personas recuperadas a la lista que se muestra en la tabla
		personas.addAll(personasDesdeBD);

		// Agregar la lista de personas en la tabla
		tabla.setItems(personas);
        
        // Modificar el registro al hacer doble click
        tabla.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
                if (personaSeleccionada != null) {
                    abrirVentanaModificar(personaSeleccionada);
                }
            }
        });
           
        // Filtrar , manejar el evento de cambio de texto en el campo de filtro
        txtFieldFiltrar.setOnKeyReleased(event -> filtrarRegistros(event));
        
        // Caragr y aplicar el estilo CSS
        String cssFile = getClass().getResource("/css/application.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        
        // Crear el menú contextual
        tablaContextMenu = new ContextMenu();

        // Crear elementos del menú contextual
        MenuItem modificarMenuItem = new MenuItem("Modificar");
        MenuItem eliminarMenuItem = new MenuItem("Eliminar");

        // Agregar acciones para los elementos del menú contextual
        modificarMenuItem.setOnAction(event -> {
			try {
				modificarPersona(null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        eliminarMenuItem.setOnAction(event -> eliminarPersona(null));

        // Agregar elementos al menú contextual
        tablaContextMenu.getItems().addAll(modificarMenuItem, eliminarMenuItem);

        // Asignar el menú contextual a la tabla
        tabla.setContextMenu(tablaContextMenu);
        
        
        
       /* String idioma = "castellano";
        // Carga el archivo .properties correspondiente para los encabezados de columna
        ResourceBundle columnHeaders = ResourceBundle.getBundle("columnHeaders", new Locale(idioma));
        
        ResourceBundle bundle = ResourceBundle.getBundle("nombreDeTuArchivoDePropiedades", new Locale(idioma));
        String addButtonLabel = bundle.getString("addButton");*/


        // Establece los encabezados de columna según el idioma
       /* colNombre.setText(columnHeaders.getString("colNombre"));
        colApellidos.setText(columnHeaders.getString("colApellidos"));
        colEdad.setText(columnHeaders.getString("colEdad"));*/
    }
}