package model;

import java.util.Objects;

public class Persona {
	
	String nombre , apellidos;
	int edad;
	
	// constroctor sin argumentos
	
	public Persona() {
		
	}
	
	public Persona (String nombre , String apellidos , int edad) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null || getClass() != obj.getClass())
	        return false;
	    final Persona other = (Persona) obj;
	    
	    if (this.edad != other.edad) {
	        return false;
	    }
	    if (!this.nombre.equals(other.nombre)) {
	        return false;
	    }
	    if (!this.apellidos.equals(other.apellidos)) {
	        return false;
	    }

	    return true;
	}

}

