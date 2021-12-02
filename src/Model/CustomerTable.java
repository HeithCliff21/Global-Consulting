/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class CustomerTable {
    
    public int customerId;
    public String customerName;
    public int addressId;
    public String address;
    public String address2;
    public String postalCode;
    public String phone;
    public int cityId;
    public String city;
    public static ObservableList<CustomerTable> customersTable = FXCollections.observableArrayList();
    
    public CustomerTable(int customerId, String customerName, int addressId, String address, String address2, String postalCode, String phone,  int cityId, String city){
        this.setCustId(customerId);
        this.setCustName(customerName);
        this.setAddId(addressId);
        this.setAddress(address);
        this.setAddress2(address2);
        this.setPostalCode(postalCode);
        this.setPhone(phone);
        this.setCityId(cityId);
        this.setCity(city);
    }
    
    public int getCustomerId(){
        return this.customerId;
    }
    public String getCustomerName(){
        return this.customerName;
    }
    public int getAddId(){
        return this.addressId;
    }
    public String getAddress(){
        return this.address;
    }
    public String getAddress2(){
        return this.address2;
    }
    public String getPostalCode(){
        return this.postalCode;
    }
    public String getPhone(){
        return this.phone;
    }
    public int getCityId(){
        return this.cityId;
    }
    public String getCity(){
        return this.city;
    }
    public void setCustId(int customerId){
        this.customerId=customerId;
    }
    public void setCustName(String customerName){
        this.customerName=customerName;
    }
    public void setAddId(int addressId){
        this.addressId=addressId;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setAddress2(String address2){
        this.address2=address2;
    }
    public void setPostalCode(String postalCode){
        this.postalCode=postalCode;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public void setCityId(int cityId){
        this.cityId=cityId;
    }
    public void setCity(String city){
        this.city=city;
    }
    
    public static ObservableList<CustomerTable> getCustomersTable(){
        customersTable.clear();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT customer.customerId , customer.customerName , address.addressId , address.address , address.address2 , address.phone , address.postalCode , city.cityId , city.city"
            +  " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
            +  " INNER JOIN city ON address.cityId =city.cityId";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 CustomerTable customerTbl = new CustomerTable(
                 rs.getInt("customerId"),
                 rs.getString("customerName"),
                 rs.getInt("addressId"),
                 rs.getString("address"),
                 rs.getString("address2"),
                 rs.getString("postalCode"),
                 rs.getString("phone"),
                 rs.getInt("cityId"),
                 rs.getString("city"));
                 
                 System.out.println(customerTbl.getCustomerId());
                 System.out.println(customerTbl.getCustomerName());
                 System.out.println(customerTbl.getAddId());
                 System.out.println(customerTbl.getAddress());
                 System.out.println(customerTbl.getPhone());
                 System.out.println(customerTbl.getCityId());
                 System.out.println(customerTbl.getCity());
                
                 
                customersTable.add(customerTbl);
             }
             statement.close();
             return customersTable;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
}
}
}
