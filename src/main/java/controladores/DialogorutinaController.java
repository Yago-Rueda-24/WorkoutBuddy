/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import Excepciones.InsertException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import servicios.RutinaServicios;
import utiles.Database;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class DialogorutinaController implements Initializable {

    private RutinaServicios service;
    private MainpageController maincontroller;

    @FXML
    private TextField TFNombre;
    @FXML
    private Button BTNCancelar;
    @FXML
    private Button BTNCrear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new RutinaServicios();

    }

    public void setMainController(MainpageController controller) {
        this.maincontroller = controller;
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) BTNCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void anadirRutina(ActionEvent event) {
        try {
            service.crearRutina(TFNombre.getText());
            maincontroller.actualizarRutinas(null);
            Stage stage = (Stage) BTNCrear.getScene().getWindow();
            stage.close();
        } catch (InsertException ex) {
            System.err.println(ex.getMessage());
        }

    }

}
