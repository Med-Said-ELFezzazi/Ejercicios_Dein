package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private Connection conexion;
    /**
     * Es el constructor que se llama al crear un objeto de esta clase, lanzado la conexión
     * @throws SQLException Hay que controlar errores de SQL
     */
    public ConexionBD() throws SQLException {    	
    	String url = Propiedades.getValor("urlParaAnimales");
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
}