/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 *
 * @author fore8
 */
@Component 
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao {

    public static final String AUDIT_FILE = "auditFile.txt";

    @Override
    public void writeAudit(String entry) throws VendingMachineDaoException {
        PrintWriter pw;

        try {
            pw = new PrintWriter(new FileWriter(AUDIT_FILE, true));

        } catch (IOException ex) {
            throw new VendingMachineDaoException("Audit failed to exicute", ex);

        }

        LocalDateTime timeStamp = LocalDateTime.now();
        pw.println(timeStamp.toString() + " :: " + entry);
        pw.flush();

    }
}
