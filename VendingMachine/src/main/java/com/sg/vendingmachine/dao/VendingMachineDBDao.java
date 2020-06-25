/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author bkb
 */
@Component

public class VendingMachineDBDao implements VendingMachineDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Item> getAllItems() throws SQLException {
        List<Item> allItems = jdbc.query("SELECT * FROM Item", new ItemMapper());
        for (Item i : allItems) {
            Item toReturn = new Item();
            toReturn.setItemId(i.getItemId());
            toReturn.setItemName(i.getItemName());
            toReturn.setItemCost(i.getItemCost());
            toReturn.setNumItemAvail(i.getNumItemAvail());
            allItems.add(toReturn);
        }
        return allItems;
    }

    @Override
    public void editItem(Item updatedItem) throws NoExistingFileException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Item searchbyId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static final class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int index) throws SQLException {
            Item i = new Item();
            i.setItemId(rs.getInt("ItemId"));
            i.setItemName(rs.getString("ItemName"));
            i.setItemCost(rs.getBigDecimal("ItemCost"));
            i.setNumItemAvail(rs.getInt("NumItemAvail"));
            return i;
        }

    }

}
