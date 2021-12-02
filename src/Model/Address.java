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
public class Address {
    
    public int addressId;
    public String address;
    public String address2;
    public int cityId;
    public static ObservableList<Address> allAddress = FXCollections.observableArrayList();
    
    public Address(int addressId, String address, String address2, int cityId) {
        this.setID(addressId);
        this.setAddress(address);
        this.setAddress2(address2);
        this.setCityID(cityId);
    }
    public int getId(){
        return this.addressId;
    }    
    
    public String getAddress(){
        return this.address;
    }
    
    public String getAddress2(){
        return this.address2;
    }
    
    public int getCityID(){
        return this.cityId;
    }

    public void setID(int addressID){
        this.addressId=addressID;
    }    
    
    public void setAddress(String address){
        this.address= address;
    }
    
    public void setAddress2(String address2){
        this.address2= address2;
    }
    
    public void setCityID(int cityID){
        this.cityId = cityID;
    }
    
    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    public static ObservableList<Address> getAllAddress(){
        allAddress.clear();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM address;";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 Address newAddress = new Address(
                 rs.getInt("addressId"),
                 rs.getString("address"),
                 rs.getString("address2"),
                 rs.getInt("cityId"));
                 
                 System.out.println("AddressID: " + newAddress.getId());
                 System.out.println("Address: " + newAddress.getAddress());
                 System.out.println("Address2: " + newAddress.getAddress2());
                 System.out.println("CityID: " + newAddress.getCityID());
             
                allAddress.add(newAddress);
             }
             statement.close();
             return allAddress;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
    }
}
}
     
       

