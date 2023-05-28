package com.example.guifinal.Item;


import java.util.LinkedList;

public class ItemList
{
    private LinkedList <Item> listOfItems = new LinkedList<Item>();

    public ItemList(){}

    //Methods
    public LinkedList<Item> getItemList() {return listOfItems;}

    public int getItemListSize() {
        return listOfItems.size();
    }

    public Item getItemFromList(int i) {
        return listOfItems.get(i);
    }


    public void addItemToList(Item item){
        listOfItems.add(item);
    }
    public void removeItemFromList(Item item) {
        this.getItemList().remove(item);
    }

}