/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.InvalidItemException;
import com.sg.vendingmachine.service.NoItemsLeftException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class VendingMachineDaoFileImplTest {

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {

        Path testPath = Paths.get("testData", "test.txt");
        Path seedPath = Paths.get("testData", "seed.txt");

        File testFile = testPath.toFile();
        //File seedFile = seedPath.toFile();

        testFile.delete();
        Files.copy(seedPath, testPath);

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void getAllItemsGoldenPath() {

        VendingMachineDaoFileImpl toTest = new VendingMachineDaoFileImpl(Paths.get("testData", "test.txt").toString());
        List<Item> allItems = toTest.getAllItems();

        assertEquals(4, allItems.size());

        int testItemIdOne = toTest.getAllItems().get(0).getItemId();
        int testItemIdTwo = toTest.getAllItems().get(1).getItemId();
        int testItemIdThree = toTest.getAllItems().get(2).getItemId();

        assertEquals(1, testItemIdOne);
        assertEquals(2, testItemIdTwo);
        assertEquals(3, testItemIdThree);

    }

    @Test
    public void checkCostGoldenPath() {

        VendingMachineDaoFileImpl toTest = new VendingMachineDaoFileImpl(Paths.get("testData", "test.txt").toString());
        List<Item> allItems = toTest.getAllItems();

        assertEquals(4, allItems.size());

        BigDecimal testItemIdOne = toTest.getAllItems().get(0).getItemCost();
        BigDecimal testItemIdTwo = toTest.getAllItems().get(1).getItemCost();

        assertEquals(new BigDecimal("1.35"), testItemIdOne);
        assertEquals(new BigDecimal("1.40"), testItemIdTwo);

    }

    @Test
    public void reduceItemByOneGoldenPath() throws NoItemsLeftException, NoExistingFileException, InvalidItemException {
        VendingMachineDaoFileImpl toTest = new VendingMachineDaoFileImpl(Paths.get("testData", "test.txt").toString());
        List<Item> allItems = toTest.getAllItems();

        Item toCheck = allItems.get(0);

        assertEquals(10, toCheck.getNumItemAvail());
        toCheck.setNumItemAvail((toCheck.getNumItemAvail()) - 1);
        toTest.reduceItemByOne(toCheck);

        assertEquals(9, toCheck.getNumItemAvail());

    }

    @Test
    public void reduceItemByOneNoItemsLeft() throws NoExistingFileException, IndexOutOfBoundsException, InvalidItemException {
        try {
            VendingMachineDaoFileImpl toTest = new VendingMachineDaoFileImpl(Paths.get("testData", "test.txt").toString());
            List<Item> allItems = toTest.getAllItems();

            Item itemToTest = allItems.get(3);

            assertEquals(0, itemToTest.getNumItemAvail());
            itemToTest.setNumItemAvail((itemToTest.getNumItemAvail()) - 1);
            toTest.reduceItemByOne(itemToTest);

            fail("Should hit a NoItemsLeftException!");
        } catch (NoItemsLeftException ex) {

        }

    }

    @Test
    public void searchbyIdGoldenPath() {
        VendingMachineDaoFileImpl toTest = new VendingMachineDaoFileImpl(Paths.get("testData", "test.txt").toString());
        List<Item> allItems = toTest.getAllItems();

      

        int testItemIdOne = toTest.getAllItems().get(0).getItemId();
        int testItemIdTwo = toTest.getAllItems().get(1).getItemId();


        assertEquals(1, testItemIdOne);
        assertEquals(2, testItemIdTwo);

    }

}
