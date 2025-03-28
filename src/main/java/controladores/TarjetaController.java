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

/**
 * FXML Controller class
 *
 * @author yago
 */
public class TarjetaController implements Initializable {

    

    @FXML
    private Label LName;
    @FXML
    private Button BTNInfo;
    @FXML
    private Button BTNBorrar;
    @FXML
    private Label LNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void muestraRutina(ActionEvent event) {
    }

    @FXML
    private void borraTarjeta(ActionEvent event) {
    }

    

    public void setNombre(String nombre) {
        LNombre.setText(nombre);
    }

}
