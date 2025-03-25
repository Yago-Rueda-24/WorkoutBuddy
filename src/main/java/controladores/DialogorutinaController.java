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
import javafx.scene.control.TextField;
import servicios.RutinaServicios;
import utiles.Database;

/**
 * FXML Controller class
 *
 * @author yago
 */
public class DialogorutinaController implements Initializable {

    private RutinaServicios service;
    
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

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void anadirRutina(ActionEvent event) {
        if(service.crearRutina(TFNombre.getText())){
            System.out.println("Salio Bien");
        }
        
    }
    
}
