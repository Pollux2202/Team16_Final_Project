package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Controller.AdminItemController;
import com.example.guifinal.Item.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditItemSceneController implements Initializable {
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
    protected void onUpdateItemClick(ActionEvent e) {
        String title = titleTextField.getText().trim();
        float fee = Float.parseFloat(feeTextField.getText().trim());
        int copies = Integer.parseInt(copyTextField.getText().trim());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit Item");
        alert.setHeaderText("Do you want to change this item info?");

        Optional<ButtonType> option = alert.showAndWait();


        if(option.get() == ButtonType.OK){
            AdminItemController.editItem.setTitle(title);
            AdminItemController.editItem.setRentalType(rentalChoiceBox.getValue());
            AdminItemController.editItem.setLoanType(loanTypeChoiceBox.getValue());
            AdminItemController.editItem.setGenre(genreChoiceBox.getValue());
            AdminItemController.editItem.setFee(fee);
            AdminItemController.editItem.setCopy(copies);

            for(Item item : Application.itemList.getItemList()){
                if(item.getId().equals(AdminItemController.editItem.getId())){
                    item = AdminItemController.editItem;
                }
            }

            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setTitle("Edit Item");

            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Update successfully!");

            alertMessage.showAndWait();
        }

        //close dialog box
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Add components into choicebox
        rentalChoiceBox.getItems().addAll("Record", "DVD", "Game");
        loanTypeChoiceBox.getItems().addAll("1-week", "2-day");
        genreChoiceBox.getItems().addAll("Action", "Comedy", "Drama", "Horror");

        titleTextField.setText(AdminItemController.editItem.getTitle());
        rentalChoiceBox.setValue(AdminItemController.editItem.getRentalType());
        loanTypeChoiceBox.setValue(AdminItemController.editItem.getLoanType());
        genreChoiceBox.setValue(AdminItemController.editItem.getGenre());
        feeTextField.setText(String.valueOf(AdminItemController.editItem.getFee()));
        copyTextField.setText(String.valueOf(AdminItemController.editItem.getCopy()));
    }
}
