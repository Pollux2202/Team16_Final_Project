package com.example.guifinal.FileReader;

import com.example.guifinal.Item.ItemList;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StoreItemInfo {

    private static ItemList itemList = new ItemList();

    public StoreItemInfo(ItemList itemList) {
        this.itemList = itemList;
    }

    public static boolean storeItemInfo() {
        File file = new File("." + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "Data" + File.separator + "items.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            for (int i = 0; i < itemList.getItemListSize(); i++) {
                fr.write(itemList.getItemFromList(i).toString());
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
