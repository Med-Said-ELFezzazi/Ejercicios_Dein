package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import controller.EscenaPrincipalController;
import dao.AnimalDao;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Animal;

public class AltaController {
	
    @FXML
    private ImageView foto;		
    
    @FXML
    private ImageView imgAnimal;	//foto variable


	private ObservableList<Animal> animales;
	
	private Animal animal;


	@FXML
	private Button btnSubir;
	
	
	@FXML
	private TextField txtCod;
	
	@FXML
	private TextField txtEdad;
	
	@FXML
	private TextField txtEspecie;
	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtObservacion;
	
	@FXML
	private TextField txtPeso;
	
	@FXML
	private TextField txtRaza;
	
	@FXML
	private TextField txtSexo;
	
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnCancelar;

	private controller.EscenaPrincipalController escenaPrincipalController;
	
	/**
	 * Inicializar los atributos del animal
	 * @param animales Lista de animales
	 * @param Objeto instanciado del animal
	 */
	public void initAttributtes(ObservableList<Animal> animales, Animal a) {
		this.animales = animales;
		animal = a;
		txtCod.setText(a.getCodigo());
		txtNombre.setText(a.getNombre());
		txtEspecie.setText(a.getEspecie());
		txtRaza.setText(a.getRaza());
		txtSexo.setText(a.getSexo());
		txtEdad.setText(a.getEdad() + "");
		txtPeso.setText(a.getPeso() + "");
		txtObservacion.setText(a.getObservacion());

	}

	/**
	 * Initializes attributes for the animal registration view.
	 * @param animales The list of animals.
	 */
	public void initAttributtes(ObservableList<Animal> animales) {
		this.animales = animales;
	}
	
	/**
	 * metodo que devuelve el animal actual
	 * @return el objeto actual
	 */
	public Animal getAnimal() {
		return animal;
	}

	/**
	 * Gets the new animal to be added.
	 * @return The new animal object.
	 */
	public Animal getNuevoAnimal() {
		return animal;
	}
	
	/**
	 * Sets the reference to the main controller.
	 * @param controller The main controller.
	 */
	public void setEscenaPrincipalController(EscenaPrincipalController controller) {
		this.escenaPrincipalController = controller;
	}
	
	/**
	 * Muestra una alerta de error con el mensaje proporcionado.
	 * @param mensaje El mensaje de error que se mostrará.
	 */
	private void mostrarError(String mensaje) {
	    Alert alertaError = new Alert(Alert.AlertType.ERROR);
	    alertaError.setTitle("ERROR");
	    alertaError.setHeaderText(null);
	    alertaError.setContentText(mensaje);
	    alertaError.showAndWait();
	}

	/**
	 * Muestra una alerta de información con el mensaje proporcionado.
	 * @param mensaje El mensaje de información que se mostrará.
	 */
	private void mostrarInformacion(String mensaje) {
	    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
	    alertaInfo.setTitle("INFO");
	    alertaInfo.setHeaderText(null);
	    alertaInfo.setContentText(mensaje);
	    alertaInfo.showAndWait();
	}
	
	/**
	 * limpiar los campos.
	 */
	private void limpiarCampos() {
		txtCod.clear();
		
	    txtNombre.clear();
	    txtEspecie.clear();
	    txtRaza.clear();
	    txtSexo.clear();
	    txtEdad.clear();
	    txtPeso.clear();
	    txtObservacion.clear();
	    
	}
	
	/**
	 * Cierra la ventana modal.
	 */
	private void cerrarVentanaModal() {
	    Stage stage = (Stage) btnGuardar.getScene().getWindow();
	    stage.close();
	}

