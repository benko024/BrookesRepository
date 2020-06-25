/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.NoExistingFileException;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sg.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author bkb
 */
public class VendingMachineService {

    public VendingMachineDao dao;
    
    private BigDecimal totalMoneyEntered = BigDecimal.ZERO;

    public VendingMachineService(VendingMachineDao dao) {
        this.dao = dao;
    }

    public List<Item> getAllItems() {
        return dao.getAllItems();
    }

    public Item vendItem(int userId) throws NoItemsLeftException, NotEnoughMoneyException, InvalidItemException, NoExistingFileException {

        //throws NotEnoughMoneyException, NoItemsLeftException, InvalidItemException
        Item retrieved = dao.searchbyId(userId);
        if (retrieved == null) {
            throw new InvalidItemException("\n\n--Id " + userId +" is an invalid id--");
        }
        if (retrieved.getNumItemAvail() > 0) {
            if (totalMoneyEntered.compareTo(retrieved.getItemCost()) >= 0) {
                Item updatedItem = new Item();
                updatedItem.setItemId(retrieved.getItemId());
                updatedItem.setItemName(retrieved.getItemName());
                updatedItem.setItemCost(retrieved.getItemCost());

                updatedItem.setNumItemAvail((retrieved.getNumItemAvail()) - 1);
                dao.reduceItemByOne(updatedItem);
                totalMoneyEntered = totalMoneyEntered.subtract(updatedItem.getItemCost());
                return updatedItem;
            } else {
                throw new NotEnoughMoneyException("\n\n--Unfortunately, you do not have enough money to purchase that item.--");
            }
        } else {
            throw new NoItemsLeftException("\n\n--Unfortunately, the vending machine is all out of that item--");
        }
    }

    public BigDecimal depositMoney(BigDecimal userInput) throws NegativeMoneyException {
        int comparison = userInput.compareTo(BigDecimal.ZERO);
        if (comparison >= 0) {
            totalMoneyEntered = userInput.add(totalMoneyEntered);
            return totalMoneyEntered;
        } else {
            throw new NegativeMoneyException("\n\n--You cannot input negative money--\n\n");
        }

    }

    public Change returnChange() {
        BigDecimal changeDue = totalMoneyEntered;
        Change toReturn = new Change(changeDue);
        totalMoneyEntered = BigDecimal.ZERO;
        return toReturn;
        
    }

    public BigDecimal getCurrentMoney() {
        return totalMoneyEntered;
    }


}
