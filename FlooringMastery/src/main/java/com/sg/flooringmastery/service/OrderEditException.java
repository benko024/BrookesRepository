/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author bkb
 */
public class OrderEditException extends Exception {

    public OrderEditException(String message) {
        super(message);
    }

    public OrderEditException(String message, Throwable innerException) {
        super(message, innerException);
    }

}
