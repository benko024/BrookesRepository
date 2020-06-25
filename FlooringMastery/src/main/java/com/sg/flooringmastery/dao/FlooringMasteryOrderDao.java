/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bkb
 */
public interface FlooringMasteryOrderDao {

    //**************************************************************************
    //*Add Order()
    //**************************************************************************
    Order addOrder(Order toAdd) throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException,
            InvalidCustomerException, InvalidProductException, InvalidStateException, InvalidOrderException,
            InvalidAreaException;

    //**************************************************************************
    //*Delete Order()
    //**************************************************************************
    void deleteOrder(Order toDelete) throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException,
            InvalidOrderNumException;

    //**************************************************************************
    //*Edit Order()
    //**************************************************************************
    void editOrder(Order toEdit) throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException,
            InvalidOrderNumException, InvalidCustomerException, InvalidStateException, InvalidProductException,
            InvalidAreaException;

    //**************************************************************************
    //*
    //*Display Order()
    //*
    //**************************************************************************
    List<Order> getAllOrders(LocalDate toCheck);

    Order getOneOrder(LocalDate userDate, int orderNum) throws InvalidOrderNumException, InvalidDateException;

}
