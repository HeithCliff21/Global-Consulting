/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import static Model.Appointment.getDateTime;
import Model.City;
import Model.Customer;
import Model.CustomerTable;
import Model.DataBase;
import static View_Controller.MainController.appointmentToAppointmentId;
import static View_Controller.MainController.appointmentToCustId;
import static View_Controller.MainController.appointmentToType;
import static View_Controller.MainController.appointmentToLocation;
import static View_Controller.MainController.appointmentToStart;
import static View_Controller.MainController.appointmentToEnd;
import static View_Controller.MainController.appointmentToContact;
import static View_Controller.MainController.appointmentToUrl;
import static View_Controller.MainController.appointmentToTitle;
import static View_Controller.MainController.appointmentToDescription;
import static com.sun.media.jfxmediaimpl.MediaUtils.error;
//import static View_Controller.MainController.appointmentToCustName;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import static sun.management.Agent.error;
interface something{
            String getTime(String Location, String Time);
            
        } 
/**
 * FXML Controller class
 *
 * @author Heith
 */
public class UpdateAptController implements Initializable {
    @FXML
    private TextField updateaptClientName;
    @FXML
    private ComboBox updateaptType;
    @FXML
    private DatePicker updateaptDate;
    @FXML
    private ComboBox updateaptStartTime;
    @FXML
    private ComboBox updateaptEndTime;
    @FXML
    private ComboBox<City> updateaptLocation;
    @FXML
    private TextField updateaptUrl;
    @FXML
    private TextField updateaptTitle;
    @FXML
    private TextArea updateaptDescription;
    @FXML
    private TextField updateaptContact;
    @FXML
    private Button updateAptSave;
    @FXML
    private Button updateAptCancel;
    
    private String exceptionMessage = new String();
    
    
    //Using the C Global Consulting Type of Consulting as references for a global consulting agency
    private final ObservableList<String> AppointmentType = FXCollections.observableArrayList("One hand Back Scratch" , "Two hand Back Scratch");
    
    private final ObservableList<String> AptStartTime = FXCollections.observableArrayList("8:00 AM","8:30 AM","9:00 AM","9:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","1:00 PM","1:30 PM","2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM");
    
    private final ObservableList<String> AptEndTime = FXCollections.observableArrayList("8:30 AM","9:00 AM","9:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","1:00 PM","1:30 PM","2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM","5:00 PM");
      
    public String setAptType(){
        String Type = (String) updateaptType.getSelectionModel().getSelectedItem();
        return Type;
    }
    
    public int LocationSet(){  
        String location = appointmentToLocation();
        if(location.equals("America/Phoenix")) {
            int locationId = 0;
            return locationId;
        } else if (location.equals("America/New_York")) {
            int locationId = 1;
            return locationId;
        }else{
            int locationId = 2;
            return locationId;
        }
     }
    
    //Set Location City Object to String to use for correct DateTime Format
    public String SetLocationName(){
        City city = updateaptLocation.getSelectionModel().getSelectedItem();
        int cityId = city.getId();
        City.setSelectedId(cityId);
        
        if(cityId == 1) {
            String Location = "America/Phoenix";
            return Location;
        }else if(cityId == 2){
            String Location = "America/New_York";
            return Location;           
        }else{
            String Location = "Europe/London";
            return Location;
        }  
    }
    public String setAptStart() throws ParseException{
        String Start = (String) updateaptStartTime.getSelectionModel().getSelectedItem();
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");
        
        //Takes Start Time selected and changes to 24 Hour Format
    	String time24 = date24Format.format(date12Format.parse(Start));   	
        return time24;
    }
        
    public String setAptEnd() throws ParseException{    
        String End = (String) updateaptEndTime.getSelectionModel().getSelectedItem();
        
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");
        
        //Takes End Time selected and changes to 24 Hour Format
    	String time24 = date24Format.format(date12Format.parse(End));   	
        return time24;
    }
    
    public String setAptDate(){
        LocalDate Date = updateaptDate.getValue();
        if (Date != null){
            String Date2 = Date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return Date2;
        } else{
            return "";
        }
    }
     
