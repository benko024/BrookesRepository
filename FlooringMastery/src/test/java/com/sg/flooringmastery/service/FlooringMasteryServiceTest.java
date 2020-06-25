/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.InvalidAreaException;
import com.sg.flooringmastery.dao.InvalidStateException;
import com.sg.flooringmastery.dao.InvalidProductException;
import com.sg.flooringmastery.dao.InvalidCustomerException;
import com.sg.flooringmastery.dao.InvalidOrderNumException;
import com.sg.flooringmastery.dao.InvalidDateException;
import com.sg.flooringmastery.dao.FlooringMasteryDaoException;
import com.sg.flooringmastery.dao.FlooringMasteryDaoNoFileException;
import com.sg.flooringmastery.dao.FlooringMasteryInMemOrderDao;
import com.sg.flooringmastery.dao.FlooringMasteryInMemProductDao;
import com.sg.flooringmastery.dao.FlooringMasteryInMemTaxDao;
import com.sg.flooringmastery.dao.FlooringMasteryOrderDao;
import com.sg.flooringmastery.dao.FlooringMasteryProductsDao;
import com.sg.flooringmastery.dao.FlooringMasteryTaxDao;
import com.sg.flooringmastery.dao.InvalidOrderException;
import com.sg.flooringmastery.dao.NewFileException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
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
public class FlooringMasteryServiceTest {

    public FlooringMasteryServiceTest() {
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

//************************************************************************** 
//*Test getOrderList
//**************************************************************************
    //1
    @Test
    public void testGetOrderListGoldenPath() throws FlooringMasteryDaoNoFileException, NewFileException {

        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        toTest.getOrderList(LocalDate.now().plusDays(1));
        List<Order> retrieved = toTest.getOrderList(LocalDate.now().plusDays(1));

        assertEquals(1, retrieved.get(0).getOrderNumber());
        assertEquals("Rosa Parks", retrieved.get(0).getCustomerName());
        assertEquals("KY", retrieved.get(0).getState());
        assertEquals(new BigDecimal("6.00"), retrieved.get(0).getTaxRate());
        assertEquals("Carpet", retrieved.get(0).getProductType());
        assertEquals(new BigDecimal("110"), retrieved.get(0).getArea());
        assertEquals(new BigDecimal("2.25"), retrieved.get(0).getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), retrieved.get(0).getLaborCostPerSqFt());
        assertEquals(new BigDecimal("247.50"), retrieved.get(0).getMaterialCost());
        assertEquals(new BigDecimal("231.00"), retrieved.get(0).getLaborCost());
        assertEquals(new BigDecimal("28.71"), retrieved.get(0).getTax());
        assertEquals(new BigDecimal("507.21"), retrieved.get(0).getTotal());

        assertEquals(2, retrieved.get(1).getOrderNumber());
        assertEquals("Amy Adams", retrieved.get(1).getCustomerName());
        assertEquals("CA", retrieved.get(1).getState());
        assertEquals(new BigDecimal("25.00"), retrieved.get(1).getTaxRate());
        assertEquals("Tile", retrieved.get(1).getProductType());
        assertEquals(new BigDecimal("150"), retrieved.get(1).getArea());
        assertEquals(new BigDecimal("3.50"), retrieved.get(1).getCostPerSqFt());
        assertEquals(new BigDecimal("4.15"), retrieved.get(1).getLaborCostPerSqFt());
        assertEquals(new BigDecimal("525.00"), retrieved.get(1).getMaterialCost());
        assertEquals(new BigDecimal("622.50"), retrieved.get(1).getLaborCost());
        assertEquals(new BigDecimal("286.88"), retrieved.get(1).getTax());
        assertEquals(new BigDecimal("1434.38"), retrieved.get(1).getTotal());

    }

//************************************************************************** 
//*Test editOrder
//**************************************************************************
    //12
    @Test
    public void testEditOrderGoldenPath() throws FileNotFoundException, FlooringMasteryDaoException, OrderEditException, InvalidOrderNumException, FlooringMasteryDaoNoFileException, InvalidCustomerException, InvalidStateException, InvalidProductException, NewFileException, InvalidAreaException, InvalidDateException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        Order toEdit = orderDao.getOneOrder(LocalDate.now().plusDays(1), 3);

        assertEquals("Harry Potter", toEdit.getCustomerName());
        assertEquals("TX", toEdit.getState());
        assertEquals("Tile", toEdit.getProductType());
        assertEquals(new BigDecimal("150"), toEdit.getArea());

