package com.example.guifinal.Controller;

import com.example.guifinal.Application;
import com.example.guifinal.Controller.LoginController;
import com.example.guifinal.Customer.GuestAccount;
import com.example.guifinal.Customer.RegularAccount;
import com.example.guifinal.Customer.VIPAccount;
import com.example.guifinal.Item.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import static com.example.guifinal.UserItemController.points;

public class UserProfileController implements Initializable {
    public static int returnTime = 0;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label address;
    @FXML
    private Label phone;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label accountType;
    @FXML
    private Label successfulReturn;
    @FXML
    private TableView<Item> rentalList;
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
    private Label currentPoints;
    @FXML
    private HBox pointLabel;

    ObservableList<Item> data = FXCollections.observableArrayList(LoginController.account.getRentalList());
    @FXML
    protected void onEditProfileButtonClick() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/guifinal/EditProfileScene.fxml"));
        Scene scene = new Scene(fxml, 400, 360);
        Stage stage = new Stage();
        stage.setTitle("Edit Profile");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();

        name.setText(LoginController.account.getName());
        address.setText(LoginController.account.getAddress());
        phone.setText(LoginController.account.getPhone());
        username.setText(LoginController.account.getUsername());
        password.setText(LoginController.account.getPassword());
    }

    @FXML
    protected void onReturnButtonClick() {
        Item selected = rentalList.getSelectionModel().getSelectedItem();
        if(selected != null) {
            LoginController.account.getRentalList().remove(selected);
            data.remove(selected);

            for (Item item : Application.itemList.getItemList()) {
                if (item.getId().equals(selected.getId())) {
                    item.setCopy(item.getCopy() + 1);
                    if (LoginController.account instanceof VIPAccount) {
                        LoginController.account.setRewardPoints(10);
                    }
                }
            }

            LoginController.account.setReturnAmount(LoginController.account.getReturnAmount() + 1);

            returnTime++;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Return Item");
            alert.setHeaderText(null);
            alert.setContentText("Return Item successfully!");

            alert.showAndWait();

            successfulReturn.setText(String.valueOf(LoginController.account.getReturnAmount()));
            currentPoints.setText(String.valueOf(LoginController.account.getRewardPoints()));
            if(!(LoginController.account instanceof VIPAccount) & LoginController.account instanceof RegularAccount & LoginController.account.getReturnAmount() == 8){

                //promote, delete the unpromoted account from the list and add the promoted account ot the list
                VIPAccount vipAccount = ((RegularAccount) LoginController.account).promote();
                Application.customerList.removeCustomerFromList(LoginController.account);
                Application.customerList.addCustomerToList(vipAccount);


                //change the current account to the promoted account
                LoginController.account = vipAccount;

                //display Vip Account type and item returned
                accountType.setText("VIP Account");
                //display reward points for vip account
                pointLabel.setVisible(true);



            } else if (LoginController.account instanceof GuestAccount & !(LoginController.account instanceof RegularAccount) & LoginController.account.getReturnAmount() == 3){
                //promote, delete the unpromoted account from the list and add the promoted account ot the list
                RegularAccount regularAccount = ((GuestAccount) LoginController.account).promote();
                Application.customerList.removeCustomerFromList(LoginController.account);
                Application.customerList.addCustomerToList(regularAccount);

                //change the current account to the promoted account
                LoginController.account = regularAccount;

                //display the account type and number of return items
                accountType.setText("Regular Account");
                //Regular account cant have rewards point (the same goes for Guest Account)
                pointLabel.setVisible(false);
                successfulReturn.setText(String.valueOf(LoginController.account.getReturnAmount()));
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");

            alert.setHeaderText("");
            alert.setContentText("Please select an item before returning");

            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setText(LoginController.account.getId());
        name.setText(LoginController.account.getName());
        address.setText(LoginController.account.getAddress());
        phone.setText(LoginController.account.getPhone());
        username.setText(LoginController.account.getUsername());
        password.setText(LoginController.account.getPassword());
        successfulReturn.setText(String.valueOf(LoginController.account.getReturnAmount()));
        currentPoints.setText(String.valueOf(LoginController.account.getRewardPoints()));
        if(LoginController.account instanceof VIPAccount){
            accountType.setText("VIP Account");
            pointLabel.setVisible(true);
        } else if(LoginController.account instanceof RegularAccount) {
            accountType.setText("Regular Account");
            pointLabel.setVisible(false);
        } else{
            accountType.setText("Guest Account");
            pointLabel.setVisible(false);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
        rentalTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("rentalType"));
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("loanType"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("genre"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<Item, Float>("fee"));

        rentalList.setItems(data);
    }
}
