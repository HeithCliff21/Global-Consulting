/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static java.time.ZoneOffset.UTC;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ofPattern;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author admin
 */
public class Appointment {
    
    public int appointmentId;
    public int customerId;
    public int userId;
    public String type;
    public String title;
    public String description;
    public String location;
    public String contact;
    public String url;
    public String start;
    public String end;
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    
    public Appointment(int appointmentId, int customerId, int userId, String type, String title, String description, String location, String contact, String url, String start, String end) {
        this.setId(appointmentId);
        this.setCustId(customerId);
        this.setUserId(userId);
        this.setTitle(title);
        this.setType(type);
        this.setDescription(description);
        this.setLocation(location);
        this.setContact(contact);
        this.setUrl(url);
        this.setStart(start);
        this.setEnd(end);
    }
    public int getAppointmentId(){
        return this.appointmentId;
    }    
    
    public int getCustomerId(){
        return this.customerId;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public String getLocation(){
        return this.location;
    }
    
    public String getContact(){
        return this.contact;
    }
    
    public String getUrl(){
        return this.url;
    }

    public String getStart(){
        return this.start;
    }
    
    public String getEnd(){
        return this.end;
    }
    
    public int getUserId(){
        return this.userId;
    }
    public String getType(){
        return this.type;
    }
    
    public void setType(String type){
        this.type= type;
    }
     
    public void setUserId(int userId){
        this.userId= userId;
    }
    
    public void setId(int appointmentID){
        this.appointmentId= appointmentID;
    }
    
    public void setCustId(int customerID){
        this.customerId = customerID;
    }
    
    public void setTitle(String title){
        this.title= title;
    }
    
    public void setDescription(String description){
        this.description= description;
    }
    
    public void setLocation(String location){
        this.location= location;
    }
    
    public void setContact(String contact){
        this.contact= contact;
    }
    
    public void setUrl(String url){
        this.url = url;
    }
    
    public void setStart(String start){
        this.start= start;
    }
    
    public void setEnd(String end){
        this.end= end;
    }
    
    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    
    //Gets All Appointment for User to be displayed
    public static ObservableList<Appointment> getAllAppointments(){
        User user = User.getCurrentUser();
        int userId = user.getUserId();
        allAppointments.clear();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM appointment WHERE userId = '" + userId + "';";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 Appointment newAppointment = new Appointment(
                 rs.getInt("appointmentId"),
                 rs.getInt("customerId"),
                 rs.getInt("userId"),
                 rs.getString("type"),
                 rs.getString("title"),
                 rs.getString("description"),
                 rs.getString("location"),
                 rs.getString("contact"),
                 rs.getString("url"),
                 rs.getString("start"),
                 rs.getString("end"));
                 
                 
                 System.out.println("title: " + newAppointment.getTitle());
                 System.out.println("description: " + newAppointment.getDescription());
                 System.out.println("location: " + newAppointment.getLocation());
                 System.out.println("contact: " + newAppointment.getContact());
                 System.out.println("url: " + newAppointment.getUrl());
                 System.out.println("start: " + newAppointment.getStart());
                 System.out.println("end: " + newAppointment.getEnd());
                 
             
                allAppointments.add(newAppointment);
             }
             statement.close();
             // Lambda Changes all appointment in observerable list to Local Time zone
             allAppointments.forEach((appointment)->{
                 
                 ZoneId ZoneLoc = ZoneId.systemDefault();
                 String locZone = ZoneLoc.toString();
                 
                 appointment.setStart(Appointment.getlocationDateTime(locZone,appointment.getStart()));
                 appointment.setEnd(Appointment.getlocationDateTime(locZone,appointment.getEnd()));
             });
             return allAppointments;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
    }
}
    //Gets Appointment for User for that month to be displayed
    public static ObservableList<Appointment> getMonthAppointments() throws ParseException{
        monthAppointments.clear();
        LocalDate now = LocalDate.now();
        LocalDate future = LocalDate.now().plusMonths(1);
        
        String sNow = now.toString();
        String sFuture = future.toString();
              
        
        User user = User.getCurrentUser();
        int userId = user.getUserId();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM appointment WHERE userId = '" + userId + "'AND start >='" + sNow + "' AND start <= '" + sFuture + "';";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 Appointment newAppointment = new Appointment(
                 rs.getInt("appointmentId"),
                 rs.getInt("customerId"),
                 rs.getInt("userId"),
                 rs.getString("type"),
                 rs.getString("title"),
                 rs.getString("description"),
                 rs.getString("location"),
                 rs.getString("contact"),
                 rs.getString("url"),
                 rs.getString("start"),
                 rs.getString("end"));
                                                         
                monthAppointments.add(newAppointment);
             }
             statement.close();
             // Lambda Changes all appointment in observerable list to Local Time zone
             monthAppointments.forEach((appointment)->{
                 
                 ZoneId ZoneLoc = ZoneId.systemDefault();
                 String locZone = ZoneLoc.toString();
                 
                 appointment.setStart(Appointment.getlocationDateTime(locZone,appointment.getStart()));
                 appointment.setEnd(Appointment.getlocationDateTime(locZone,appointment.getEnd()));
             });
             return monthAppointments;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
    }
}
        //Gets Appointment for User for that week to be displayed
        public static ObservableList<Appointment> getWeekAppointments() throws ParseException{
        weekAppointments.clear();
        LocalDate now = LocalDate.now();
        LocalDate future = LocalDate.now().plusWeeks(1);
        
        String sNow = now.toString();
        String sFuture = future.toString();
               
        User user = User.getCurrentUser();
        int userId = user.getUserId();
        
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM appointment WHERE userId = '" + userId + "'AND start >='" + sNow + "' AND start <= '" + sFuture + "';";
            ResultSet rs = statement.executeQuery(query);
             while(rs.next()){
                 Appointment newAppointment = new Appointment(
                 rs.getInt("appointmentId"),
                 rs.getInt("customerId"),
                 rs.getInt("userId"),
                 rs.getString("type"),
                 rs.getString("title"),
                 rs.getString("description"),
                 rs.getString("location"),
                 rs.getString("contact"),
                 rs.getString("url"),
                 rs.getString("start"),
                 rs.getString("end"));
                                                         
                weekAppointments.add(newAppointment);
             }
             statement.close();
             // Lambda Changes all appointment in observerable list to Local Time zone
             weekAppointments.forEach((appointment)->{
                 
                 ZoneId ZoneLoc = ZoneId.systemDefault();
                 String locZone = ZoneLoc.toString();
                 
                 appointment.setStart(Appointment.getlocationDateTime(locZone,appointment.getStart()));
                 appointment.setEnd(Appointment.getlocationDateTime(locZone,appointment.getEnd()));
             });
             return weekAppointments;
             
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
    }
}
  
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static final String DATEAM_FORMAT = "MM-dd-yyyy hh:mm a";
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private static DateTimeFormatter formatterAM = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
    
    // Switches UTC to Location
    public static String getlocationDateTime(String location, String time){
        //Seting Start and End to Format to be converted to Location Time
        String time2 = time.replaceAll("\\.0*$", "");
        
        LocalDateTime ldt = LocalDateTime.parse(time2, DateTimeFormatter.ofPattern(DATE_FORMAT));
        ZoneId ZoneLoc = ZoneId.of(location);
        ZonedDateTime Appointment = ldt.atZone(UTC);
       
        LocalDateTime srtL = LocalDateTime.ofInstant(Appointment.toInstant(), ZoneId.of(location));
                           
        String srtS = srtL.toString();
        String dtime = srtL.format(formatterAM);
        
        return dtime;    
    }
    
    // Switches Location to UTC
    public static String getUTCLocationDateTime(String location, String time) throws ParseException{
        
        String time2 = time.replaceAll("\\.0*$", "");
        
    
        LocalDateTime ldt = LocalDateTime.parse(time2, DateTimeFormatter.ofPattern(DATE_FORMAT));
        ZoneId ZoneaptLoc = ZoneId.of(location);
        ZoneId ZoneLoc = ZoneId.systemDefault();
        ZonedDateTime Appointment = ldt.atZone(ZoneLoc);
       
     
        //Changing to Location Time
        LocalDateTime srtL = LocalDateTime.ofInstant(Appointment.toInstant(), ZoneId.of(location));
                           
        String srtS = srtL.toString();
        String dtime = srtL.format(formatter);
        
        return dtime;    
    
    }

    // For taking Local Time to Appointment Location Time
    public static String getAptLocationDateTime(String location, String time) throws ParseException{
    
        LocalDateTime ldt = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATEAM_FORMAT));
        ZoneId ZoneaptLoc = ZoneId.of(location);
        ZoneId ZoneLoc = ZoneId.systemDefault();
        ZonedDateTime Appointment = ldt.atZone(ZoneLoc);
       
     
        //Changing to Location Time
        LocalDateTime srtL = LocalDateTime.ofInstant(Appointment.toInstant(), ZoneId.of(location));
                           
        String srtS = srtL.toString();
        String dtime = srtL.format(formatter);
        
        return dtime;    
    
    }      
    
    // Seperates Location time to be displayed in edit window
    public static String getlocationTime(String location, String Ttime) throws ParseException{
            
        String time = getAptLocationDateTime(location,Ttime);
        String timeS[] =time.split(" ");
        String timeL = timeS[1];
        
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        
        String time12 = date12Format.format(date24Format.parse(timeL));
       
        return time12;
    }
    //Seperates Location date to be displayed in edit window
    public static String getlocationDate(String location, String Ttime) throws ParseException{
        
        String date = getAptLocationDateTime(location,Ttime);
        String dateS[] =date.split(" ");
        String dateL = dateS[0]; 
        
        DateTimeFormatter dateold = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter datenew = DateTimeFormatter.ofPattern("MM-dd-yyyy");
       
        String dateO = datenew.format(dateold.parse(dateL));
              
        return dateO;
    }
    
        // For Combining Appointment Date and Time and changing to UTC time to add to database
        public static String getDateTime(String date, String time, String location) {
        String dateInString = date + " " + time;       
        LocalDateTime ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(DATE_FORMAT));
        ZoneId ZoneLoc = ZoneId.of(location);
       
        ZonedDateTime Appointment = ldt.atZone(ZoneLoc);
        
        LocalDateTime utc = LocalDateTime.ofInstant(Appointment.toInstant(), ZoneId.of("UTC"));
        
        String AptUtc = utc.toString();
        return AptUtc;      
}
     // Saves Apt to Database
     public static boolean addApt(int customerId, String title, String description, String location, String contact, String type, String url, String startDateTime, String endDateTime) {
                try {
                    Statement statement = DataBase.conn.createStatement();
                    User user = User.getCurrentUser();
                    String username = user.getUsername();
                    int userId = user.getUserId();
                    int appointmentId = allAppointments.size() +1;
                    String query = "INSERT INTO appointment SET appointmentId=" + appointmentId + ", customerId=" + customerId + ", userId=" + userId + ", title='" + title + "', description='" +
                    description + "', location='" + location + "', contact='" + contact + "', type='" + type + "', url='" + url + "', start='" + startDateTime + "', end='" + endDateTime + 
                            "', createDate=CURRENT_TIMESTAMP, createdBy='" + username + "', lastUpdate=CURRENT_TIMESTAMP, lastUpdateBy='" + username + "'";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
}       
     // Update Apt to Database
      public static boolean updateApt(int aptId, String title, String description, String location, String contact, String type, String url, String startDateTime, String endDateTime) {
        try {
                    Statement statement = DataBase.conn.createStatement();
                    User user = User.getCurrentUser();
                    String username = user.getUsername();
                    int userId = user.getUserId();
                    String query = "UPDATE appointment SET title='" + title + "', description='" + description + "', contact='" + contact 
                      + "', location='" + location + "', start='" + startDateTime + "', end='" + endDateTime + "', lastUpdate=CURRENT_TIMESTAMP, lastUpdateBy='" + username 
                      + "' WHERE appointmentId='" + aptId + "'";
                    int update = statement.executeUpdate(query);
                    if(update == 1) {
                        return true;
                    }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
}
      
     // Delete Customer from Database
    public static boolean deleteapt (int aptId) {
        try {
            Statement statement = DataBase.conn.createStatement();
            String queryOne = "DELETE FROM appointment WHERE appointmentId=" + aptId;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                    return true;
                }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
      
       //Check if User has an Appointment at Date start and End Time   
       public static boolean appointmentAvialableUser(String start, String End) throws SQLException{
            User user = User.getCurrentUser();
            int userId = user.getUserId();
            try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM appointment WHERE userId = '" + userId + "' AND start >='" + start + "' AND start <= '" + End + "';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            if(rs.getInt("appointmentId") >= 1){
                return false;
            }        
            } catch (SQLException e){
                System.out.println("SQLExcpection: " + e.getMessage());
                return true; 
             }
            return true;
       }
       
       //Check if Client has an Appointment at Date start and End Time   
       public static boolean appointmentAvialableCust(String start, String End, int custId) throws SQLException{
            try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + custId + "'AND start >='" + start + "' AND start <= '" + End + "';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            if(rs.getInt("appointmentId") >= 1){
                return false;
            }        
            } catch (SQLException e){
                System.out.println("SQLExcpection: " + e.getMessage());
                return true; 
             }
            return true;
            }
       
       //Appointment Validation Method
    public static String isAptValid(String aptType, String location, String date, String sTime, String eTime, String contact, String url, String title, String description, String errorMessage) {
        if (aptType.equals("")) {
            errorMessage = errorMessage + ("Appointment Type cannot be empty\n");
        }
        if (location.equals("")) {
            errorMessage = errorMessage + ("Location cannot be blank\n");
        }
        if (date.equals("")) {
            errorMessage = errorMessage + ("Date cannot be empty\n");
        }
        if (sTime.equals("")) {
            errorMessage = errorMessage + ("Start Time cannot be empty\n");
        }
        if (eTime.equals("")) {
            errorMessage = errorMessage + ("End cannot be empty\n");
        }
        if (contact.equals("")) {
            errorMessage = errorMessage + ("Contact cannot be blank\n");
        }
        if (url.equals("")) {
            errorMessage = errorMessage + ("Url cannot be blank\n");
        }
        if (title.equals("")) {
            errorMessage = errorMessage + ("Title cannot be blank\n");
        }
        if (description.equals("")) {
            errorMessage = errorMessage + ("Description cannot be empty\n");
        }       
        return errorMessage;
    }  
}