    // Gets Info to update Apt in database
     @FXML
    void updateAptSave(ActionEvent event) throws IOException, ParseException, SQLException {
        
        int aptId = appointmentToAppointmentId();
        int custId = appointmentToCustId();
        String title = (updateaptTitle.getText()!= null) ? updateaptTitle.getText() : "";
        String description = (updateaptDescription.getText()!= null) ? updateaptDescription.getText() : "";
        String location = SetLocationName();
        String contact = (updateaptContact.getText()!= null) ? updateaptContact.getText() : "";
        String type = setAptType();
        String url = (updateaptUrl.getText()!= null) ? updateaptUrl.getText() : "";
        String start = setAptStart();
        String end = setAptEnd();
        String date = setAptDate();
                
        //Need to Figure out Error if no drop down is selected
        if(start.equals("")|end.equals("")|date.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Apt Date");
                alert.setHeaderText("Need Appointment Date and Time");
                alert.setContentText("Please Selected Date and Time for Appointment");
                alert.showAndWait();              
        }else {
        
        String startDateTime = Appointment.getDateTime(date, start, location);
        String endDateTime = Appointment.getDateTime(date, end, location);
        
        
        try {
            exceptionMessage = Appointment.isAptValid(type, location, date, start, end, contact, url, title, description, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Customer");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else if(Appointment.appointmentAvialableUser(startDateTime, endDateTime) == false){
            // Appointment isn't avialbe due to User has another appointment at that time 
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Overlapping Appointment");
                alert.setHeaderText("User has another Appointment at this Time");
                alert.setContentText("Please select another time");
                alert.showAndWait();
            }else if (Appointment.appointmentAvialableCust(startDateTime, endDateTime, custId) == false){
           // Appointment isn't avialbe due to customer has another appointment at that time 
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Overlapping Appointment");
                alert.setHeaderText("Client has another Appointment at this Time");
                alert.setContentText("Please select another time");
                alert.showAndWait();
            }else {        
                Appointment.updateApt(aptId, title, description, location, contact, type, url, startDateTime, endDateTime);
        
                Parent UpdateCustomer = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(UpdateCustomer);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }   
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part");
            alert.setHeaderText("Error");
            alert.setContentText("Please Check Fields and make sure you fill out each field");
            alert.showAndWait();
        }               
    }}
    
    @FXML
    void updateAptCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel adding a appointment for client " + updateaptClientName.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent partsCancel = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(partsCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("Cancel has been clicked.");
        }
    }
    
     // Factory to create Cell of DatePicker
    private Callback<DatePicker, DateCell> getDayCellFactory() {
 
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
 
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
 
                        // Disable Saturyday and Sunday
                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY //
                            || item.getDayOfWeek() == DayOfWeek.SUNDAY
                            || item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #939696;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }
    
    public static final LocalDate setDate2(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
}
    
    @FXML
     public void SetDate() throws ParseException {
   
        String dateL = Appointment.getlocationDate(appointmentToLocation(), appointmentToStart());
        DatePicker newaptDate = new DatePicker();
        newaptDate.setValue(setDate2(dateL));
        
        updateaptDate.setValue(setDate2(dateL));
        newaptDate.setShowWeekNumbers(false);
     }
     
     public void startTime() throws ParseException{
         String time = Appointment.getlocationTime(appointmentToLocation(), appointmentToStart());
                 updateaptStartTime.getSelectionModel().select(time);
     }
     
     public void endTime() throws ParseException{
         String time = Appointment.getlocationTime(appointmentToLocation(), appointmentToEnd());
         updateaptEndTime.getSelectionModel().select(time);
     }
     

     public String customerName(int custID){
         //Customer.getAllCustomers();
        try {
            Statement statement = DataBase.conn.createStatement();
            String query = "SELECT * FROM customer WHERE customerId = '" + custID + "';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            String custname = rs.getString("customerName");

            statement.close();
            return custname;
        }catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
     }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {                  
        //customerName();
        //updateaptType.getSelectionModel().select(appointmentToType());
        //updateaptLocation.getSelectionModel().select(appointmentToLocation());
        updateaptType.setItems(AppointmentType);
        updateaptLocation.getItems().addAll(City.allCities);
        updateaptStartTime.setItems(AptStartTime);
        updateaptEndTime.setItems(AptEndTime);       
        try {
            startTime();
        } catch (ParseException ex) {
            Logger.getLogger(UpdateAptController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            endTime();
        } catch (ParseException ex) {
            Logger.getLogger(UpdateAptController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateaptContact.setText(appointmentToContact());
        updateaptUrl.setText(appointmentToUrl());
        updateaptTitle.setText(appointmentToTitle());
        updateaptDescription.setText(appointmentToDescription());
        updateaptClientName.setText(customerName(appointmentToCustId()));
        updateaptType.getSelectionModel().select(appointmentToType());
        updateaptLocation.getSelectionModel().select(LocationSet());
   
        try {            
            SetDate();
        } catch (ParseException ex) {
            Logger.getLogger(UpdateAptController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        updateaptDate.setDayCellFactory(dayCellFactory);
      
}
}


