/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.NoExistingFileException;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineInMemDao;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author bkb
 */
public class VendingMachineServiceTest {

//    VendingMachineDao dao = new VendingMachineInMemDao();
//    VendingMachineService toTest = new VendingMachineService(dao);
    ApplicationContext ctx
            = new ClassPathXmlApplicationContext("applicationContext.xml");
    VendingMachineService service
            = ctx.getBean("serviceLayer", VendingMachineService.class);
    
    

    public VendingMachineServiceTest() {
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
    public void testVendItemGoldenPath() throws NegativeMoneyException,
            NoItemsLeftException,
            NotEnoughMoneyException,
            InvalidItemException,
            NoExistingFileException {

    VendingMachineDao dao = new VendingMachineInMemDao();
    VendingMachineService toTest = new VendingMachineService(dao);

        
        BigDecimal moneyEntered = new BigDecimal("10");
        toTest.depositMoney(moneyEntered);
        Item retrieved = toTest.vendItem(1);
        assertEquals(9, retrieved.getNumItemAvail());
        assertEquals(9, dao.searchbyId(1).getNumItemAvail());
    }

    @Test
    public void testVendItemInvalidId() throws NegativeMoneyException,
            NoItemsLeftException,
            NotEnoughMoneyException,
            NoExistingFileException {

        
    VendingMachineDao dao = new VendingMachineInMemDao();
    VendingMachineService toTest = new VendingMachineService(dao);
        
        try {
            BigDecimal moneyEntered = new BigDecimal("10");
            toTest.depositMoney(moneyEntered);
            Item retrieved = toTest.vendItem(-1);
            fail("You should not be able to vend an item that does not exist. ");
        } catch (InvalidItemException ex) {
        }
    }

    @Test
    public void testVendItemNotEnoughMoney() throws NegativeMoneyException,
            NoItemsLeftException,
            InvalidItemException,
            NoExistingFileException {
        
    VendingMachineDao dao = new VendingMachineInMemDao();
    VendingMachineService toTest = new VendingMachineService(dao);

        try {
            BigDecimal moneyEntered = new BigDecimal("0.00");
            toTest.depositMoney(moneyEntered);
            Item retrieved = toTest.vendItem(1);
            fail("You should not be able to buy something when you have no money!");
        } catch (NotEnoughMoneyException ex) {
        }
    }

    @Test
    public void testVendItemAllOut() throws NegativeMoneyException,
            NotEnoughMoneyException,
            InvalidItemException,
            NoExistingFileException {
        
    VendingMachineDao dao = new VendingMachineInMemDao();
    VendingMachineService toTest = new VendingMachineService(dao);

        try {
            BigDecimal moneyEntered = new BigDecimal("10");
            toTest.depositMoney(moneyEntered);
            Item retrieved = toTest.vendItem(2);

            fail("You should not be able to buy anything when the machine is out of items!");

        } catch (NoItemsLeftException ex) {

        }
    }

    @Test
    public void testGetAllItemsGoldenPath() {

    VendingMachineDao dao = new VendingMachineInMemDao();
    VendingMachineService toTest = new VendingMachineService(dao);
    
        toTest.getAllItems();
        List<Item> retrieved = toTest.getAllItems();
        assertEquals(1, retrieved.get(0).getItemId());
        assertEquals("Chips Ahoy snack pack", retrieved.get(0).getItemName());
        assertEquals(new BigDecimal("1.25"), retrieved.get(0).getItemCost());
        assertEquals(10, retrieved.get(0).getNumItemAvail());

        assertEquals(2, retrieved.get(1).getItemId());
        assertEquals("Chocolate bar", retrieved.get(1).getItemName());
        assertEquals(new BigDecimal("1.30"), retrieved.get(1).getItemCost());
        assertEquals(0, retrieved.get(1).getNumItemAvail());
    }

    @Test
    public void testDepositMoneyGoldenPath() throws NegativeMoneyException {

        VendingMachineDao dao = new VendingMachineInMemDao();
        VendingMachineService toTest = new VendingMachineService(dao);
        BigDecimal moneyToEnter = new BigDecimal("3.00");
        toTest.depositMoney(moneyToEnter);

        assertEquals(new BigDecimal("3.00"), toTest.getCurrentMoney());

    }

    @Test
    public void testDepositMoneyNegativeMoney() {
        VendingMachineDao dao = new VendingMachineInMemDao();
        VendingMachineService toTest = new VendingMachineService(dao);

        try {

            BigDecimal moneyToEnter = new BigDecimal("-3.00");
            toTest.depositMoney(moneyToEnter);

            fail("The systems hould not allow the user to deposit negative money!");
        } catch (NegativeMoneyException ex) {

        }
    }

    @Test
    public void testReturnChange() throws NegativeMoneyException,
            NoItemsLeftException,
            NotEnoughMoneyException,
            InvalidItemException {

        VendingMachineDao dao = new VendingMachineInMemDao();
        VendingMachineService toTest = new VendingMachineService(dao);
        BigDecimal moneyEntered = new BigDecimal("2");
        toTest.depositMoney(moneyEntered);

        Change retrieved = new Change(new BigDecimal("2"));

        assertEquals(8, retrieved.getQuarters());
        assertEquals(0, retrieved.getDimes());
        assertEquals(0, retrieved.getNickels());
        assertEquals(0, retrieved.getPennies());

    }

    @Test
    public void testGetCurrentMoney() throws NegativeMoneyException {
        VendingMachineDao dao = new VendingMachineInMemDao();
        VendingMachineService toTest = new VendingMachineService(dao);
        BigDecimal totalMoneyEntered = new BigDecimal("2.00");
        toTest.depositMoney(totalMoneyEntered);
        toTest.getCurrentMoney();

        assertEquals(new BigDecimal("2.00"), toTest.getCurrentMoney());

    }

    @Test
    public void testGetCurrentMoneyNegMoney() {

        VendingMachineDao dao = new VendingMachineInMemDao();
        VendingMachineService toTest = new VendingMachineService(dao);
        try {

            BigDecimal totalMoneyEntered = new BigDecimal("-2.00");
            toTest.depositMoney(totalMoneyEntered);
            toTest.getCurrentMoney();

            fail("The system should NOT accept or return negative money!");
        } catch (NegativeMoneyException ex) {

        }

    }
}
