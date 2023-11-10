module Ejercicio_C {
	requires javafx.controls;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml;
	opens model to javafx.base;

}
