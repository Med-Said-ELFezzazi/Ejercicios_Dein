package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private TextField txtFieldFiltrar;

    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnExportar;

    @FXML
    private Button btnImportar;

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
     * para modificar el registro y usa el metodo arriba
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
     * Elimina la persona seleccionada de la tabla.
     *
     * @param event Evento de acción.
     */
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
    /**
     * Filtra los registros en la tabla según el texto ingresado en el campo de texto de filtrado.
     *
     * @param event El evento de teclado que desencadena el método.
     */
    @FXML
    void filtrarRegistros(KeyEvent event) {
        // Obtiene el texto ingresado en el campo de filtrado y lo convierte a minúsculas
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

    /**
     * Procesa el archivo CSV seleccionado e importa los datos en la tabla.
     *
     * @param file Archivo CSV seleccionado.
     */
    private void importarDatosDesdeCSV(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            
            // Saltar la primera línea que contiene la cabecera
            br.readLine();

            while ((linea = br.readLine()) != null) {
                // Dividir la línea en valores separados por comas
                String[] valores = linea.split(",");
                if (valores.length == 3) {
                    String nombre = valores[0].trim();
                    String apellidos = valores[1].trim();
                    int edad = Integer.parseInt(valores[2].trim());

                    // Verificar si el registro ya existe en la tabla
                    boolean registroExistente = false;
                    for (Persona persona : personas) {
                        if (persona.getNombre().equals(nombre) && persona.getApellidos().equals(apellidos) && persona.getEdad() == edad) {
                            registroExistente = true;
                            mostrarError("Hay persona(s) en la tabla que coincide con datos del archivo csv");
                            break;
                        }
                    }

                    if (!registroExistente) {
                        // Agregar el nuevo registro a la tabla
                        personas.add(new Persona(nombre, apellidos, edad));
                    }
                }
            }

            // Ventana INFO para informar que los datos han importado correctamente
            mostrarInformacion("Datos importados correctamente.");
        } catch (IOException e) {
            mostrarError("Error al importar los datos desde el archivo CSV: " + e.getMessage());
        }
    }

    /**
     * Método llamado al hacer clic en el botón para importar personas desde un archivo CSV.
     *
     * @param event Evento de acción.
     */
    @FXML
    void importarPersonas(ActionEvent event) {
        // Crear un diálogo FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar desde CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"));

        // Mostrar el cuadro de diálogo para seleccionar un archivo
        File file = fileChooser.showOpenDialog(btnImportar.getScene().getWindow());

        if (file != null) {
            // Procesar el archivo CSV
            importarDatosDesdeCSV(file);
        }
    }

    /**
     * Método para exportar las personas de la tabla a un archivo CSV.
     *
     * @param event Evento de acción.
     */
    @FXML
    void exportarPersonas(ActionEvent event) {
        // Crear un diálogo FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar a CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"));

        // Mostrar el cuadro de diálogo para seleccionar la ubicación y el nombre del archivo
        File file = fileChooser.showSaveDialog(btnExportar.getScene().getWindow());

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                // Primero escribir la cabecera del archivo CSV
                fileWriter.write("Nombre,Apellidos,Edad\n");

                // Recorrer la lista de personas y escribir sus datos en el archivo
                for (Persona persona : personas) {
                    String linea = persona.getNombre() + "," + persona.getApellidos() + "," + persona.getEdad() + "\n";
                    fileWriter.write(linea);
                }
                
                // Ventana INFO para informar que los datos han exportado correctamente
                mostrarInformacion("Datos exportados correctamente.");
            } catch (IOException e) {
                mostrarError("Error al exportar los datos: " + e.getMessage());
            }
        }
    }

    /**
     * Inicializa la interfaz y asigna atributos a las columnas de la tabla.
     *
     * @param url Ubicación de inicialización.
     * @param rb  Recursos para la inicialización.
     */
    // Estructura datos
    public void initialize(URL url, ResourceBundle rb) {
    	
    	// Asignación de los atributos a las columnas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        
        // Agregar la lista de personas en la tabla
        personas = FXCollections.observableArrayList();
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
    }
}