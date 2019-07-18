/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.dto;

import java.math.BigDecimal;

/**
 *
 * @author fore8
 */
public enum Coin {

    Penny(1), NICKLE(5), DIME(10), QUARTER(25), DOLLAR(100);
    
    private int value = 0;
    
    Coin(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    
}
