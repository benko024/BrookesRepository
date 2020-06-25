/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author bkb
 */
public class FlooringMasteryInMemOrderDao implements FlooringMasteryOrderDao {

    List<Order> allOrders = new ArrayList<>();

    public FlooringMasteryInMemOrderDao() {

        //0
        Order goldenOrder1 = new Order();
        goldenOrder1.setOrderDate((LocalDate.now().plusDays(1)));
        goldenOrder1.setOrderNumber(1);
        goldenOrder1.setCustomerName("Rosa Parks");
        goldenOrder1.setState("KY");
        goldenOrder1.setTaxRate(new BigDecimal("6.00"));
        goldenOrder1.setProductType("Carpet");
        goldenOrder1.setArea(new BigDecimal("110"));
        goldenOrder1.setCostPerSqFt(new BigDecimal("2.25"));
        goldenOrder1.setLaborCostPerSqFt(new BigDecimal("2.10"));
        goldenOrder1.getMaterialCost();
        goldenOrder1.getLaborCost();
        goldenOrder1.getTax();
        goldenOrder1.getTotal();

        //1
        Order goldenOrder2 = new Order();
        goldenOrder2.setOrderDate((LocalDate.now().plusDays(1)));
        goldenOrder2.setOrderNumber(2);
        goldenOrder2.setCustomerName("Amy Adams");
        goldenOrder2.setState("CA");
        goldenOrder2.setTaxRate(new BigDecimal("25.00"));
        goldenOrder2.setProductType("Tile");
        goldenOrder2.setArea(new BigDecimal("150"));
        goldenOrder2.setCostPerSqFt(new BigDecimal("3.50"));
        goldenOrder2.setLaborCostPerSqFt(new BigDecimal("4.15"));
        goldenOrder2.getMaterialCost();
        goldenOrder2.getLaborCost();
        goldenOrder2.getTax();
        goldenOrder2.getTotal();

        //2
        Order toEdit = new Order();
        toEdit.setOrderDate((LocalDate.now().plusDays(1)));
        toEdit.setOrderNumber(3);
        toEdit.setCustomerName("Harry Potter");
        toEdit.setState("TX");
        toEdit.setProductType("Tile");
        toEdit.setArea(new BigDecimal("150"));
        toEdit.setCostPerSqFt(new BigDecimal("3.50"));
        toEdit.setLaborCostPerSqFt(new BigDecimal("4.15"));
        toEdit.setTaxRate(new BigDecimal("6.00"));
        toEdit.getMaterialCost();
        toEdit.getLaborCost();
        toEdit.getTax();
        toEdit.getTotal();

//        //3
//        Order incorrectOrderNum = new Order();
//        incorrectOrderNum.setOrderDate((LocalDate.now().plusDays(1)));
//        incorrectOrderNum.setOrderNumber(-1);
//        incorrectOrderNum.setCustomerName("Harry Potter");
//        incorrectOrderNum.setState("TX");
//        incorrectOrderNum.setProductType("Tile");
//        incorrectOrderNum.setArea(new BigDecimal("150"));
//        incorrectOrderNum.setCostPerSqFt(new BigDecimal("3.50"));
//        incorrectOrderNum.setLaborCostPerSqFt(new BigDecimal("4.15"));
//        incorrectOrderNum.setTaxRate(new BigDecimal("6.00"));
//        incorrectOrderNum.getMaterialCost();
//        incorrectOrderNum.getLaborCost();
//        incorrectOrderNum.getTax();
//        incorrectOrderNum.getTotal();

        allOrders.add(goldenOrder1);
        allOrders.add(goldenOrder2);
        allOrders.add(toEdit);
//        allOrders.add(incorrectOrderNum);

    }

    @Override
    public Order addOrder(Order toAdd) throws FlooringMasteryDaoNoFileException, NewFileException {

        int maxId = allOrders
                .stream()
                .mapToInt(o -> o.getOrderNumber())
                .max()
                .orElse(0);

        toAdd.setOrderNumber(maxId + 1);
        allOrders.add(toAdd);
        return toAdd;

    }

    @Override
    public void deleteOrder(Order toDelete) throws FlooringMasteryDaoNoFileException, NewFileException {

        LocalDate toCreate = toDelete.getOrderDate();

        int index = -1;
        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);
            if (toCheck.getOrderNumber() == toDelete.getOrderNumber()) {
                index = i;
                break;
            }
        }
        allOrders.remove(index);

//        int orderNum = toDelete.getOrderNumber();
//        allOrders = allOrders
//                .stream()
//                .filter(o -> o.getOrderNumber() != orderNum)
//                .collect(Collectors.toList());
    }

    @Override
    public void editOrder(Order toEdit) throws FlooringMasteryDaoNoFileException, NewFileException {

        List<Order> editedList
                = allOrders
                        .stream()
                        .map(
                                o
                                -> o.getOrderNumber() == toEdit.getOrderNumber() ? toEdit : o
                        ).collect(Collectors.toList());

        allOrders = editedList;
    }

    @Override
    public List<Order> getAllOrders(LocalDate toCheck) {

        return allOrders;

    }

    @Override
    public Order getOneOrder(LocalDate userDate, int orderNum) {

        return allOrders
                .stream()
                .filter(o -> o.getOrderNumber() == orderNum)
                .findAny()
                .orElse(null);

        //why didn't I use the userdate?
        //I think that I don't actually use the user date for anything
        //except to get all of the orders. Since we already 
        //have "allorders" defined up at the top, I don't need to use it at all. 
    }

}
