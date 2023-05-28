package com.example.guifinal.Customer;

import com.example.guifinal.Item.Item;

import java.util.ArrayList;

public abstract class Customer {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private ArrayList<Item> listOfRental;
    private String username;
    private String password;
    private int returnAmount;
    private int rewardPoints;

    public Customer(){};

    public Customer(String customerID, String customerName, String customerAddress, String customerPhone, ArrayList<Item> listOfRental, String username, String password, int returnAmount, int rewardPoints) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.listOfRental = listOfRental;
        this.username = username;
        this.password = password;
        this.returnAmount = returnAmount;
        this.rewardPoints = rewardPoints;
    }

    public  String getId() {return customerID;}
public  int getRewardPoints(){return rewardPoints;}
    public void setRewardPoints(int rewardPoints){this.rewardPoints += rewardPoints;}
    protected void setId(String input) {

        //ID Validation for Customer
        while (true) {
            if (input.length() != 4) {break;}
            char[] ID = input.toCharArray();
            if (ID[0] != 'C') {break;}
            for (int index = 1; index < 4; index++) {
                if (!Character.isDigit(ID[index])) {break;}
                this.customerID = input;
                return;
            }
            System.out.println("Invalid input for ID!");
        }
    }

    public  String getName() {return customerName;}

    public void setName(String name) {this.customerName = name;}

    public String getAddress() {return customerAddress;}

    public void setAddress(String address) {this.customerAddress = address;}

    public String getPhone() {return customerPhone;}

    public void setPhone(String phone) {this.customerPhone = phone;}

    public ArrayList<Item> getRentalList() {return listOfRental;}

    public void setRentalList(ArrayList<Item> rentalList) {this.listOfRental = rentalList;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public int getReturnAmount() {return returnAmount;}

    public void setReturnAmount(int returnAmount) {this.returnAmount = returnAmount;}


    public String writeCustomer(String customerType) {
        String str = customerID + "," +
                    customerName + "," +
                    customerAddress + "," +
                    customerPhone + "," +
                    returnAmount + "," +
                    customerType + "," +
                    username + "," +
                    password + "," +
                rewardPoints;
        str += "\r\n";
        for (Item item : listOfRental) {
            str += item.getId() + "\r\n";
        }
        return str;
    }

    public abstract Customer promote();
}
