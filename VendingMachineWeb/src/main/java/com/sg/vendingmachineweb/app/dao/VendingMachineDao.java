/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.dao;

import com.sg.vendingmachineweb.app.dto.Item;
import java.util.List;

/**
 *
 * @author fore8
 */

    public interface VendingMachineDao {

    List<Item> getDisplayItems()
            throws VendingMachineDaoException;

    void updateQuantity(Item updateItem)
            throws VendingMachineDaoException;

    public Item pickItem(String name)
            throws VendingMachineDaoException;

    
}

