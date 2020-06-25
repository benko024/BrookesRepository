/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sg.vendingmachine.service.VendingMachineService;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author bkb
 */
public class app {

    public static void main(String[] args) {

//        UserIO Io = new UserIOConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(Io);
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl("vendingMachineContents.txt");
//        VendingMachineService service = new VendingMachineService(myDao);
//        VendingMachineController controller = new VendingMachineController(myView, service);
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller",VendingMachineController.class);
        controller.run();
    }
}
