package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Item.Item;
import com.example.guifinal.Item.ItemList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminItemController implements Initializable {
    public static Item editItem;
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


    public void checkRentalType() {
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

    public void checkItemStatus() {
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
        checkItemStatus();
        itemTable.setItems(data);
    }
    itemTable.refresh();
}

    protected void displayItemType(ActionEvent event) {
        itemTable.getItems().clear();

        if(rentalChoiceBox.getValue() != null) {
            checkRentalType();
            itemTable.setItems(data);
            return;
        }

        itemTable.refresh();
    }

    @FXML
    protected void addItem() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/AddItemScene.fxml"));
        Scene scene = new Scene(fxml, 600, 700);
        Stage stage = new Stage();
        stage.setTitle("Add New Item");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        itemTable.refresh();
    }


    @FXML
    protected void onEditButtonClick() throws IOException {
        editItem = itemTable.getSelectionModel().getSelectedItem();
        if(editItem != null) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/EditItemScene.fxml"));
            Scene scene = new Scene(fxml, 400, 450);
            Stage stage = new Stage();
            stage.setTitle("Edit Item");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();

            itemTable.getItems().clear();
            data = FXCollections.observableArrayList(Application.itemList.getItemList());
            itemTable.setItems(data);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");

            alert.setHeaderText("");
            alert.setContentText("Please select an item before editing");

            alert.showAndWait();
        }
    }

    @FXML
    protected void onDeleteButtonClick(){
        Item selected = itemTable.getSelectionModel().getSelectedItem();
        if(selected != null) {
            Application.itemList.removeItemFromList(selected);
            data.remove(selected);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Item");

            alert.setHeaderText(null);
            alert.setContentText("Delete Item successfully!");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");

            alert.setHeaderText("");
            alert.setContentText("Please select an item before deleting");

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
