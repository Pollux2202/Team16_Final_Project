package com.example.guifinal.FileReader;

import com.example.guifinal.Customer.*;
import com.example.guifinal.Item.Item;
import com.example.guifinal.Application;
import com.example.guifinal.Customer.Customer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerFileReader {

    private static boolean checkCustomerID(String id) {
        if (id.charAt(0) != 'C') {
            return false;
        }
        if (!Character.isDigit(id.charAt(1)) && !Character.isDigit(id.charAt(2)) && !Character.isDigit(id.charAt(3))) {
            return false;
        }
        return true;
    }

    public static boolean checkPhoneNumber(String phoneNum) {
        for (int i = 0; i < phoneNum.length(); i++) {
            if (!Character.isDigit(phoneNum.charAt(i))) {
                return false;
            }
        }
        return true;
    }
//Check if number of rentals is an int
    private static boolean checkNumberOfRental(String numOfRentals) {
        try {
            int numOfRentalsInt = Integer.parseInt(numOfRentals);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }
    private static boolean checkRewardPoints(String rewardPoints){
        try {
            int vipRewardPoints = Integer.parseInt((rewardPoints));
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static boolean checkCustomerType(String customerType) {
        switch (customerType) {
            case "Guest", "Regular", "VIP": return true;
        }
        return false;
    }

    private static Customer createCustomer(String str) {

        //Process the string
        //Return the index of new line character depend on whether the system is Window or macOS
        int position;
        if (str.indexOf("\r\n") != -1) {
            position = str.indexOf("\r\n");
        } else {
            position = str.indexOf("\n");
        }
        //Split the string into components
        StringBuffer resString = new StringBuffer(str);
        resString.insert(position, ",");
        str = resString.toString();
        String[] subStr = str.split(",");

        subStr[6] =subStr[6].replace(" ","");
        subStr[7] =subStr[7].replace(" ","");
        subStr[8] = subStr[8].replace(" ","");

        //a customer record has the following format
        //ID,Name,Address,Phone,Number of rentals,customer type, username, password followed by a list of items' IDs

        //Check condition of each attribute, return null Customer if the condition is wrong
        if (!checkCustomerID(subStr[0])) {
            return null;
        }
        if (!checkPhoneNumber(subStr[3])) {
            return null;
        }
        if (!checkNumberOfRental(subStr[4])) {
            return null;
        }
        if (!checkCustomerType(subStr[5])) {
            return null;
        }
        if (!checkRewardPoints(subStr[8])){
            return null;
        }

        //Create customer with suitable Account Type
        switch (subStr[5]) {
            case "Guest": return new GuestAccount(subStr[0], subStr[1], subStr[2], subStr[3], subStr[6], subStr[7], Integer.parseInt(subStr[4]), Integer.parseInt(subStr[8]));
            case "Regular": return new RegularAccount(new GuestAccount(subStr[0], subStr[1], subStr[2], subStr[3], subStr[6], subStr[7], Integer.parseInt(subStr[4]), Integer.parseInt(subStr[8])));
            case "VIP": return new VIPAccount(new RegularAccount(new GuestAccount(subStr[0], subStr[1], subStr[2], subStr[3], subStr[6], subStr[7], Integer.parseInt(subStr[4]), Integer.parseInt(subStr[8]))));
        }
        return null;
    }

    private static boolean checkIDFormat(String id) {
        if (id.charAt(0) != 'I') {
            return false;
        }
        if (!Character.isDigit(id.charAt(1)) && !Character.isDigit(id.charAt(2)) && !Character.isDigit(id.charAt(3))) {
            return false;
        }
        if (id.charAt(4) != '-') {
            return false;
        }
        if (!Character.isDigit(id.charAt(5)) && !Character.isDigit(id.charAt(6)) && !Character.isDigit(id.charAt(7)) && !Character.isDigit(id.charAt(8))) {
            return false;
        }
        return true;
    }

    private static Item createItem(String str) {

        //Process the string
        //Return the index of new line character depend on whether the system is Window or macOS
        int position;
        if (str.contains("\r\n")) {
            position = str.indexOf("\r\n");
        } else {
            position = str.indexOf("\n");
        }
        //Split the string into components
        StringBuffer resString = new StringBuffer(str);
        resString.insert(position, ",");
        str = resString.toString();
        String[] subStr = str.split(",");

        //Check the format of the item's ID
        if (!checkIDFormat(subStr[0])) {
            return null;
        }

        //Get the Item from the itemList to add to rentalsList
        for (int i = 0; i < Application.itemList.getItemListSize(); i++) {
            if (subStr[0].equals(Application.itemList.getItemFromList(i).getId())) {
                return Application.itemList.getItemFromList(i);
            }
        }
        return null;
    }

    //input
    public static CustomerList readCustomerFromFile() {
        ArrayList<String> unpCustomerList = new ArrayList<String>();
        CustomerList customerList = new CustomerList();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("." + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "Data" + File.separator + "customers.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while(line != null ) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                unpCustomerList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                line = bufferedReader.readLine();

                //If the input is Customers' Info
                String info = unpCustomerList.get(unpCustomerList.size() - 1);
//                System.out.println(info + "x");
//                System.out.println(info.length());
                if (info.length() > 15) {
                    Customer customer = createCustomer(info);
                    if (customer != null) {
                        customerList.addCustomerToList(customer);
                    }
                } else { //If the input is the itemList of that customer
                    Item item = createItem(info);
                    if (item != null) {
                        customerList.getCustomerFromList(customerList.getCustomerListSize() - 1).getRentalList().add(item);
                    }
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        return customerList;
    }
}
