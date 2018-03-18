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
    
    protected LoginModel loginModel = new LoginModel();
    @FXML private Label isConnected;
    @FXML private TextField userTextField;
    @FXML private TextField pwTextField;
    @FXML private Label errorLabel;
    @FXML private Button register;
    @FXML private Button login;
    
    public void Login(ActionEvent event){
        try {
                String username = userTextField.getText();
                String password = pwTextField.getText();
                String[] data = loginModel.getData(username, password);
                
            if(data!=null){
            try {
                    //load new screen
                    FXMLLoader loader = new FXMLLoader();
                    Parent mainMenu = loader.load(getClass().getResource("STMain.fxml").openStream());
                    //pass on user info
                    STMainController main = (STMainController)loader.getController();
                    main.setUser(checkType(data[4], username, password));
                    main.setName(data[2], data[3]);
                    main.setAllowance(data[5]);
                    Scene mainMenuScene = new Scene(mainMenu);
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(mainMenuScene);
                    window.show();
                    loginModel.connection.close();
                    
                } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(username.equals("")||password.equals("")){
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
    
    private User checkType(String type, String user, String pass){
        
        if(type.equals("Student")){
               Student s = new Student(user, pass);
               return s;
           }else if(type.equals("Teacher")){
               Teacher t = new Teacher(user, pass);
               return t;
           }else{
               Administrator a = new Administrator(user, pass);
               return a;
           }
    }
}
