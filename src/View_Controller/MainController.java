/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Address;
import Model.Appointment;
import static Model.Appointment.weekAppointments;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.CustomerTable;
import Model.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Heith
 */
public class MainController implements Initializable {
    
    @FXML
    private TableView<CustomerTable> MainCustomersTable;
    @FXML
    private TableColumn<CustomerTable, Integer> MainCustomerID;
    @FXML
    private TableColumn<CustomerTable, String> MainCustomerName;
    @FXML
    private TableColumn<CustomerTable, String> MainCustomerPhone;
    @FXML
    private TableColumn<CustomerTable, String> MainCustomerAddress;
    @FXML 
    private TableColumn<CustomerTable, String> MainCustomerCity;
    
    
    @FXML
    private TableView<Appointment> AllAptTable;
    @FXML
    private TableColumn<Appointment, Integer> AllAptID;
    @FXML
    private TableColumn<Appointment, Integer> AllClientID;
    @FXML
    private TableColumn<Appointment, String> AllLocation;
    @FXML
    private TableColumn<Appointment, String> AllStartTime;
    @FXML 
    private TableColumn<Appointment, String> AllEndTime;
    
    @FXML
    private TableView<Appointment> MonthAptTable;
    @FXML
    private TableColumn<Appointment, Integer> MonthAptID;
    @FXML
    private TableColumn<Appointment, Integer> MonthClientID;
    @FXML
    private TableColumn<Appointment, String> MonthLocation;
    @FXML
    private TableColumn<Appointment, String> MonthStartTime;
    @FXML 
    private TableColumn<Appointment, String> MonthEndTime;
    
    @FXML
    private TableView<Appointment> WeekAptTable;
    @FXML
    private TableColumn<Appointment, Integer> WeekAptID;
    @FXML
    private TableColumn<Appointment, Integer> WeekClientID;
    @FXML
    private TableColumn<Appointment, String> WeekLocation;
    @FXML
    private TableColumn<Appointment, String> WeekStartTime;
    @FXML 
    private TableColumn<Appointment, String> WeekEndTime;
    
    private static Customer updateCustomer;
    private static int updateCustomerId;
    private static String updateCustomerName;
    private static String updateCustomerPhone;
    private static String updateCustomerAddress;
    private static String updateCustomerAddress2;
    private static int updateCustomerCityId;
    private static String updateCustomerZip;
    private static int updateCustomerAddressId;
    @FXML
    private TextField MainCustomerSearch;
    
   
    private static Appointment updateAppointmnet;
    private static int updateAptId;
    private static int updateAptCustId;
    private static String updateAptCustName;
    private static String updateAptType;
    private static String updateAptLocation;
    private static String updateAptStart;
    private static String updateAptEnd;
    private static String updateAptContact;
    private static String updateAptUrl;
    private static String updateAptTitle;
    private static String updateAptDescription;
    
       
    public static int customerToCustomerId() {
        return updateCustomerId;
    }   
    public static String customerToCustName() {
        return updateCustomerName;
    }
     public static String customerToCustPhone() {
        return updateCustomerPhone;
    }
      public static String customerToCustAddress() {
        return updateCustomerAddress;
    }
       public static String customerToCustAddress2() {
        return updateCustomerAddress2;
    }
        public static int customerToCustCityId() {
        return updateCustomerCityId;
    }
        public static String customerToCustZip() {
        return updateCustomerZip;
    }
         public static int customertoCustAddressId(){
             return updateCustomerAddressId;
         }
      
