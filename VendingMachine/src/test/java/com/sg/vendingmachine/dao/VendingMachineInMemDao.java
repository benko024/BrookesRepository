/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.NoItemsLeftException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author bkb
 */
public class VendingMachineInMemDao implements VendingMachineDao {

    List<Item> allItems = new ArrayList<>();
    
    public VendingMachineInMemDao() {
       Item testItem = new Item();
       testItem.setItemId(1);
       testItem.setItemName("Chips Ahoy snack pack");
       testItem.setItemCost(new BigDecimal("1.25"));
       testItem.setNumItemAvail(10);
       
       Item outOfStockItem = new Item();
       outOfStockItem.setItemId(2);
       outOfStockItem.setItemName("Chocolate bar");
       outOfStockItem.setItemCost(new BigDecimal("1.30"));
       outOfStockItem.setNumItemAvail(0);

       
       allItems.add(testItem);
       allItems.add(outOfStockItem);

    }

    @Override
    public List<Item> getAllItems() {
        return allItems;
    }

    @Override
    public void checkCost(Item updatedItem) throws NoExistingFileException {

        List<Item> editedList
                = allItems
                        .stream()
                        .map(
                                i
                                -> i.getItemCost() == updatedItem.getItemCost() ? updatedItem : i
                        ).collect(Collectors.toList());
    allItems = editedList;

    }

    @Override
    public void reduceItemByOne(Item updatedItem) {

        List<Item> editedlist
                = allItems
                        .stream()
                        .map(
                                i
                                -> i.getItemId() == updatedItem.getItemId() ? updatedItem : i
                        ).collect(Collectors.toList());

        allItems = editedlist;

    }

    @Override
    public Item searchbyId(int id) {

        return allItems
                .stream()
                .filter(i -> i.getItemId() == id)
                .findAny()
                .orElse(null);

    }

}
