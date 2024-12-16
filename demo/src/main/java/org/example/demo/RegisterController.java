package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.demo.modelo.bd.UsuarioDAO;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UsuarioDAO usuarioDAO;

    public RegisterController() {
        usuarioDAO = new UsuarioDAO();
    }

    @FXML
    public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (usuarioDAO.registrarUsuario(username, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Registro Exitoso");
            alert.setContentText("El usuario ha sido registrado correctamente.");
            alert.showAndWait();

            backToLogin();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Registro Fallido");
            alert.setContentText("El usuario ya existe o hubo un error.");
            alert.showAndWait();
        }
    }

    @FXML
    public void backToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
