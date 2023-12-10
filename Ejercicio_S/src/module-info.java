module Ejercicio_S {
	requires javafx.controls;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml;
	opens model to javafx.base;
    opens dao to javafx.base;
}
