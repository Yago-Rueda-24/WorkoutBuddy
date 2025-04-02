package utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class Database {

    private static Database db;
    private static final String APP_PROPERTIES = "src/main/resources/com/yagorueda/workoutbuddy/application.properties";
    private static final String SCHEMA = "src/main/resources/com/yagorueda/workoutbuddy/schema.sql";
    private String driver;
    private String url;
    private final String dbname = "DemoDB.db";

    private Database() {
        Properties prop = new Properties();
        try (FileInputStream fs = new FileInputStream(APP_PROPERTIES)) {
            prop.load(fs);
        } catch (IOException e) {
            throw new RuntimeException("error al cargar el archivo");
        }
        driver = prop.getProperty("datasource.driver");
        url = prop.getProperty("datasource.url");
        if (driver == null || url == null) {
            throw new RuntimeException("Error en la configuración de la url o driver");
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se pudo cargar el driver JDBC", e);
        }

    }

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void createDatabase() {
        if (!databaseExists()) {
            executeScript(SCHEMA);
        }
    }

    public boolean databaseExists() {
        File dbFile = new File(dbname);
        return dbFile.exists();
    }

    public void executeScript(String fileName) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // separa las sentencias sql en dos listas, una para drop y otra para el resto
        // pues se ejecutaran de forma diferente
        List<String> batchUpdate = new ArrayList<>();
        List<String> batchDrop = new ArrayList<>();
        StringBuilder previousLines = new StringBuilder(); // guarda lineas anteriores al separador (;)
        for (String line : lines) {
            line = line.trim();
            if (line.length() == 0 || line.startsWith("--")) // ignora lineas vacias comentarios de linea
            {
                continue;
            }
            if (line.endsWith(";")) {
                String sql = previousLines.toString() + line;
                // separa drop del resto
                if (line.toLowerCase().startsWith("drop")) {
                    batchDrop.add(sql);
                } else {
                    batchUpdate.add(sql);
                }
                // nueva linea
                previousLines = new StringBuilder();
            } else {
                previousLines.append(line + " ");
            }
        }
        // Ejecuta todas las sentencias, primero los drop (si existen)
        if (!batchDrop.isEmpty()) {
            this.executeBatch(batchDrop);
        }
        if (!batchUpdate.isEmpty()) {
            this.executeBatch(batchUpdate);
        }
    }

    public void executeBatch(List<String> sqls) {
        try (Connection cn = DriverManager.getConnection(this.url); Statement stmt = cn.createStatement()) {
            for (String sql : sqls) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            // Ojo, no dejar pasar las excepciones (no limitarse a dejar el codigo
            // autoegenerado por Eclipse con printStackTrace)
            throw new RuntimeException(e);
        }
    }

    public void insertData(String sql, Object... params) {

        try (Connection conn = DriverManager.getConnection(this.url); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]); // Índice en JDBC comienza en 1
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public int contarFilas(String sql, Object... params) {
        int count = 0;

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement stmt = conn.prepareStatement(sql);) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]); // Índice en JDBC comienza en 1
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public <T> List<T> executeQueryPojo(Class<T> pojoClass, String sql, Object... params) throws Exception {
        Connection conn = null;
        try {
            conn = this.connect();
            BeanListHandler<T> beanListHandler = new BeanListHandler<>(pojoClass);
            QueryRunner runner = new QueryRunner();
            return runner.query(conn, sql, beanListHandler, params);
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

}
