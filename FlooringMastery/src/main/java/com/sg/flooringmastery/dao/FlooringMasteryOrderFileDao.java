/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bkb
 */
public class FlooringMasteryOrderFileDao implements FlooringMasteryOrderDao {

    String folder;

    public FlooringMasteryOrderFileDao(String folder) {
        this.folder = folder;
    }

    //**************************************************************************
    //*Display Order()
    //**************************************************************************
    @Override
    public List<Order> getAllOrders(LocalDate toCheck) {
        List<Order> allOrders = new ArrayList<>();
        String path = dateToPath(toCheck);
        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(
                            new FileReader(path)
                    )
            );
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
                while (fileScanner.hasNextLine()) {
                    String row = fileScanner.nextLine();
                    Order toAdd = convertLineToOrder(row);
                    allOrders.add(toAdd);
                }
            }
            fileScanner.close();

        } catch (FileNotFoundException ex) {

        }
        return allOrders;

    }

    //**************************************************************************
    //*Add Order()
    //**************************************************************************
    @Override
    public Order addOrder(Order toAdd) throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException,
            InvalidCustomerException, InvalidProductException, InvalidStateException, InvalidOrderException, InvalidAreaException {
        if (toAdd != null) {
            LocalDate orderDate = toAdd.getOrderDate();
            if (orderDate == null) {
                throw new InvalidDateException("You cannot add a date that is null.");
            }
            if (toAdd.getCustomerName() == null) {
                throw new InvalidCustomerException("You cannot add a customer that is null.");
            }
            if (toAdd.getCustomerName() == "") {
                throw new InvalidCustomerException("You cannot add acustomer with an empty name");
            }
            if (toAdd.getProductType() == null || toAdd.getProductType() == "") {
                throw new InvalidProductException("You cannot add a null product.");
            }
            if (toAdd.getState() == null || toAdd.getState() == ("")) {
                throw new InvalidStateException("You cannot add a null state.");
            }
            if (toAdd.getArea() == null || toAdd.getState() == ("")) {
                throw new InvalidAreaException("You cannot add a null area");
            }
            if (toAdd.getArea().compareTo(new BigDecimal("0")) <= 0) {
                throw new InvalidAreaException("You cannot add a negative area.");
            }
            if (toAdd.getCustomerName().matches("^[A-Za-z0-9,.' ]+$")) {
                List<Order> allOrders = getAllOrders(orderDate);

                int maxId = 0;

                for (Order o : allOrders) {
                    if (o.getOrderNumber() > maxId) {
                        maxId = o.getOrderNumber();
                    }
                }

                toAdd.setOrderNumber(maxId + 1);

                allOrders.add(toAdd);
                writeFile(allOrders, orderDate);

                return toAdd;
            } else {
                throw new InvalidCustomerException("You cannot add a customer with these characters in their name.");
            }
        } else {
            throw new InvalidOrderException("You cannot add a null order.");
        }

    }

    //**************************************************************************
    //*Edit Order()
    //**************************************************************************
    @Override
    public void editOrder(Order toEdit) throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException,
            InvalidOrderNumException, InvalidCustomerException, InvalidStateException, InvalidProductException, InvalidAreaException {
        LocalDate toCreate = toEdit.getOrderDate();

        if (toCreate != null) {
            if (toEdit.getCustomerName() != null) {
                if (toEdit.getCustomerName().isBlank()) {
                    throw new InvalidCustomerException("You cannot add a customer with a blank name.");
                } else {
                    if (toEdit.getCustomerName().matches("^[A-Za-z0-9,.' ]+$")) {
                        if (toEdit.getState() != null) {
                            if (toEdit.getState().isBlank()) {
                                throw new InvalidStateException("You cannot add a blank state");
                            } else {
                                if (toEdit.getProductType() == null || toEdit.getProductType().isBlank()) {
                                    throw new InvalidProductException("You cannot add an invalid product");
                                } else {
                                    if (toEdit.getArea() != null) {
                                        if (toEdit.getArea().compareTo(new BigDecimal("0")) <= 0) {
                                            throw new InvalidAreaException("You cannot add an order with a negative area");
                                        } else {
                                            List<Order> allOrders = getAllOrders(toCreate);
                                            int index = -1;
                                            for (int i = 0; i < allOrders.size() + 1; i++) {
                                                Order toCheck = allOrders.get(i);
                                                if (toEdit.getOrderNumber() <= 0) {
                                                    throw new InvalidOrderNumException("There should be no order numbers that are less than or equal to 0");
                                                }
                                                if (toCheck.getOrderNumber() == toEdit.getOrderNumber()) {
                                                    index = i;
                                                    break;
                                                }
                                            }

                                            allOrders.set(index, toEdit);

                                            writeFile(allOrders, toCreate);
                                        }
                                    } else {
                                        throw new InvalidAreaException("You cannot add an order with null area");
                                    }
                                }
                            }
                        } else {
                            throw new InvalidStateException("You cannot add a null state");
                        }
                    } else {
                        throw new InvalidCustomerException("You cannot add a customer that has any other characters in it.");
                    }

                }
            } else {
                throw new InvalidCustomerException("You cannot add a customer who has a null name.");
            }
        } else {
            throw new InvalidDateException("You cannot add an order that has a null date");
        }
    }

    //**************************************************************************
    //*Delete Order()
    //**************************************************************************
    @Override
    public void deleteOrder(Order toDelete) throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidOrderNumException {
        LocalDate toCreate = toDelete.getOrderDate();
        if (toCreate == null) {
            throw new InvalidDateException("You cannot delete a null date.");
        } else {
            if (toDelete.getOrderNumber() <= 0) {
                throw new InvalidOrderNumException("You cannot delete an order with a negative order number.");
            } else {
                List<Order> allOrders = getAllOrders(toCreate);
                int index = -1;
                for (int i = 0; i < allOrders.size(); i++) {
                    Order toCheck = allOrders.get(i);
                    if (toCheck.getOrderNumber() == toDelete.getOrderNumber()) {
                        index = i;
                        break;
                    }
                }
                allOrders.remove(index);
                writeFile(allOrders, toCreate);
            }
        }

    }

    //**************************************************************************
    //*The code below is used by multiple above methods
    //**************************************************************************
    private void writeFile(List<Order> allOrders, LocalDate toPath) throws FlooringMasteryDaoNoFileException {

        String path = dateToPath(toPath);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            String headerRow = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
            writer.println(headerRow);

            for (Order o : allOrders) {
                String line = convertOrderToLine(o);
                writer.println(line);
            }

            writer.flush();
            writer.close();
        } catch (IOException ex) {

            throw new FlooringMasteryDaoNoFileException("Could not open file: " + path, ex);
        }

    }

    private Order convertLineToOrder(String row) {
        String[] cells = row.split(",");

        int orderNumber = Integer.parseInt(cells[0]);
        String customerName = cells[1];
        String state = cells[2];
        String taxRate = cells[3];
        String productType = cells[4];
        String area = cells[5];
        String costPerSqFt = cells[6];
        String laborCostPerSqFt = cells[7];

        Order toAdd = new Order();
        toAdd.setOrderNumber(orderNumber);
        toAdd.setCustomerName(customerName);
        toAdd.setState(state);
        toAdd.setTaxRate(new BigDecimal(taxRate));
        toAdd.setProductType(productType);
        toAdd.setArea(new BigDecimal(area));
        toAdd.setCostPerSqFt(new BigDecimal(costPerSqFt));
        toAdd.setLaborCostPerSqFt(new BigDecimal(laborCostPerSqFt));

        BigDecimal laborCost = toAdd.getLaborCost();
        BigDecimal materialCost = toAdd.getMaterialCost();

        BigDecimal tax = toAdd.getTax();

        return toAdd;
    }

    private String convertOrderToLine(Order o) {
        return o.getOrderNumber() + ","
                + o.getCustomerName() + ","
                + o.getState() + ","
                + o.getTaxRate() + ","
                + o.getProductType() + ","
                + o.getArea() + ","
                + o.getCostPerSqFt() + ","
                + o.getLaborCostPerSqFt() + ","
                + o.getMaterialCost() + ","
                + o.getLaborCost() + ","
                + o.getTax() + ","
                + o.getTotal();

    }

    public String dateToPath(LocalDate orderDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String orderString = orderDate.format(formatter);
        String fileName = "Orders_" + orderString + ".txt";
        String toReturn = Paths.get(folder, fileName).toString();

        return toReturn;
    }

    @Override
    public Order getOneOrder(LocalDate userDate, int orderNum) throws InvalidOrderNumException, InvalidDateException {
        Order toReturn = null;

        if (userDate != null) {
            List<Order> allOrders = getAllOrders(userDate);
            if (orderNum > 0) {
                for (Order toCheck : allOrders) {
                    if (toCheck.getOrderNumber() == orderNum) {
                        toReturn = toCheck;
                        break;
                    } else {
                        throw new InvalidOrderNumException("That order number does not exist.\n");
                    }
                }
                return toReturn;
            } else {
                throw new InvalidOrderNumException("You cannot add an invalid number.");
            }

        } else {
            throw new InvalidDateException("You cannot get an order for a file that does not exist.");
        }
    }

}
