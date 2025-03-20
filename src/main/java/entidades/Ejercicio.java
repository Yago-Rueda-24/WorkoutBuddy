package entidades;

import java.util.ArrayList;

public class Ejercicio {

	private int id;
	private int idRutina;
	
	private String nombre;
	private int series;
	private int repeticiones;
	private ArrayList<GrupoMuscular> grupoMuscular;

	public Ejercicio(String nombre) {
		this.nombre = nombre;
	}

	public Ejercicio(String nombre, int series, int repeticiones) {
		this(nombre);
		this.series = series;
		this.repeticiones = repeticiones;
	}

	public Ejercicio(String nombre, int series, int repeticiones, ArrayList<GrupoMuscular> grupoMuscular) {
		this(nombre, series, repeticiones);
		this.grupoMuscular = grupoMuscular;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		if (series < 1) {
			// TODO Lanzar excepción
		} else {
			this.series = series;
		}

	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		if (repeticiones < 1) {
			// TODO Lanzar Excepción
		} else {
			this.repeticiones = repeticiones;
		}

	}

	public ArrayList<GrupoMuscular> getGrupoMuscular() {
		return grupoMuscular;
	}

	public void setGrupoMuscular(ArrayList<GrupoMuscular> grupoMuscular) {
		if(grupoMuscular.isEmpty()) {
			//TODO Lanzar Excepcion
		}else {
			this.grupoMuscular = grupoMuscular;
		}
		
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdRutina() {
		return idRutina;
	}

	public void setIdRutina(int idRutina) {
		this.idRutina = idRutina;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if((obj instanceof Ejercicio)== false) {
			return false;
		}
		Ejercicio aux = (Ejercicio) obj;
		
		return this.nombre.equals(aux.nombre) && this.repeticiones == aux.repeticiones && this.series == aux.series 
				&& this.grupoMuscular.equals(aux.grupoMuscular);
		
	}
	
	

}
