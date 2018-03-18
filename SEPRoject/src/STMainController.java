/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AliyahButt1
 */
public class STMainController implements Initializable {

    @FXML private Label nameLabel; 
    @FXML private Button logout;
    @FXML private Label allowLabel;
    @FXML private Label typeLabel;
    
    User user;
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void Logout(ActionEvent event) throws IOException{
        user = null;
        
        Parent loginMenu = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
        Scene loginScene = new Scene(loginMenu);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();        
    }
    
    public void setName(String fname, String lname){
        nameLabel.setText(fname + " " + lname);
    }
    
    public void setAllowance(String allow){
        allowLabel.setText("Allowance: " + allow + "hours");
        user.setAllowance(Integer.parseInt(allow));
    }

    public void setUser(User user){
        this.user = user;
        
        if(user instanceof Teacher) typeLabel.setText("Teacher");
        else if(user instanceof Student) typeLabel.setText("Student");
        
        
    }    
    
}
