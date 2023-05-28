package com.example.guifinal.Customer;



import java.util.ArrayList;

public class CustomerList {
    private ArrayList<Customer> listOfCustomer = new ArrayList<Customer>();

    public CustomerList(){};

    //Methods
    public ArrayList<Customer> getCustomerList() {return listOfCustomer;}

    public void addCustomerToList(Customer customer){
        listOfCustomer.add(customer);
    }

    public void removeCustomerFromList(Customer customer){
        listOfCustomer.remove(customer);
    }



    public Customer getCustomerFromList(int i) {
        return listOfCustomer.get(i);
    }

    public int getCustomerListSize() {
        return listOfCustomer.size();
    }
}
