/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class MainpageController implements Initializable {

    private GridPane grid;
    @FXML
    private MenuItem MNRutina;
    @FXML
    private VBox lista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prueba();
    }

    public void prueba() {
        try {
            int i;
            for (i = 0; i <= 7; i++) {
                // Cargar la tarjeta
                // Obtener el controlador de la tarjeta
                // Añadir la tarjeta al contenedor

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjeta.fxml"));
                AnchorPane tarjeta = loader.load();

                lista.getChildren().add(tarjeta);

            }

            // Cargar el FXML de la tarjeta
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abreDialogo(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogorutina.fxml"));
            AnchorPane dialogo = loader.load();

            DialogorutinaController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Añadir Registro");
            dialogStage.setScene(new Scene(dialogo));

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
