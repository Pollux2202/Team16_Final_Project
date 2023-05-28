package com.example.guifinal.Item;

public class Record extends Item{
   private String itemGenre;


    public Record(String itemGenre) {
        this.itemGenre = itemGenre;
    }

    public Record(String ID, String title, String rentalType, String genre, String loanType, int copy, float fee, String itemGenre) {
        super(ID, title, rentalType, genre, loanType, copy, fee);
        this.itemGenre = itemGenre;
    }

    public String getItemGenre() {
        return itemGenre;
    }

    public void setItemGenre(String itemGenre) {
        this.itemGenre = itemGenre;
    }
}
