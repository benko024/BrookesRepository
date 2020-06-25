/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

/**
 *
 * @author bkb
 */
public class NoItemsLeftException extends Exception {
    public NoItemsLeftException( String message ){
        super( message );
    }
    
    public NoItemsLeftException( String message, Throwable innerException ){
        super( message, innerException );
    }
}
