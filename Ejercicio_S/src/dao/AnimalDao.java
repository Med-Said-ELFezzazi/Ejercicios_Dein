package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Animal;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Date;

public class AnimalDao {
	
	private ConexionBD conexion;
	
    /**
     * Consulta la información de un animal en base al código.
     *
     * @param codigo Código del animal a consultar.
     * @return Información del animal en formato de cadena.
     */
	public String consultar(String codigo) {
		String rslt_consulta = "";
		try {
			conexion = new ConexionBD();
			String consulta = "SELECT nombre,especie,raza,sexo,edad,peso,observacion FROM animal where codigo = '" + codigo + "'";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
		        String nombre = rs.getString("nombre");
		        String especie = rs.getString("especie");
		        String raza = rs.getString("raza");
		        String sexo = rs.getString("sexo");
		        int edad = rs.getInt("edad");
		        double peso = rs.getDouble("peso");
		        String observacion = rs.getString("observacion");
		        rslt_consulta = " Nombre: "+ nombre + "\n Especie: "+ especie + "\n Raza: "+ raza + "\n Sexo: "+sexo + "\n Edad: "+edad+ "\n Peso: "+peso + "\n Y la observacion: "+observacion;
			}
			rs.close();
			conexion.CloseConexion();
	
		} catch (SQLException e){
			e.printStackTrace();	
		}
		return rslt_consulta;
	}
	
    /**
     * Carga la lista de animales desde la base de datos.
     *
     * @return Lista observable de animales.
     */
	public ObservableList<Animal> cargarAnimales() {
		
		ObservableList<Animal> animales = FXCollections.observableArrayList();
		
		try {
			conexion = new ConexionBD();
			String consulta = "SELECT * FROM animal";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				// El usuario no le importa el id del animal
				String codigo = rs.getString("codigo");
				String nombre = rs.getString("nombre");
				String especie = rs.getString("especie");
				String raza = rs.getString("raza");
				String sexo = rs.getString("sexo");
				int edad = rs.getInt("edad");
				Double peso = rs.getDouble("peso");
				String observacion = rs.getString("observacion");
				//Date fecha_primera_consulta = new Date(rs.getDate("fecha_primera_consulta").getTime());
                Date fecha_Primera_Consulta = rs.getDate("fecha_primera_consulta");
                // Obtener el campo "foto" como arreglo de bytes (BLOB)
                Blob blobFoto = rs.getBlob("foto");
                byte[] fotoBytes = null;

                if (blobFoto != null) {
                    fotoBytes = blobFoto.getBytes(1, (int) blobFoto.length());
                }
				//Date fecha_primera_consulta = new Date(System.currentTimeMillis());
				//String ruta_foto = rs.getString("ruta_foto");
				Animal a = new Animal(codigo, nombre, especie, raza, sexo, edad, peso , observacion, fecha_Primera_Consulta, fotoBytes);
				animales.add(a);
			}
			rs.close();
			conexion.CloseConexion();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return animales;
	}
	
    /**
     * Agrega un nuevo animal a la base de datos.
     *
     * @param animal Animal a agregar.
     * @return true si la operación fue exitosa, false de lo contrario.
     */
	public boolean agregarAnimal(Animal animal) {
	    try {
	        conexion = new ConexionBD();
	        String consulta = "INSERT INTO animal (codigo, nombre, especie, raza, sexo, edad, peso, observacion, fecha_primera_consulta, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?)";
	        PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

	        pstmt.setString(1, animal.getCodigo());
	        pstmt.setString(2, animal.getNombre());
	        pstmt.setString(3, animal.getEspecie());
	        pstmt.setString(4, animal.getRaza());
	        pstmt.setString(5, animal.getSexo());
	        pstmt.setInt(6, animal.getEdad());
	        pstmt.setDouble(7, animal.getPeso());
	        pstmt.setString(8, animal.getObservacion());
	        pstmt.setBytes(9, animal.getFoto());

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
     * Elimina un animal de la base de datos.
     *
     * @param animal Animal a eliminar.
     * @return true si la operación fue exitosa, false de lo contrario.
     */
	public boolean eliminarAnimal(Animal animal) {
		try {
			conexion = new ConexionBD();
			String consulta = "DELETE FROM animal WHERE codigo = '" + animal.getCodigo() + "' AND nombre = '" + animal.getNombre() + "'"; // la manera correcta para eliminar
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
     * Modifica los datos de un animal en la base de datos.
     *
     * @param animalModificado Animal con los datos modificados.
     * @param animalDestino    Animal con los datos originales.
     * @return true si la operación fue exitosa, false de lo contrario.
     */
	public boolean modificarAnimal(Animal animalModificado, Animal animalDestino) {
	    try {
	        conexion = new ConexionBD();
	        String consulta = "UPDATE animal SET codigo = ?, nombre = ?, especie = ?, raza = ?, sexo = ?, edad = ?, peso = ?, observacion = ?, foto = ? WHERE codigo = ? AND nombre = ? AND especie = ? AND raza = ? AND sexo = ? AND edad = ? AND peso = ? AND observacion = ? AND foto = ?";

	        PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

	        // Establecer los parámetros usando los métodos set correspondientes
	        pstmt.setString(1, animalModificado.getCodigo());
	        pstmt.setString(2, animalModificado.getNombre());
	        pstmt.setString(3, animalModificado.getEspecie());
	        pstmt.setString(4, animalModificado.getRaza());
	        pstmt.setString(5, animalModificado.getSexo());
	        pstmt.setInt(6, animalModificado.getEdad());
	        pstmt.setDouble(7, animalModificado.getPeso());
	        pstmt.setString(8, animalModificado.getObservacion());
	        // Establecer la foto como un blob (byte[])
	        pstmt.setBytes(9, animalModificado.getFoto());

	        // Resto de los parámetros para la cláusula WHERE
	        pstmt.setString(10, animalDestino.getCodigo());
	        pstmt.setString(11, animalDestino.getNombre());
	        pstmt.setString(12, animalDestino.getEspecie());
	        pstmt.setString(13, animalDestino.getRaza());
	        pstmt.setString(14, animalDestino.getSexo());
	        pstmt.setInt(15, animalDestino.getEdad());
	        pstmt.setDouble(16, animalDestino.getPeso());
	        pstmt.setString(17, animalDestino.getObservacion());
	        // Establecer la foto como un blob (byte[])
	        pstmt.setBytes(18, animalDestino.getFoto());

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
     * Obtiene la foto de un animal desde la base de datos y la establece en un ImageView.
     *
     * @param codigo     Código del animal.
     * @param nombre     Nombre del animal.
     * @param imageView  ImageView donde se establecerá la imagen.
     */
	public void getFoto(String codigo, String nombre, ImageView imageView) {
		  try {
	            conexion = new ConexionBD();
	            String consulta = "SELECT foto FROM animal WHERE codigo = ? AND nombre = ?";

	            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
	            pstmt.setString(1, codigo);
	            pstmt.setString(2, nombre);

	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                Blob blobFoto = rs.getBlob("foto");

	                if (blobFoto != null) {
	                    byte[] fotoBytes = blobFoto.getBytes(1, (int) blobFoto.length());
	                    Image foto = new Image(new ByteArrayInputStream(fotoBytes));
	                    imageView.setImage(foto);
	                }
	            }

	            rs.close();
	            conexion.CloseConexion();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}	
}
