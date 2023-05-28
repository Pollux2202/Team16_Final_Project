package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Controller.LoginController;
import com.example.guifinal.Customer.Customer;
import com.example.guifinal.Customer.GuestAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
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
    private TextField confirmPassTextField;

    @FXML
    protected void onRegisterButtonClick(ActionEvent e) {
        //get input
        String name = nameTextField.getText().trim();
        String address = addressTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String confirmPassword = confirmPassTextField.getText().trim();

        for (Customer customer : Application.customerList.getCustomerList()) {
            if (customer.getUsername().equals(username) & customer != LoginController.account) {
                errorMessage.setText("This username has been used! Please choose another one.");
                return;
            }
        }

        if(!password.equals(confirmPassword)) {
            errorMessage.setText("Password doesn't match.");
            return;
        }
        String newCusotomerID = "";
//        String id = Application.customerList.getCustomerFromList(Application.customerList.getCustomerListSize() - 1).getId();
//        id = id.replace("C","");
//        int num = Integer.parseInt(id);
//        num++;

        int num = Application.customerList.getCustomerListSize() + 1;
        //Creating new customer ID on Condition
        if(num < 10) {
            newCusotomerID = "C00" + String.valueOf(num);
        } else if (num >= 10 & num < 100){
            newCusotomerID = "C0" + String.valueOf(num);
        } else {
            newCusotomerID = "C" + String.valueOf(num);
        }

        //check input and create new Item
        Customer customer = new GuestAccount(newCusotomerID, name, address, phone, username, password, 0, 0);
        Application.customerList.addCustomerToList(customer);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register");
        alert.setHeaderText("Register successfully!");

        alert.showAndWait();

        //close dialog box
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
