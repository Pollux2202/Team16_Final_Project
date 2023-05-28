package com.example.guifinal.Item;

public class Game extends Item{

    public Game() {
    }

    public Game(String ID, String title, String rentalType, String genre, String loanType, int copy, float fee) {
        super(ID, title, rentalType, genre, loanType, copy, fee);
    }
}
