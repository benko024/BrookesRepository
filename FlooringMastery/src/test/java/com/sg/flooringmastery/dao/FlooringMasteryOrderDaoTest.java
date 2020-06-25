/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
public class FlooringMasteryOrderDaoTest {

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        Path testFolderPath = Paths.get("TestOrders");
        Path seedFolderPath = Paths.get("SeedOrders");
        File testFolder = testFolderPath.toFile();
        File[] listOfFiles = testFolder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File toDelete = listOfFiles[i];
            toDelete.delete();
        }
        File seedFolder = seedFolderPath.toFile();
        File[] seedFileList = seedFolder.listFiles();
        for (int i = 0; i < seedFileList.length; i++) {
            File toCopy = seedFileList[i];
            Files.copy(toCopy.toPath(), Paths.get(testFolderPath.toString(), toCopy.getName()), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterEach
    public void tearDown() {
    }

//**************************************************************************
//* GetAllOrders()
//**************************************************************************
    @Test
    public void testGetAllOrdersGoldenPath() {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");

        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(1, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("George Washington", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("6.00"), allOrders.get(0).getTaxRate());

    }

    @Test
    public void testGetAllOrdersInvalidDate() {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1400, Month.MAY, 30);
        List<Order> allOrders = toTest.getAllOrders(userDate);
        assertTrue(allOrders.isEmpty());
    }

//**************************************************************************
//* AddOrder()
//**************************************************************************
    @Test
    public void testAddOrderGoldenPath() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidProductException, InvalidStateException, InvalidOrderException,
            InvalidAreaException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");

        LocalDate userDate = LocalDate.of(2025, Month.MAY, 30);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        Order toAdd = new Order();
        toAdd.setOrderDate(userDate);
        toAdd.setCustomerName("Maya Angelou");
        toAdd.setProductType("Carpet");
        toAdd.setArea(new BigDecimal("150"));
        toAdd.setState("TX");
        toAdd.setCostPerSqFt(new BigDecimal("2.25"));
        toAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        toAdd.setTaxRate(new BigDecimal("4.45"));

        toTest.addOrder(toAdd);
        List<Order> allOrdersAfterAdd = toTest.getAllOrders(userDate);

        assertEquals(allOrders.size() + 1, allOrdersAfterAdd.size());

        Order retrieved = toTest.getAllOrders(userDate).get(0);
        assertEquals("Maya Angelou", retrieved.getCustomerName());
        assertEquals(1, retrieved.getOrderNumber());
        assertEquals("Carpet", retrieved.getProductType());
        assertEquals(new BigDecimal("150"), retrieved.getArea());
        assertEquals("TX", retrieved.getState());
        assertEquals(new BigDecimal("2.25"), retrieved.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), retrieved.getLaborCostPerSqFt());
        assertEquals(new BigDecimal("4.45"), retrieved.getTaxRate());

        Order secondAdd = new Order();
        secondAdd.setOrderDate(userDate);
        secondAdd.setCustomerName("Wanda");
        secondAdd.setProductType("Carpet");
        secondAdd.setArea(new BigDecimal("120"));
        secondAdd.setState("TX");
        secondAdd.setCostPerSqFt(new BigDecimal("2.25"));
        secondAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        secondAdd.setTaxRate(new BigDecimal("4.45"));

        toTest.addOrder(secondAdd);
        allOrdersAfterAdd = toTest.getAllOrders(userDate);

        assertEquals(allOrders.size() + 2, allOrdersAfterAdd.size());

        retrieved = toTest.getAllOrders(userDate).get(1);
        assertEquals("Wanda", retrieved.getCustomerName());
        assertEquals(2, retrieved.getOrderNumber());
        assertEquals("Carpet", retrieved.getProductType());
        assertEquals(new BigDecimal("120"), retrieved.getArea());
        assertEquals("TX", retrieved.getState());
        assertEquals(new BigDecimal("2.25"), retrieved.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), retrieved.getLaborCostPerSqFt());
        assertEquals(new BigDecimal("4.45"), retrieved.getTaxRate());

    }

    @Test
    public void testAddOrderNullDate() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidCustomerException, InvalidProductException, InvalidStateException, InvalidOrderException,
            InvalidAreaException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = null;
        Order toAdd = new Order();
        toAdd.setOrderDate(userDate);
        toAdd.setCustomerName("Maya Angelou");
        toAdd.setProductType("Carpet");
        toAdd.setArea(new BigDecimal("150"));
        toAdd.setState("TX");
        toAdd.setCostPerSqFt(new BigDecimal("2.25"));
        toAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        toAdd.setTaxRate(new BigDecimal("4.45"));
        try {
            toTest.addOrder(toAdd);
            fail("Should not be able to add an order for a null date");
        } catch (InvalidDateException ex) {
        }
    }

    @Test
    public void testAddOrderNullCustomerName() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidProductException, InvalidStateException, InvalidOrderException, InvalidAreaException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);

        Order toAdd = new Order();
        toAdd.setOrderDate(userDate);
        toAdd.setCustomerName(null);
        toAdd.setProductType("Carpet");
        toAdd.setArea(new BigDecimal("150"));
        toAdd.setState("TX");
        toAdd.setCostPerSqFt(new BigDecimal("2.25"));
        toAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        toAdd.setTaxRate(new BigDecimal("4.45"));
        try {
            toTest.addOrder(toAdd);
            fail("Should not be able to add an order with a null customer name");
        } catch (InvalidCustomerException ex) {

        }
    }

    @Test
    public void testAddOrderBadCustomerName() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException,
            InvalidProductException, InvalidStateException, InvalidOrderException, InvalidAreaException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);

        Order toAdd = new Order();
        toAdd.setOrderDate(userDate);
        toAdd.setCustomerName("&**@(#$!@#45)");
        toAdd.setProductType("Carpet");
        toAdd.setArea(new BigDecimal("150"));
        toAdd.setState("TX");
        toAdd.setCostPerSqFt(new BigDecimal("2.25"));
        toAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        toAdd.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(toAdd);
            fail("Should not be able to add an order with a customer name containing those characters");
        } catch (InvalidCustomerException ex) {
        }
    }

    @Test
    public void testAddOrderEmptyName() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidProductException, InvalidStateException, InvalidOrderException,
            InvalidAreaException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);

        Order toAdd = new Order();
        toAdd.setOrderDate(userDate);
        toAdd.setCustomerName("");
        toAdd.setProductType("Carpet");
        toAdd.setArea(new BigDecimal("150"));
        toAdd.setState("TX");
        toAdd.setCostPerSqFt(new BigDecimal("2.25"));
        toAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        toAdd.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(toAdd);
            fail("Should not be able to add an order with a customer name containing those characters");
        } catch (InvalidCustomerException ex) {
        }

    }

    @Test
    public void testAddOrderNullProduct() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidCustomerException, InvalidStateException, InvalidOrderException,
            InvalidAreaException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        Order toAdd = new Order();
        toAdd.setOrderDate(userDate);
        toAdd.setCustomerName("Karl");
        toAdd.setProductType(null);
        toAdd.setArea(new BigDecimal("150"));
        toAdd.setState("TX");
        toAdd.setCostPerSqFt(new BigDecimal("2.25"));
        toAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        toAdd.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(toAdd);
            fail("Should not be able to add a null product");
        } catch (InvalidProductException ex) {

        }
    }

    @Test
    public void testAddOrderEmptyProduct() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidCustomerException, InvalidStateException, InvalidOrderException,
            InvalidAreaException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        Order toAdd = new Order();
        toAdd.setOrderDate(userDate);
        toAdd.setCustomerName("Karl");
        toAdd.setProductType("");
        toAdd.setArea(new BigDecimal("150"));
        toAdd.setState("TX");
        toAdd.setCostPerSqFt(new BigDecimal("2.25"));
        toAdd.setLaborCostPerSqFt(new BigDecimal("2.10"));
        toAdd.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(toAdd);
            fail("Should not be able to add an empty product.");
        } catch (InvalidProductException ex) {

        }
    }

    @Test
    public void testAddOrderNullState() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidCustomerException, InvalidProductException, InvalidOrderException, InvalidAreaException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        Order nullState = new Order();
        nullState.setOrderDate(userDate);
        nullState.setCustomerName("Karl");
        nullState.setProductType("Carpet");
        nullState.setArea(new BigDecimal("150"));
        nullState.setState(null);
        nullState.setCostPerSqFt(new BigDecimal("2.25"));
        nullState.setLaborCostPerSqFt(new BigDecimal("2.10"));
        nullState.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(nullState);
            fail("Should not be able to add an order with a null state.");
        } catch (InvalidStateException ex) {

        }
    }

    @Test
    public void testAddOrderEmptyState() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidCustomerException, InvalidProductException, InvalidOrderException, InvalidAreaException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        Order nullState = new Order();
        nullState.setOrderDate(userDate);
        nullState.setCustomerName("Karl");
        nullState.setProductType("Carpet");
        nullState.setArea(new BigDecimal("150"));
        nullState.setState("");
        nullState.setCostPerSqFt(new BigDecimal("2.25"));
        nullState.setLaborCostPerSqFt(new BigDecimal("2.10"));
        nullState.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(nullState);
            fail("Should not be able to add an order with a null state.");
        } catch (InvalidStateException ex) {

        }
    }

    @Test
    public void testAddNullOrderObject() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidCustomerException, InvalidProductException, InvalidStateException,
            InvalidAreaException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        Order nullOrder = null;

        try {
            toTest.addOrder(nullOrder);
            fail("Should not be able to add a null order!");
        } catch (InvalidOrderException ex) {
        }

    }

    @Test
    public void testAddOrderNullArea() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidCustomerException, InvalidProductException, InvalidStateException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        Order nullArea = new Order();
        nullArea.setOrderDate(userDate);
        nullArea.setCustomerName("Karl");
        nullArea.setProductType("Carpet");
        nullArea.setArea(null);
        nullArea.setState("CA");
        nullArea.setCostPerSqFt(new BigDecimal("2.25"));
        nullArea.setLaborCostPerSqFt(new BigDecimal("2.10"));
        nullArea.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(nullArea);
        } catch (InvalidAreaException ex) {
        }
    }

    @Test
    public void testAddOrderNegativeArea() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException, InvalidCustomerException, InvalidProductException, InvalidStateException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1776, Month.JULY, 4);
        Order nullArea = new Order();
        nullArea.setOrderDate(userDate);
        nullArea.setCustomerName("Karl");
        nullArea.setProductType("Carpet");
        nullArea.setArea(new BigDecimal("-40"));
        nullArea.setState("CA");
        nullArea.setCostPerSqFt(new BigDecimal("2.25"));
        nullArea.setLaborCostPerSqFt(new BigDecimal("2.10"));
        nullArea.setTaxRate(new BigDecimal("4.45"));

        try {
            toTest.addOrder(nullArea);
        } catch (InvalidAreaException ex) {
        }
    }

