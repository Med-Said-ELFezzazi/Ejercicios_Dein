package model;

import java.sql.Date;
import java.util.Arrays;


//import java.sql.Date;

public class Animal {

	private String codigo , nombre, especie, raza, sexo, observacion;
	private int edad;
	private double peso;
	private Date fechaPrimeraConsulta;
    private byte[] foto;
	
    /**
     * Constructor de la clase Animal.
     *
     * @param codigo                Código del animal.
     * @param nombre                Nombre del animal.
     * @param especie               Especie del animal.
     * @param raza                  Raza del animal.
     * @param sexo                  Sexo del animal.
     * @param edad                  Edad del animal.
     * @param peso                  Peso del animal.
     * @param observacion           Observación sobre el animal.
     * @param fechaPrimeraConsulta  Fecha de la primera consulta del animal.
     * @param foto                  Arreglo de bytes que representa la foto del animal.
     */
	public Animal(String codigo, String nombre, String especie, String raza, String sexo,
			int edad, Double peso, String observacion, Date fechaPrimeraConsulta, byte[] foto) {
		
		this.codigo = codigo;
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.sexo = sexo;
		this.edad = edad;
		this.peso = peso;
		this.observacion = observacion;
		this.fechaPrimeraConsulta = fechaPrimeraConsulta;
		this.foto = foto;
	}
	
	public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFecha_Primera_Consulta() {
		return fechaPrimeraConsulta;
	}

	public void setFecha_Primera_Consulta(Date fecha_Primera_Consulta) {
		this.fechaPrimeraConsulta = fecha_Primera_Consulta;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null || getClass() != obj.getClass())
	        return false;
	    final Animal other = (Animal) obj;
	    
	    if (!this.codigo.equals(other.codigo)) {
	    	return false;
	    }
	    
	    if (!this.nombre.equals(other.nombre)) {
	    	return false;
	    }
	    
	    if (!this.especie.equals(other.especie)) {
	    	return false;
	    }
	    
	    if (!this.raza.equals(other.raza)) {
	    	return false;
	    }
	    
	    if (!this.sexo.equals(other.sexo)) {
	    	return false;
	    }
	    
	    if (this.edad != other.edad) {
	        return false;
	    }
	    
	    
	    if (this.peso != other.peso) {
	        return false;
	    }
	    
	    if (!this.observacion.equals(other.observacion)) {
	    	return false;
	    }
	    
	    if (!Arrays.equals(this.foto, other.foto)) {
            return false;
        }

	    return true;
	}
	
	@Override
	public String toString() {
		return "Animal [codigo=" + codigo + ", nombre=" + nombre + ", especie=" + especie + ", raza=" + raza + ", sexo="
				+ sexo + ", observacion=" + observacion + ", edad=" + edad + ", peso=" + peso + "]";
	}
	
}