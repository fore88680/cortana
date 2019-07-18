/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.service;


import com.sg.vendingmachineweb.app.dao.VendingMachineAuditDao;
import com.sg.vendingmachineweb.app.dao.VendingMachineDao;
import com.sg.vendingmachineweb.app.dao.VendingMachineDaoException;
import com.sg.vendingmachineweb.app.dto.Change;
import com.sg.vendingmachineweb.app.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author fore8
 */
@Component 
public class VendingMachineServiceImpl implements VendingMachineService {

    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    BigDecimal userMoney = BigDecimal.ZERO;

    @Autowired
    public VendingMachineServiceImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> getDisplayItems() throws VendingMachineDaoException {
        auditDao.writeAudit("Items displayed");
        return dao.getDisplayItems();

    }

    @Override
    public Item pickItem(String name) throws VendingMachineDaoException {
        auditDao.writeAudit("item " + name + " selected");
        return dao.pickItem(name);
    }

    public void updateQuantity(Item name) throws VendingMachineDaoException {
        name.setInventoryCount(name.getInventoryCount() - 1);
    }

    @Override
    public void addCoin(BigDecimal total) {
       
        this.userMoney = this.userMoney.add(total);
        
    }

    @Override
    public BigDecimal getMoneyIn() {
       
        return userMoney;
        
    }

    @Override
    public Change purchase(Item item) throws VendingMachineDaoException, InsufficentFundsException, NoItemInInventoryException {

        if (userMoney.compareTo(item.getPrice()) < 0) {
            throw new InsufficentFundsException("Insufficent Money amount");
        } else if (item.getInventoryCount() <= 0) {
            throw new NoItemInInventoryException("Sorry item out of stock");
        }

        item.setInventoryCount(item.getInventoryCount() - 1);

        userMoney = userMoney.subtract(item.getPrice());
        Change change = new Change(userMoney);
        userMoney = BigDecimal.ZERO;
        
        auditDao.writeAudit("Item " + item.getName() + " purchased");
        return change;
    }

}
