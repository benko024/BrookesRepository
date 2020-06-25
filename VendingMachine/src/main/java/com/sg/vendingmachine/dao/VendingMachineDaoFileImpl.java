/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.service.NoItemsLeftException;
import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkb
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    String path = "vendingMachineContents.txt";

    public VendingMachineDaoFileImpl(String path) {
        this.path = path;
    }

    

    private Item convertLinetoItem(String row) {
        //:: is the delimiter

        String[] cells = row.split("::");

        int itemId = Integer.parseInt(cells[0]);
        String itemName = cells[1];
        BigDecimal itemCost = new BigDecimal(cells[2]);
        int numItemsAvail = Integer.parseInt(cells[3]);

        Item toAdd = new Item();

        toAdd.setItemId(itemId);
        toAdd.setItemName(itemName);
        toAdd.setItemCost(itemCost);
        toAdd.setNumItemAvail(numItemsAvail);

        return toAdd;

    }

    @Override
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(
                            new FileReader(path)
                    )
            );
            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Item toAdd = convertLinetoItem(row);

                allItems.add(toAdd);
                
            }
            fileScanner.close();
        } catch (FileNotFoundException ex) {
        }
        return allItems;
    }

    @Override
    public void checkCost(Item updatedItem) throws NoExistingFileException {
        List<Item> allItems = getAllItems();
        int index = -1;
        for (int i = 0; i < allItems.size(); i++) {
            Item toCheck = allItems.get(i);
            if (toCheck.getItemId() == updatedItem.getItemId()) {
                index = i;
                break;
            }
        }
        allItems.set(index, updatedItem);
        writeFile(allItems);

    }

    @Override
    public void reduceItemByOne(Item updatedItem) throws NoItemsLeftException  {
        List<Item> allItems = getAllItems();
        int index = -1;
        
        for (int i = 0; i < allItems.size(); i++) {
            Item toCheck = allItems.get(i);
            if (toCheck.getItemId() == updatedItem.getItemId()) {
                index = i;
                if (toCheck.getNumItemAvail() >0) {
                    break;
                }
                else {
                    throw new NoItemsLeftException("Unfortunately, we're all out of that item.");
                }
            }
        }
        
        allItems.set(index, updatedItem);
        
        try {
            writeFile(allItems);
        } catch (NoExistingFileException ex) {
        }
    }
    

    
    @Override
    public Item searchbyId(int id) {
        Item toReturn = null;
        List<Item> allItems = getAllItems();
        for (Item toCheck : allItems) {
            if (toCheck.getItemId() == id) {
                toReturn = toCheck;
                break;
            }
        }
        return toReturn;
    }

    private void writeFile(List<Item> allItems) throws NoExistingFileException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            for (Item newItem : allItems) {
                String line = convertItemToLine(newItem);
                writer.println(line);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            throw new NoExistingFileException("Could not open file: " + path, ex);
        }
    }

    private String convertItemToLine(Item newItem) {
        return newItem.getItemId() + "::"
                + newItem.getItemName() + "::"
                + newItem.getItemCost() + "::"
                + newItem.getNumItemAvail();
    }

}
