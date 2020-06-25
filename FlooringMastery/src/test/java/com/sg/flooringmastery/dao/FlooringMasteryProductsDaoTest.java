/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bkb
 */
public class FlooringMasteryProductsDaoTest {

    public FlooringMasteryProductsDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllProductsGoldenPath() throws FlooringMasteryDaoException {
        FlooringMasteryProductsDao toTest = new FlooringMasteryProductsFileDao("testProductFile.txt");

        List<Product> products = toTest.getAllProducts();

        assertEquals(4, products.size());

        assertEquals("Carpet", products.get(0).getProductType());
        assertEquals(new BigDecimal("2.25"), products.get(0).getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), products.get(0).getLaborPerSqFt());

        assertEquals("Wood", products.get(3).getProductType());
        assertEquals(new BigDecimal("5.15"), products.get(3).getCostPerSqFt());
        assertEquals(new BigDecimal("4.75"), products.get(3).getLaborPerSqFt());

    }
    

    @Test
    public void testGetByNameGoldenPath() throws FlooringMasteryDaoException {
        FlooringMasteryProductsDao toTest = new FlooringMasteryProductsFileDao("testProductFile.txt");
        String toCheck = "Tile";
        Product stateTax = toTest.getProductByName(toCheck);

        assertEquals("Tile", stateTax.getProductType());

    }
    


}
