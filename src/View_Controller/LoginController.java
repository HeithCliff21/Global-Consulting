/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.User;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Heith
 */
public class LoginController {
    
    @FXML
    private Label localLogin;
    @FXML
    private Label localUsername;
    @FXML
    private Label localPassword;
    @FXML
    private Button localSubmitbtn;
    @FXML
    private Button localCancelbtn;
    @FXML
    private Label localErrorMessage;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
   

  
        
    
 @FXML
            //Setting Language Default is English Norweigen is thee other language
    private void setLanguage () {
     ResourceBundle rb = ResourceBundle.getBundle("Languages/Login", Locale.getDefault());
     localLogin.setText(rb.getString("Login"));
     localUsername.setText(rb.getString("Username"));
     localPassword.setText(rb.getString("Password"));
     localSubmitbtn.setText(rb.getString("Submit"));
     localCancelbtn.setText(rb.getString("Cancel"));
    }
    
    private void setError() {
        ResourceBundle rb = ResourceBundle.getBundle("Languages/Login", Locale.getDefault());
        localErrorMessage.setText(rb.getString("Error"));
    }
    
            // Submit log-in credentials to be checked
    @FXML
    private void Submitbtn(ActionEvent event) throws SQLException, Exception {
        // Retrieves user's inputs and clears password field
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
            // Check credentials against database
        boolean checkCredentials = User.login(userName, password);
            // Check if credentials were correct
        if (checkCredentials) {
            // Show main screen 
        Parent MainScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(MainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
            } else {
                // Display Error Message
                setError();
            }
        }   
    
    @FXML
    private void Cancelbtn(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you wanting to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }      
    }
    
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        System.err.println(Locale.getDefault());
        setLanguage();
    }    
}
