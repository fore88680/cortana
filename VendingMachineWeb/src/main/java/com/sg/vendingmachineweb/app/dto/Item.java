/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author fore8
 */
public class Item {

    private String name;
    private BigDecimal price;
    private int selectionNumber;
    private int inventoryCount;

    
    
    

    public Item(String name, BigDecimal price, int selectionNumber, int inventoryCount) {
        this.name = name;
        this.price = price;
        this.selectionNumber = selectionNumber;
        this.inventoryCount = inventoryCount;
    }

    
    public Item(String name) {
        this.name = name;
    }
            
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSelectionNumber() {
        return selectionNumber;
    }

    public void setSelectionNumber(int selectionNumber) {
        this.selectionNumber = selectionNumber;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.price);
        hash = 41 * hash + this.selectionNumber;
        hash = 41 * hash + this.inventoryCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.selectionNumber != other.selectionNumber) {
            return false;
        }
        if (this.inventoryCount != other.inventoryCount) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    
    

   

}
