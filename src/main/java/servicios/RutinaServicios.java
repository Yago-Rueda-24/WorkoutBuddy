package servicios;

import entidades.Rutina;
import java.util.ArrayList;
import java.util.List;
import utiles.Database;

public class RutinaServicios {
    
    public ArrayList<Rutina> recuperaRutinas() throws Exception {
        String sql = "SELECT * from rutina";
        List<Rutina> auxlist = Database.getInstance().executeQueryPojo(Rutina.class, sql);
        return new ArrayList<>(auxlist);
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
