package com.example.guifinal.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    @FXML
    private StackPane adminStackPane;

    @FXML
    protected void goToHomePage() throws IOException {
        adminStackPane.getChildren().removeAll();
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/AdminHome.fxml"));
        adminStackPane.getChildren().removeAll();
        adminStackPane.getChildren().setAll(fxml);
    }

    @FXML
    protected void goToCustomerList() throws IOException {
        adminStackPane.getChildren().removeAll();
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/AdminCustomer.fxml"));
        adminStackPane.getChildren().removeAll();
        adminStackPane.getChildren().setAll(fxml);
    }

    @FXML
    protected void goToItemList() throws IOException {
        adminStackPane.getChildren().removeAll();
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/AdminItem.fxml"));
        adminStackPane.getChildren().removeAll();
        adminStackPane.getChildren().setAll(fxml);
    }

    @FXML
    protected void logOutButtonClicked(ActionEvent e) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/Login.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxml, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/AdminHome.fxml"));
            adminStackPane.getChildren().removeAll();
            adminStackPane.getChildren().setAll(fxml);
        } catch (IOException exception) {
        }
    }
}
