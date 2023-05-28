package com.example.guifinal.FileReader;

import com.example.guifinal.Item.Item;
import com.example.guifinal.Item.ItemList;

import java.io.*;
import java.util.ArrayList;
import java.lang.*;

public class ItemFileReader {

    protected static boolean checkItemID(String id) {
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

    private static boolean checkRentalType(String rentalType) {
        switch (rentalType) {
            case "Record", "DVD", "Game": return true;
        }
        return false;
    }

    private static boolean checkLoanType(String loanType) {
        switch (loanType) {
            case "2-day", "1-week": return true;
        }
        return false;
    }

    private static boolean checkCopiesInStock(String numOfCopies) {
        try {
            int numOfCopiesInt = Integer.parseInt(numOfCopies);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static boolean checkRentalFee(String rentalFee) {
        try {
            double rentalFeeDble = Double.parseDouble(rentalFee);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static boolean checkItemGenre(String genre) {
        switch (genre) {
            case "Action", "Horror", "Drama", "Comedy": return true;
        }
        return false;
    }

    private static Item createItem(String str) {

        //Process the string and
        //return the index of new line character  whether the system running on Window or macOS
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

        //The item record has the following format
        //ID,Title,Rent type,Loan type,Number of copies,rental fee,[genre] if the item is a record or a DVD

        //Check condition of each attribute(subString), return null if the condition is wrong
        if (!checkItemID(subStr[0])) {
            return null;
        }
        if (!checkRentalType(subStr[2])) {
            return null;
        }
        if (!checkLoanType(subStr[3])) {
            return null;
        }
        if (!checkCopiesInStock(subStr[4])) {
            return null;
        }
        if (!checkRentalFee(subStr[5])) {
            return null;
        }
        if (!subStr[2].equals("Game")) {
            if (!checkItemGenre(subStr[6])) {
                return null;
            }
        } else {
            subStr[6] = "";
        }

        //Return new object if all attributes pass the condition
        return new Item(subStr[0], subStr[1], subStr[2], subStr[6] , subStr[3], Integer.parseInt(subStr[4]), Float.parseFloat(subStr[5]));
    }

    public static ItemList readItemFromFile() {
        ArrayList<String> unpItemList = new ArrayList<String>();
        ItemList pItemList = new ItemList();

        try {
            //Read file of each line
            BufferedReader bufferedReader = new BufferedReader(new  FileReader("." + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "Data" + File.separator + "items.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while(line != null ) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                unpItemList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                line = bufferedReader.readLine();

                //create item based on the line
                Item item = createItem(unpItemList.get(unpItemList.size()-1));
                if (item != null) {
                    pItemList.addItemToList(item);
                }
            }
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Return the list of Items
        return pItemList;
    }
}
