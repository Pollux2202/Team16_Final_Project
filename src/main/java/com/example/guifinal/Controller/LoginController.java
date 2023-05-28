package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Customer.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public static Customer account;

    @FXML
    private Label errorMessage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    public boolean checkAccount() {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        for(Customer customer: Application.customerList.getCustomerList()){
            if(customer.getUsername().equals(username) & customer.getPassword().equals(password)){
                account = customer;
                return true;
            }
        }
        return false;
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent e) throws IOException {
        if(checkAccount()) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/UserPanel.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxml, 1320, 800);
            stage.setScene(scene);
            stage.show();
        } else if (usernameTextField.getText().equals("admin") & passwordTextField.getText().equals("123")){
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/AdminPanel.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxml, 1320, 800);
            stage.setScene(scene);
            stage.show();
        } else {
            errorMessage.setText("Invalid username or password! Please enter again");
        }
    }

    @FXML
    protected void onCreateAnAccountClick() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/Register.fxml"));
        Scene scene = new Scene(fxml, 600, 450);
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
