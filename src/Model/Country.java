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
public class Country {
    public int countryId;
    public String country;
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    
    public Country(int countryId, String cournty) {
        this.setID(countryId);
        this.setCountry(cournty);
    }
    public int getId(){
        return this.countryId;
    }    
    
    public String getCountry(){
        return this.country;
    }
    
    public void setID(int countryID){
        this.countryId= countryID;
    }    
    
    public void setCountry(String country){
        this.country= country;
    }
    
    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    public static ObservableList<Country> getAllCountries(){
        allCountries.clear();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM country;";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 Country newCoutnry = new Country(
                 rs.getInt("countryId"),
                 rs.getString("country"));
                 
                 System.out.println("CourntyID: " + newCoutnry.getId());
                 System.out.println("Country: " + newCoutnry.getCountry());
               
             
                allCountries.add(newCoutnry);
             }
             statement.close();
             return allCountries;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
}
     
    }
}
