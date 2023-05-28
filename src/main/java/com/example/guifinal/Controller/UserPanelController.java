package com.example.guifinal.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserPanelController implements Initializable {
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button itemButton;
    @FXML
    private StackPane contentArea;


    @FXML
    protected void onHomeButtonClick() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/UserHome.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    protected void onProfileButtonClick() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/UserProfile.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    protected void onItemButtonClick() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/UserItem.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    protected void onLogOutButtonClick(ActionEvent e) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/Login.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxml, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/UserHome.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException exception) {
        }
    }
}