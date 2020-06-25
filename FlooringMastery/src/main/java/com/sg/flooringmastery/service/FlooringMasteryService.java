/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.InvalidAreaException;
import com.sg.flooringmastery.dao.InvalidStateException;
import com.sg.flooringmastery.dao.InvalidProductException;
import com.sg.flooringmastery.dao.InvalidCustomerException;
import com.sg.flooringmastery.dao.InvalidOrderNumException;
import com.sg.flooringmastery.dao.InvalidDateException;
import com.sg.flooringmastery.dao.FlooringMasteryDaoException;
import com.sg.flooringmastery.dao.FlooringMasteryDaoNoFileException;
import com.sg.flooringmastery.dao.FlooringMasteryOrderDao;
import com.sg.flooringmastery.dao.FlooringMasteryProductsDao;
import com.sg.flooringmastery.dao.FlooringMasteryTaxDao;
import com.sg.flooringmastery.dao.InvalidOrderException;
import com.sg.flooringmastery.dao.NewFileException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bkb
 */
public class FlooringMasteryService {

    FlooringMasteryOrderDao orderDao;
    FlooringMasteryTaxDao taxDao;
    FlooringMasteryProductsDao productDao;

    public FlooringMasteryService(FlooringMasteryOrderDao orderDao,
            FlooringMasteryTaxDao taxDao,
            FlooringMasteryProductsDao productDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }

//**************************************************************************
    public List<Order> getOrderList(LocalDate toCreate) throws
            FlooringMasteryDaoNoFileException,
            NewFileException {
        List<Order> toReturn = orderDao.getAllOrders(toCreate);
        if (toReturn.isEmpty()) {
            throw new NewFileException("There were no orders for this day.\n");
        }
        return toReturn;
    }

//**************************************************************************
    public Order addOrder(List<Product> productList, Order toAdd) throws InvalidDateException,
            InvalidCustomerException, InvalidStateException, FileNotFoundException, NewFileException,
            InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {

        try {
            verifyValidAddDate(toAdd);
            verifyCustomerName(toAdd);
            verifyState(toAdd);
            verifyProductType(productList, toAdd);
            verifyArea(toAdd);

            toAdd.setTaxRate(taxDao.getTaxRate(toAdd.getState()));
            Product ordProd;

            ordProd = productDao.getProductByName(toAdd.getProductType());

            toAdd.setCostPerSqFt(ordProd.getCostPerSqFt());
            toAdd.setLaborCostPerSqFt(ordProd.getLaborPerSqFt());

            orderDao.addOrder(toAdd);

        } catch (FlooringMasteryDaoException ex) {

        }
        return toAdd;
    }

    public List<Product> getProductList() throws FileNotFoundException, FlooringMasteryDaoException {
        List<Product> toReturn = productDao.getAllProducts();
        return toReturn;
    }

    public List<Tax> getTaxList() throws FileNotFoundException {
        List<Tax> toReturn = taxDao.getTaxList();
        return toReturn;
    }

    private boolean verifyValidAddDate(Order toAdd) throws InvalidDateException {
        LocalDate today = LocalDate.now();
        boolean validDate = false;
        LocalDate userDate = toAdd.getOrderDate();
        if (userDate.isAfter(today)) {
            validDate = true;
        } else {
            throw new InvalidDateException("You must enter a date that is in the future.");
        }
        return validDate;
    }

    private boolean verifyCustomerName(Order toAdd) throws InvalidCustomerException {
        boolean validName = false;
        String customerName = toAdd.getCustomerName();
        if (customerName == null || customerName.isBlank() || customerName.isEmpty() || customerName.equals("")) {
            throw new InvalidCustomerException("Customer name cannot be blank.");
        } else {
            if (customerName.matches("^[A-Za-z0-9,.' ]+$")) {
                if (customerName.isEmpty()) {
                    throw new InvalidCustomerException("Customer name cannot be empty.");
                } else {
                    if (customerName == null) {
                        throw new InvalidCustomerException("Customer name cannot be null.");
                    } else {
                        validName = true;
                    }
                }
            } else {
                throw new InvalidCustomerException("Customer Name can only contain characters A-Z, 0-9, \nspaces, commas, periods, and apostrophes.\n");
            }
        }
        return validName;
    }

    private boolean verifyState(Order toAdd) throws InvalidStateException, FileNotFoundException {
        boolean validState = false;
        List<Tax> taxList = taxDao.getTaxList();
        String state = toAdd.getState();
        for (int i = 0; i < taxList.size(); i++) {
            if (taxList.get(i).getStateAbr().equalsIgnoreCase(state)) {
                validState = true;
                return validState;
            }
        }
        if (!validState) {
            throw new InvalidStateException("Unfortunately, we are not able to sell to " + state + " at this time.\n");
        }
        return validState;
    }

