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
        String sqlcount = "SELECT COUNT(*) FROM rutina WHERE nombre == ?";
        int coincidencias = Database.getInstance().contarFilas(sqlcount, nombre);
        if (coincidencias == 0) {
            Database.getInstance().insertData(sql, nombre);
            return true;
        } else {
            return false;
        }

    }
}
