/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */


public class Customer{
    
    public int customerId;
    public String customerName;
    public int addressId;
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    
  
    public Customer(int customerId, String customerName, int addressId){
        this.setID(customerId);
        this.setName(customerName);
        this.setAddressID(addressId);
    }
    
    public int getID(){
        return this.customerId;
    }    
    
    public String getName(){
        return this.customerName;
    }
    
    public int getAddressID(){
        return this.addressId;
    }

    public void setID(int customerID){
        this.customerId=customerID;
    }    
    
    public void setName(String custName){
        this.customerName=custName;
    }
    
    public void setAddressID(int AddressID){
        this.addressId = AddressID;
    }
    
    public String getCustName(int custID){
        
        try {
        Statement statement = DataBase.conn.createStatement();
        String query = "SELECT * FROM customer WHERE customerId = '" + custID + "';";
        ResultSet rs = statement.executeQuery(query);
        String custname = rs.getString("customerName");
        
        statement.close();
        return custname;
     }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
}
}
        
    // Returns all Customers in Database
    public static ObservableList<Customer> getAllCustomers(){
        allCustomers.clear();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM customer;";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 Customer newCustomer = new Customer(
                 rs.getInt("customerId"),
                 rs.getString("customerName"),
                 rs.getInt("addressId"));
                 
                 System.out.println("CityID: " + newCustomer.getID());
                 System.out.println("CityName: " + newCustomer.getName());
                 System.out.println("CounrtyID: " + newCustomer.getAddressID());
             
                allCustomers.add(newCustomer);
             }
             statement.close();
             return allCustomers;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
}
    }
        
    // Saves new Customer to Database
    public static boolean saveCustomer(String name, String address, String address2, int cityId, String zipCode, String phone) {
        try {
            Statement statement = DataBase.conn.createStatement();
            User user = User.getCurrentUser();
            String username = user.getUsername();
            int addressId = Address.allAddress.size() +1;
            String queryOne = "INSERT INTO address SET addressId=" + addressId + ", address='" + address + "', address2='" + address2 + "', phone='" + phone + "', postalCode='" + zipCode + "', cityId=" + cityId + ", createDate= CURRENT_TIMESTAMP , createdBy='" + username + "', lastUpdate= CURRENT_TIMESTAMP , lastUpdateBy='" + username + "'";
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                int customerId = allCustomers.size() +1;
                String queryTwo = "INSERT INTO customer SET customerId=" + customerId + " , customerName='" + name + "', addressId=" + addressId + " , active=1 , createDate=CURRENT_TIMESTAMP , createdBy='" + username + "', lastUpdate=CURRENT_TIMESTAMP , lastUpdateBy='" + username + "'";
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
    
    // Update existing Customer in Database
    public static boolean updateCustomer(int customerId, int addressId, String Name, String Address, String Address2, int cityId, String Zip, String Phone) {
        try {
            Statement statement = DataBase.conn.createStatement();
            User user = User.getCurrentUser();
            String username = user.getUsername();
            
            String queryOne = "UPDATE address SET address='" + Address + "', address2='" + Address2 + "', cityId=" + cityId + ", postalCode='" + Zip + "', phone='" + Phone + "', lastUpdate=CURRENT_TIMESTAMP , lastUpdateBy='" + username + "'" 
                + "WHERE addressId=" + addressId;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "UPDATE customer SET customerName='" + Name + "', addressId='" + addressId + "', active='1' , lastUpdate=CURRENT_TIMESTAMP , lastUpdateBy='" + username + "'" 
                        + " WHERE customerId=" + customerId;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
    
    // Delete Customer from Database
    public static boolean deleteCustomer(int customerId, int addressId) {
        try {
            Statement statement = DataBase.conn.createStatement();
            String queryOne = "DELETE FROM customer WHERE customerId=" + customerId;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "DELETE FROM address WHERE addressId=" + addressId;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
    
    //Save new Customer in DataBase
    //public static boolean saveCustomer(String name, String address, String city, String zip, String phone){
        
    //}
    
   
    //Client Validation Method
    public static String isClientValid(String clientName, String clientPhone, String clientAddress, int clientCity, String clientZip, String errorMessage) {
        if (clientName.equals("")) {
            errorMessage = errorMessage + ("Name cannot be empty\n");
        }
        if (clientPhone.equals("")) {
            errorMessage = errorMessage + ("Phone Number cannot be empty\n");
        }
        if (clientAddress.equals("")) {
            errorMessage = errorMessage + ("Address cannot be empty\n");
        }
        if (clientCity <= 0) {
            errorMessage = errorMessage + ("City cannot be empty\n");
        }        
        if (clientZip.equals("")) {
            errorMessage = errorMessage + ("Zip Code cannot be empty\n");
        }
//        if (clientPhone.contains("[a-zA-Z]+") == false){
//            errorMessage = errorMessage + ("Phone Number can't have non number characters\n");
//        }
//        if (clientZip.contains("[a-zA-Z]+") == false){
//            errorMessage = errorMessage + ("Zip Code can't have non number characters\n");
//        }
        return errorMessage;
    }  
}
