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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TableView<RoomBooking> table;
    @FXML private TableColumn<RoomBooking, Integer> idCol;
    @FXML private TableColumn<RoomBooking, String> buildCol;
    @FXML private TableColumn<RoomBooking, String> roomCol;
    @FXML private TableColumn<RoomBooking, String> dateCol;
    @FXML private TableColumn<RoomBooking, String> sTimeCol;
    @FXML private TableColumn<RoomBooking, String> eTimeCol;
    
    
    protected STMainModel model = new STMainModel();
    User user;
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void Logout(ActionEvent event) throws IOException{
        try {
            user = null;
            Parent loginMenu = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
            Scene loginScene = new Scene(loginMenu);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.show();
            model.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(STMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void viewBookings() throws SQLException{
        ObservableList<RoomBooking> bookings = model.getBooking(user.getName());
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        buildCol.setCellValueFactory(new PropertyValueFactory<>("building"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        sTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        eTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        table.setItems(bookings);
    }
}
