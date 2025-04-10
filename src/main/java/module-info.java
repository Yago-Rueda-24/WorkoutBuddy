/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module WorkoutBuddy {
    requires java.sql;
    requires org.apache.commons.dbutils;
    requires org.apache.commons.beanutils;
    requires org.apache.commons.logging;
    requires commons.collections;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;


    exports com.yagorueda.workoutbuddy;
    exports controladores;
    exports utiles;
    exports entidades;

    // Permites acceso por reflexión a los controladores para FXMLLoader
    opens controladores to javafx.fxml;
}
