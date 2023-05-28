package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Customer.*;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminCustomerController implements Initializable {
    public static Customer selectedCustomer;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox<String> customerTypeChoiceBox;

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, Integer> returnAmountColumn;

    ObservableList<Customer> customerData = FXCollections.observableArrayList(Application.customerList.getCustomerList());



    @FXML
    protected void searchForCustomer(MouseEvent e) {
        String search = searchTextField.getText().trim();
        CustomerList searchedList = new CustomerList();
        customerTableView.getItems().clear();
        customerData.removeAll();

        for(Customer customer: Application.customerList.getCustomerList()){
            if(customer.getName().contains(search) || customer.getId().equals(search)){
                searchedList.addCustomerToList(customer);
            }
        }

        customerData =  FXCollections.observableArrayList(searchedList.getCustomerList());
        customerTableView.setItems(customerData);

    }

    public void checkCustomerType() {
        CustomerList searchedList = new CustomerList();

        switch(customerTypeChoiceBox.getValue()){
            case "All":
                customerData.removeAll();
                customerData = FXCollections.observableArrayList(Application.customerList.getCustomerList());
                break;
            case "Guest Account":
                customerData.removeAll();
                for (Customer customer : Application.customerList.getCustomerList()) {
                    if (customer instanceof GuestAccount & !(customer instanceof RegularAccount) & !(customer instanceof VIPAccount)) {
                        searchedList.addCustomerToList(customer);
                    }
                }
                customerData = FXCollections.observableArrayList(searchedList.getCustomerList());
                break;
            case "Regular Account":
                customerData.removeAll();
                for (Customer customer : Application.customerList.getCustomerList()) {
                    if (customer instanceof RegularAccount & !(customer instanceof VIPAccount)) {
                        searchedList.addCustomerToList(customer);
                    }
                }
                customerData = FXCollections.observableArrayList(searchedList.getCustomerList());
                break;
            case "VIP Account":
                customerData.removeAll();
                for (Customer customer : Application.customerList.getCustomerList()) {
                    if (customer instanceof VIPAccount) {
                        searchedList.addCustomerToList(customer);
                    }
                }
                customerData = FXCollections.observableArrayList(searchedList.getCustomerList());
                break;
        }
    }


    protected void displayCustomerOnType(ActionEvent e) {
        ItemList searchedList = new ItemList();
        customerTableView.getItems().clear();

        if(customerTypeChoiceBox.getValue() != null) {
            checkCustomerType();
            customerTableView.setItems(customerData);
        }
    }

    @FXML
    protected void viewRentalList() throws IOException {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/RentalListView.fxml"));
            Scene scene = new Scene(fxml, 800, 346);
            Stage stage = new Stage();
            stage.setTitle("Rental List");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");

            alert.setHeaderText("");
            alert.setContentText("Please select a customer first");

            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTypeChoiceBox.getItems().addAll("All","Guest Account", "Regular Account", "VIP Account");
        customerTypeChoiceBox.setOnAction(this::displayCustomerOnType);


        searchTextField.textProperty().addListener((observable,oldValue,newValue) ->{
            customerTableView.setItems(getCustomerList(newValue));

        });

        idColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        returnAmountColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("returnAmount"));

        customerTableView.setItems(customerData);


        }

    ObservableList<Customer> getCustomerList(String keyword){
        ObservableList<Customer> customerList = FXCollections.observableList(new ArrayList<>());

        keyword = keyword.toLowerCase();
        for (Customer customer : customerData){
            if (keyword.isBlank() ||
                    customer.getId().toLowerCase().contains(keyword) ||
                    customer.getName().toLowerCase().contains(keyword)) {
                customerList.add(customer);
            }
        }

        return customerList;
    }
}
