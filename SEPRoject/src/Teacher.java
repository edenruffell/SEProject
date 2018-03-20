import com.jfoenix.controls.*;
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

public class Teacher extends User implements Initializable {

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

    @FXML private Pane searchTimePane;
    @FXML private ComboBox siteBox;
    @FXML private ComboBox buildingBox;
    @FXML private ComboBox roomBox;
    @FXML private JFXButton findTimeButton;
    @FXML private JFXDatePicker datePicker;
    @FXML private TableView<Room> resultsTable;
    @FXML private TableColumn<Room, String> rnameCol;
    @FXML private TableColumn<Room, String> capacityCol;
    @FXML private TableColumn<Room, String> computerCol;
    
    private String selectedSite;
    private String selectedBuilding;
    private String selectedRoom;
    private String selectedDate;
    
    protected ObservableList<RoomBooking> bookings;
    protected ObservableList<String> siteList;
    protected ObservableList<String> buildingList = FXCollections.observableArrayList();
    protected ObservableList<String> roomList = FXCollections.observableArrayList();
    protected ObservableList<Room> rooms;
    protected StudentModel model = new StudentModel();
    
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchTimePane.setVisible(false);
        viewPane.setVisible(false);
        try {
            siteList = model.getSites();
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        siteBox.setItems(siteList);
    }
    
    @Override
    public void logout(ActionEvent event) throws IOException{
        try {
            Parent loginMenu = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
            Scene loginScene = new Scene(loginMenu);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.show();
            model.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setName(){
        nameLabel.setText(fName + " " + lName);
    }
    
    public void setAllowanceText(String allow){
        allowLabel.setText("Allowance: " + allow + " hours");
    }

    @Override
    public void setUserDetails(String[] data){

        username = data[0];
        password = data[1];
        fName = data[2];
        lName = data[3];
        userType = data[4];
        allowance = Integer.parseInt(data[5]);
        typeLabel.setText(userType); 
        
    }
    
    @Override
    public void updateAllowance(RoomBooking booking){
        int add = booking.getHoursBooked();
        allowance = allowance + add;
        setAllowanceText(Integer.toString(allowance));
    }

    @Override
    public void viewBookings() throws SQLException{
        searchTimePane.setVisible(false);
        viewPane.setVisible(true);
        bookings = model.getBooking(username);
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        buildCol.setCellValueFactory(new PropertyValueFactory<>("building"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        sTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        eTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        bookingTable.setItems(bookings);   
    }
    
    public void cancelBooking() throws SQLException{
        try{
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
            RoomBooking booking = bookings.get(selectedIndex);
            updateAllowance(booking);
            model.update(allowance, username);
            model.removeBooking(booking.getID());
            bookingTable.getItems().remove(selectedIndex);
            
        }catch(Exception a){
            errorLabel.setText("No bookings have been selected.");            
        }     
    }
    
    public void search(){
        viewPane.setVisible(false);
        searchTimePane.setVisible(true);
    }
    
    public void setBuildings() throws SQLException{
        try{
            selectedSite = siteBox.getSelectionModel().getSelectedItem().toString();
            buildingList = model.getBuildings(selectedSite);
            buildingBox.setItems(buildingList);
        }catch(NullPointerException e){
            searchError.setText("Please select a site."); 
        }
    }
    
    public void setRooms() throws SQLException{
        try{
            selectedBuilding = buildingBox.getSelectionModel().getSelectedItem().toString();
            roomList = model.getRooms(selectedBuilding);
            roomBox.setItems(roomList);
        }catch(NullPointerException e){
            searchError.setText("Please select a building."); 
        }
    }
    
    public void clearLabel(){
        searchError.setText("");
    }
       
    public void searchRooms() throws SQLException{
        try{
            selectedRoom = roomBox.getSelectionModel().getSelectedItem().toString();
        }catch(NullPointerException e){
            searchError.setText("Please select a room.");
        }
        try{
            selectedDate = datePicker.getValue().toString();
            rooms = model.searchRooms(selectedSite, selectedBuilding, selectedRoom, selectedDate);
            rnameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            capacityCol.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
            computerCol.setCellValueFactory(new PropertyValueFactory<>("Computers"));
            resultsTable.setItems(rooms);
        }catch(NullPointerException e){
            searchError.setText("Please select a date.");
        }        
    }      
}

