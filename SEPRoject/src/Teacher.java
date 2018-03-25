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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AliyahButt1
 */
public class Teacher extends User implements Initializable {

    @FXML private Label nameLabel;
    @FXML private Label message;
  //  @FXML private Button requests;
    @FXML private Button logout;
    @FXML private Button settings;
    @FXML private Label allowLabel;
    @FXML private Label typeLabel;
    @FXML private Label errorLabel;
    @FXML private Label searchError;
    @FXML private Label setRoom;
    @FXML private Label setBuilding;
    @FXML private Label setDate;
    @FXML private Label setStart;
    @FXML private Label setEnd;
    @FXML private JFXButton confirmBooking;
    @FXML private Label heading;
    
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
    @FXML private Label compLabel;
    @FXML private Label capLabel;
    @FXML private JFXComboBox siteBox;
    @FXML private JFXComboBox buildingBox;
    @FXML private JFXComboBox roomBox;
    @FXML private JFXButton findTimeButton;
    @FXML private JFXButton back;
    @FXML private JFXDatePicker datePicker;
    @FXML private TableView<Room.Time> resultsTable;
    @FXML private TableColumn<Room, String> timeCol;
    @FXML private TableColumn<Room, String> availableCol;
    @FXML private JFXPopup popup;
    @FXML private Pane bookingPane;
    
    @FXML private Pane detailsPane;
    @FXML private Label usernameLabel;
    @FXML private JFXPasswordField oldpw;
    @FXML private JFXPasswordField newpw;
    @FXML private JFXPasswordField retypepw;
    @FXML private JFXButton savepw;
    
    
    private String selectedSite;
    private String selectedBuilding;
    private String selectedRoom;
    private String selectedDate;

    protected ObservableList<RoomBooking> bookings;
    protected ObservableList<String> siteList;
    protected ObservableList<String> buildingList = FXCollections.observableArrayList();
    protected ObservableList<String> roomList = FXCollections.observableArrayList();
    protected ObservableList<Room.Time> times = FXCollections.observableArrayList();
    protected TeacherModel model = new TeacherModel();
    
    private Room room;
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        detailsPane.setVisible(true);
        searchTimePane.setVisible(false);
        bookingPane.setVisible(false);
        viewPane.setVisible(false);
        try {
            siteList = model.getSites();
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        siteBox.setItems(siteList);
        initPopUp();
    }
    
    public void settings(){
        detailsPane.setVisible(true);
        searchTimePane.setVisible(false);
        bookingPane.setVisible(false);
        viewPane.setVisible(false);  
    }
    
    public void updatePW() throws SQLException{
        String oldpw = this.oldpw.getText();
        String newpw = this.newpw.getText();
        String retype = this.retypepw.getText();
        
        if(oldpw.equals("")||newpw.equals("")||retype.equals(""))
            message.setText("Password fields cannot be empty");
        else if(!oldpw.equals(password))
            message.setText("Old password is incorrect");
        else if(!newpw.equals(retype))
            message.setText("New password doesn't match retype.");
        else{
            model.updatePW(username, password);
            message.setText("Password has been changed.");
        }
    }

