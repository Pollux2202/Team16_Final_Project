package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Item.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemSceneController implements Initializable {
    @FXML
    private Label errorMessage;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private ChoiceBox<String> rentalChoiceBox;
    @FXML
    private ChoiceBox<String> loanTypeChoiceBox;
    @FXML
    private ChoiceBox<String> genreChoiceBox;
    @FXML
    private TextField feeTextField;
    @FXML
    private TextField copyTextField;

    @FXML
    protected void addItem(ActionEvent event) {
        try {
            //get input
            String id = idTextField.getText().trim();
            String title = titleTextField.getText().trim();
            float fee = Float.parseFloat(feeTextField.getText().trim());
            int copies = Integer.parseInt(copyTextField.getText().trim());

            if (id.charAt(0) != 'I') {
                errorMessage.setText("Invalid Item ID");
                return;
            }
            if (!Character.isDigit(id.charAt(1)) && !Character.isDigit(id.charAt(2)) && !Character.isDigit(id.charAt(3))) {
                errorMessage.setText("Invalid Item ID");
                return;
            }
            if (id.charAt(4) != '-') {
                errorMessage.setText("Invalid Item ID");
                return;
            }
            if (!Character.isDigit(id.charAt(5)) && !Character.isDigit(id.charAt(6)) && !Character.isDigit(id.charAt(7)) && !Character.isDigit(id.charAt(8))) {
                errorMessage.setText("Invalid Item ID");
                return;
            }

            for (Item item : Application.itemList.getItemList()) {
                if (item.getId().equals(id)) {
                    errorMessage.setText("This item is already exist.");
                    return;
                }
            }

            //check new item input and create new Items
            if (rentalChoiceBox.equals("Game")) {
                genreChoiceBox.disabledProperty();
                Item item = new Item(id, title, rentalChoiceBox.getValue(), "", loanTypeChoiceBox.getValue(), copies, fee);
                Application.itemList.addItemToList(item);
            } else {
                Item item = new Item(id, title, rentalChoiceBox.getValue(), genreChoiceBox.getValue(), loanTypeChoiceBox.getValue(), copies, fee);
                Application.itemList.addItemToList(item);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add New Item");
            alert.setHeaderText("Add Item Successfully!");

            alert.showAndWait();

            //close the add dialog
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
        catch (NumberFormatException e){
            errorMessage.setText("Invalid format");
        }
        catch (NullPointerException e) {
            errorMessage.setText("Missing information detected!");
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rentalChoiceBox.getItems().addAll("Record", "DVD", "Game");
        loanTypeChoiceBox.getItems().addAll("1-week", "2-day");
        genreChoiceBox.getItems().addAll("Action", "Horror", "Drama", "Comedy");
    }
}
