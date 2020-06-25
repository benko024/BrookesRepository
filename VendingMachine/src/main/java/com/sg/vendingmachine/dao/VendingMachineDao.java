/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.service.NoItemsLeftException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.InvalidItemException;
import java.util.List;

/**
 *
 * @author bkb
 */
public  interface VendingMachineDao {

    public List<Item> getAllItems();
    
    public void checkCost(Item updatedItem) throws NoExistingFileException;
    
    public void reduceItemByOne(Item updatedItem) throws NoItemsLeftException, InvalidItemException; 
    
    public Item searchbyId(int id);
    
}
