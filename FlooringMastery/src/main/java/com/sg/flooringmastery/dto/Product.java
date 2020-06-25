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
public class Product {

    private String productType;
    private BigDecimal costPerSqFt;
    private BigDecimal laborPerSqFt;

    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return the costPerSqFt
     */
    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    /**
     * @param costPerSqFt the costPerSqFt to set
     */
    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    /**
     * @return the laborPerSqFt
     */
    public BigDecimal getLaborPerSqFt() {
        return laborPerSqFt;
    }

    /**
     * @param laborPerSqFt the laborPerSqFt to set
     */
    public void setLaborPerSqFt(BigDecimal laborPerSqFt) {
        this.laborPerSqFt = laborPerSqFt;
    }

}