//**************************************************************************
//* EditOrder()
//**************************************************************************
    @Test
    public void testEditOrderGoldenPath() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidStateException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Ryan Patrick O'Connor");
        toEdit.setState("WA");
        toEdit.setArea(new BigDecimal("169"));
        toEdit.setProductType("Laminate");
        toEdit.setTaxRate(new BigDecimal("9.25"));
        toEdit.setCostPerSqFt(new BigDecimal("1.75"));
        toEdit.setLaborCostPerSqFt(new BigDecimal("2.10"));

        toTest.editOrder(toEdit);

        Order retrieved = allOrders.get(0);

        assertEquals("Ryan Patrick O'Connor", retrieved.getCustomerName());
        assertEquals("WA", retrieved.getState());
        assertEquals(new BigDecimal("169"), retrieved.getArea());
        assertEquals("Laminate", retrieved.getProductType());
        assertEquals(new BigDecimal("9.25"), retrieved.getTaxRate());
        assertEquals(new BigDecimal("1.75"), retrieved.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), retrieved.getLaborCostPerSqFt());

        Order secondEdit = allOrders.get(1);
        assertEquals("Julie O'Connor", secondEdit.getCustomerName());

        secondEdit.setOrderDate(userDate);
        secondEdit.setCustomerName("Julie O");
        secondEdit.setState("WA");
        secondEdit.setArea(new BigDecimal("300"));
        secondEdit.setProductType("Carpet");
        secondEdit.setTaxRate(new BigDecimal("9.25"));
        secondEdit.setCostPerSqFt(new BigDecimal("2.25"));
        secondEdit.setLaborCostPerSqFt(new BigDecimal("2.10"));

        toTest.editOrder(secondEdit);

        Order secondRetrieved = allOrders.get(1);

        assertEquals("Julie O", secondRetrieved.getCustomerName());
        assertEquals("WA", secondRetrieved.getState());
        assertEquals(new BigDecimal("300"), secondRetrieved.getArea());
        assertEquals("Carpet", secondRetrieved.getProductType());
        assertEquals(new BigDecimal("9.25"), secondRetrieved.getTaxRate());
        assertEquals(new BigDecimal("2.25"), secondRetrieved.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), secondRetrieved.getLaborCostPerSqFt());

    }

    @Test
    public void testEditOrderNullDate() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidOrderNumException, InvalidCustomerException,
            InvalidStateException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());

        toEdit.setOrderDate(null);
        toEdit.setCustomerName("Ryan Patrick O'Connor");
        toEdit.setState("WA");

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidDateException");
        } catch (InvalidDateException ex) {

        }

    }

    @Test
    public void testEditOrderNegativeOrderNum() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidCustomerException,
            InvalidStateException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderNumber(-1);
        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Ryan Patrick O'Connor");
        toEdit.setState("WA");

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidOrderNumException");
        } catch (InvalidOrderNumException ex) {

        }
    }

    @Test
    public void testEditOrderNullCustomerName() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidStateException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName(null);
        toEdit.setState("WA");

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidCustomerException");
        } catch (InvalidCustomerException ex) {

        }
    }

    @Test
    public void testEditOrderEmptyCustomerName() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidStateException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("");
        toEdit.setState("WA");

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidCustomerException");
        } catch (InvalidCustomerException ex) {

        }
    }

    @Test
    public void testEditOrderBadCustomerName() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidStateException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("&#$*)@$)!_@#()^^#@*$");
        toEdit.setState("WA");

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidCustomerException");
        } catch (InvalidCustomerException ex) {

        }

    }

    @Test
    public void testEditOrderNullState() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Sarah");
        toEdit.setState(null);

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidCustomerException");
        } catch (InvalidStateException ex) {

        }

    }

    @Test
    public void testEditOrderEmptyState() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidProductException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Sarah");
        toEdit.setState("");

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidCustomerException");
        } catch (InvalidStateException ex) {

        }
    }

    @Test
    public void testEditOrderNullProduct() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidStateException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Sarah");
        toEdit.setState("WA");
        toEdit.setProductType(null);

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidProductException");
        } catch (InvalidProductException ex) {

        }

    }

    @Test
    public void testEditOrderEmptyProduct() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidStateException, InvalidAreaException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Sarah");
        toEdit.setState("WA");
        toEdit.setProductType("");

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidProductException");
        } catch (InvalidProductException ex) {

        }
    }

    @Test
    public void testEditOrderNullArea() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidProductException, InvalidStateException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Sarah");
        toEdit.setState("WA");
        toEdit.setProductType("Carpet");
        toEdit.setArea(null);

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidAreaException");
        } catch (InvalidAreaException ex) {
        }
    }

    @Test
    public void testEditOrderNegativeArea() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException, InvalidOrderNumException,
            InvalidCustomerException, InvalidStateException, InvalidProductException, InvalidOrderException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1996, Month.OCTOBER, 5);

        List<Order> allOrders = toTest.getAllOrders(userDate);

        assertEquals(3, allOrders.size());
        assertEquals(1, allOrders.get(0).getOrderNumber());
        assertEquals("Ryan O'Connor", allOrders.get(0).getCustomerName());
        assertEquals(new BigDecimal("25.00"), allOrders.get(0).getTaxRate());

        Order toEdit = allOrders.get(0);

        assertEquals("Ryan O'Connor", toEdit.getCustomerName());
        assertEquals(1, toEdit.getOrderNumber());

        toEdit.setOrderDate(userDate);
        toEdit.setCustomerName("Sarah");
        toEdit.setState("WA");
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("-100"));

        try {
            toTest.editOrder(toEdit);
            fail("Should hit InvalidAreaException");
        } catch (InvalidAreaException ex) {
        }

    }

