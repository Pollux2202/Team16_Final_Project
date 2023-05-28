package com.example.guifinal.Customer;


import com.example.guifinal.Item.Item;

import java.util.ArrayList;

public class GuestAccount extends Customer {
    public GuestAccount(){}

    public GuestAccount(String customerID, String customerName, String customerAddress, String customerPhone, String username, String password, int returnAmount, int rewardPoints) {
        super(customerID, customerName, customerAddress, customerPhone, new ArrayList<>(), username, password, returnAmount, rewardPoints);
    }

    //promoting method
    public RegularAccount promote() {

        return new RegularAccount(this);
    }

}
