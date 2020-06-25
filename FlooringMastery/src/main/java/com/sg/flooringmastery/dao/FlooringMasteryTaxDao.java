/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Tax;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author bkb
 */
public interface FlooringMasteryTaxDao {

    //**************************************************************************
    //*
    //*Display Tax
    //*
    //**************************************************************************
    List<Tax> getTaxList() throws FileNotFoundException;

    BigDecimal getTaxRate(String toCheck) throws FileNotFoundException;
    
}