    public static int appointmentToAppointmentId() {
        return updateAptId;
    }   
    public static int appointmentToCustId() {
        return updateAptCustId;
    } 
    public static String appointmentToCustName() {
        return updateAptCustName;
    } 
    public static String appointmentToType() {
        return updateAptType;
    }
     public static String appointmentToLocation() {
        return updateAptLocation;
    }
       public static String appointmentToStart() {
        return updateAptStart;
    }
        public static String appointmentToEnd() {
        return updateAptEnd;
    }
        public static String appointmentToContact() {
        return updateAptContact;
    }
        public static String appointmentToUrl(){
        return updateAptUrl;
    }
        public static String appointmentToTitle(){
        return updateAptTitle;
    }
        public static String appointmentToDescription(){
        return updateAptDescription;
    }

       
    @FXML
    void AddCustomer(ActionEvent event) throws IOException {
        Parent addCustomer = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(addCustomer);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    
    
    @FXML
    void UpdateCustomer(ActionEvent event) throws IOException {
        CustomerTable customer = MainCustomersTable.getSelectionModel().getSelectedItem();
        
        if(customer == null){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error editing Customer");
                alert.setHeaderText("No Customer Selected");
                alert.setContentText("Please selected a Customer to edit");
                alert.showAndWait();   
        }else{
                     
        updateCustomerId = customer.getCustomerId();
        updateCustomerName = customer.getCustomerName();
        updateCustomerPhone = customer.getPhone();
        updateCustomerAddress = customer.getAddress();
        updateCustomerAddress2 = customer.getAddress2();
        updateCustomerCityId = customer.getCityId();
        updateCustomerZip = customer.getPostalCode();
        updateCustomerAddressId = customer.getAddId();
        
        Parent UpdateCustomer = FXMLLoader.load(getClass().getResource("UpdateCustomer.fxml"));
        Scene scene = new Scene(UpdateCustomer);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        }        
    }
    
    
    //Delete Customer from database
    @FXML
    void DeleteCustomer(ActionEvent event) throws IOException {
        CustomerTable customer = MainCustomersTable.getSelectionModel().getSelectedItem();
        
        if(customer == null){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Deleting Customer");
                alert.setHeaderText("No Customer Selected");
                alert.setContentText("Please selected a Customer");
                alert.showAndWait();                              
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to client " + customer.getCustomerName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Customer.deleteCustomer(customer.getCustomerId(), customer.getAddId());            
                updateCustomerTable();
                System.out.println("Client " + customer.getCustomerName() + " was removed.");
            } else {
                System.out.println("Client " + customer.getCustomerName() + " was not removed.");
            } 
        }
    }
    
     @FXML
    void Reports (ActionEvent event) throws IOException {             
        Parent Report = FXMLLoader.load(getClass().getResource("Report.fxml"));
        Scene scene = new Scene(Report);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    
       @FXML
    void AddApt(ActionEvent event) throws IOException {
        CustomerTable customer = MainCustomersTable.getSelectionModel().getSelectedItem();
        if(customer == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Apt");
                alert.setHeaderText("Please select a client");
                alert.setContentText("Please selected a client to add a appointment");
                alert.showAndWait();              
        }else{
        
        updateCustomerId = customer.getCustomerId();
        updateCustomerName = customer.getCustomerName();
        
         
        
        Parent AddApt = FXMLLoader.load(getClass().getResource("AddApt.fxml"));
        Scene scene = new Scene(AddApt);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }}
       
    
    @FXML
    void UpdateApt(ActionEvent event) throws IOException {
        ArrayList<TableView> appointTableArray = new ArrayList<>();
    
        
        appointTableArray.add(AllAptTable);
        appointTableArray.add(MonthAptTable);
        appointTableArray.add(WeekAptTable);
                      
        AtomicReference<Appointment> appointmentArray = new AtomicReference<>();
        
        //Lambda get selected from any of the Appointment tables
        appointTableArray.forEach((appointmentTable)->{            
            if(appointmentTable.getSelectionModel().getSelectedItem() != null){                
            appointmentArray.set((Appointment) appointmentTable.getSelectionModel().getSelectedItem());        
            }
            
        });
        if (appointmentArray.get() != null ) {
            Appointment appointment = appointmentArray.get();
            
            updateAptId = appointment.getAppointmentId();
            updateAptCustId = appointment.getCustomerId();
            updateAptTitle = appointment.getTitle();
            updateAptDescription = appointment.getDescription();
            updateAptLocation = appointment.getLocation();
            updateAptContact = appointment.getContact();
            updateAptUrl = appointment.getUrl();
            updateAptStart = appointment.getStart();
            updateAptEnd = appointment.getEnd();
            updateAptType = appointment.getType();

            Parent UpdateApt = FXMLLoader.load(getClass().getResource("UpdateApt.fxml"));
            Scene scene = new Scene(UpdateApt);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Edditing Appointment");
                alert.setHeaderText("No Appointment Selected");
                alert.setContentText("Please selected an appointment to edit");
                alert.showAndWait();              
                throw new IOException("No Appointment Selected");
        }

        
    }
    
    //Get info to Delete Apt
       @FXML
    void DeleteApt(ActionEvent event) throws IOException, ParseException {
        Appointment appointment = AllAptTable.getSelectionModel().getSelectedItem();
        
        if(appointment == null){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Deleting Appointment");
                alert.setHeaderText("No Appointment Selected");
                alert.setContentText("Please selected an Appointment");
                alert.showAndWait(); 
        }else{
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you sure you want to delete apt for client " + customerName(appointment.getCustomerId()) + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Appointment.deleteapt(appointment.getAppointmentId());  
            updateAppointmentTable();
            System.out.println("Appointment for client " + customerName(appointment.getCustomerId()) + " was removed.");
            
        } else {
            System.out.println("Appointment for client " + customerName(appointment.getCustomerId()) + " was not removed.");
        } 
        }
    }
    
