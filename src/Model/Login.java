/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Heith
 */
public class Login {
    
    private static final String FILENAME = "logins.txt";
    
     public static void log (String username, String location, String dateTime, boolean success) {
        try (FileWriter fw = new FileWriter(FILENAME, true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println ("UserName:" + username + " Location:" + location + " DateTime:" + dateTime + " " + (success ? " Success" : " Failure"));
        } catch (IOException e) {
            System.out.println("Logger Error: " + e.getMessage());
        }
}      
}
