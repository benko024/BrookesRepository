/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author bkb
 */
public class Item {
    private int itemId;
    private String itemName;
    private int numItemAvail;
    private BigDecimal itemCost;

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the numItemAvail
     */
    public int getNumItemAvail() {
        return numItemAvail;
    }

    /**
     * @param numItemAvail the numItemAvail to set
     */
    public void setNumItemAvail(int numItemAvail) {
        this.numItemAvail = numItemAvail;
    }

    /**
     * @return the itemCost
     */
    public BigDecimal getItemCost() {
        return itemCost;
    }

    /**
     * @param itemCost the itemCost to set
     */
    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    
}
