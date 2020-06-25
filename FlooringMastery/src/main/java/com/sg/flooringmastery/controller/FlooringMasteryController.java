/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryDaoException;
import com.sg.flooringmastery.dao.FlooringMasteryDaoNoFileException;
import com.sg.flooringmastery.dao.NewFileException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.dao.InvalidAreaException;
import com.sg.flooringmastery.dao.InvalidCustomerException;
import com.sg.flooringmastery.dao.InvalidDateException;
import com.sg.flooringmastery.dao.InvalidOrderException;
import com.sg.flooringmastery.dao.InvalidOrderNumException;
import com.sg.flooringmastery.dao.InvalidProductException;
import com.sg.flooringmastery.dao.InvalidStateException;
import com.sg.flooringmastery.service.OrderEditException;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bkb
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryService service;

    public FlooringMasteryController(FlooringMasteryView view,
            FlooringMasteryService service) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;

        while (keepGoing) {

            try {
                int menuChoice = view.showMenu();

                switch (menuChoice) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                }
            } catch (InvalidDateException
                    | InvalidAreaException
                    | InvalidCustomerException
                    | InvalidStateException
                    | InvalidProductException
                    | FlooringMasteryDaoNoFileException
                    | NewFileException | FileNotFoundException
                    | InvalidOrderNumException | OrderEditException | FlooringMasteryDaoException | InvalidOrderException ex) {
                view.displayErrorMessage(ex.getMessage());
            }

        }
    }

//**************************************************************************
//*Display Order
//**************************************************************************
    private void displayOrders() throws FlooringMasteryDaoNoFileException, NewFileException {
        LocalDate orderDate = view.getDate();
        List<Order> orderList = service.getOrderList(orderDate);

        view.displayOrders(orderList);

    }

//**************************************************************************
//*Add Order
//**************************************************************************
    private void addOrder() throws InvalidDateException, InvalidAreaException, InvalidCustomerException,
            InvalidStateException, InvalidProductException, FileNotFoundException,
            FlooringMasteryDaoNoFileException, NewFileException, FlooringMasteryDaoException, InvalidOrderException {

        List<Product> productList = service.getProductList();
        List<Tax> taxList = service.getTaxList();
        Order toAdd = view.getOrder(productList, taxList);
        toAdd = service.addOrder(productList, toAdd);
        view.displayAddSuccess(toAdd);

    }

//**************************************************************************
//*Edit Order
//**************************************************************************
    private void editOrder() throws FlooringMasteryDaoNoFileException,
            NewFileException, InvalidDateException, InvalidOrderNumException,
            OrderEditException, FileNotFoundException, InvalidCustomerException,
            InvalidStateException, InvalidProductException, InvalidAreaException,
            FlooringMasteryDaoException, InvalidOrderException {

        LocalDate userDate = view.getDateToUpdate();
        int orderNum = view.getOrderNum();
        Order toEdit = service.getOneOrder(userDate, orderNum);
        view.getEdit(toEdit, service.getProductList(), service.getTaxList());
        boolean confirmEdit = view.confirmEdit();
        if (confirmEdit) {
            toEdit = service.editOrder(toEdit);
            view.displayEditSuccess(toEdit);
        } else {
            view.displayNotEdited(toEdit);
        }
    }

//**************************************************************************
//*Remove Order
//**************************************************************************
    private void removeOrder() throws InvalidDateException, InvalidOrderNumException, FlooringMasteryDaoNoFileException, NewFileException {
        LocalDate userDate = view.getDateToUpdate();
        int orderNum = view.getOrderNum();
        Order toDelete = service.getOneOrder(userDate, orderNum);
        view.displayOrderToDelete(toDelete);
        boolean confirmDelete = view.confirmDelete();
        if (confirmDelete) {
            view.displayDeleteSuccess(toDelete);
            service.removeOrder(toDelete);
        } else {
            view.displayNotEdited(toDelete);
        }
    }
}
