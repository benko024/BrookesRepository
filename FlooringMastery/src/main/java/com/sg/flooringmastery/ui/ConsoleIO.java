/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bkb
 */
public class ConsoleIO {

    final private Scanner console = new Scanner(System.in);

    public void print(String msg) {
        System.out.print(msg);
    }

    public String readString(String prompt) {
        print(prompt);
        String userInput = console.nextLine();
        return userInput;
    }

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

    public int readInt(String prompt, int min, int max) {
        int result = readInt(prompt);
        boolean keepGoing = true;
        while (keepGoing) {
            if (result < min || result > max) {
                print("\n-Please input a valid number-\n");
                result = readInt(prompt);
            } else {
                keepGoing = false;
            }
        }
        return result;
    }

    public BigDecimal readBigDecimal(String prompt) {
        String num = "0";
        BigDecimal bigDecimalNum = null;
        boolean invalidInput = true;
        while (invalidInput) {
            try {
                num = readString(prompt);
                bigDecimalNum = new BigDecimal(num);
                bigDecimalNum.setScale(2, RoundingMode.HALF_UP);
                return bigDecimalNum;
            } catch (NumberFormatException e) {
                print("Input error. Please enter a valid number\n");
            }
        }
        return bigDecimalNum;
    }

    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {

        BigDecimal bigDecimalNum = null;

        boolean valid = false;
        while (!valid) {
            try {
                String num = readString(prompt);
                bigDecimalNum = new BigDecimal(num);
                bigDecimalNum.setScale(2, RoundingMode.HALF_UP);
                if (bigDecimalNum.compareTo(min) == -1) {
                    print("Please enter an area of at least " + min + " sq ft.\n");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                print("Input error. Please enter a valid number\n");
            }
        }
        return bigDecimalNum;
    }

    public String editString(String prompt, String originalValue) {
        String toReturn = readString(prompt);
        if (toReturn.isEmpty()) {
            toReturn = originalValue;
        }
        return toReturn;
    }

    public LocalDate stringToDateFuture(String prompt) {
        LocalDate today = LocalDate.now();
        LocalDate date = null;
        boolean valid = false;
        while (!valid) {
            String dateString = readString(prompt);
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                date = LocalDate.parse(dateString, formatter);
                if (date.isAfter(today)) {
                    valid = true;
                } else {
                    print("The date must be in the future.\n");
                }
            } catch (Exception e) {
                print("Please input a valid date, in the format MM/dd/yyyy.\n");
            }
        }
        return date;

    }

    public LocalDate stringToDate(String prompt) {

        LocalDate date = null;
        boolean valid = false;
        while (!valid) {
            String dateString = readString(prompt);
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                date = LocalDate.parse(dateString, formatter);
                valid = true;
            } catch (Exception e) {
                print("Please input a valid date, in the format MM/dd/yyyy.\n");
            }
        }
        return date;

    }

    public String readProduct(String prompt, List<Product> productList) {

        Product toAdd = new Product();
        String toReturn = readString(prompt);
        boolean validProduct = false;
        while (!validProduct) {
            toAdd.setProductType(toReturn);
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getProductType().equalsIgnoreCase(toReturn)) {
                    validProduct = true;
                }
            }
            if (!validProduct) {
                toReturn = readString("Unfortunately, " + toReturn + " is not available at this time.\n"
                        + "Please input a valid product: ");
            }
        }
        return toReturn;
    }

    public String readState(String prompt, List<Tax> taxList) {

        Tax toCheck = new Tax();
        String toReturn = readString(prompt);
        boolean validState = false;
        while (!validState) {
            toCheck.setStateAbr(toReturn);
            toCheck.setStateFullName(toReturn);
            for (int i = 0; i < taxList.size(); i++) {
                if (taxList.get(i).getStateAbr().equalsIgnoreCase(toReturn)) {
                    validState = true;
                }
            }
            if (!validState) {
                toReturn = readString("Unfortunately, we are not able to sell to " + toReturn + " at this time.\n"
                        + "Please choose a valid state (abbreviated): ");
            }
        }

        return toReturn;
    }

    public String readName(String prompt) {

        String name = readString(prompt);
        boolean validName = false;
        while (!validName) {
            if (name == null || name.equals("")) {
                name = readString("Customer Name cannot be blank.\nPlease input the customer name: ");
            } else {
                if (name.matches("^[A-Za-z0-9,.' ]+$")) {
                    validName = true;
                } else {
                    name = readString("Customer Name can only contain characters A-Z, 0-9, \nspaces, commas, periods, and apostrophes.\n"
                            + "Please input a valid Customer Name: ");
                }
            }
        }
        return name;
    }

    public BigDecimal editBigDecimal(String prompt, BigDecimal originalValue, BigDecimal min) {

        boolean validInput = false;
        BigDecimal toReturn = null;

        while (!validInput) {
            String num = readString(prompt);
            if (num.isBlank()) {
                toReturn = originalValue;
                validInput = true;
            } else {
                try {
                    toReturn = new BigDecimal(num);
                    int compareResult = toReturn.compareTo(min);
                    if (compareResult >= 0) {
                        validInput = true;
                    }
                } catch (NumberFormatException ex) {
                }
            }
        }
        return toReturn;
    }

    public String editProduct(String prompt, List<Product> productList, String originalProduct) {
        Product toEdit = new Product();
        String toReturn = readString(prompt);
        boolean validProduct = false;
        while (!validProduct) {
            toEdit.setProductType(toReturn);
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getProductType().equalsIgnoreCase(toReturn)) {
                    validProduct = true;
                } else if (toReturn.isEmpty()) {
                    toReturn = originalProduct;
                    validProduct = true;
                }

            }
            if (!validProduct) {
                toReturn = readString("Unfortunately, " + toReturn + " is not avaialable at this time.\n"
                        + "Please input a valid product: ");
            }

        }
        return toReturn;
    }

    public String editState(String prompt, List<Tax> taxList, String originalState) {
        Tax toEdit = new Tax();
        String toReturn = readString(prompt);
        boolean validState = false;
        while (!validState) {
            toEdit.setStateAbr(toReturn);
            for (int i = 0; i < taxList.size(); i++) {
                if (taxList.get(i).getStateAbr().equalsIgnoreCase(toReturn)) {
                    validState = true;
                } else if (toReturn.isEmpty()) {
                    toReturn = originalState;
                    validState = true;
                }
            }
            if (!validState) {
                toReturn = readString("Unfortunately, " + toReturn + " is not avaialable at this time.\n"
                        + "Please input a valid product: ");
            }
        }
        return toReturn;
    }

    public boolean readYesOrNo(String prompt) {
        String userInput = readString(prompt);
        boolean validInput = false;
        while (!validInput) {
            if (userInput.equalsIgnoreCase("Yes")) {
                validInput = true;
            } else {
                if (userInput.equalsIgnoreCase("No")) {
                    validInput = true;
                } else {
                    userInput = readString("Please input either yes or no.");
                }
            }
        }
        boolean yes = false;
        if (userInput.equalsIgnoreCase("yes")){ 
            yes = true;
        }
        else { 
            yes = false;
        }
        return yes;
    }
}
