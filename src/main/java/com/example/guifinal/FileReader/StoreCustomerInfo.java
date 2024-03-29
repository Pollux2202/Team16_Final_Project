package com.example.guifinal.FileReader;

import com.example.guifinal.Customer.CustomerList;

import java.io.*;

import com.example.guifinal.Customer.RegularAccount;
import com.example.guifinal.Customer.VIPAccount;

public class StoreCustomerInfo {

    private static CustomerList customerList = new CustomerList();


    public StoreCustomerInfo(CustomerList customerList) {
        this.customerList = customerList;
    }

    public static boolean storeCustomerInfo() {
        File file = new File("." + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "Data" + File.separator + "customers.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            for (int i = 0; i < customerList.getCustomerListSize(); i++) {
                if (customerList.getCustomerFromList(i) instanceof VIPAccount) {
                    fr.write(customerList.getCustomerFromList(i).writeCustomer("VIP"));
                } else if (customerList.getCustomerFromList(i) instanceof RegularAccount) {
                    fr.write(customerList.getCustomerFromList(i).writeCustomer("Regular"));
                } else {
                    fr.write(customerList.getCustomerFromList(i).writeCustomer("Guest"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