    public String customerName(int custID){         
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
    
    public void searchCustomers(){
        ObservableList<CustomerTable>filteredList;
        filteredList = FXCollections.observableArrayList();
        String search = MainCustomerSearch.getText();
        for (CustomerTable customer : CustomerTable.getCustomersTable()){
           String name = customer.getCustomerName(); 
           if (name.contains(search)){
            filteredList.add(customer);
        } 
           MainCustomersTable.setItems(filteredList);
        }
    }

    //Displays/sets Customers table
    public void updateCustomerTable(){
        Address.getAllAddress();        
        City.getAllCities();
        Country.getAllCountries();
        Customer.getAllCustomers();
        MainCustomersTable.setItems(CustomerTable.getCustomersTable());
    }

    //Displays/sets appointment for User All, Month, and Week. 
    public void updateAppointmentTable() throws ParseException {
        Appointment.getAllAppointments();
        Appointment.getMonthAppointments();
        Appointment.getWeekAppointments();
        AllAptTable.setItems(Appointment.getAllAppointments());  
        MonthAptTable.setItems(Appointment.getMonthAppointments());
        WeekAptTable.setItems(Appointment.getWeekAppointments());
      }
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    
    //Checks If there is an Appointment within 15 min
    public void checkForAppt() throws SQLException, ParseException{
	LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = LocalDateTime.now().plusMinutes(15);
                      
        String sNow = now.format(formatter);
        String sFuture = future.format(formatter);
        String location = "UTC";
        
        //Taking Current Time and 15 min and switching into "UTC" Time
        String sNowU = Appointment.getUTCLocationDateTime(location, sNow);
        String sFutureU = Appointment.getUTCLocationDateTime(location, sFuture);
               
        //Check against database if there are any appointments within 15 min. 
        if (Appointment.appointmentAvialableUser(sNowU,sFutureU) == false){
            //Message for Appointment within 15 min
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment");
            alert.setHeaderText("Pending Appointment");
            alert.setContentText("You have an Appointment with 15 min");
            alert.showAndWait();
        }      
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateCustomerTable();
        try {
            updateAppointmentTable();
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            checkForAppt();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MainCustomerID.setCellValueFactory(new PropertyValueFactory("customerId"));
        MainCustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
        MainCustomerPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        MainCustomerAddress.setCellValueFactory(new PropertyValueFactory("address"));
        MainCustomerCity.setCellValueFactory(new PropertyValueFactory("city"));
        
        AllAptID.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        AllClientID.setCellValueFactory(new PropertyValueFactory("customerId"));
        AllLocation.setCellValueFactory(new PropertyValueFactory("location"));
        AllStartTime.setCellValueFactory(new PropertyValueFactory("start"));
        AllEndTime.setCellValueFactory(new PropertyValueFactory("end"));
        
        MonthAptID.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        MonthClientID.setCellValueFactory(new PropertyValueFactory("customerId"));
        MonthLocation.setCellValueFactory(new PropertyValueFactory("location"));
        MonthStartTime.setCellValueFactory(new PropertyValueFactory("start"));
        MonthEndTime.setCellValueFactory(new PropertyValueFactory("end"));
        
        WeekAptID.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        WeekClientID.setCellValueFactory(new PropertyValueFactory("customerId"));
        WeekLocation.setCellValueFactory(new PropertyValueFactory("location"));
        WeekStartTime.setCellValueFactory(new PropertyValueFactory("start"));
        WeekEndTime.setCellValueFactory(new PropertyValueFactory("end"));
    }    
    
}
