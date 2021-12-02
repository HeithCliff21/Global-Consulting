/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.City;
import Model.Customer;
import static View_Controller.MainController.customerToCustAddress;
import static View_Controller.MainController.customerToCustAddress2;
import static View_Controller.MainController.customerToCustCityId;
import static View_Controller.MainController.customerToCustName;
import static View_Controller.MainController.customerToCustPhone;
import static View_Controller.MainController.customerToCustZip;
import static View_Controller.MainController.customerToCustomerId;
import static View_Controller.MainController.customertoCustAddressId;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Heith
 */
public class UpdateCustomerController implements Initializable {
    
    @FXML
    private TextField UpdateCustomerName;
    
    @FXML
    private TextField UpdateCustomerPhone;
    
    @FXML
    private TextField UpdateCustomerAddress;
    
    @FXML
    private TextField UpdateCustomerAddress2;
    
    @FXML
    private TextField UpdateCustomerZipCode;
    
    @FXML
    private TextField UpdateCustomerCountry;
    
    @FXML
    private ComboBox<City> UpdateCustomerCity;
    
    @FXML
    private Button UpdateCustomerSave;
    
    @FXML
    private Button UpdateCustomerCancel;
    
    private String exceptionMessage = new String();
      
    @FXML
    public void SetCountry(ActionEvent event) {
        CountrySet();
}
     public void CountrySet(){
         City city = UpdateCustomerCity.getSelectionModel().getSelectedItem();
        int cityId = city.getId();
        City.setSelectedId(cityId);
    
        if(cityId == 3) {
            UpdateCustomerCountry.setText("England");
        } else {
            UpdateCustomerCountry.setText("United States");       
        }
     }
    
     // Update Customer record 
    @FXML
    void UpdateCustomerSave(ActionEvent event) throws IOException {
        // Retrieves Values from text boxes
        String Name = UpdateCustomerName.getText();
        String Phone = UpdateCustomerPhone.getText();
        String Address = UpdateCustomerAddress.getText();
        String Address2 = UpdateCustomerAddress2.getText();
        String Zip = UpdateCustomerZipCode.getText();
        int cityId = City.getSelectedId();
        int addressId = customertoCustAddressId();
        int customerId = customerToCustomerId();
        
        try {
            exceptionMessage = Customer.isClientValid(Name, Phone ,Address,cityId, Zip, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Customer");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else {        
                Customer.updateCustomer(customerId, addressId, Name, Address, Address2, cityId, Zip, Phone);
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
        alert.setContentText("Please Check Fields all fields have values");
        alert.showAndWait();
        }
    }
        
    @FXML
    void UpdateCustomerCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel Modifying Part");
        alert.setContentText("Are you sure you want to cancel Client update for " + UpdateCustomerName.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Part add has been cancelled.");
            Parent partsCancel = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(partsCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("You clicked cancel. Please complete part info.");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateCustomerCity.getItems().addAll(City.allCities);
        UpdateCustomerName.setText(customerToCustName());
        UpdateCustomerPhone.setText(customerToCustPhone());
        UpdateCustomerAddress.setText(customerToCustAddress());
        UpdateCustomerAddress2.setText(customerToCustAddress2());
        UpdateCustomerZipCode.setText(customerToCustZip());
        try {
            City city = City.lookupCity(customerToCustCityId());
            UpdateCustomerCity.getSelectionModel().select(city);
            CountrySet();
        } catch (Exception ex) {
            Logger.getLogger(UpdateCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
}

