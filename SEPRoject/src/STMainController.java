/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AliyahButt1
 */
public class STMainController implements Initializable {

    @FXML private Label nameLabel; 
  //  @FXML private Button requests;
    @FXML private Button logout;
    @FXML private Label allowLabel;
    @FXML private Label typeLabel;
    @FXML private Label errorLabel;
    @FXML private Label searchError;
    
    @FXML private Pane viewPane;
    @FXML private Button cancel;
    @FXML private TableView<RoomBooking> bookingTable;
    @FXML private TableColumn<RoomBooking, Integer> idCol;
    @FXML private TableColumn<RoomBooking, String> buildCol;
    @FXML private TableColumn<RoomBooking, String> roomCol;
    @FXML private TableColumn<RoomBooking, String> dateCol;
    @FXML private TableColumn<RoomBooking, String> sTimeCol;
    @FXML private TableColumn<RoomBooking, String> eTimeCol;

    @FXML private Pane searchPane;
    @FXML private ComboBox siteBox;
    @FXML private ComboBox buildingBox;
    @FXML private ComboBox roomBox;
    @FXML private JFXButton findRooms;
    @FXML private TableView<Room> resultsTable;
    @FXML private TableColumn<Room, String> siteCol;
    @FXML private TableColumn<Room, String> rbuildingCol;
    @FXML private TableColumn<Room, String> rnameCol;
    @FXML private TableColumn<Room, String> capacityCol;
    @FXML private TableColumn<Room, String> computerCol;
    
    protected ObservableList<RoomBooking> bookings;
    protected ObservableList<String> siteList;
    protected ObservableList<String> buildingList = FXCollections.observableArrayList();
    protected ObservableList<String> roomList = FXCollections.observableArrayList();
    protected ObservableList<Room> rooms;
    protected STMainModel model = new STMainModel();
    
    User user;
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchPane.setVisible(false);
        viewPane.setVisible(false);
        try {
            siteList = model.getSites();
        } catch (SQLException ex) {
            Logger.getLogger(STMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        siteBox.setItems(siteList);
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
        allowLabel.setText("Allowance: " + allow + " hours");
        user.setAllowance(Integer.parseInt(allow));
    }

    public void setUser(User user){
        this.user = user;        
        if(user instanceof Teacher) typeLabel.setText("Teacher");
        else if(user instanceof Student) typeLabel.setText("Student");        
    }
    

    
    public void viewBookings() throws SQLException{
        searchPane.setVisible(false);
        viewPane.setVisible(true);
        bookings = model.getBooking(user.getName());
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        buildCol.setCellValueFactory(new PropertyValueFactory<>("building"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        sTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        eTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        bookingTable.setItems(bookings);   
    }
    
    public void search(){
        viewPane.setVisible(false);
        searchPane.setVisible(true);
    }
    
    public void setBuildings() throws SQLException{
        try{
            String selected = siteBox.getSelectionModel().getSelectedItem().toString();
            buildingList = model.getBuildings(selected);
            buildingBox.setItems(buildingList);
        }catch(NullPointerException e){
            searchError.setText("Please select a site."); 
        }
    }
    
    public void setRooms() throws SQLException{
        try{
            String selected = buildingBox.getSelectionModel().getSelectedItem().toString();
            roomList = model.getRooms(selected);
            roomBox.setItems(roomList);
        }catch(NullPointerException e){
            searchError.setText("Please select a building."); 
        }
    }
    
     public void searchRooms() throws SQLException{        
      //  rooms = model.searchRooms();
        siteCol.setCellValueFactory(new PropertyValueFactory<>("Site"));
       // siteCol.setCellValueFactory(new PropertyValueFactory<>("site"));
        rbuildingCol.setCellValueFactory(new PropertyValueFactory<>("Building"));
        rnameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
        computerCol.setCellValueFactory(new PropertyValueFactory<>("Computers"));
        resultsTable.setItems(rooms);
        
        
    }

    public void cancelBooking() throws SQLException{
        try{
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
            RoomBooking booking = bookings.get(selectedIndex);
            model.update(updateAllowance(booking), user.getName());
            model.removeBooking(booking.getID());
            bookingTable.getItems().remove(selectedIndex);
            
        }catch(Exception a){
            errorLabel.setText("No bookings have been selected.");            
        }     
    }
    
    private int updateAllowance(RoomBooking booking){
        int old = user.getAllowance();
        int add = booking.getHoursBooked();
        int updated = old + add;
        setAllowance(Integer.toString(updated));
        
        return updated;
    }    
}
