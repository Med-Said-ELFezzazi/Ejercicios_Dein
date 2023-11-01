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

	public ObservableList<Persona> cargarPersonas() {

		ObservableList<Persona> personas = FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			String consulta = "select * from persona";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				// El usuario no le importa el id de la persona
				// int idPersona = rs.getInt("id");
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
	 * // Funcion de agregar persona en la BD public boolean agregarPersona(Persona
	 * persona) { try { conexion = new ConexionBD(); String consulta =
	 * "INSERT INTO persona (nombre, apellidos, edad) VALUES ('" +
	 * persona.getNombre() + "', '" + persona.getApellidos() + "', " +
	 * persona.getEdad() + ")"; Statement stmt =
	 * conexion.getConexion().createStatement();
	 * 
	 * int filasAfectadas = stmt.executeUpdate(consulta); stmt.close();
	 * conexion.CloseConexion();
	 * 
	 * return filasAfectadas > 0; } catch (SQLException e) { e.printStackTrace();
	 * return false; } }
	 */

	/**
	 * 
	 * @param persona
	 * @return
	 */
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

	/**
	 * Funcion de modificar persona en la BD
	 * 
	 * @param personaModificada es la persona con la nueva info a meter
	 * @param personaDestino    es la persona que va a sufrir las modificaciones
	 * @return
	 */
	public boolean modificarPersona(Persona personaModificada, Persona personaDestino) {
		try {
			conexion = new ConexionBD();
			String consulta = "UPDATE persona SET nombre = '" + personaModificada.getNombre() + "' , apellidos = '"
					+ personaModificada.getApellidos() + "' , edad = " + personaModificada.getEdad()
					+ " WHERE nombre = '" + personaDestino.getNombre() + "' AND apellidos = '"
					+ personaDestino.getApellidos() + "'  AND edad = " + personaDestino.getEdad();
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

			int filasAfectadas = pstmt.executeUpdate(consulta);
			pstmt.close();
			conexion.CloseConexion();

			return filasAfectadas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param persona
	 * @return
	 */
	// Funcion de eliminar persona en la Base de datos
	public boolean eliminarPersona(Persona persona) {
		try {
			conexion = new ConexionBD();
			String consulta = "DELETE FROM persona WHERE nombre = '" + persona.getNombre() + "' AND apellidos = '"
					+ persona.getApellidos() + "' AND edad = " + persona.getEdad(); // la manera correcta para eliminar
																					// persona
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			/*
			 * pstmt.setString(1, persona.getNombre()); pstmt.setString(2,
			 * persona.getApellidos()); ESO NO FUNCIONA PONEMOS LOS VALORES DIRECTAMENTE
			 * MEDIANTE UN GET pstmt.setInt(3, persona.getEdad());
			 */

			int filasAfectadas = pstmt.executeUpdate(consulta);
			pstmt.close();
			conexion.CloseConexion();

			return filasAfectadas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}