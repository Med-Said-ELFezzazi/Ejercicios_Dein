package model;

/**
 * Clase que representa a una persona.
 */

public class Persona {
    // Atributos de la clase
	String nombre , apellidos;
	int edad;
	
	// constroctor sin argumentos en java se crea por defecto

	   /**
     * Constructor con argumentos de la clase Persona.
     *
     * @param nombre    El nombre de la persona.
     * @param apellidos Los apellidos de la persona.
     * @param edad      La edad de la persona.
     */
	public Persona (String nombre , String apellidos , int edad) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}
	/**
     * Obtiene el nombre de la persona.
     *
     * @return El nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param nombre El nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos de la persona.
     *
     * @return Los apellidos de la persona.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos de la persona.
     *
     * @param apellidos Los apellidos de la persona.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene la edad de la persona.
     *
     * @return La edad de la persona.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad de la persona.
     *
     * @param edad La edad de la persona.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }
}