        toEdit.setCustomerName("Hermione Granger");
        toEdit.setState("CA");
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("112"));
        toTest.editOrder(toEdit);

        assertEquals("Hermione Granger", toEdit.getCustomerName());
        assertEquals("CA", toEdit.getState());
        assertEquals("Carpet", toEdit.getProductType());
        assertEquals(new BigDecimal("112"), toEdit.getArea());
        assertEquals(new BigDecimal("2.25"), toEdit.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), toEdit.getLaborCostPerSqFt());
        assertEquals(new BigDecimal("25.00"), toEdit.getTaxRate());
        assertEquals(new BigDecimal("252.00"), toEdit.getMaterialCost());
        assertEquals(new BigDecimal("235.20"), toEdit.getLaborCost());
        assertEquals(new BigDecimal("121.80"), toEdit.getTax());
        assertEquals(new BigDecimal("609.00"), toEdit.getTotal());
    }

    //13
    @Test
    public void testEditOrderNullCustName() throws FileNotFoundException, FlooringMasteryDaoException, FlooringMasteryDaoNoFileException, NewFileException, OrderEditException, InvalidOrderNumException, InvalidStateException, InvalidProductException, InvalidAreaException, InvalidDateException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));
        Order toEdit = orderList.get(2);
        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName(null);
        toEdit.setState("CA");
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("112"));
        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");
        } catch (InvalidCustomerException ex) {
        }
    }

    //14
    @Test
    public void testEditOrderInvalidName() throws FlooringMasteryDaoException, FileNotFoundException, FlooringMasteryDaoNoFileException, NewFileException, OrderEditException, InvalidOrderNumException, InvalidStateException, InvalidProductException, InvalidAreaException, InvalidDateException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));
        Order toEdit = orderList.get(2);
        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName("@#)($@#)");
        toEdit.setState("CA");
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("112"));
        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");
        } catch (InvalidCustomerException ex) {

        }

    }

    //15
    @Test
    public void testEditOrderNullState() throws FlooringMasteryDaoNoFileException, NewFileException, FileNotFoundException, FlooringMasteryDaoException, OrderEditException, InvalidOrderNumException, InvalidCustomerException, InvalidProductException, InvalidAreaException, InvalidDateException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));
        Order toEdit = orderList.get(2);
        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName("Auli'i Cravalho");
        toEdit.setState(null);
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("112"));
        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");
        } catch (InvalidStateException ex) {
        }
    }

    //16
    @Test
    public void testEditOrderStateNotInList() throws FileNotFoundException, FlooringMasteryDaoException, FlooringMasteryDaoNoFileException, NewFileException, OrderEditException, InvalidOrderNumException, InvalidCustomerException, InvalidProductException, InvalidDateException, InvalidAreaException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));
        Order toEdit = orderList.get(2);
        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName("Auli'i Cravalho");
        toEdit.setState("HI");
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("112"));

        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");

        } catch (InvalidStateException ex) {

        }
    }

    //17
    @Test
    public void testEditOrderAreaTooSmall() throws FlooringMasteryDaoNoFileException, NewFileException, FileNotFoundException, FlooringMasteryDaoException, OrderEditException, InvalidOrderNumException, InvalidCustomerException, InvalidStateException, InvalidProductException, InvalidDateException, InvalidAreaException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));
        Order toEdit = orderList.get(2);
        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName("Auli'i Cravalho");
        toEdit.setState("CA");
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("5"));
        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");
        } catch (InvalidAreaException ex) {
        }
    }

    //18
    @Test
    public void testEditOrderNegativeArea() throws FlooringMasteryDaoNoFileException, NewFileException, FileNotFoundException, FlooringMasteryDaoException, OrderEditException, InvalidOrderNumException, InvalidCustomerException, InvalidStateException, InvalidProductException, InvalidDateException, InvalidAreaException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));
        Order toEdit = orderList.get(2);
        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName("Auli'i Cravalho");
        toEdit.setState("CA");
        toEdit.setProductType("Carpet");
        toEdit.setArea(new BigDecimal("-500"));
        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");
        } catch (InvalidAreaException ex) {

        }
    }

    //19
    @Test
    public void testEditOrderNullProduct() throws FileNotFoundException, FlooringMasteryDaoException, FlooringMasteryDaoNoFileException, NewFileException, OrderEditException, InvalidOrderNumException, InvalidCustomerException, InvalidStateException, InvalidAreaException, InvalidDateException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        List<Product> productList = toTest.getProductList();

        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));

        Order toEdit = orderList.get(2);

        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName("Auli'i Cravalho");
        toEdit.setState("CA");
        toEdit.setProductType(null);
        toEdit.setArea(new BigDecimal("200"));

        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");

        } catch (InvalidProductException ex) {

        }
    }

    //20
    @Test
    public void testEditOrderInvalidProduct() throws FileNotFoundException, FlooringMasteryDaoException, FlooringMasteryDaoNoFileException, NewFileException, OrderEditException, InvalidCustomerException, InvalidOrderNumException, InvalidStateException, InvalidAreaException, InvalidDateException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        List<Product> productList = toTest.getProductList();

        List<Order> orderList = orderDao.getAllOrders(LocalDate.now().plusDays(1));

        Order toEdit = orderList.get(2);

        toEdit.setOrderDate(LocalDate.now().plusDays(1));
        toEdit.setCustomerName("Auli'i Cravalho");
        toEdit.setState("CA");
        toEdit.setProductType("trampoline");
        toEdit.setArea(new BigDecimal("200"));

        try {
            toEdit = toTest.editOrder(toEdit);
            fail("You should not be able to add a customer with a null name.");

        } catch (InvalidProductException ex) {

        }
    }

