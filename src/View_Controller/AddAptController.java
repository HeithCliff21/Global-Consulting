/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.City;
import Model.Customer;
import static View_Controller.MainController.customerToCustName;
import static View_Controller.MainController.customerToCustomerId;
import static View_Controller.MainController.customertoCustAddressId;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import static java.util.Optional.empty;
import java.util.ResourceBundle;
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
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Heith
 */
public class AddAptController implements Initializable {
    
    @FXML
    private TextField newaptClientName;
    @FXML
    private ComboBox newaptAppointmentType;
    @FXML
    private DatePicker newaptDate;
    @FXML
    private ComboBox newaptStartTime;
    @FXML
    private ComboBox newaptEndTime;
    @FXML
    private ComboBox<City> newaptLocation;
    @FXML
    private TextField newaptUrl;
    @FXML
    private TextField newaptTitle;
    @FXML
    private TextArea newaptDescription;
    @FXML
    private TextField newaptContact;
    @FXML
    private Button newAptSave;
    @FXML
    private Button newAptCancel;
    
    private String exceptionMessage = new String();
    
    //Using the C Global Consulting Type of Consulting as references for a global consulting agency
    private final ObservableList<String> AppointmentType = FXCollections.observableArrayList("One hand Back Scratch" , "Two hand Back Scratch");
    
    private final ObservableList<String> AptStartTime = FXCollections.observableArrayList("8:00 AM","8:30 AM","9:00 AM","9:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","1:00 PM","1:30 PM","2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM");
    
    private final ObservableList<String> AptEndTime = FXCollections.observableArrayList("8:30 AM","9:00 AM","9:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM","1:00 PM","1:30 PM","2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM","5:00 PM");
    
    public String setAptType(){
        String Type = (String) newaptAppointmentType.getSelectionModel().getSelectedItem();
        if (Type != null){
        return Type;
        } else{
            return "";
        }
    }
    //Set Location City Object to String to use for correct DateTime Format
    public String SetLocationName(){
        City city = newaptLocation.getSelectionModel().getSelectedItem();
        if(city != null){       
        int cityId = city.getId();
            City.setSelectedId(cityId);

            if(cityId == 1) {
                String Location = "America/Phoenix";
                return Location;
            }else if(cityId == 2){
                String Location = "America/New_York";
                return Location;           
            }
        }
            String Location = "Europe/London";
            return Location;         
    }
    public String setAptStart() throws ParseException{
        String Start = (String) newaptStartTime.getSelectionModel().getSelectedItem();
            if(Start != null){
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");

            //Takes Start Time selected and changes to 24 Hour Format
            String time24 = date24Format.format(date12Format.parse(Start));   	
            return time24;
            } else{
                return "";
            }
    }
    
    
    public String setAptEnd() throws ParseException{
        String End = (String) newaptEndTime.getSelectionModel().getSelectedItem();
            if (End != null){
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");

            //Takes End Time selected and changes to 24 Hour Format
            String time24 = date24Format.format(date12Format.parse(End));
            return time24;
            } else {
                return "";
            }
    }
    
    public String setAptDate(){
        LocalDate Date = newaptDate.getValue();        
        if (Date != null){
            String Date2 = Date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return Date2;
        } else{
            return "";
        }
    }
    
    //Get info to Save apt into database
     @FXML
    void newAptSave(ActionEvent event) throws IOException, ParseException, SQLException {        
        int customerId = customerToCustomerId();
        String title = (newaptTitle.getText() != null) ? newaptTitle.getText() : "";
        String description = (newaptDescription.getText() != null) ? newaptDescription.getText() : "";
        String location = SetLocationName();
        String contact = (newaptContact.getText()!= null) ? newaptContact.getText() : "";
        String type = setAptType();
        String url = (newaptUrl.getText()!= null) ? newaptUrl.getText() : "";
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
        } else{     
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
            }else if (Appointment.appointmentAvialableCust(startDateTime, endDateTime, customerId) == false){
           // Appointment isn't avialbe due to customer has another appointment at that time 
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Overlapping Appointment");
                alert.setHeaderText("Client has another Appointment at this Time");
                alert.setContentText("Please select another time");
                alert.showAndWait();
            }else {        
                Appointment.addApt(customerId, title, description, location, contact, type, url, startDateTime, endDateTime);
        
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
    void newAptCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel adding a appointment for client '" + newaptClientName.getText() + "'?");
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
    
    @FXML
     public void SetDate() {
        DatePicker newaptDate = new DatePicker();
        
        newaptDate.setValue(LocalDate.now());
        newaptDate.setShowWeekNumbers(false);
        
     }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newaptAppointmentType.setItems(AppointmentType);
        newaptLocation.getItems().addAll(City.allCities);
        newaptStartTime.setItems(AptStartTime);
        newaptEndTime.setItems(AptEndTime);
        newaptClientName.setText(customerToCustName());     
        SetDate();
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        newaptDate.setDayCellFactory(dayCellFactory);
}
}