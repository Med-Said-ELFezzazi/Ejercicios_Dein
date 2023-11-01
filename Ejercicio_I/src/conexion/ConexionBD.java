package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConexionBD {
    private Connection conexion;
    /**
     * Es el constructor que se llama al crear un objeto de esta clase, lanzado la conexión
     * @throws SQLException Hay que controlar errores de SQL
     */
    public ConexionBD() throws SQLException {
       /* String host = "localhost";
        String baseDatos = "personas";
        String usuario = "root";      ESO ES HARDCODE 
        String password = "";
        String cadenaConexion = "jdbc:mysql://" + host + "/" + baseDatos+ "?serverTimezone=" + TimeZone.getDefault().getID();
        conexion = DriverManager.getConnection(cadenaConexion, usuario, password);
        conexion.setAutoCommit(true);*/
    	
    	String url = Propiedades.getValor("urlParaPersonas");
    	String user = Propiedades.getValor("user");
    	String password = Propiedades.getValor("password");
    	conexion = DriverManager.getConnection(url, user, password);
    	conexion.setAutoCommit(true);

    }
    /**
     * Esta clase devuelve la conexión creada
     * @return una conexión a la BBDD
     */
    public Connection getConexion() {
        return conexion;
    }
    
    public Connection CloseConexion() throws SQLException{
    	conexion.close();
    	return conexion;
    }
    
    /*public Connection CloseConexion() throws SQLException{
    	this.conexion.close();
    	return conexion;
    }*/
}
