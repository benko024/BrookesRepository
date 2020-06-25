/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkb
 */
public class FlooringMasteryProductsFileDao implements FlooringMasteryProductsDao {

    String path;

    public FlooringMasteryProductsFileDao(String path) {
        this.path = path;
    }

    //**************************************************************************
    //*
    //*Display Product Options
    //*
    //**************************************************************************
    @Override
    public List<Product> getAllProducts() throws FlooringMasteryDaoException {
        List<Product> allProducts = new ArrayList<>();
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
                    Product toAdd = convertLineToProduct(row);
                    allProducts.add(toAdd);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new FlooringMasteryDaoException("The file could not be found.");
        }
        return allProducts;
    }

    @Override
    public Product getProductByName(String name) throws FlooringMasteryDaoException {
        return getAllProducts().stream()
                .filter(p -> p.getProductType().equalsIgnoreCase(name)).findAny().orElse(null);
    }
    

    private Product convertLineToProduct(String row) {

        String[] cells = row.split(",");

        String ProductType = cells[0];
        BigDecimal costPSqFt = new BigDecimal(cells[1]);
        BigDecimal laborCostPSqFt = new BigDecimal(cells[2]);

        Product toAdd = new Product();
        toAdd.setProductType(ProductType);
        toAdd.setCostPerSqFt(costPSqFt);
        toAdd.setLaborPerSqFt(laborCostPSqFt);

        return toAdd;
    }
}
