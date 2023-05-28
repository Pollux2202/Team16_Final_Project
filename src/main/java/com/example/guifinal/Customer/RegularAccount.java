package com.example.guifinal.Customer;

import com.example.guifinal.Item.Item;

import java.util.ArrayList;

public class RegularAccount extends GuestAccount {
    protected RegularAccount(){}

    public RegularAccount(GuestAccount guestAccount) {
        super(guestAccount.getId(),
        guestAccount.getName(),
        guestAccount.getAddress(),
                guestAccount.getPhone(),
                guestAccount.getUsername(),
                guestAccount.getPassword(),
                guestAccount.getReturnAmount(),
                guestAccount.getRewardPoints());
        this.setRentalList(guestAccount.getRentalList());
    }




    //Methods

    public VIPAccount promote() {

        return new VIPAccount(this);
    }
}
