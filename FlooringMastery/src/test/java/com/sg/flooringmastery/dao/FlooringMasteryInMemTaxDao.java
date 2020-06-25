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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bkb
 */
public class FlooringMasteryInMemTaxDao implements FlooringMasteryTaxDao {

    List<Tax> allTaxes = new ArrayList<>();

    public FlooringMasteryInMemTaxDao() {

        Tax texas = new Tax();
        texas.setStateAbr("TX");
        texas.setStateFullName("Texas");
        texas.setTaxRate(new BigDecimal("4.45"));

        Tax washington = new Tax();
        washington.setStateAbr("WA");
        washington.setStateFullName("Washington");
        washington.setTaxRate(new BigDecimal("9.25"));

        Tax kentucky = new Tax();
        kentucky.setStateAbr("KY");
        kentucky.setStateFullName("Kentucky");
        kentucky.setTaxRate(new BigDecimal("6.00"));

        Tax california = new Tax();
        california.setStateAbr("CA");
        california.setStateFullName("California");
        california.setTaxRate(new BigDecimal("25.00"));

        allTaxes.add(texas);
        allTaxes.add(washington);
        allTaxes.add(kentucky);
        allTaxes.add(california);

    }

    @Override
    public List<Tax> getTaxList() throws FileNotFoundException {
        return allTaxes;
    }

    @Override
    public BigDecimal getTaxRate(String toCheck) throws FileNotFoundException {

        List<Tax> taxList = getTaxList();
        

        BigDecimal taxRate = new BigDecimal("0");
        for (int i = 0; i < taxList.size(); i++) {
            if (taxList.get(i).getStateAbr().equalsIgnoreCase(toCheck)
                    || taxList.get(i).getStateFullName().equalsIgnoreCase(toCheck)) {
                taxRate = taxList.get(i).getTaxRate();
            }
        }
        return taxRate;

    }

}
