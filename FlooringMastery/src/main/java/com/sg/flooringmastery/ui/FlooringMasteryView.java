/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author bkb
 */
public class FlooringMasteryView {

    private ConsoleIO io;

    public FlooringMasteryView(ConsoleIO io) {
        this.io = io;
    }
//**************************************************************************
//*
//*Run()
//*
//**************************************************************************

    public int showMenu() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        io.print("* <<Flooring Program>>\n");
        io.print("*\n");
        io.print("* 1. Display Orders\n");
        io.print("* 2. Add an Order\n");
        io.print("* 3. Edit an Order\n");
        io.print("* 4. Remove an Order\n");
        io.print("* 5. Quit\n");
        io.print("*\n");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");

        int menuChoice = io.readInt("Please enter selection: ", 1, 5);
        return menuChoice;
    }

    public void displayErrorMessage(String message) {

        io.print(message);

    }

//**************************************************************************
//*
//*Display()
//*
//**************************************************************************
    public LocalDate getDate() {
        LocalDate toReturn = io.stringToDate("Order Date to Display: ");
        return toReturn;
    }

//**************************************************************************
//*
//*AddOrder()
//*
//**************************************************************************
    public Order getOrder(List<Product> productList, List<Tax> taxList) {
        Order customerOrder = new Order();
        LocalDate date = io.stringToDateFuture("Date of Order (in format MM/dd/yyyy): ");
        String name = io.readName("Customer Name: ");
        displayStateOptions(taxList);
        String state = io.readState("Select state from list above (abbreviated): ", taxList);
        displayProductOptions(productList);
        String product = io.readProduct("Product Type: ", productList);
        BigDecimal area = io.readBigDecimal("Area (sq ft): ", new BigDecimal("100"));

        customerOrder.setOrderDate(date);
        customerOrder.setCustomerName(name);
        customerOrder.setState(state);
        customerOrder.setProductType(product);
        customerOrder.setArea(area);

        return customerOrder;

    }

    private void displayProductOptions(List<Product> productList) {
        io.print("\nProduct Options: \n");
        for (int i = 0; i < productList.size(); i++) {
            Product toPrint = productList.get(i);
            io.print("\nProduct Type: " + toPrint.getProductType() + " | "
                    + "Cost per sq ft: " + toPrint.getCostPerSqFt() + " | "
                    + "Labor cost per sq ft: " + toPrint.getLaborPerSqFt());
        }
        io.print("\n\n");
    }

    private void displayStateOptions(List<Tax> taxList) {
        io.print("\nState Options:\n");
        for (int i = 0; i < taxList.size(); i++) {
            Tax toPrint = taxList.get(i);
            io.print("\nState " + (i + 1) + ": " + toPrint.getStateFullName() + "("
                    + toPrint.getStateAbr() + ")");
        }
        io.print("\n\n");
    }

    public void displayOrders(List<Order> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            Order toPrint = orderList.get(i);

            io.print("-\nOrder Number: " + toPrint.getOrderNumber() + " | "
                    + "Customer Name: " + toPrint.getCustomerName() + " | "
                    + "State: " + toPrint.getState() + " | "
                    + "Product Type: " + toPrint.getProductType() + " | "
                    + "Area (in sq ft): " + toPrint.getArea() + " | "
                    + "Cost per sq ft: " + toPrint.getCostPerSqFt() + " | "
                    + "Labor cost per sq ft: " + toPrint.getLaborCostPerSqFt() + " | \n"
                    + "Material Cost: " + toPrint.getMaterialCost() + " | "
                    + "Labor Cost: " + toPrint.getLaborCost() + " | "
                    + "Tax: " + toPrint.getTax() + " | \n"
                    + "Total: " + toPrint.getTotal() + "\n-\n-\n");
        }
    }

    public void displayAddSuccess(Order toAdd) {
        int orderNum = toAdd.getOrderNumber();
        io.print("Order number " + orderNum + " was added successfully.\n");
    }

//**************************************************************************
//*
//*Edit Order()
//*
//**************************************************************************   
    public boolean confirmEdit() {

        boolean toReturn = false;
        String userResponse = io.readString("Enter 'Yes' if you want to save your edits.\n"
                + "Enter 'No' (or any other character) if you want to keep the original Order: ");
        if (userResponse.equalsIgnoreCase("yes")) {
            toReturn = true;
        } else {
            toReturn = false;
        }
        return toReturn;
    }

