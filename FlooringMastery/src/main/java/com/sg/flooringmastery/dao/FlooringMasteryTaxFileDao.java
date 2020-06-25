/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bkb
 */
public class FlooringMasteryTaxFileDao implements FlooringMasteryTaxDao {

    String path = "Taxes.txt";

    public FlooringMasteryTaxFileDao(String path) {
        this.path = path;
    }

    //**************************************************************************
    //*
    //*Display Tax
    //*
    //**************************************************************************
    @Override
    public List<Tax> getTaxList() throws FileNotFoundException {
        List<Tax> taxList = new ArrayList<>();

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
                    Tax toAdd = convertLineToTax(row);
                    taxList.add(toAdd);
                }

            }

        } catch (FileNotFoundException ex) {

        }
        return taxList;

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

    private Tax convertLineToTax(String row) {

        String[] cells = row.split(",");

        String StateAbbrev = cells[0];
        String stateName = cells[1];
        BigDecimal taxRate = new BigDecimal(cells[2]);

        Tax toAdd = new Tax();
        toAdd.setStateAbr(StateAbbrev);
        toAdd.setStateFullName(stateName);
        toAdd.setTaxRate(taxRate);

        return toAdd;
    }

}