    private boolean verifyProductType(List<Product> productList, Order toAdd) throws InvalidProductException {
        boolean validProduct = false;
        String productType = toAdd.getProductType();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductType().equalsIgnoreCase(productType)) {
                validProduct = true;
                return validProduct;
            }
        }
        if (!validProduct) {
            throw new InvalidProductException("Product " + productType + " is not available at this time.\n");
        }
        return validProduct;
    }

    private boolean verifyArea(Order toAdd) throws InvalidAreaException {
        boolean validArea = false;
        BigDecimal area = toAdd.getArea();
        if (area != null) {
            int comparison = area.compareTo(new BigDecimal("100"));
            if (comparison >= 0) {
                validArea = true;
            } else {
                throw new InvalidAreaException("You entered an area of " + area + ".\n"
                        + "The area must be at least 100 sq ft.\n");
            }
        } else {
            throw new InvalidAreaException("The area cannot be null.");
        }
        return validArea;
    }

//**************************************************************************
    public Order editOrder(Order toEdit) throws OrderEditException, InvalidOrderNumException,
            FlooringMasteryDaoNoFileException, InvalidCustomerException,
            InvalidStateException, FileNotFoundException, InvalidProductException,
            NewFileException, InvalidAreaException, InvalidDateException, FlooringMasteryDaoException,
            InvalidAreaException, InvalidOrderException{

        boolean validDate = verifyEditDeleteDate(toEdit);
        if (validDate) {
            boolean validNum = verifyValidOrderNum(toEdit);
            if (validNum) {
                verifyCustomerName(toEdit);
                verifyState(toEdit);
                List<Product> productList = productDao.getAllProducts();
                verifyProductType(productList, toEdit);
                verifyEditOrDeleteArea(toEdit);

                toEdit.setTaxRate(taxDao.getTaxRate(toEdit.getState()));
                Product ordProd = productDao.getProductByName(toEdit.getProductType());
                toEdit.setCostPerSqFt(ordProd.getCostPerSqFt());
                toEdit.setLaborCostPerSqFt(ordProd.getLaborPerSqFt());

                orderDao.editOrder(toEdit);
            }
        }
        return toEdit;
    }

//**************************************************************************
    public void removeOrder(Order toDelete) throws InvalidDateException, InvalidOrderNumException,
            FlooringMasteryDaoNoFileException, NewFileException {
        boolean validDate = verifyEditDeleteDate(toDelete);
        if (validDate) {
            boolean validNum = verifyValidOrderNum(toDelete);
            if (validNum) {
                orderDao.deleteOrder(toDelete);
            }
        }
    }

//**************************************************************************
    private boolean verifyValidOrderNum(Order toCheck) throws InvalidOrderNumException {
        boolean validOrder = false;
        List<Order> orderList = orderDao.getAllOrders(toCheck.getOrderDate());
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNumber() == toCheck.getOrderNumber()) {
                validOrder = true;
                return validOrder;
            }
        }
        if (!validOrder) {
            throw new InvalidOrderNumException("This order number does not exist\n");
        }
        return validOrder;

    }

    private boolean verifyEditDeleteDate(Order toEdit) throws InvalidDateException {

        boolean validDate = false;
        LocalDate userDate = toEdit.getOrderDate();
        List<Order> orderList = orderDao.getAllOrders(userDate);
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNumber() == toEdit.getOrderNumber()) {
                validDate = true;
            }
        }
        if (!validDate) {
            throw new InvalidDateException("There is no file for the date that you entered.");
        }
        return validDate;
    }

    private boolean verifyEditOrDeleteArea(Order toAdd) throws InvalidAreaException {
        boolean validArea = false;

        BigDecimal area = toAdd.getArea();

        if (area != null) {
            int comparison = area.compareTo(new BigDecimal("100"));
            if (comparison >= 0) {
                validArea = true;
            } else {
                throw new InvalidAreaException("You entered an area of " + area + ".\n"
                        + "The area must be at least 100 sq ft.\n");
            }

        }
        return validArea;
    }

    public Order getOneOrder(LocalDate userDate, int orderNum) throws InvalidOrderNumException, InvalidDateException {
        Order toReturn = orderDao.getOneOrder(userDate, orderNum);
        toReturn.setOrderDate(userDate);
        return toReturn;
    }
}
