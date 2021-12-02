/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Heith
 */
public class ReportController implements Initializable {
    
    @FXML
    private TextArea schedule;
    
    @FXML
    private TextArea aptByMonth;
    
    @FXML
    private TextArea aptByLocation;
   
    @FXML
    void Back(ActionEvent event) throws IOException {
        Parent MainScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(MainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    //Creates report for user schedule with appointid type and start and end times
    public void reportSchedule(){
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT user.userId, user.userName, appointment.appointmentId, appointment.type, appointment.start, appointment.end " +
                    "FROM user JOIN appointment on  user.userId = appointment.userId ";
//                    +
//                    "GROUP BY user.userId, MONTH(start), start";
            ResultSet rs = statement.executeQuery(query);            
            StringBuilder scheduleText = new StringBuilder();
            scheduleText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-30s %5$-44s %6$s \n", 
                  "Consultant ID",  "UserName", "Appointment ID", "Appointment Type" , "Start", "End"));
            scheduleText.append(String.join("", Collections.nCopies(170, "-")));
            scheduleText.append("\n");
            while(rs.next()) {
                scheduleText.append(String.format("%1$-33s %2$-32s %3$-35s %4$-30s %5$-30s %6$s \n", 
                    rs.getInt("userId"),
                    rs.getString("userName"),
                    rs.getInt("appointmentId"),                   
                    rs.getString("type"),
                    rs.getString("start"),
                    rs.getString("end")));
            }
            statement.close();
            schedule.setText(scheduleText.toString());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
}

   //Creates report for number of appointment for Month
    public void reportMonth(){
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT MONTHNAME(start) as 'Month', appointment.type, COUNT(*) as 'Total' FROM appointment GROUP BY MONTHNAME(start), type";   
            ResultSet rs = statement.executeQuery(query);            
            StringBuilder monthText = new StringBuilder();
            monthText.append(String.format("%1$-25s %2$-30s %3$s \n", 
                  "Month",  "Appointment Type", "Total"));
            monthText.append(String.join("", Collections.nCopies(65, "-")));
            monthText.append("\n");
            while(rs.next()) {
                monthText.append(String.format("%1$-28s %2$-35s %3$s \n", 
                    rs.getString("Month"),
                    rs.getString("type"),                   
                    rs.getString("Total")));
            }
            statement.close();
            aptByMonth.setText(monthText.toString());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    //Creates report for number of appointment for location
    public void reportLocation(){
         try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT MONTHNAME(start) as 'Month', appointment.location, COUNT(*) as 'Total' FROM appointment GROUP BY MONTHNAME(start), location";   
            ResultSet rs = statement.executeQuery(query);            
            StringBuilder locationText = new StringBuilder();
            locationText.append(String.format("%1$-25s %2$-30s %3$s \n", 
                  "Month",  "Appointment Location", "Total"));
            locationText.append(String.join("", Collections.nCopies(65, "-")));
            locationText.append("\n");
            while(rs.next()) {
                locationText.append(String.format("%1$-28s %2$-35s %3$s \n", 
                    rs.getString("Month"),
                    rs.getString("location"),                   
                    rs.getString("Total")));
            }
            statement.close();
            aptByLocation.setText(locationText.toString());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        reportSchedule();
        reportMonth();
        reportLocation();
    }
    
}