	/**
	 * Maneja el evento de acción cuando se hace clic en el botón "Guardar".
	 * Valida la entrada, crea un nuevo objeto Animal y lo agrega a la lista.
	 * @param event El evento de acción desencadenado al hacer clic en el botón "Guardar".
	 */
	@FXML
	void guardar(ActionEvent event) {
	    String codigo = this.txtCod.getText();
	    String nombre = this.txtNombre.getText();
	    String especie = this.txtEspecie.getText();
	    String raza = this.txtRaza.getText();
	    String sexo = this.txtSexo.getText();

	    String ed = this.txtEdad.getText();
	    String pes = this.txtPeso.getText();
	    String observacion = this.txtObservacion.getText();

	    if (codigo.isEmpty() || nombre.isEmpty() || especie.isEmpty() || sexo.isEmpty() || ed.isEmpty() || pes.isEmpty() || observacion.isEmpty()) {
	        mostrarError("Todos los campos obligatorios deben estar llenos");
	    } else {
	        try {
	            int edad = Integer.parseInt(ed);
	            double peso = Double.parseDouble(pes);
	            if (!"m".equalsIgnoreCase(sexo) && !"f".equalsIgnoreCase(sexo)) {
	            	mostrarError("El sexo introducido invalido (F/M)");
	            }else if (edad < 0) {
	                mostrarError("La edad debe ser mayor o igual a cero minimum ");
	            }else if (peso < 0) {
	                mostrarError("El peso debe ser mayor que cero ");
	            } else if (edad < 0 && peso < 0) {
	                mostrarError("La edad debe ser mayor o igual a cero minimum ");
	                mostrarError("El peso debe ser mayor que cero ");
	            } else {
	                byte[] fotoBytes = obtenerFotoSeleccionada();

	                Animal newAnimal = new Animal(codigo, nombre, especie, raza, sexo, edad, peso, observacion, null, fotoBytes);
	                if (animales.contains(newAnimal)) {
	                    mostrarError("El animal ya existe");
	                    limpiarCampos();
	                } else {
	                    AnimalDao animalDao = new AnimalDao();
	                    boolean exito = animalDao.agregarAnimal(newAnimal);

	                    if (exito) {
	                    	/*if (imgAnimal.getImage() != null) {
	                    		saveImg(codigo + "_" + nombre);   NO HACE FALTA YA TENEMOS LAS FOTOS IN LA BD                 		                    		
	                    	}*/
	                        mostrarInformacion("El nuevo animal añadido correctamente");
	                    } else {
	                        mostrarError("Error al añadir el nuevo animal");
	                    }

	                    animales.add(newAnimal);
	                    limpiarCampos();
	                    cerrarVentanaModal();
	                }
	            }
	        } catch (NumberFormatException e) {
	            mostrarError("El campo edad/peso está vacío o no es un número válido.");
	        }
	    }
	}
    //me metodo para guardar la foto in images
    @FXML
    void saveImg(String nomFoto) {
    	  // Verificar si hay una imagen cargada en el ImageView
        if (imgAnimal.getImage() != null) {
            // Obtener la imagen del ImageView
            Image selectedImage = imgAnimal.getImage();

            // Crear la ruta de destino en resources/images
            String destinationPath = "resources/images/" + nomFoto + ".png";

            // Guardar la imagen en el archivo de destino
            File destinationFile = new File(destinationPath);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(selectedImage, null);

            try {
                ImageIO.write(bufferedImage, "png", destinationFile);
                mostrarInformacion("Imagen guardada con éxito en: " + destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	mostrarError("No se ha podido guardar el imagen ");
            
        }
    }

    /**
     * Convierte la imagen seleccionada en el ImageView a un array de bytes.
     * @return La representación en array de bytes de la imagen seleccionada.
     */
    private byte[] obtenerFotoSeleccionada() {
        // Assuming you have an ImageView named fotoAnimal to display the selected photo
        Image image = imgAnimal.getImage();

        // Check if an image is set
        if (image != null) {
            // Convert the JavaFX Image to a BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            // Convert the BufferedImage to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedImage, "png", outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputStream.toByteArray();
        } else {
            // If no image is set, return null or handle it based on your requirements
            return null;
        }
    }
    
    /**
     * Maneja el evento de acción cuando se hace clic en el botón "SubirFoto".
     * Abre un selector de archivos para seleccionar un archivo de imagen para cargar.
     * @param event El evento de acción desencadenado al hacer clic en el botón "SubirFoto".
     */
    @FXML
    void subirFoto(ActionEvent event) {  	
    	
  	   // Configurar el FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"));

        // Mostrar el cuadro de diálogo para seleccionar un archivo
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null) {
            // Cargar la imagen seleccionada en el ImageView
            Image selectedImage = new Image(selectedFile.toURI().toString());
            foto.setImage(null);
            imgAnimal.setImage(selectedImage);
        }
       }
    
    /**
     * Maneja el evento de acción cuando se hace clic en el botón "Cancelar".
     * Cierra la ventana modal.
     * @param event El evento de acción desencadenado al hacer clic en el botón "Cancelar".
     */
    @FXML
    void cancelar(ActionEvent event) {
		// Obtener el Stage actual
		Stage stage = (Stage) btnCancelar.getScene().getWindow();

		// Cerrar la ventana modal
		stage.close();
    } 
    
    /**
     * Procesa el archivo de imagen proporcionado y devuelve su contenido como un array de bytes.
     * @param fotoFile El archivo de imagen que se va a procesar.
     * @return La representación en array de bytes del archivo de imagen.
     */
    private byte[] procesarFoto(File fotoFile) {
    	// Procesar el archivo de imagen (puedes implementar esta lógica según tus necesidades)
    	// En este ejemplo, simplemente se lee el contenido del archivo como bytes
    	try {
    		return Files.readAllBytes(fotoFile.toPath());
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * Guarda la imagen proporcionada en un archivo con el nombre especificado en el directorio "images".
     * @param image La imagen que se va a guardar.
     * @param nombreArchivo El nombre del archivo para guardar la imagen.
     */
    private void guardarImagen(Image image, String nombreArchivo) {
        // Obtener el directorio resources/images
        String directorioRecursos = getClass().getClassLoader().getResource("images").getFile();
        File directorio = new File(directorioRecursos);

        // Crear el archivo en el directorio con el nombre proporcionado
        File archivoFoto = new File(directorio, nombreArchivo);

        // Guardar la imagen en el archivo
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", archivoFoto);
            mostrarInformacion("Imagen guardada correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al guardar la imagen.");
        }
    }
}