//**************************************************************************
//* RemoveOrder()
//**************************************************************************
    @Test
    public void testRemoveOrderGoldenPath() throws FlooringMasteryDaoNoFileException, NewFileException, InvalidDateException,
            InvalidOrderNumException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1997, Month.MAY, 7);

        List<Order> allOrders = toTest.getAllOrders(userDate);
        allOrders.get(0).setOrderDate(userDate);

        Order toDelete = allOrders.get(0);

        assertEquals(3, allOrders.size());
        assertEquals(LocalDate.of(1997, Month.MAY, 7), toDelete.getOrderDate());
        assertEquals(1, toDelete.getOrderNumber());
        assertEquals("Emma Watson", toDelete.getCustomerName());
        assertEquals(new BigDecimal("9.25"), toDelete.getTaxRate());
        assertEquals("WA", toDelete.getState());
        assertEquals("Laminate", toDelete.getProductType());

        toTest.deleteOrder(toDelete);

        List<Order> retrieved = toTest.getAllOrders(userDate);

        assertEquals(2, retrieved.size());
        assertFalse(retrieved.contains(toDelete));

    }

    @Test
    public void testRemoveOrderNullDate() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidOrderNumException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1997, Month.MAY, 7);

        List<Order> allOrders = toTest.getAllOrders(userDate);
        allOrders.get(0).setOrderDate(null);

        Order toDelete = allOrders.get(0);

        assertEquals(3, allOrders.size());
        assertEquals(1, toDelete.getOrderNumber());
        assertEquals("Emma Watson", toDelete.getCustomerName());
        assertEquals(new BigDecimal("9.25"), toDelete.getTaxRate());
        assertEquals("WA", toDelete.getState());
        assertEquals("Laminate", toDelete.getProductType());

        try {
            toTest.deleteOrder(toDelete);
        } catch (InvalidDateException ex) {

        }
    }

    @Test
    public void testRemoveOrderNegativeOrderNum() throws FlooringMasteryDaoNoFileException, NewFileException,
            InvalidDateException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1997, Month.MAY, 7);

        List<Order> allOrders = toTest.getAllOrders(userDate);
        allOrders.get(0).setOrderDate(LocalDate.of(1997, Month.MAY, 7));

        Order toDelete = allOrders.get(0);
        toDelete.setOrderNumber(-1);

        assertEquals(3, allOrders.size());

        assertEquals("Emma Watson", toDelete.getCustomerName());
        assertEquals(new BigDecimal("9.25"), toDelete.getTaxRate());
        assertEquals("WA", toDelete.getState());
        assertEquals("Laminate", toDelete.getProductType());

        try {
            toTest.deleteOrder(toDelete);
        } catch (InvalidOrderNumException ex) {

        }
    }

