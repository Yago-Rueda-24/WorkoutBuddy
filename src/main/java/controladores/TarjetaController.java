/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import servicios.RutinaServicios;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class TarjetaController implements Initializable {

    private RutinaServicios service;

    @FXML
    private Button BTNInfo;
    @FXML
    private Button BTNBorrar;
    @FXML
    private Label LNombre;
    private MainpageController maincontroller;

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
    private void borraTarjeta(ActionEvent event) {
        if (service.borrarRutina(LNombre.getText())) {
            this.maincontroller.actualizarRutinas(null);
        }
    }

    public void setNombre(String nombre) {
        LNombre.setText(nombre);
    }

    @FXML
    private void AbrirRutina(ActionEvent event) {
    }

}
