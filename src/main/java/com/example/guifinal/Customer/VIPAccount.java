package com.example.guifinal.Customer;

import com.example.guifinal.Item.Item;

import java.util.ArrayList;

public class VIPAccount extends RegularAccount {
    public VIPAccount(RegularAccount regularAccount) {
        this.setRentalList(regularAccount.getRentalList());
        this.setId(regularAccount.getId());
        this.setName(regularAccount.getName());
        this.setAddress(regularAccount.getAddress());
        this.setPhone(regularAccount.getPhone());
        this.setRentalList(regularAccount.getRentalList());
        this.setUsername(regularAccount.getUsername());
        this.setPassword(regularAccount.getPassword());
        this.setReturnAmount(regularAccount.getReturnAmount());
        this.setRewardPoints(regularAccount.getRewardPoints());
    }

}