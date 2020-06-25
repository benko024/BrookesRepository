/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author bkb
 */
public class Change {

    private int quarters;
    private int dimes;
    private int nickels;
    private int pennies;

    /**
     * @return the quarters
     */
    public int getQuarters() {
        return quarters;
    }

    /**
     * @return the dimes
     */
    public int getDimes() {
        return dimes;
    }

    /**
     * @return the nickels
     */
    public int getNickels() {
        return nickels;
    }

    /**
     * @return the pennies
     */
    public int getPennies() {
        return pennies;
    }

    public Change(BigDecimal buildFrom) {
        int totalPennies = buildFrom.multiply(new BigDecimal(100)).intValueExact();

        quarters = totalPennies / 25;

        totalPennies %= 25;

        dimes = totalPennies / 10;

        totalPennies %= 10;

        nickels = totalPennies / 5;

        totalPennies %= 5;

        pennies = totalPennies;
    }

}
