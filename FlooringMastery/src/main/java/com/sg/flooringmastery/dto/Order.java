/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 *
 * @author bkb
 */
public class Order {

    private LocalDate orderDate;
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSqFt;
    private BigDecimal laborCostPerSqFt;

    public Order() {
    }

    public Order(LocalDate userDate,
            String customerName,
            String state,
            String product,
            BigDecimal area) {

        this.orderDate = userDate;
        this.customerName = customerName;
        this.state = state;
        this.productType = product;
        this.area = area;

    }

    /**
     * @return the orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
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
     * @return the area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(BigDecimal area) {
        this.area = area;
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
     * @return the laborCostPerSqFt
     */
    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    /**
     * @param laborCostPerSqFt the laborCostPerSqFt to set
     */
    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    /**
     * @return the materialCost
     */
    public BigDecimal getMaterialCost() {
        area = getArea();
        costPerSqFt = getCostPerSqFt();
        BigDecimal materialCost = area.multiply(costPerSqFt).setScale(2, RoundingMode.HALF_DOWN);
        return materialCost;
    }

    /**
     * @return the laborCost
     */
    public BigDecimal getLaborCost() {
        area = getArea();
        laborCostPerSqFt = getLaborCostPerSqFt();
        BigDecimal laborCost = area.multiply(laborCostPerSqFt).setScale(2, RoundingMode.HALF_DOWN);
        return laborCost;
    }

    /**
     * @return the tax
     */
    public BigDecimal getTax() {
        BigDecimal laborCost = getLaborCost();
        BigDecimal materialCost = getMaterialCost();
        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return tax;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        BigDecimal materialCost = getMaterialCost();
        BigDecimal laborCost = getLaborCost();
        BigDecimal tax = getTax();

        BigDecimal total = (materialCost.add(laborCost)).add(tax).setScale(2, RoundingMode.HALF_DOWN);
        return total;
    }

    /**
     * @return the orderDate
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

}
