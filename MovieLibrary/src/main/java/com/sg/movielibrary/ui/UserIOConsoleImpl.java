/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movielibrary.ui;

import java.util.Scanner;

/**
 *
 * @author bkb
 */
public class UserIOConsoleImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);

    //Prints out whatever you put into the String msg thing. 
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    //Prints out a prompt and then takes in whatever the user inputted.
    @Override
    public String readString(String msgPrompt) {
        print(msgPrompt);
        String userInput = console.nextLine();

        return userInput;
    }

//    @Override
//    public String readStringIgnoreCase(String msgPrompt) {
//        System.out.println(msgPrompt);
//        String userInput = "Bob";
//        userInput.equalsIgnoreCase(console.nextLine());
//
//        return userInput;
//    }

    //prints a prompt, asks for a response, and ensures that the response
    //must be a number (So you would use this to get a number from the user, 
    //and you'd just put in the prompt as something like "Please enter the number
    //of cats you want to input." (Or whatever.)
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    //prints a prompt, asks for a response, and ensures that the response
    //must be a number (So you would use this to get a number from the user, 
    //and you'd just put in the prompt as something like "Please enter the number
    //of cats you want to input." (Or whatever.) This one is a little more 
    //complex because the user has to put in a number between the int min and int max.
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    //Just like readInt but with longs instead
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    //Just like read int with the max and min, but with longs instead
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    //Just like read int but with floats instead
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    //Just like read int but with floats instead and with the min and max as floats
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    //Just like read int but with doubles instead
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    //Just like read int but with doubles and double mins and maxs instead
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
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
