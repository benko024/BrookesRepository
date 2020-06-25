/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;


import com.sg.flooringmastery.dto.Tax;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class FlooringMasteryTaxDaoTest {
    
//    FlooringMasteryTaxDao toTest = new FlooringMasteryTaxFileDao("tesTaxFile.txt");
    public FlooringMasteryTaxDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllStatesGoldenPath() throws FileNotFoundException {
        FlooringMasteryTaxDao toTest = new FlooringMasteryTaxFileDao("testTaxFile.txt");

        List<Tax> taxes = toTest.getTaxList();

        assertEquals(4, taxes.size());

        assertEquals("TX", taxes.get(0).getStateAbr());
        assertEquals(new BigDecimal("4.45"), taxes.get(0).getTaxRate());

        assertEquals("CA", taxes.get(3).getStateAbr());
        assertEquals(new BigDecimal("25.00"), taxes.get(3).getTaxRate());

    }

    @Test
    public void testGetTaxByAbbrevGoldenPath() throws FileNotFoundException {
        FlooringMasteryTaxDao toTest = new FlooringMasteryTaxFileDao("testTaxFile.txt");
        String toCheck = "TX";
        BigDecimal stateTax = toTest.getTaxRate(toCheck);
        
        assertEquals(new BigDecimal("4.45"), stateTax);
        
    }
}
