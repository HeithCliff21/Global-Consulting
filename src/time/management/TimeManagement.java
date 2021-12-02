/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time.management;

import Model.City;
import Model.DataBase;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Heith
 */
public class TimeManagement extends Application {
    
    @Override
     public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            DataBase.makeConnection();
            //City.getAllCities();
            launch(args);
            DataBase.closeConnection();
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        } 
        catch (Exception ex) {
           System.out.println("Error: "+ ex.getMessage());
        }     
    }
}
