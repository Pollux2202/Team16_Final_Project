package com.example.guifinal.FileReader;

import com.example.guifinal.Customer.*;
import com.example.guifinal.Item.*;
import com.example.guifinal.FileReader.*;
import com.example.guifinal.Item.Item;

public class Functions extends Item {
    //Functions for reading files from text files
    public static ItemList readItemFromList() {
        return ItemFileReader.readItemFromFile();
    }

    public static CustomerList readCustomerListFile() {
        return CustomerFileReader.readCustomerFromFile();
    }

    public static boolean saveAllFiles(ItemList itemList, CustomerList customerList) {
        StoreItemInfo storeItemInfo = new StoreItemInfo(itemList);
        //Check if the items have been stored on the terminal
        if (StoreItemInfo.storeItemInfo()) {
            //Pop up message on terminal when update success
            System.out.println("Successfully update items");
        } else {
            //Pop up message on terminal when update fails
            System.out.println("Cannot update items");
            return false;
        }

        StoreCustomerInfo storeCustomerInfo = new StoreCustomerInfo(customerList);
        //Check if the items have been stored on the terminal
        if (StoreCustomerInfo.storeCustomerInfo()) {
            //Pop up message on terminal when update success
            System.out.println("Successfully update customers");
        } else {
            //Pop up message on terminal when update fails
            System.out.println("Cannot update customers");
            return false;
        }

        return true;
    }
}
