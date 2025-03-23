/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class MainpageController implements Initializable {

    @FXML
    private GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    prueba();
        
        
    }

    public void prueba() {
        try {
            // Cargar el FXML de la tarjeta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjeta.fxml"));
            AnchorPane tarjeta = loader.load();  // Cargar la tarjeta

            // Obtener el controlador de la tarjeta

            // Añadir la tarjeta al contenedor
          grid.add(tarjeta, 0, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
