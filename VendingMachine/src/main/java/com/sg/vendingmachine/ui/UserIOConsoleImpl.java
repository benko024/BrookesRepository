/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 *
 * @author bkb
 */
public class UserIOConsoleImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.print(msg);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        String userInput = console.nextLine();

        return userInput;
    }

    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = readString(prompt);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                print("Input error. Please enter a valid number.");
            }
        }
        return num;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result = readInt(prompt);
        boolean keepGoing = true;
        while (keepGoing) {
            if(result < min || result > max){
                print("\n-Please input a valid number-\n");
                result = readInt(prompt);
            }
            else {
                keepGoing = false;
            }
        } 
        
        

        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        String num = "0";    
        BigDecimal bigDecimalNum = null;
        
        boolean invalidInput=true;
               
        while (invalidInput) {
            try{
                num = readString(prompt);
                bigDecimalNum = new BigDecimal(num);
                bigDecimalNum.setScale(2, RoundingMode.HALF_UP);
                return bigDecimalNum;
            } catch (NumberFormatException e) {
                print("Input error. Please enter a valid number!");
            }
        }
        
        return bigDecimalNum;
    }

    @Override
    public String editString(String prompt, String originalValue) {
        String toReturn = readString(prompt);
        if (toReturn.isEmpty()) {
            toReturn = originalValue;
        }

        return toReturn;
    }
    

}
