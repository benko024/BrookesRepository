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
public class NewFileException extends Exception {

    public NewFileException(String message) {
        super( message );
    }
    
    public NewFileException(String message, Throwable innerException) {
        super( message, innerException);
    }
    
}
