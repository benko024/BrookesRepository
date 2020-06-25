/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.Change;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author bkb
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void Welcome() {
        io.print("Welcome to the vending machine!");
        io.print("\n \n");
    }

    public BigDecimal getMoney() {
        BigDecimal moneyValue = null;
        boolean invalid = true;
        while (invalid) {
            String StartingNum = io.readString(" \n\nPlease input the amount of money you have to spend: $");
            try {
                moneyValue = new BigDecimal(StartingNum);
                moneyValue.setScale(2, RoundingMode.HALF_UP);
                invalid = false;
            } catch (NumberFormatException e) {
                io.print("Input error. Please enter a number.\n\n");
            }
        }

        return moneyValue;
    }

    public void displayItems(List<Item> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            Item toPrint = itemList.get(i);
            io.print("Item Id: " + (i + 1) + " | "
                    + toPrint.getItemName() + " | "
                    + "Cost: $" + toPrint.getItemCost() + " | "
                    + "Number Available: " + toPrint.getNumItemAvail() + "\n");
        }
    }

    public int getItemId(List<Item> itemList) {
        int index = io.readInt("\nPlease select the Id of the item you would like to purchase: ", 1, itemList.size()) - 1;
        Item userChoice = itemList.get(index);
        return userChoice.getItemId();
    }

    public Item returnReducedItems(Item retrieved) {
        Item updatedItem = new Item();
        updatedItem.setItemId(retrieved.getItemId());
        updatedItem.setItemName(retrieved.getItemName());
        updatedItem.setItemCost(retrieved.getItemCost());
        updatedItem.setNumItemAvail((retrieved.getNumItemAvail()) - 1);
        return updatedItem;
    }

    public void displayErrorMessage(String message) {
        io.print(message + "\n\n\n****\n\n\n");
    }

    public void displayChange(Change toCheck) {
        
        io.print("Here is your change in coins: \n");
        io.print("Quarters: " + toCheck.getQuarters() + "\n");
        io.print("Dimes: " + toCheck.getDimes() + "\n");
        io.print("Nickels: " + toCheck.getNickels() + "\n");
        io.print("Pennies: " + toCheck.getPennies() + "\n");
        io.print("\n\n\n****\n\n\n");
    }

    public int showMenu() {

        io.print("\n____Main Menu____\n");
        io.print("1. Enter money\n");
        io.print("2. Vend Item\n");
        io.print("3. Return change\n");
        io.print("4. Check current balance\n");
        io.print("5. Quit\n");

        int choice = io.readInt("Please enter selection: ", 1, 5);
        return choice;
    }

    public void displayTotalMoney(BigDecimal userInput, BigDecimal moneyEntered) {
        io.print("You added $" + userInput + ". ");
        io.print("You now have $" + moneyEntered + "\n\n****\n\n");
    }

    public void printExitMessage() {
        io.print("Okay, maybe next time! \n");
    }
    
    public void printCurrentMoney(BigDecimal currentMoney){
        io.print("\nYou currently have $" + currentMoney + "\n\n");
    }

    public void printItemVended(Item retrieved) {
        io.print("Thank you for purchasing " + retrieved.getItemName() + "!");
    }
  
    public void printChangeMessage(BigDecimal change){
        io.print("\nThank you for using the vending machine! Your change is: $" + change + "\n");
    }

}
