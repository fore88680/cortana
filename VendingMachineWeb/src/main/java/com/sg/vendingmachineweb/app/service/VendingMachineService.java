/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.service;


import com.sg.vendingmachineweb.app.dao.VendingMachineDaoException;
import com.sg.vendingmachineweb.app.dto.Change;
import com.sg.vendingmachineweb.app.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author fore8
 */
public interface VendingMachineService {

  
// coin stuff and validation here
    // items updation in dao
   

    public List<Item> getDisplayItems() throws VendingMachineDaoException;
   
    public Item pickItem(String name) throws VendingMachineDaoException;
       
    public void addCoin (BigDecimal total);
      
    public BigDecimal getMoneyIn();

   public Change purchase (Item item) throws VendingMachineDaoException, InsufficentFundsException,
           NoItemInInventoryException;
    
    
}

    
    
    // public BigDecimal addCoins(String coins, BigDecimal total) {

        //if (coins.equals("NICKLE")) {
         //   total = total.add(Coin.NICKLE.getDenom());
      //  } else if (coins.equals("DIME")) {
      //      total = total.add(Coin.DIME.getDenom());
     //   } else if (coins.equals("QUARTER")) {
     //       total = total.add(Coin.QUARTER.getDenom());
     //   } else if (coins.equals("DOLLAR")) {
     //       total = total.add(Coin.DOLLAR.getDenom());
     //   } else {
    //        System.out.println("Wrong Input Coin");
    //    }
     //   return total;
   // }
     
      // @Override
    //public BigDecimal addMoney(Coin coin) throws VendingMachineDaoException {
// service or controller 
        //Scanner sc = new Scanner(System.in);
       // String coins = sc.nextLine();
       // balance = addCoins(coins, balance);
       // return balance;
   // }