    @Override
    public void logout(ActionEvent event) throws IOException {
        try {
            Parent loginMenu = FXMLLoader.load(getClass().getResource("RegistrantView.fxml"));
            Scene loginScene = new Scene(loginMenu);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.show();
            model.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setName() {
        nameLabel.setText(fName + " " + lName);
    }

    public void setAllowanceText(String allow) {
        allowLabel.setText("Allowance: " + allow + " hours");
    }

    @Override
    public void setUserDetails(String[] data) {

        username = data[0];
        password = data[1];
        fName = data[2];
        lName = data[3];
        userType = data[4];
        allowance = Integer.parseInt(data[5]);
        typeLabel.setText("Account Type: " + userType);
        usernameLabel.setText("Username " + username);
    }

    @Override
    public void updateAllowance(int hours) {
        allowance = allowance + hours;
        setAllowanceText(Integer.toString(allowance));
    }

    @Override
    public void viewBookings() throws SQLException {
        detailsPane.setVisible(false);
        searchTimePane.setVisible(false);
        bookingPane.setVisible(false);
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

    public void cancelBooking() throws SQLException {
        try {
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
            RoomBooking booking = bookings.get(selectedIndex);
            updateAllowance(booking.getHoursBooked());
            model.updateAllowanceDB(allowance, username);
            model.removeBooking(booking.getID());
            bookingTable.getItems().remove(selectedIndex);

        } catch (Exception a) {
            errorLabel.setText("No bookings have been selected.");
        }
    }

    public void search() {
        detailsPane.setVisible(false);
        searchTimePane.setVisible(true);
        bookingPane.setVisible(false);
        viewPane.setVisible(false); 
    }

    public void setBuildings() throws SQLException {
        try {
            selectedSite = siteBox.getSelectionModel().getSelectedItem().toString();
            buildingList = model.getBuildings(selectedSite);
            buildingBox.setItems(buildingList);
        } catch (NullPointerException e) {
            searchError.setText("Please select a site.");
        }
    }

    public void setRooms() throws SQLException {
        try {
            selectedBuilding = buildingBox.getSelectionModel().getSelectedItem().toString();
            roomList = model.getRooms(selectedBuilding);
            roomBox.setItems(roomList);
        } catch (NullPointerException e) {
            searchError.setText("Please select a building.");
        }
    }

    public void clearLabel() {
        searchError.setText("");
    }
       
    public void searchRooms() throws SQLException{
        resultsTable.getItems().clear();
        try{
            selectedRoom = roomBox.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            searchError.setText("Please select a room.");
        }
        try {
            selectedDate = datePicker.getValue().toString();
            room = model.searchRooms(selectedSite, selectedBuilding, selectedRoom, selectedDate);
            addTimes(room);
            timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));
            availableCol.setCellValueFactory(new PropertyValueFactory<>("Available"));
            resultsTable.setItems(times);
            capLabel.setText("Capacity: " + room.getCapacity());
            compLabel.setText("Computers:" + room.getComputers());
        }catch(NullPointerException e){
            searchError.setText("Please select a date.");
            return;
        }        
    }
    
    public void addTimes(Room r){
        Room.Time[] list = r.getTimeArray();
        for(int i=0; i<list.length; i++){
            times.add(list[i]);
        }     
    }

    @Override
    public void makeBooking() throws SQLException {
        model.saveBooking(room, setDate.getText(), this.username, setStart.getText(), setEnd.getText());
        updateAllowance(-1);
        model.updateAllowanceDB(allowance, username);
        room = model.searchRooms(selectedSite, selectedBuilding, selectedRoom, selectedDate);
        resultsTable.getItems().clear();
        addTimes(room);
        heading.setText("Booking confirmed!");
        confirmBooking.setDisable(true);  
    }
    
    public void showPopup(MouseEvent e){
        String currentRoom = roomBox.getSelectionModel().getSelectedItem().toString();
        int index = resultsTable.getSelectionModel().getSelectedIndex();
        if (resultsTable.getItems().isEmpty()){}
        else if(!times.get(index).isAvailable()){}
        else if(!currentRoom.equals(room.getName())) searchError.setText("Results do not match current room. Please press \"Find Times\" again.");
        else popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, e.getX(), e.getY());
    }

    private void initPopUp() {
        JFXButton b = new JFXButton("Make Booking?");
        b.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int index = resultsTable.getSelectionModel().getSelectedIndex();
            popup.close();
            setBuilding.setText(buildingBox.getSelectionModel().getSelectedItem().toString());
            setRoom.setText("Room: " + roomBox.getSelectionModel().getSelectedItem().toString());
            setDate.setText(datePicker.getValue().toString());
            setStart.setText(times.get(index).getTime());
            setEnd.setText(addHour(times.get(index).getTime()));
            bookingPane.setVisible(true);
        }
    });
        b.setStyle("-fx-background-color: #ffffff; ");
        b.setPadding(new Insets(10));
        popup.setContent(b);
        popup.setSource(resultsTable);
    }


    
    private String addHour(String time){
        String[] array = time.split(":");
        int hour = Integer.parseInt(array[0]);
        
        return ((hour+1) + ":00");    
    }
    
    public void back(){
        bookingPane.setVisible(false);
    }
    
    public void makeRepeatBookingRequest(String startDate, String endDate) throws SQLException{

        String requestType = "Repeat Booking";
        RoomBooking booking;
        RepeatBookingRequest rbr;// = new RepeatBookingRequest();
         try{
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
          
            booking = bookings.get(selectedIndex);
            model.updateAllowanceDB(allowance, username);
         
            rbr = new RepeatBookingRequest(booking.getID(),booking,  startDate,  endDate);
            model.makeRepeatBookingRequest(rbr);
            
            
        }catch(Exception a){
            errorLabel.setText("No bookings have been selected.");      
            
       

        }     
         
    }     
         
    public void makeOverrideRequest() throws SQLException{

        String requestType = "Repeat Booking";
        OverrideRequest or;
        RoomBooking roombooking;
        
         try{
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
          
            roombooking = bookings.get(selectedIndex);
           // model.update(allowance, username);
        
            or = new OverrideRequest( roombooking.getID(),username, roombooking);
            model.makeOverrideRequest(or);
            
            
        }catch(Exception a){
            errorLabel.setText("No bookings have been selected.");      
            
        }     
         
    }    

    @Override
    void searchByRooms() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void searchByTime() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

