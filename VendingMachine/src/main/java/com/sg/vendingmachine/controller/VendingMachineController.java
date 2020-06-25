/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.NoExistingFileException;
import com.sg.vendingmachine.service.InvalidItemException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.VendingMachineService;
import com.sg.vendingmachine.ui.VendingMachineView;
import com.sg.vendingmachine.service.NoItemsLeftException;
import com.sg.vendingmachine.service.Change;

import com.sg.vendingmachine.service.NegativeMoneyException;
import com.sg.vendingmachine.service.NotEnoughMoneyException;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkb
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineService service;

    public VendingMachineController(VendingMachineView view,
            VendingMachineService service) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;

        view.Welcome();

        while (keepGoing) {
            try {
                view.displayItems(service.getAllItems());
                int menuChoice = view.showMenu();
                switch (menuChoice) {
                    case 1:
                        addMoney();
                        break;
                    case 2:
                        vendItem();
                        break;
                    case 3:
                        returnChange();
                        break;
                    case 4:
                        getCurrentBalance();
                        break;
                    case 5:
                        keepGoing = false;
                        exit();
                        break;
                }
            } catch (NoItemsLeftException |
                    NotEnoughMoneyException |
                    NegativeMoneyException | 
                    InvalidItemException |
                    NoExistingFileException ex) {
                view.displayErrorMessage(ex.getMessage());
 
        }
    }
    }

    private void addMoney() throws NegativeMoneyException {
        BigDecimal currentMoney = service.getCurrentMoney();
        view.printCurrentMoney(currentMoney);
        BigDecimal userInput = view.getMoney();
        BigDecimal totalMoney = service.depositMoney(userInput);
        view.displayTotalMoney(userInput, totalMoney);
    }

    private void vendItem() throws NoItemsLeftException, NotEnoughMoneyException, InvalidItemException, NoExistingFileException {
        int itemId = view.getItemId(service.getAllItems());
        Item retrieved = service.vendItem(itemId);
        view.returnReducedItems(retrieved);
        view.printItemVended(retrieved);
        getCurrentBalance();
    }

    private void returnChange() {
        BigDecimal change = service.getCurrentMoney();
        view.printChangeMessage(change);
        Change toCheck = service.returnChange();
        view.displayChange(toCheck);

    }

    private void exit() {
        view.printExitMessage();
    }

    private void getCurrentBalance() {
        BigDecimal balance = service.getCurrentMoney();
        view.printCurrentMoney(balance);
        
    }
}
