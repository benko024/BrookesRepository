/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author bkb
 */
public class Tax {
    private String stateAbr;
    private String stateFullName;
    private BigDecimal taxRate;

    /**
     * @return the stateAbr
     */
    public String getStateAbr() {
        return stateAbr;
    }

    /**
     * @param stateAbr the stateAbr to set
     */
    public void setStateAbr(String stateAbr) {
        this.stateAbr = stateAbr;
    }

    /**
     * @return the stateFullName
     */
    public String getStateFullName() {
        return stateFullName;
    }

    /**
     * @param stateFullName the stateFullName to set
     */
    public void setStateFullName(String stateFullName) {
        this.stateFullName = stateFullName;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