////************************************************************************** 
////*Test deleteOrder
////**************************************************************************
    @Test
    public void testDeleteOrderGoldenPath() throws FlooringMasteryDaoNoFileException, NewFileException, FileNotFoundException, FlooringMasteryDaoException, InvalidDateException, InvalidOrderNumException {

        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        
        LocalDate userDate = LocalDate.now().plusDays(1);
        toTest.getOrderList(userDate);
        List<Order> retrieved = toTest.getOrderList(userDate);

        Order toDelete = retrieved.get(2);
        assertEquals("Harry Potter", toDelete.getCustomerName());
        toTest.removeOrder(toDelete);
        assertFalse(retrieved.contains(toDelete));

    }


    //
    //*If I have time, I should add more tests to delete.
    //
   
////************************************************************************** 
////*Test addOrder
////**************************************************************************
    //2
    @Test
    public void testAddOrderGoldenPath() throws FileNotFoundException, FlooringMasteryDaoException, InvalidDateException, InvalidCustomerException, InvalidStateException, NewFileException, InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {

        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        toTest.getOrderList(LocalDate.now().plusDays(1));
        List<Order> retrieved = toTest.getOrderList(LocalDate.now().plusDays(1));

        List<Product> productList = toTest.getProductList();

        Order firstAddition = new Order();

        firstAddition.setOrderDate(LocalDate.now().plusDays(1));
        firstAddition.setCustomerName("Pam Halpert");
        firstAddition.setState("CA");
        firstAddition.setProductType("Carpet");
        firstAddition.setArea(new BigDecimal("130"));
        firstAddition.setCostPerSqFt(new BigDecimal("2.25"));
        firstAddition.setLaborCostPerSqFt(new BigDecimal("2.10"));
        firstAddition.setTaxRate(new BigDecimal("25.00"));
        firstAddition.getMaterialCost();
        firstAddition.getLaborCost();
        firstAddition.getTax();
        firstAddition.getTotal();

        Order secondAddition = new Order();
        secondAddition.setOrderDate(LocalDate.now().plusDays(1));
        secondAddition.setCustomerName("Dwight Schrute");
        secondAddition.setState("TX");
        secondAddition.setProductType("Carpet");
        secondAddition.setArea(new BigDecimal("130"));
        secondAddition.setCostPerSqFt(new BigDecimal("2.25"));
        secondAddition.setLaborCostPerSqFt(new BigDecimal("2.10"));
        secondAddition.setTaxRate(new BigDecimal("4.45"));
        secondAddition.getMaterialCost();
        secondAddition.getLaborCost();
        secondAddition.getTax();
        secondAddition.getTotal();

        firstAddition = toTest.addOrder(productList, firstAddition);
        secondAddition = toTest.addOrder(productList, secondAddition);

        assertEquals(4, firstAddition.getOrderNumber());
        assertEquals("Pam Halpert", firstAddition.getCustomerName());
        assertEquals("CA", firstAddition.getState());
        assertEquals(new BigDecimal("25.00"), firstAddition.getTaxRate());
        assertEquals("Carpet", firstAddition.getProductType());
        assertEquals(new BigDecimal("130"), firstAddition.getArea());
        assertEquals(new BigDecimal("2.25"), firstAddition.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), firstAddition.getLaborCostPerSqFt());
        assertEquals(new BigDecimal("292.50"), firstAddition.getMaterialCost());
        assertEquals(new BigDecimal("273.00"), firstAddition.getLaborCost());
        assertEquals(new BigDecimal("141.38"), firstAddition.getTax());
        assertEquals(new BigDecimal("706.88"), firstAddition.getTotal());

        assertEquals(5, secondAddition.getOrderNumber());
        assertEquals("Dwight Schrute", secondAddition.getCustomerName());
        assertEquals("TX", secondAddition.getState());
        assertEquals(new BigDecimal("4.45"), secondAddition.getTaxRate());
        assertEquals("Carpet", secondAddition.getProductType());
        assertEquals(new BigDecimal("130"), secondAddition.getArea());
        assertEquals(new BigDecimal("2.25"), secondAddition.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), secondAddition.getLaborCostPerSqFt());
        assertEquals(new BigDecimal("292.50"), secondAddition.getMaterialCost());
        assertEquals(new BigDecimal("273.00"), secondAddition.getLaborCost());
        assertEquals(new BigDecimal("25.16"), secondAddition.getTax());
        assertEquals(new BigDecimal("590.66"), secondAddition.getTotal());

    }

    //3
    @Test
    public void testAddOrderAddDateInPast() throws FileNotFoundException, FlooringMasteryDaoException, InvalidStateException, InvalidCustomerException, NewFileException, InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        List<Product> productList = toTest.getProductList();

        Order dateInPast = new Order();

        dateInPast.setOrderDate(LocalDate.of(1400, Month.MARCH, 28));
        dateInPast.setCustomerName("Pam Halpert");
        dateInPast.setState("CA");
        dateInPast.setProductType("Carpet");
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a date in the past.");
        } catch (InvalidDateException ex) {
        }

    }

    //4
    @Test
    public void testAddOrderInvalidCustomerName() throws FileNotFoundException, FlooringMasteryDaoException, InvalidStateException, NewFileException, InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidDateException, InvalidOrderException {

        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        List<Product> productList = toTest.getProductList();

        Order dateInPast = new Order();

        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("Andafs&F*(@");
        dateInPast.setState("CA");
        dateInPast.setProductType("Carpet");
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidCustomerException ex) {

        }
    }

    //5
    @Test
    public void testAddOrderNullCustomerName() throws FlooringMasteryDaoException, FileNotFoundException, InvalidDateException, InvalidStateException, NewFileException, InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        Order dateInPast = new Order();
        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName(null);
        dateInPast.setState("CA");
        dateInPast.setProductType("Carpet");
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidCustomerException ex) {

        }
    }

    @Test
    public void testAddOrderEmptyName() throws InvalidDateException, InvalidStateException, FileNotFoundException, NewFileException, InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, FlooringMasteryDaoException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        Order dateInPast = new Order();
        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("");
        dateInPast.setState("CA");
        dateInPast.setProductType("Carpet");
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("You can't add a customer with an empty name.");
        } catch (InvalidCustomerException ex) {

        }
    }

    //6
    @Test
    public void testAddOrderNullState() throws FileNotFoundException, FlooringMasteryDaoException, InvalidDateException, InvalidCustomerException, NewFileException, InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        Order dateInPast = new Order();
        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("Emma Watson");
        dateInPast.setState(null);
        dateInPast.setProductType("Carpet");
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidStateException ex) {

        }

    }

    //7
    @Test
    public void testAddOrderStateNotInList() throws FileNotFoundException, FlooringMasteryDaoException, InvalidDateException, InvalidCustomerException, NewFileException, InvalidProductException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        Order dateInPast = new Order();
        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("Emma Watson");
        dateInPast.setState("HI");
        dateInPast.setProductType("Carpet");
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidStateException ex) {

        }
    }

    //8
    @Test
    public void testAddOrderNullProduct() throws FileNotFoundException, FlooringMasteryDaoException, InvalidDateException, InvalidCustomerException, InvalidStateException, NewFileException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);
        List<Product> productList = toTest.getProductList();
        Order dateInPast = new Order();
        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("Emma Watson");
        dateInPast.setState("KY");
        dateInPast.setProductType(null);
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidProductException ex) {

        }
    }

    //9
    @Test
    public void testAddOrderProductNotInList() throws FileNotFoundException, FlooringMasteryDaoException, InvalidDateException, InvalidCustomerException, InvalidStateException, NewFileException, InvalidAreaException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        List<Product> productList = toTest.getProductList();

        Order dateInPast = new Order();

        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("Emma Watson");
        dateInPast.setState("KY");
        dateInPast.setProductType("trampoline");
        dateInPast.setArea(new BigDecimal("130"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidProductException ex) {

        }
    }

    //10
    @Test
    public void testAddOrderAreaTooSmall() throws FileNotFoundException, FlooringMasteryDaoException, InvalidDateException, InvalidCustomerException, InvalidStateException, NewFileException, InvalidProductException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        List<Product> productList = toTest.getProductList();

        Order dateInPast = new Order();

        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("Emma Watson");
        dateInPast.setState("KY");
        dateInPast.setProductType("carpet");
        dateInPast.setArea(new BigDecimal("-100"));
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));
        dateInPast.getMaterialCost();
        dateInPast.getLaborCost();
        dateInPast.getTax();
        dateInPast.getTotal();

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidAreaException ex) {

        }
    }

    //11
    @Test
    public void testAddOrderNullArea() throws FlooringMasteryDaoException, FileNotFoundException, InvalidDateException, InvalidCustomerException, InvalidStateException, NewFileException, InvalidProductException, FlooringMasteryDaoNoFileException, InvalidOrderException {
        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();

        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        List<Product> productList = toTest.getProductList();

        Order dateInPast = new Order();

        dateInPast.setOrderDate(LocalDate.now().plusDays(1));
        dateInPast.setCustomerName("Emma Watson");
        dateInPast.setState("KY");
        dateInPast.setProductType("carpet");
        dateInPast.setArea(null);
        dateInPast.setCostPerSqFt(new BigDecimal("2.25"));
        dateInPast.setLaborCostPerSqFt(new BigDecimal("2.10"));
        dateInPast.setTaxRate(new BigDecimal("25.00"));

        try {
            toTest.addOrder(productList, dateInPast);
            fail("This should not allow you to add a customer with an invalid name.");
        } catch (InvalidAreaException ex) {

        }
    }

