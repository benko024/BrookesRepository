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
public class NegativeMoneyException extends Exception {

    public NegativeMoneyException( String message ){
        super( message );
    }
    
    public NegativeMoneyException( String message, Throwable innerException ){
        super( message, innerException );
    }
    
}
