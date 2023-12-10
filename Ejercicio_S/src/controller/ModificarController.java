package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import dao.AnimalDao;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Animal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class ModificarController implements Initializable{
	private boolean existe;


	  @FXML
	    private Button btnCancelar;

	    @FXML
	    private Button btnGuardar;

	    @FXML
	    private Button btnSubir;

	    @FXML
	    private ImageView imgAnimal;

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
	    
		private ObservableList<Animal> animales;
			
		private String codigoSeleccionado;
		private String nombreSeleccionado;
		

	    // Métodos getter para obtener los valores seleccionados
	    public String getCodigoSeleccionado() {
	        return codigoSeleccionado;
	    }

	    public String getNombreSeleccionado() {
	        return nombreSeleccionado;
	    }

	    public ModificarController() {
	    	
	    }
	    
	    private Animal animal;

		private EscenaPrincipalController escenaPrincipalController;
	    
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
			
			byte[] fotoExiste = a.getFoto();
			if (fotoExiste == null) {
				Image img = new Image(getClass().getResourceAsStream("/images/Foto.jpg"));//sobre rutas de fotos hay q usar getResourceASStream
				imgAnimal.setImage(img);
				
		        // Crear un Timeline que se ejecutará después de 1 segundos
		        Duration delay = Duration.seconds(1);
		        Timeline timeline = new Timeline(new KeyFrame(delay, new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                // Utilizar Platform.runLater para ejecutar el código en el hilo de la interfaz de usuario
		                Platform.runLater(new Runnable() {
		                    @Override
		                    public void run() {
		                        // Muestra la ventana de información después de 5 segundos
		                        mostrarInformacion("No tienes foto asignada al animal.");
		                    }
		                });
		            }
		        }));

		        // Iniciar el Timeline
		        timeline.play();
		    }
					
	        AnimalDao animalDao = new AnimalDao();
	        String cod_animal = a.getCodigo();
	        String nom_animal = a.getNombre();
	        animalDao.getFoto(cod_animal, nom_animal, imgAnimal);
			
		}
		
		public void setEscenaPrincipalController(EscenaPrincipalController controller) {
			this.escenaPrincipalController = controller;
		}
	    // Ventanas INFO Y ERROR
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
			txtCod.clear();
			
		    txtNombre.clear();
		    txtEspecie.clear();
		    txtRaza.clear();
		    txtSexo.clear();
		    txtEdad.clear();
		    txtPeso.clear();
		    txtObservacion.clear();
		}
		
		private void cerrarVentanaModal() {
		    Stage stage = (Stage) btnGuardar.getScene().getWindow();
		    stage.close();
		}
	    
		
		@FXML
		void modiFoto(ActionEvent event) {
			 // Configurar el FileChooser
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagen Nuevo", "*.png", "*.jpg", "*.gif"));

	        // Mostrar el cuadro de diálogo para seleccionar un archivo
	        File selectedFile = fileChooser.showOpenDialog(null);
	        
	        if (selectedFile != null) {
	            // Cargar la imagen seleccionada en el ImageView
	            Image selectedImage = new Image(selectedFile.toURI().toString());
	            imgAnimal.setImage(null);
	            imgAnimal.setImage(selectedImage);
	        }
		}
		
		
		
		
		
		

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

		                Animal animalModificado = new Animal(codigo, nombre, especie, raza, sexo, edad, peso, observacion, null, fotoBytes);
		                if (animales.contains(animalModificado)) {
		                    mostrarError("El animal ya existe");
		                } else {
		                    AnimalDao animalDao = new AnimalDao();
		                    boolean exito = animalDao.modificarAnimal(animalModificado, animal);

		                    if (exito) {
		                    	/*borrarImg(animal.getCodigo() + "_" + animal.getNombre() + ".png");
		                    	if (imgAnimal.getImage() != null) {
		                    		saveImg(codigo + "_" + nombre);      NO HACE FALTA TODOS LOS CAMBIOS SE REALIZAN en BD           		                    		
		                    	}*/
		                        mostrarInformacion("Animal modificado correctamente");
		                        
	                            // Actualiza animal original con los nuevos datos
		                        animal.setCodigo(codigo);
		                        animal.setNombre(nombre);
		                        animal.setEspecie(especie);
		                        animal.setRaza(raza);
		                        animal.setSexo(sexo);
		                        animal.setEdad(edad);
		                        animal.setPeso(peso);
		                        animal.setObservacion(observacion);
		                    } else {
		                        mostrarError("Error al modificar el animal");
		                    }
		                    
		                    limpiarCampos();
		                    cerrarVentanaModal();
		                }
		            }
		        } catch (NumberFormatException e) {
		            mostrarError("El campo edad/peso está vacío o no es un número válido.");
		        }
		    }

	    }
	    
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
	    
	    //me metodod para eleminar foto anterior 
	    @FXML
	    boolean borrarImg(String nomFoto) {
	    	String pathFoto= "resources/images/" + nomFoto;
	    	 File fileToDelete = new File(pathFoto);

	         // Verifica si el archivo existe antes de intentar eliminarlo
	         if (fileToDelete.exists()) {
	             // Intenta eliminar el archivo
	             return fileToDelete.delete();
	         } else {
	             System.out.println("El archivo no existe en la ruta proporcionada.");
	             return false;
	         }
	    }
	    
	    @FXML
	    void cancelar(ActionEvent event) {
	    	// Obtener el Stage actual
	    	Stage stage = (Stage) btnCancelar.getScene().getWindow();
	    	
	    	// Cerrar la ventana modal
	    	stage.close();
	    }
	    	       	   
	    public void initialize(URL url, ResourceBundle rb) {
	    	
	    /*	if (existe == false) {
    		
				String absolutePath = getClass().getClassLoader().getResource("images/Foto.jpg").toExternalForm();
				Image image = new Image(absolutePath);
				imgAnimal.setImage(image);
	    	}
	     	/*String codigo = this.txtCod.getText();
		    String nombre = this.txtNombre.getText();
	    	laFoto(codigo + "_" + nombre + ".png");*/
	    	//Image img = new Image("resources/images/ventana_ven.png");
	    	//imgTst.setImage(img);
	    	/*
	        String codigo = animal.getCodigo();
	        String nombre = animal.getNombre();
			*/
	        // Llamar al método para  la foto con el código y nombre del animal
	    	//select();

	        //System.out.println("Código: " + codigoSeleccionado);
	        //System.out.println("Nombre: " + nombreSeleccionado);
    	
	    	
	    	/*
	    	String codigo = this.txtCod.getText();
		    String nombre = this.txtNombre.getText();
	        String rutaImagen = "resources/images/" + codigo + "_" + nombre + ".png";

	        System.out.println("Ruta de la imagen: " + rutaImagen);

	        // Crear un objeto Image con la ruta de la imagen
	        Image imagen = new Image(new File(rutaImagen).toURI().toString());
	        
	        */
	       /* if (imgTst.getImage() == null) {
	        	imgTst.setImage(imagen);
	        	
	        }
	    	//imgAnimal.setImage(imagen);*/
	    	
	    	}
	}
