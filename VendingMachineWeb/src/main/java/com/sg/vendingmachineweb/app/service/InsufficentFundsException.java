/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.service;

/**
 *
 * @author fore8
 */
public class InsufficentFundsException extends Exception {

    InsufficentFundsException(String message) { 
        super(message);

    }
    
    public InsufficentFundsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
