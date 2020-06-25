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
public class NotEnoughMoneyException extends Exception {
    
    public NotEnoughMoneyException( String message ){
        super( message );
    }
    
    public NotEnoughMoneyException( String message, Throwable innerException ){
        super( message, innerException );
    }
}
