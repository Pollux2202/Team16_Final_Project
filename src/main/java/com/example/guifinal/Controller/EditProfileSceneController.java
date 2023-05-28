package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Customer.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditProfileSceneController implements Initializable {
    @FXML
    private Label errorMessage;
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;


    @FXML
    protected void onUpdateButtonCLick(ActionEvent e) {
        String name = nameTextField.getText().trim();
        String address = addressTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit profile");
        alert.setHeaderText("Do you really want to initialize update?");

        Optional<ButtonType> option = alert.showAndWait();
         // Execute update operation after OK button clicked
        if(option.get() == ButtonType.OK){
            LoginController.account.setName(name);
            LoginController.account.setAddress(address);
            LoginController.account.setPhone(phone);
            LoginController.account.setUsername(username);
            LoginController.account.setPassword(password);

            for(Customer customer : Application.customerList.getCustomerList()){
                if(customer.getId().equals(LoginController.account.getId())){
                    customer = LoginController.account;
                }
            }

            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setTitle("Edit Profile");

            // Header Text: null
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Your profile has been updated successfully!");

            alertMessage.showAndWait();
        }


        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameTextField.setText(LoginController.account.getName());
        addressTextField.setText(LoginController.account.getAddress());
        phoneTextField.setText(LoginController.account.getPhone());
        usernameTextField.setText(LoginController.account.getUsername());
        passwordTextField.setText(LoginController.account.getPassword());
    }
}
