package entidades;

import java.util.Set;

public class Rutina {
	
	private int id;
	private String nombre;
	private Set<Ejercicio> ejercicios;
	
	
	public Rutina(String nombre, Set<Ejercicio> ejercicios) {
		super();
		this.nombre = nombre;
		this.ejercicios = ejercicios;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Set<Ejercicio> getEjercicios() {
		return ejercicios;
	}


	public void setEjercicios(Set<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
