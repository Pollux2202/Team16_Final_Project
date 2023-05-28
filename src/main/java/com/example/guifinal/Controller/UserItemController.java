package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Customer.GuestAccount;
import com.example.guifinal.Customer.RegularAccount;
import com.example.guifinal.Customer.VIPAccount;
import com.example.guifinal.Item.Item;
import com.example.guifinal.Item.ItemList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class UserItemController implements Initializable {
    public static int rentTime = 0;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox<String> rentalChoiceBox;

    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> idColumn;
    @FXML
    private TableColumn<Item, String> titleColumn;
    @FXML
    private TableColumn<Item, String> rentalTypeColumn;
    @FXML
    private TableColumn<Item, String> loanTypeColumn;
    @FXML
    private TableColumn<Item, String> genreColumn;
    @FXML
    private TableColumn<Item, Float> feeColumn;
    @FXML
    private TableColumn<Item, Integer> copiesColumn;

    ObservableList<Item> data = FXCollections.observableArrayList(Application.itemList.getItemList());

    @FXML
    protected void onSearchButtonClick(MouseEvent event) {
        String searchedItem = searchTextField.getText().trim();
        ItemList searchedList = new ItemList();
        itemTable.getItems().clear();
        data.removeAll();

        for(Item item: Application.itemList.getItemList()){
            if(item.getTitle().contains(searchedItem) || item.getId().contains(searchedItem)){
                searchedList.addItemToList(item);
            }
        }

        data =  FXCollections.observableArrayList(searchedList.getItemList());
        itemTable.setItems(data);
    }

    public void checkRentalTypeComboBox() {
        ItemList searchedList = new ItemList();

        switch (rentalChoiceBox.getValue()) {
            case "All":
                data.removeAll();
                data = FXCollections.observableArrayList(Application.itemList.getItemList());
                break;
            case "Record":
                data.removeAll();
                for (Item item : Application.itemList.getItemList()) {
                    if (item.getRentalType().equals("Record")) {
                        searchedList.addItemToList(item);
                    }
                }
                data = FXCollections.observableArrayList(searchedList.getItemList());
                break;
            case "DVD":
                data.removeAll();
                for (Item item : Application.itemList.getItemList()) {
                    if (item.getRentalType().equals("DVD")) {
                        searchedList.addItemToList(item);
                    }
                }
                data = FXCollections.observableArrayList(searchedList.getItemList());
                break;
            case "Game":
                data.removeAll();
                for (Item item : Application.itemList.getItemList()) {
                    if (item.getRentalType().equals("Game")) {
                        searchedList.addItemToList(item);
                    }
                }
                data = FXCollections.observableArrayList(searchedList.getItemList());
                break;
        }
    }

    public void checkStatusComboBox() {
        ItemList searchedList = new ItemList();

        switch (statusChoiceBox.getValue()) {
            case "All":
                data.removeAll();
                data = FXCollections.observableArrayList(Application.itemList.getItemList());
                break;
            case "Available":
                data.removeAll();
                for (Item item : Application.itemList.getItemList()) {
                    if (item.getCopy() > 0) {
                        searchedList.addItemToList(item);
                    }
                }
                data = FXCollections.observableArrayList(searchedList.getItemList());
                break;
            case "Out of Stock":
                data.removeAll();
                for (Item item : Application.itemList.getItemList()) {
                    if (item.getCopy() == 0) {
                        searchedList.addItemToList(item);
                    }
                }
                data = FXCollections.observableArrayList(searchedList.getItemList());
                break;
        }
    }

protected void viewItemStatus(ActionEvent event){
    itemTable.getItems().clear();
    if(statusChoiceBox.getValue() != null){
        checkStatusComboBox();
        itemTable.setItems(data);

    }
    itemTable.refresh();
}

    protected void displayItemType(ActionEvent event) {

        itemTable.getItems().clear();

         if(rentalChoiceBox.getValue() != null) {
             checkRentalTypeComboBox();
             itemTable.setItems(data);

         }
        itemTable.refresh();
    }

    @FXML
    protected void onRentItemClick() {
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if(selected != null) {
            //check if the customer is Guest Account(Guest account can only rent 2 items at a time)
            if(LoginController.account instanceof GuestAccount & LoginController.account.getRentalList().size() == 2 & !(LoginController.account instanceof RegularAccount) & !(LoginController.account instanceof VIPAccount)){
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error");

                alertError.setHeaderText(null);
                alertError.setContentText("Guest Account can only rent 2 items at a time");

                alertError.showAndWait();
                return;
            }

            if(LoginController.account instanceof GuestAccount & selected.getLoanType().equals("2-day") & !(LoginController.account instanceof RegularAccount) & !(LoginController.account instanceof VIPAccount)){
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error");
                alertError.setHeaderText(null);
                alertError.setContentText("Guest Account can not rent a 2-day item");
                alertError.showAndWait();
                return;
            }

            for (Item item : Application.itemList.getItemList()) {
                if (item.getId().equals(selected.getId())) {
                    if (item.getCopy() == 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Rent Item");
                        // Header Text: null
                        alert.setHeaderText(null);
                        alert.setContentText("This item is out of stock!");

                        alert.showAndWait();
                    } else {
                        item.setCopy(item.getCopy() - 1);
//                        LoginController.account.setRewardPoints(10);
//                        points = points + 10;
                        LoginController.account.getRentalList().add(item);

                        rentTime++;

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                        //if the user is VIP account and have equal or more than 100 points
                        if(

                                LoginController.account instanceof VIPAccount && LoginController.account.getRewardPoints() >= 100
                         ){
                            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                            alert1.setTitle("Free Rent Initialize!");

                            // Header Text: null
                            alert1.setHeaderText(null);
                            alert1.setContentText("You have 100 reward points or more. Do you want to change 100 reward points for a free rent?");

                            Optional<ButtonType> option = alert1.showAndWait();

                            if(option.get() == ButtonType.OK){
//                                LoginController.account.setSuccessfullyReturn(((VIPAccount) LoginController.account).getSuccessfullyReturn() - 10);
                                LoginController.account.setRewardPoints(-100);
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle("Rent Item");

                                // Header Text: null
                                alert2.setHeaderText(null);
                                alert2.setContentText("Successfully rent item for free!");

                                alert2.showAndWait();

                                itemTable.getItems().clear();
                                data = FXCollections.observableArrayList(Application.itemList.getItemList());
                                itemTable.setItems(data);
                                return;
                            } else if (option.get() == ButtonType.CANCEL) {
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Rent Item");

                                // Header Text: null
                                alert2.setHeaderText(null);
                                alert2.setContentText("Rent successfully!");

                                alert2.showAndWait();

                                itemTable.getItems().clear();
                                data = FXCollections.observableArrayList(Application.itemList.getItemList());
                                itemTable.setItems(data);
                                return;
                            } else {
                                return;
                            }
                        }

                        alert.setTitle("Rent Item");

                        // Header Text: null
                        alert.setHeaderText(null);
                        alert.setContentText("Rent successfully!");

                        alert.showAndWait();

                        itemTable.getItems().clear();
                        data = FXCollections.observableArrayList(Application.itemList.getItemList());
                        itemTable.setItems(data);
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");

            alert.setHeaderText("");
            alert.setContentText("Please selected an item before you rent!");

            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rentalChoiceBox.getItems().addAll("All","Record", "DVD","Game");
        statusChoiceBox.getItems().addAll("All","Available", "Out of Stock");

        rentalChoiceBox.setOnAction(this::displayItemType);
        statusChoiceBox.setOnAction(this::viewItemStatus);
        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            itemTable.setItems(getItemList(newValue));
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
        rentalTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("rentalType"));
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("loanType"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("genre"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<Item, Float>("fee"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("copy"));

        itemTable.setItems(data);
    }
    ObservableList<Item> getItemList(String keyword){
        ObservableList<Item> itemList = FXCollections.observableList(new ArrayList<>());

        keyword = keyword.toLowerCase();
        for (Item item : data){
            if (keyword.isBlank() ||
                    item.getId().toLowerCase().contains(keyword) ||
                    item.getTitle().toLowerCase().contains(keyword)) {
                itemList.add(item);
            }
        }

        return itemList;
    }
}
