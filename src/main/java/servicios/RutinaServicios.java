package servicios;

import entidades.Ejercicio;
import java.util.ArrayList;
import utiles.Database;

public class RutinaServicios {

    public ArrayList<Ejercicio> recuperaEjercicios() {
        String sql = "SELECT * from rutina";
        return null;
    }

    public boolean crearRutina(String nombre) {
        String sql = "INSERT into rutina (nombre) values (?)";
        Database.getInstance().insertData(sql,nombre);
        return true;
    }
}
