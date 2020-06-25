/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import java.io.IOException;

/**
 *
 * @author bkb
 */
public class NoExistingFileException extends Exception {

    public NoExistingFileException( String message ){
        super( message );
    }
    
    public NoExistingFileException(String message, Throwable ex) {
        super( message, ex );
    }
    
}
