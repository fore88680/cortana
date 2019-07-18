/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.dao;

/**
 *
 * @author fore8
 */
public interface VendingMachineAuditDao {
    
    
    public void writeAudit (String entry) throws VendingMachineDaoException;
}
