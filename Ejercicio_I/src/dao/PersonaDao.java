package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Persona;

public class PersonaDao {
	
    private ConexionBD conexion;
    

  public ObservableList<Persona> cargarPersonas()  {
    	
    	ObservableList<Persona> personas = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();        	
        	String consulta = "select * from persona";
        	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);      
        	ResultSet rs = pstmt.executeQuery();   
				
			 while (rs.next()) {
				 // El usuario no le importa el id de la persona				 
				 //int idPersona = rs.getInt("id");
				 String nombre = rs.getString("nombre");
				 String apellidos = rs.getString("apellidos");
				 int edad = rs.getInt("edad");
				 Persona p1 = new Persona(nombre, apellidos, edad);
				 
				 // Imprimir los datos de la BD en la consola	
				 // System.out.println(p1.toString());
				 	
				 personas.add(p1);
        }             
		rs.close();       
        conexion.CloseConexion();
        
	    } catch (SQLException e) {	    	
	    	e.printStackTrace();
	    }    
        return personas;    
    }

  /*
	// Funcion de agregar persona en la BD
  public boolean agregarPersona(Persona persona) {
	    try {
	        conexion = new ConexionBD();
	        String consulta = "INSERT INTO persona (nombre, apellidos, edad) VALUES ('" + persona.getNombre() + "', '" + persona.getApellidos() + "', " + persona.getEdad() + ")";
	        Statement stmt = conexion.getConexion().createStatement();
	        
	        int filasAfectadas = stmt.executeUpdate(consulta);
	        stmt.close();
	        conexion.CloseConexion();

	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}*/
 
  public boolean agregarPersona(Persona persona) {
	    try {
	        conexion = new ConexionBD();
	        String consulta = "INSERT INTO persona (nombre, apellidos, edad) VALUES (?, ?, ?)";
	        PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
	        
	        pstmt.setString(1, persona.getNombre());
	        pstmt.setString(2, persona.getApellidos());
	        pstmt.setInt(3, persona.getEdad());

	        int filasAfectadas = pstmt.executeUpdate();
	        pstmt.close();
	        conexion.CloseConexion();

	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	} 


	
	// Funcion de modificar persona en la BD
	public boolean modificarPersona(Persona persona) {
	    try {
	        conexion = new ConexionBD();
	        String consulta = "UPDATE personas.persona SET nombre = ?, apellidos = ?, edad = ? WHERE id = ?";
	        PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
	        pstmt.setString(1, persona.getNombre());
	        pstmt.setString(2, persona.getApellidos());
	        pstmt.setInt(3, persona.getEdad());
	        pstmt.setInt(4, persona.getId());

	        int filasAfectadas = pstmt.executeUpdate();
	        pstmt.close();
	        conexion.CloseConexion();

	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	// Funcion de eliminar persona en la Base de datos
	public boolean eliminarPersona(Persona persona) {
	    try {
	        conexion = new ConexionBD();
	        String consulta = "DELETE FROM persona WHERE id = "+persona.getId();
	        PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
	        //pstmt.setInt(1, persona.getId());

	        int filasAfectadas = pstmt.executeUpdate(consulta);
	        pstmt.close();
	        conexion.CloseConexion();

	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	/*public boolean eliminarPersona(Persona persona) {
	    try {
	        conexion = new ConexionBD();
	        String consulta = "DELETE FROM persona WHERE id = '" + persona.getId();
	        Statement stmt = conexion.getConexion().createStatement();
	        
	        int filasAfectadas = stmt.executeUpdate(consulta);
	        stmt.close();
	        conexion.CloseConexion();

	        return filasAfectadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }*/
}