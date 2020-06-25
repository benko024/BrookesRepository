/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bkb
 */
public class FlooringMasteryInMemProductDao implements FlooringMasteryProductsDao {

    List<Product> allProducts = new ArrayList<>();

    public FlooringMasteryInMemProductDao() {

        Product carpet = new Product();
        carpet.setProductType("Carpet");
        carpet.setCostPerSqFt(new BigDecimal("2.25"));
        carpet.setLaborPerSqFt(new BigDecimal("2.10"));

        Product laminate = new Product();
        laminate.setProductType("Laminate");
        laminate.setCostPerSqFt(new BigDecimal("1.75"));
        laminate.setLaborPerSqFt(new BigDecimal("2.10"));

        Product tile = new Product();
        tile.setProductType("Tile");
        tile.setCostPerSqFt(new BigDecimal("3.50"));
        tile.setLaborPerSqFt(new BigDecimal("4.15"));
        
        Product wood = new Product();
        wood.setProductType("Wood");
        wood.setCostPerSqFt(new BigDecimal("5.15")); 
        wood.setLaborPerSqFt(new BigDecimal("4.75"));

        allProducts.add(carpet);
        allProducts.add(laminate);
        allProducts.add(tile);
        allProducts.add(wood);

    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryDaoException {
        return allProducts;
    }

    @Override
    public Product getProductByName(String productName) throws FlooringMasteryDaoException {
        return allProducts
                .stream()
                .filter(p -> p.getProductType() == productName)
                .findAny()
                .orElse(null);

    }

}
