/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.DataBase.conn;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Heith
 */
public class CustomerTest {
       
    
    /**
     * Test of saveCustomer method, of class Customer.
     */
    @Test
    public void testSaveCustomer() throws SQLException {
        System.out.println("saveCustomer");
        try {
            DataBase.makeConnection();
            User.login("test", "test");
            Address.getAllAddress();
            Customer.getAllCustomers();
        } catch (Exception ex) {
            Logger.getLogger(CustomerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String name = "JUnit";
        String address = "Testing";
        String address2 = " ";
        int cityId = 3;
        String zipCode = "12345";
        String phone = "1234567890";
        boolean expResult = true;
        boolean result = Customer.saveCustomer(name, address, address2, cityId, zipCode, phone);
        assertEquals(expResult, result);
    }
}
