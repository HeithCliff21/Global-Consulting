/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class City {
    
    public int cityID;
    public String name;
    public int countryID;
    public static ObservableList<City> allCities = FXCollections.observableArrayList();
    public static int selectedID;
    public static int selectedCountryID;
    public static String selectedName;
    
    public City(int cityID, String name, int countryID) {
        this.setId(cityID);
        this.setName(name);
        this.setCountryID(countryID);
    }
    public int getId(){
        return this.cityID;
    }    
    
    public String getName(){
        return this.name;
    }
    
    public int getCountryID(){
        return this.countryID;
    }
    
    public static int getSelectedId() {
        return City.selectedID;
    }
    
    public static int getSelectedCountryId(){
        return City.selectedCountryID;
    }
    
    public static void setSelectedCountryId(int selectedCountryID){
        City.selectedCountryID = selectedCountryID;
    }
    
    public static void setSelectedId(int selectedID){
        City.selectedID = selectedID;
    }
   
    public static void setSelectedName(String selectedName){
        City.selectedName = selectedName;
    }
   
    public void setId(int cityID){
        this.cityID=cityID;
    }    
    
    public void setName(String cityName){
        this.name= cityName;
    }
    
    public void setCountryID(int countryID){
        this.countryID = countryID;
    }
    public String citynames(String names) {
        return getName();
    }
    
    public static City lookupCity(int cityId) throws Exception {
        for (int i = 0; i < City.allCities.size(); i++) {
            City currentCity = City.allCities.get(i);
            
            if (currentCity.getId() == cityId) {
                return currentCity;
            }
        }
        
        throw new Exception(" not found");
    }
     
    @Override
    public String toString(){
        return this.getName();
    }
    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    public static ObservableList<City> getAllCities(){
        allCities.clear();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM city;";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 City newCity = new City(
                 rs.getInt("cityId"),
                 rs.getString("city"),
                 rs.getInt("countryID"));
                 
                 System.out.println("CityID: " + newCity.getId());
                 System.out.println("CityName: " + newCity.getName());
                 System.out.println("CounrtyID: " + newCity.getCountryID());
             
                allCities.add(newCity);
             }
             statement.close();
             return allCities;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
}
     
    }
}