//**************************************************************************
//*
//*Used Throughout 
//*
//**************************************************************************
    public LocalDate getDateToUpdate() {
        LocalDate toReturn = io.stringToDate("Date to Edit or Delete: ");
        return toReturn;
    }

    public int getOrderNum() {
        int toReturn = io.readInt("Order number to delete or edit: ");
        return toReturn;
    }

    public void displayJustOneOrder(Order toDisplay) {
        io.print("-\nOrder Number: " + toDisplay.getOrderNumber() + " | "
                + "Customer Name: " + toDisplay.getCustomerName() + " | "
                + "State: " + toDisplay.getState() + " | "
                + "Product Type: " + toDisplay.getProductType() + " | "
                + "Area (in sq ft): " + toDisplay.getArea() + " | "
                + "Cost per sq ft: " + toDisplay.getCostPerSqFt() + " | "
                + "Labor cost per sq ft: " + toDisplay.getLaborCostPerSqFt() + " | \n"
                + "Material Cost: " + toDisplay.getMaterialCost() + " | "
                + "Labor Cost: " + toDisplay.getLaborCost() + " | "
                + "Tax: " + toDisplay.getTax() + " | \n"
                + "Total: " + toDisplay.getTotal() + "\n-\n-\n");

    }

    public Order getEdit(Order toEdit, List<Product> productList, List<Tax> taxList) {

        io.print("You are editing order # " + toEdit.getOrderNumber() + " on date "
                + toEdit.getOrderDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "\n"
                + "Press enter to keep the current data. \n");

        toEdit.setCustomerName(io.editString("Enter a new customer name (" + toEdit.getCustomerName() + "):", toEdit.getCustomerName()));
        displayStateOptions(taxList);
        toEdit.setState(io.editState("Enter a new state (" + toEdit.getState() + "):", taxList, toEdit.getState()));
        displayProductOptions(productList);
        toEdit.setProductType(io.editProduct("Enter a new product (" + toEdit.getProductType() + "):", productList, toEdit.getProductType()));
        BigDecimal toSet = io.editBigDecimal("Enter a new area (" + toEdit.getArea() + "):", toEdit.getArea(), new BigDecimal("100"));
        if (toSet != null) {
            toEdit.setArea(toSet);
        } else {

        }
        return toEdit;

    }

    public void displayNotEdited(Order toEdit) {
        io.print("The order has not been edited or deleted.\n");
    }

    public void displayEditSuccess(Order toEdit) {
        io.print("You successfully edited order #" + toEdit.getOrderNumber() + ". Your edits are displayed below:\n");
        displayJustOneOrder(toEdit);
        io.print("\n");
    }

    public void displayOrderToDelete(Order toDelete) {
        io.print("\nThe order you want to delete is: ");
        io.print("-\nOrder Number: " + toDelete.getOrderNumber() + " | "
                + "Customer Name: " + toDelete.getCustomerName() + " | "
                + "State: " + toDelete.getState() + " | "
                + "Product Type: " + toDelete.getProductType() + " | "
                + "Area (in sq ft): " + toDelete.getArea() + " | "
                + "Cost per sq ft: " + toDelete.getCostPerSqFt() + " | "
                + "Labor cost per sq ft: " + toDelete.getLaborCostPerSqFt() + " | \n"
                + "Material Cost: " + toDelete.getMaterialCost() + " | "
                + "Labor Cost: " + toDelete.getLaborCost() + " | "
                + "Tax: " + toDelete.getTax() + " | \n"
                + "Total: " + toDelete.getTotal() + "\n-\n-\n");
    }

    public boolean confirmDelete() {
        boolean confirmDelete = io.readYesOrNo("\nAre you sure you want to delete this order? Type 'Yes' to delete or 'No' to keep the file: ");
        boolean delete = false;
        if (confirmDelete) {
            delete = true;
        } else {
            delete = false;
        }
        return delete;
    }

    public void displayDeleteSuccess(Order toDelete) {
        io.print("\nThe following order has been deleted: ");
        io.print("-\nOrder Number: " + toDelete.getOrderNumber() + " | "
                + "Customer Name: " + toDelete.getCustomerName() + " | "
                + "State: " + toDelete.getState() + " | "
                + "Product Type: " + toDelete.getProductType() + " | "
                + "Area (in sq ft): " + toDelete.getArea() + " | "
                + "Cost per sq ft: " + toDelete.getCostPerSqFt() + " | "
                + "Labor cost per sq ft: " + toDelete.getLaborCostPerSqFt() + " | \n"
                + "Material Cost: " + toDelete.getMaterialCost() + " | "
                + "Labor Cost: " + toDelete.getLaborCost() + " | "
                + "Tax: " + toDelete.getTax() + " | \n"
                + "Total: " + toDelete.getTotal() + "\n-\n-\n");
    }

}
