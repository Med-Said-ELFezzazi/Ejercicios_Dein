module Ejercicio_F {
		requires javafx.controls;
		requires javafx.web;
		requires javafx.fxml;
		requires javafx.swing;
		requires javafx.media;
		requires javafx.graphics;
		requires javafx.base;
	    requires java.sql;
		
		opens application to javafx.graphics, javafx.fxml;
		opens controllers to javafx.graphics, javafx.fxml;
		opens model to javafx.base;
	    opens dao to javafx.base;

	}
