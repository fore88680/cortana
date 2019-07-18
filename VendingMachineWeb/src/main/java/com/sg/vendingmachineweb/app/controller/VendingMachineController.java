/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.controller;


import com.sg.vendingmachineweb.app.dao.VendingMachineDaoException;
import com.sg.vendingmachineweb.app.service.VendingMachineService;
import com.sg.vendingmachineweb.app.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fore8
 */
@RestController
@RequestMapping("/api")
public class VendingMachineController {

  VendingMachineService service;
    
  
     public VendingMachineController (VendingMachineService service) {
          this.service = service;
     }
     
     @GetMapping
      public List<Item> getDisplayItems() throws VendingMachineDaoException {
      List<Item> allItems = service.getDisplayItems();
      return allItems;
        
      }
     }
   
    



