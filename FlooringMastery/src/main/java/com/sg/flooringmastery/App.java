/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.FlooringMasteryOrderFileDao;
import com.sg.flooringmastery.dao.FlooringMasteryProductsFileDao;
import com.sg.flooringmastery.dao.FlooringMasteryTaxFileDao;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.ui.ConsoleIO;
import com.sg.flooringmastery.ui.FlooringMasteryView;

/**
 *
 * @author bkb
 */
public class App {

    public static void main(String[] args)  {

        ConsoleIO io = new ConsoleIO();
        FlooringMasteryView view = new FlooringMasteryView(io);
        FlooringMasteryOrderFileDao orderDao = new FlooringMasteryOrderFileDao("Orders"); 

        FlooringMasteryTaxFileDao taxDao = new FlooringMasteryTaxFileDao("Taxes.txt");
        FlooringMasteryProductsFileDao productDao = new FlooringMasteryProductsFileDao("Products.txt"); 
        FlooringMasteryService service = new FlooringMasteryService(orderDao, taxDao, productDao);

        FlooringMasteryController controller = new FlooringMasteryController(view, service);
        controller.run();
    }

}


