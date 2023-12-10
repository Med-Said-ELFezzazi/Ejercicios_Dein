package controller;


import java.io.IOException;
import java.net.URL;
//import java.sql.Date;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.AnimalDao;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Animal;


public class EscenaPrincipalController implements Initializable {
	
    @FXML
    private TableView<Animal> tabla;
    
    @FXML
    private TableColumn<Animal, String> colCodigo;
    
    @FXML
    private TableColumn<Animal, String> colNombre;

    @FXML
    private TableColumn<Animal, String> colEspecie;

    @FXML
    private TableColumn<Animal, String> colRaza;
    
    @FXML
    private TableColumn<Animal, String> colSexo; 

    @FXML
    private TableColumn<Animal, Integer> colEdad;

    @FXML
    private TableColumn<Animal, Double> colPeso;

    @FXML
    private TableColumn<Animal, String> colObservacion;

    @FXML
    private TableColumn<Animal, Date> colFecha;
    
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnConsultar;

    @FXML
    private Button btnModificar;

    private String codigoSeleccionado;
    
    private String nombreSeleccionado;

    ObservableList<Animal> animales;
    
    /**
     * Abre la ventana modal para agregar un nuevo animal.
     * @param event Evento que desencadena la acción (clic en el botón).
     * @throws IOException Si hay un error al cargar la interfaz gráfica.
     */
    @FXML
    public void agregarAnimal(ActionEvent event) throws IOException {
       
            // Cargar el archivo FXML de la ventana modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VentanaDarAlta.fxml"));
            Parent root = loader.load();

            // Crear una instancia del controlador de la ventana modal
            AltaController modalController = loader.getController();
            modalController.initAttributtes(animales);
            
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
            stage.setTitle("Nuevo animal");

            // Establecer la escena en el escenario
            stage.setScene(scene);

            // Mostrar la ventana modal y esperar hasta que se cierre
            stage.showAndWait();

            // Actualizar la tabla
            tabla.refresh();
    }
    
    /**
     * Abre la ventana modal para modificar un animal seleccionado.
     * @param animalSeleccionado Animal seleccionado para editar.
     */
    @FXML
    public void abrirVentanaModificar(Animal animalSeleccionado) {
        try {
            // Cargar el archivo FXML de la ventana modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VentanaModificar.fxml"));
            Parent root = loader.load();

            // Crear una instancia del controlador de la ventana modal
            ModificarController modalController = loader.getController();
            modalController.initAttributtes(animales, animalSeleccionado);

            // Configurar el controlador de la ventana modal
            modalController.setEscenaPrincipalController(this);

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear y configurar la ventana
            Stage stage = new Stage();
            
            // ventana con tamaño fijo
            stage.setResizable(true);

            // Hacer que la ventana sea modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Establecer el título de la ventana
            stage.setTitle("Editar Animal");

            // Establecer la escena en el escenario
            stage.setScene(scene);
            stage.showAndWait();
            // Actualizar la tabla
            tabla.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el código del animal seleccionado.
     * @return Código del animal seleccionado.
     */
    public String codSeleccionado() {
        return codigoSeleccionado;
    }
    
    /**
     * Obtiene el nombre del animal seleccionado.
     * @return Nombre del animal seleccionado.
     */
    public String nomSeleccionado() {
        return nombreSeleccionado;
    }
    
    /**
     * Modifica un animal seleccionado.
     * @param event Evento que desencadena la acción (clic en el botón).
     */
    @FXML
    void modificarAnimal(ActionEvent event) {
        // Obtén la persona seleccionada en la tabla
        Animal animalSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (animalSeleccionado != null) {
            abrirVentanaModificar(animalSeleccionado);
        } else {
            mostrarError("Selecciona un regitro para modificar");
        }
    }
    
    /**
     * Elimina un animal seleccionado.
     * @param event Evento que desencadena la acción (clic en el botón).
     */
    @FXML
    void borrarAnimal(ActionEvent event) {
    	Animal animalSeleccionada = tabla.getSelectionModel().getSelectedItem();
        
        // Si algun registro esta seleccionado , saldra una ventana para confirmar con el usuario
        if (animalSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Seguro que quieres eliminar este animal?");
            alert.setContentText(animalSeleccionada.getNombre() + " con el codigo : " + animalSeleccionada.getCodigo());

            Optional<ButtonType> result = alert.showAndWait();
            
            // A la hora de click OK se elimina la persona de la tabla
            if (result.get() == ButtonType.OK) {
            	
            	AnimalDao animalDao = new AnimalDao();      	
            	
            	boolean exito = animalDao.eliminarAnimal(animalSeleccionada);
            	
            	if (exito) {
            		mostrarInformacion("Animal bien eliminado de la base de datos");
            	} else {
            		mostrarError("No se ha podido eliminar el animal de la base de datos");
            	}
            	
            	// Elimina animal de la lista
                animales.remove(animalSeleccionada);
                
                // Mostrar la ventana informativa
                mostrarInformacion("Animal eliminado correctamente");
                
                // Actualiza la tabla
                tabla.refresh(); 
            }
        } else {
            // En el caso de no haya ningun registro seleccionado , saldra una ventana ERROR
            mostrarError("Selecciona un registro para eliminar");
        }
    }

    /**
     * Abre la ventana modal para consultar datos de un animal.
     * @param event Evento que desencadena la acción (clic en el botón).
     * @throws IOException Si hay un error al cargar la interfaz gráfica.
     */
    @FXML
    void consultarAnimal(ActionEvent event) throws IOException {
    	   // Cargar el archivo FXML de la ventana modal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Consultar.fxml"));
        Parent root = loader.load();
        // Crear la escena
        Scene scene = new Scene(root);

        // Crear y configurar la ventana
        Stage stage = new Stage();
        
        // ventana con tamaño fijo
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Consultar datos de animal");
        stage.setScene(scene);
        stage.showAndWait();

    }
    
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
    
	   /**
     * Inicializa la escena principal.
     * @param arg0 URL de inicialización (no utilizada en este caso).
     * @param arg1 ResourceBundle de inicialización (no utilizada en este caso).
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
		colRaza.setCellValueFactory(new PropertyValueFactory<>("raza"));
		colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
		colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		colObservacion.setCellValueFactory(new PropertyValueFactory<>("observacion"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_Primera_Consulta"));
		//colRuta.setCellValueFactory(new PropertyValueFactory<>("ruta_foto"));
		
		animales = FXCollections.observableArrayList();
		
		AnimalDao animalDao = new AnimalDao();
		
		ObservableList<Animal> animalesDesdeBD = animalDao.cargarAnimales();
		
		animales.addAll(animalesDesdeBD);
		
		tabla.setItems(animales);
		
		tabla.refresh();
		
		 // Modificar el registro al hacer doble click
        tabla.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Animal animalSeleccionado = tabla.getSelectionModel().getSelectedItem();
                if (animalSeleccionado != null) {
                    abrirVentanaModificar(animalSeleccionado);
                }
            }
        });
	}
}
