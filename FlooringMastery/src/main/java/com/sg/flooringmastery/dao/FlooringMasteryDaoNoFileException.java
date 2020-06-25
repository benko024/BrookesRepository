/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import java.io.IOException;

/**
 *
 * @author bkb
 */
public class FlooringMasteryDaoNoFileException extends Exception {

    public FlooringMasteryDaoNoFileException(String message) {
        super( message );
    }
    
    public FlooringMasteryDaoNoFileException(String message, Throwable innerException) {
        super( message, innerException);
    }

}
