/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author AliyahButt1
 */
public class LoginController implements Initializable {
    
    LoginModel loginModel = new LoginModel();
    @FXML private Label isConnected;
    @FXML private TextField userTextField;
    @FXML private TextField pwTextField;
    @FXML private Label errorLabel;
    @FXML private Button register;
    @FXML private Button login;
    
    /**
     * Initialises the controller class.
     */
    
    
  /*  public void showMainMenu(ActionEvent event){
        if(userTextField.getText().equals("")||pwTextField.getText().equals("")){
            errorLabel.setText("Username or password cannot be blank.");
        }else if(userTextField.getText().equals("a")&&pwTextField.getText().equals("a")){
                
                try {
                    Parent mainMenu = FXMLLoader.load(getClass().getResource("STMain.fxml"));
                    Scene mainMenuScene = new Scene(mainMenu);
            
                    //get Stage
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            
                    window.setScene(mainMenuScene);
                    window.show();
                } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }else errorLabel.setText("Incorrect username or password. Please try again."); 
    }*/
    
    public void Login(ActionEvent event){
        try {
            if(loginModel.isLogin(userTextField.getText(), pwTextField.getText())){
            try {
                    Parent mainMenu = FXMLLoader.load(getClass().getResource("STMain.fxml"));
                    Scene mainMenuScene = new Scene(mainMenu);
            
                    //get Stage
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            
                    window.setScene(mainMenuScene);
                    window.show();
                } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(userTextField.getText().equals("")||pwTextField.getText().equals("")){
            errorLabel.setText("Username or password cannot be blank.");
        }else errorLabel.setText("Incorrect username or password. Please try again.");
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(loginModel.isConnected()) isConnected.setText("Connected");
        else isConnected.setText("Not connected");
    }
}
