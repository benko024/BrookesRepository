/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

/**
 *
 * @author bkb
 */
public class FlooringMasteryDaoException extends Exception {

    public FlooringMasteryDaoException(String message) {
        super( message );
    }
    
    public FlooringMasteryDaoException(String message, Throwable innerException) {
        super( message, innerException);
    }
    
}
