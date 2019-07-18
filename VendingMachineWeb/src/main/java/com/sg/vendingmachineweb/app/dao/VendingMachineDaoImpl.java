/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachineweb.app.dao;

import com.sg.vendingmachineweb.app.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author fore8
 */
@Component 
public class VendingMachineDaoImpl implements VendingMachineDao{
       private Map<String, Item> itemsFile = new HashMap<>();

    private static final String ITEMS_FILE = "Items.txt";
    private static final String DELIMITER = "::";

    // List<Item> items = new ArrayList<>();
    // BigDecimal balance = new BigDecimal("0");
    @Override
    public List<Item> getDisplayItems() throws VendingMachineDaoException {
        loadFile();
        return new ArrayList<Item>(itemsFile.values());
    }

    @Override
    public Item pickItem(String name) throws VendingMachineDaoException {
        loadFile();
        return itemsFile.get(name);
    }

    @Override
    public void updateQuantity(Item updateItem) throws VendingMachineDaoException {
        loadFile();
        itemsFile.remove(updateItem.getName());
        itemsFile.put(updateItem.getName(), updateItem);
        writeFile();

    }

    private void loadFile() throws VendingMachineDaoException {
        Scanner sc;
        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEMS_FILE)));
        } catch (FileNotFoundException ex) {
            throw new VendingMachineDaoException("File not found", ex);
        }

        String currentLine;
        String[] tokens;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            tokens = currentLine.split(DELIMITER);

            Item i = new Item(tokens[0]);
            i.setPrice(new BigDecimal(tokens[1]));
            i.setInventoryCount(Integer.parseInt(tokens[2]));

            itemsFile.put(i.getName(), i);

        }
        sc.close();
    }

    private void writeFile() throws VendingMachineDaoException {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter(ITEMS_FILE));
        } catch (IOException ex) {

            throw new VendingMachineDaoException("Could not save file", ex);
        }
        List<Item> itemsList = new ArrayList<Item>(itemsFile.values());
        for (Item i : itemsList) {
            pw.println(i.getName() + DELIMITER
                    + i.getPrice() + DELIMITER
                    + i.getInventoryCount());

            pw.flush();
        }

        pw.close();

    }

    
}
