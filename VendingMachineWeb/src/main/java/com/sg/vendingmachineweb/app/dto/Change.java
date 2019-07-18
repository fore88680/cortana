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
public class Change {

    private int penny;
    private int nickle;
    private int dime;
    private int quarter;
    private int dollar;

   public Change(BigDecimal total) {

        BigDecimal[] result = total.divideAndRemainder(new BigDecimal(1));
        dollar = result[0].intValue();
        BigDecimal remainder = result[1];
        if (remainder.compareTo(BigDecimal.ZERO) > 0) {
            result = remainder.divideAndRemainder(new BigDecimal(.25));
            quarter = result[0].intValue();
            remainder = result[1];
            if (remainder.compareTo(BigDecimal.ZERO) > 0) {
                result = remainder.divideAndRemainder(new BigDecimal(.10));
                dime = result[0].intValue();
                remainder = result[1];
                if (remainder.compareTo(BigDecimal.ZERO) > 0) {
                    result = remainder.divideAndRemainder(new BigDecimal(.05));
                    nickle = result[0].intValue();
                    remainder = result[1];
                    if (remainder.compareTo(BigDecimal.ZERO) > 0) {
                        result = remainder.divideAndRemainder(new BigDecimal(.01));
                        penny = result[0].intValue();
                        remainder = result[1];
                    }
                }
            }
        }

    }

    public int getPenny() {
        return penny;
    }

    public int getNickle() {
        return nickle;
    }

    public int getDime() {
        return dime;
    }

    public int getQuarter() {
        return quarter;
    }

    public int getDollar() {
        return dollar;
    }

}