//**************************************************************************
//* GetONeOrder()
//**************************************************************************
    @Test
    public void testGetOneOrder() throws InvalidOrderNumException, InvalidDateException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1997, Month.MAY, 7);
        List<Order> orderList = toTest.getAllOrders(userDate);

        Order firstToGet = toTest.getOneOrder(userDate, 1);

        assertEquals(1, firstToGet.getOrderNumber());
        assertEquals("Emma Watson", firstToGet.getCustomerName());
        assertEquals(new BigDecimal("9.25"), firstToGet.getTaxRate());
        assertEquals("WA", firstToGet.getState());
        assertEquals("Laminate", firstToGet.getProductType());

    }

    //15
    @Test
    public void testGetOneOrderNullDate() throws InvalidOrderNumException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1997, Month.MAY, 7);

        try {
            toTest.getOneOrder(null, 1);
        } catch (InvalidDateException ex) {

        }

    }

    //16
    @Test
    public void testGetOneOrderNegativeOrderNum() throws InvalidDateException {

        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1997, Month.MAY, 7);

        try {
            toTest.getOneOrder(userDate, -1);
            fail("You should not be able to get an invalid order.");
        } catch (InvalidOrderNumException ex) {

        }
    }

    @Test
    public void testGetOneOrderInvalidOrderNum() throws InvalidDateException {
        FlooringMasteryOrderDao toTest = new FlooringMasteryOrderFileDao("TestOrders");
        LocalDate userDate = LocalDate.of(1997, Month.MAY, 7);
       
        
        try {
            toTest.getOneOrder(userDate, 100);
            fail("You should not be able to get an invalid order.");
        } catch (InvalidOrderNumException ex) {
            
        }
    }

}
