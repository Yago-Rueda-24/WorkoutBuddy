package com.yagorueda.workoutbuddy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import utiles.Database;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Cargar la vista principal desde el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainpage.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Pantalla Principal con Tarjetas");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   

    public static void main(String[] args) {
        Database.getInstance().createDatabase();
        launch();
    }

}