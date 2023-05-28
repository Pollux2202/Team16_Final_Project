package com.example.guifinal.Controller;

import com.example.guifinal.Controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserHomeController implements Initializable {
    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(LoginController.account.getName());
    }
}
