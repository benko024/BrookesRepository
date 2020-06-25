/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author bkb
 */
public interface FlooringMasteryProductsDao {

    //**************************************************************************
    //*
    //*Display Product Options
    //*
    //**************************************************************************
    List<Product> getAllProducts() throws FlooringMasteryDaoException;

    Product getProductByName(String name) throws FlooringMasteryDaoException;
    
}
