/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import entidades.Rutina;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import servicios.RutinaServicios;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class MainpageController implements Initializable {
    
    private RutinaServicios service;
    
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
        this.service = new RutinaServicios();
        prueba();
    }
    
    public void prueba() {
        try {
            int i;
            for (i = 0; i <= 1; i++) {
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
    
    public void actualizarRutinas() {
        
        try {
            ArrayList<Rutina> rutinas = service.recuperaRutinas();
            lista.getChildren().clear();
            for (Rutina rut : rutinas) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tarjeta.fxml"));
                AnchorPane tarjeta = loader.load();
                TarjetaController controller = loader.getController();
                controller.setNombre(rut.getNombre());
                controller.setMainController(this);
                lista.getChildren().add(tarjeta);
            }
        } catch (Exception ex) {
            Logger.getLogger(MainpageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void abreDialogo(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogorutina.fxml"));
            AnchorPane dialogo = loader.load();
            
            DialogorutinaController controller = loader.getController();
            controller.setMainController(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Añadir Registro");
            dialogStage.setScene(new Scene(dialogo));
            
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