////************************************************************************** 
////*Test getOneOrder
////**************************************************************************
    //21
    @Test
    public void testGetOneOrderGoldenPath() throws FileNotFoundException, FlooringMasteryDaoException, InvalidOrderNumException, InvalidDateException {

        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        Order firstToGet = orderDao.getOneOrder(LocalDate.now().plusDays(1), 3);

        assertEquals("Harry Potter", firstToGet.getCustomerName());
        assertEquals("TX", firstToGet.getState());
        assertEquals("Tile", firstToGet.getProductType());
        assertEquals(new BigDecimal("150"), firstToGet.getArea());

        Order secondToGet = orderDao.getOneOrder(LocalDate.now().plusDays(1), 1);

        assertEquals(1, secondToGet.getOrderNumber());
        assertEquals("Rosa Parks", secondToGet.getCustomerName());
        assertEquals("KY", secondToGet.getState());
        assertEquals(new BigDecimal("6.00"), secondToGet.getTaxRate());
        assertEquals("Carpet", secondToGet.getProductType());
        assertEquals(new BigDecimal("110"), secondToGet.getArea());
        assertEquals(new BigDecimal("2.25"), secondToGet.getCostPerSqFt());
        assertEquals(new BigDecimal("2.10"), secondToGet.getLaborCostPerSqFt());
        assertEquals(new BigDecimal("247.50"), secondToGet.getMaterialCost());
        assertEquals(new BigDecimal("231.00"), secondToGet.getLaborCost());
        assertEquals(new BigDecimal("28.71"), secondToGet.getTax());
        assertEquals(new BigDecimal("507.21"), secondToGet.getTotal());

    }

    //22
    @Test
    public void testGetOneOrderInvalidOrderNum() throws FileNotFoundException, FlooringMasteryDaoException, InvalidDateException {

        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        try {
            Order firstToGet = orderDao.getOneOrder(LocalDate.now().plusDays(1), -1);
        } catch (InvalidOrderNumException ex) {

        }
    }

    //23
    @Test
    public void testGetOneOrderNullDate() throws FileNotFoundException, FlooringMasteryDaoException, InvalidOrderNumException {

        FlooringMasteryOrderDao orderDao = new FlooringMasteryInMemOrderDao();
        FlooringMasteryTaxDao taxDao = new FlooringMasteryInMemTaxDao();
        FlooringMasteryProductsDao productDao = new FlooringMasteryInMemProductDao();
        FlooringMasteryService toTest = new FlooringMasteryService(orderDao, taxDao, productDao);

        try {
            Order firstToGet = orderDao.getOneOrder(null, 1);
        } catch (InvalidDateException ex) {

        }
    }

}
