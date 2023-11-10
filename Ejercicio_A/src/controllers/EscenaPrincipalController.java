package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;


public class EscenaPrincipalController implements Initializable {

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private CheckBox checkBoxPregunta;

    // aqui habia que especificar el tipo de comboBox "String" antes estaba asi private ComboBox<String> comboBoxEdad;
    @FXML
    private ComboBox<String> comboBoxEdad;

    @FXML
    private ListView<String> listViewCual;

    @FXML
    private RadioButton rbHombre;

    @FXML
    private RadioButton rbMujer;

    @FXML
    private RadioButton rbOtro;

    @FXML
    private Slider sliderCine;
    
  //  private double valorSliderCine;

    @FXML
    private Slider sliderCompras;
    
 //   private double valorSliderCompras;

    @FXML
    private Slider sliderTele;
    
  //  private double valorSliderTele;

    @FXML
    private TextField txtHermanos;

    @FXML
    private TextField txtProfesion;
    
    @FXML
    private ToggleGroup grpSexo;

    // Métodos de control de entrada

    /**
     * Verifica si la cadena es un número.
     *
     * @param cadena La cadena a verificar.
     * @return true si la cadena es un número, false de lo contrario.
     */
    private boolean esNumero(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Maneja el evento de mostrar el diálogo al hacer clic en el botón "Aceptar".
     *
     * @param event El evento de acción.
     */
    @FXML
    void mostrarDialgo(ActionEvent event) {  	
    	
    	
        String profesion = txtProfesion.getText(); // Obtener el valor del campo profesión
        String hermanos = txtHermanos.getText();
        String edad = comboBoxEdad.getSelectionModel().getSelectedItem();
        String sports;
        // tenia puesto en el if abajo chekbox isselected
        if (profesion.isEmpty() || esNumero(profesion) || hermanos.isEmpty() || !esNumero(hermanos) || (checkBoxPregunta.isSelected() && selectedSports.isEmpty())){
        	
        	if (profesion.isEmpty() || esNumero(profesion)) {
        		profesion = "Hay que rellenar el campo profesion";
        	} else {
        		profesion = "";
        	}
        	if (hermanos.isEmpty() || !esNumero(hermanos)) {
        		hermanos = "Hay que rellenar el campo numero de hermanos";
        	} else {
        		hermanos = "";
        	}
        	if (checkBoxPregunta.isSelected() && selectedSports.isEmpty()) {
        		sports = "Tienes que indicar los deportes que practicas";
        	} else {
        		sports = "";
        	}
        	
        	Alert alertaError = new Alert(Alert.AlertType.ERROR);
        	alertaError.setTitle("TUS DATOS");
        	alertaError.setHeaderText(null);
        	alertaError.setContentText(profesion + "\n" + hermanos + "\n" + sports);
        	alertaError.showAndWait();        	
        	
        } else {      
	
	        // control de sexo , asigno a una variable los valores cuando el radio button is selected
	        String textoSexo;
	        if (rbHombre.isSelected()) {
	        	textoSexo = " Hombre";
	        } else if (rbMujer.isSelected()) {
	        	textoSexo =  " Mujer";
	        } else {
	        	textoSexo = " Otro";
	        }
	        
	        // control del checkbox sobre si practica algun deporte
	        String valores = " ";
	        if (checkBoxPregunta.isSelected()) {
	        	if (!selectedSports.isEmpty()) {
	            	for (String valor : selectedSports) {
	            		valores = valores + valor + "\n";
	            	}
	        	} else {
	        		valores = "Tienes que indicar los deportes que practicas";
	        	}     
	        }
	        
	        // Sliders value
	        double valorCompras = sliderCompras.getValue();
	        double valorTele = sliderTele.getValue();
	        double valorCine = sliderCine.getValue();
	        
	        DecimalFormat decimalFormat = new DecimalFormat("#.#");
	        String valorFormateadoCompras = decimalFormat.format(valorCompras);
	        String valorFormateadoTele = decimalFormat.format(valorTele);
	        String valorFormateadoCine = decimalFormat.format(valorCine);
	
	       // Definicion de la ventana INFORMATION
	        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
	        alerta.setTitle("TUS DATOS");
	        alerta.setHeaderText(null);
	        alerta.setContentText(" Profesión: " + profesion+"\n Nº de hermanos: " + hermanos+"\n edad : "+ edad +"\n sexo" 
	        + textoSexo+ "\n Deportes que practicas: \n"+valores.toString()+"Grado de aficción a las compras : "+valorFormateadoCompras+
	        "\n Grado de aficción a ver televisión : " + valorFormateadoTele + "\n Grado de aficción ir al cine : " + valorFormateadoCine);
	        alerta.showAndWait();
	        
	    	// Reiniciar la lista de selectedSports para que cada vez el user le apetece cambiar los sports se mostra bien
	    	selectedSports.clear();
    	
        }
    }
    
    
    ArrayList<String> selectedSports = new ArrayList<String>();
    @FXML
    void seleccionarDeporte(MouseEvent event) {

    	String deporteSeleccionado = listViewCual.getSelectionModel().getSelectedItem().toString();
    	if (!selectedSports.contains(deporteSeleccionado)) {
    		selectedSports.add(deporteSeleccionado);
    	}
    }

    @FXML
    void seleccionarEdad(ActionEvent event) {
    	String EdadSeleccionado = comboBoxEdad.getSelectionModel().getSelectedItem().toString();
    	
    }
    
    public void initialize(URL url, ResourceBundle rb) {
    	
    	// Voy a crear la funcion de visualizacion del comboBox Edad:
    	ObservableList<String> listaComboBox = FXCollections.observableArrayList("Menores de 18","Enrtre 18 y 30","Entre 31 y 50","Entre 51 y 70","Mayores de 70");
    	comboBoxEdad.setItems(listaComboBox);
    	
    	// crear la funcion de visualizacion del listViewCual:
    	
    	ObservableList<String> listaViewList = FXCollections.observableArrayList("Tenis","Fútbal","Baloncesto","Natación","Ciclismo","otro");
    	listViewCual.setItems(listaViewList);
    	listViewCual.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	   	
    	// Cancelar button para salir del APP !ESO NO HACE FALTA BASTA CON INCIALIZAR EL BUTTON ABAJO
    	btnCancelar.setOnAction(e -> Platform.exit());
    }
}