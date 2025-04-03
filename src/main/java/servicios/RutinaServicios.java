package servicios;

import Excepciones.InsertException;
import entidades.Rutina;
import java.util.ArrayList;
import java.util.List;
import utiles.Database;

public class RutinaServicios {

    public ArrayList<Rutina> recuperaRutinas(String nombreRutina) throws Exception {

        if (nombreRutina == null || nombreRutina.isEmpty()) {
            String sql = "SELECT * from rutina";
            List<Rutina> auxlist = Database.getInstance().executeQueryPojo(Rutina.class, sql);
            return new ArrayList<>(auxlist);
        } else {
            String sql = "SELECT * from rutina where rutina.nombre LIKE ?";
            List<Rutina> auxlist = Database.getInstance().executeQueryPojo(Rutina.class, sql, nombreRutina + "%");
            return new ArrayList<>(auxlist);
        }

    }

    public boolean borrarRutina(String nombre) {
        String sql = "DELETE FROM rutina WHERE nombre == ?";
        String sqlcount = "SELECT COUNT(*) FROM rutina WHERE nombre == ?";
        int coincidencias = Database.getInstance().contarFilas(sqlcount, nombre);
        if (coincidencias != 0) {
            Database.getInstance().insertData(sql, nombre);
            return true;
        } else {
            return false;
        }

    }

    public boolean crearRutina(String nombre) throws InsertException {
        String sql = "INSERT into rutina (nombre) values (?)";
        String sqlcount = "SELECT COUNT(*) FROM rutina WHERE nombre == ?";

        if (nombre.isEmpty() || nombre == null) {
            throw new InsertException("Debes introducir un nombre para la rutina");
        }
        if (nombre.length() > 25) {
            throw new InsertException("No puedes introducir más de 20 caracteres para el nombre");
        }
        int coincidencias = Database.getInstance().contarFilas(sqlcount, nombre);
        if (coincidencias == 0) {
            Database.getInstance().insertData(sql, nombre);
            return true;
        } else {
            throw new InsertException("Ya existe una rutina con ese nombre");
        }

    }
}
