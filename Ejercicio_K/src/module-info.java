module Ejercicio_K {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires javafx.web;
	requires javafx.swing;
	requires javafx.media;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml;
}
