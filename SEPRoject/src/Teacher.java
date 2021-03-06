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
    @FXML private Button logout;
    @FXML private Button settings;
    @FXML private Button overrideBooking;
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
    @FXML private Button repeatBooking;
    @FXML private TableView<RoomBooking> bookingTable;
    @FXML private TableColumn<RoomBooking, Integer> idCol;
    @FXML private TableColumn<RoomBooking, String> buildCol;
    @FXML private TableColumn<RoomBooking, String> roomCol;
    @FXML private TableColumn<RoomBooking, String> dateCol;
    @FXML private TableColumn<RoomBooking, String> sTimeCol;
    @FXML private TableColumn<RoomBooking, String> eTimeCol;

    @FXML private Pane searchByRoomPane;
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
    
    @FXML private Pane searchByTimePane;
    @FXML private JFXComboBox siteBox2;
    @FXML private JFXComboBox buildingBox2;
    @FXML private JFXComboBox timeBox;
    @FXML private JFXButton findRoomButton;
    @FXML private JFXButton back2;
    @FXML private JFXDatePicker datePicker2;
    @FXML private JFXDatePicker repeatStartDate;
    @FXML private JFXDatePicker repeatEndDate;
    @FXML private TableView<Room> resultsTable2;
    @FXML private TableColumn<Room, String> roomCol2;
    @FXML private TableColumn<Room, String> compCol;
    @FXML private TableColumn<Room, String> capCol;
    @FXML private JFXPopup popup2;
   
    private String selectedSite;
    private String selectedBuilding;
    private String selectedRoom;
    private String selectedDate;
    private String selectedTime;        
    private Room room;

    protected ObservableList<RoomBooking> bookings;
    protected ObservableList<String> siteList;
    protected ObservableList<String> timeList = FXCollections.observableArrayList();
    protected ObservableList<String> buildingList = FXCollections.observableArrayList();
    protected ObservableList<String> roomList = FXCollections.observableArrayList();
    protected ObservableList<Room.Time> times = FXCollections.observableArrayList();
    protected ObservableList<Room> rooms = FXCollections.observableArrayList();
    protected TeacherModel model = new TeacherModel();
    
    /**
     * Initialises the controller class.
     */
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewDetails();
        try {
            siteList = model.getSites();
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        siteBox.setItems(siteList);
        siteBox2.setItems(siteList);
        setTimes();
        timeBox.setItems(timeList);
        initPopUp(); 
        initPopUp2();
    }

    public void viewDetails(){
        detailsPane.setVisible(true);
        searchByRoomPane.setVisible(false);
        searchByTimePane.setVisible(false);
        bookingPane.setVisible(false);
        viewPane.setVisible(false);
        siteBox.setVisible(true);
    }

    @Override
    public void viewBookings() throws SQLException {
        detailsPane.setVisible(false);
        searchByRoomPane.setVisible(false);
        searchByTimePane.setVisible(false);
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

    public void viewSearchByRoom() {
        detailsPane.setVisible(false);
        searchByRoomPane.setVisible(true);
        searchByTimePane.setVisible(false);
        bookingPane.setVisible(false);
        viewPane.setVisible(false);
    }
    
    public void viewSearchByTime(){
        detailsPane.setVisible(false);
        searchByRoomPane.setVisible(false);
        searchByTimePane.setVisible(true);
        bookingPane.setVisible(false);
        viewPane.setVisible(false);
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
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void searchByRooms() throws SQLException{
        resultsTable.getItems().clear();
        try{
            selectedRoom = roomBox.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            searchError.setText("Please select a room.");
        }
        try {
            selectedDate = datePicker.getValue().toString();
            room = model.searchByRooms(selectedSite, selectedBuilding, selectedRoom, selectedDate);
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
    
    @Override
    public void searchByTime() throws SQLException {
        resultsTable2.getItems().clear();
        try{
            selectedBuilding = buildingBox2.getSelectionModel().getSelectedItem().toString();
        }catch(NullPointerException e){
            searchError.setText("Please select a building.");
        }
        try{
            selectedTime = timeBox.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            searchError.setText("Please select a time.");
        }
        try {
            selectedDate = datePicker2.getValue().toString();
            rooms = model.searchByTime(selectedSite, selectedBuilding, selectedTime, selectedDate);
            roomCol2.setCellValueFactory(new PropertyValueFactory<>("Name"));
            compCol.setCellValueFactory(new PropertyValueFactory<>("Computers"));
            capCol.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
            resultsTable2.setItems(rooms);
        }catch(NullPointerException e){
            e.printStackTrace();
            searchError.setText("Please select a date.");
            return;
        }         
        
    }
    
    public void addBookingDB() throws SQLException{
        if(searchByTimePane.isVisible()) makeBooking2();
        else makeBooking();
    }
    
    @Override
    public void makeBooking() throws SQLException {
        model.saveBooking(room, setDate.getText(), this.username, setStart.getText(), setEnd.getText());
        updateAllowance(-1);
        model.updateAllowanceDB(allowance, username);
        searchByRooms();
        heading.setText("Booking confirmed!");
        confirmBooking.setDisable(true);  
    }
    
    public void makeBooking2() throws SQLException {
        model.saveBooking(room, setDate.getText(), this.username, setStart.getText(), setEnd.getText());
        updateAllowance(-1);
        model.updateAllowanceDB(allowance, username);
        searchByTime();
        heading.setText("Booking confirmed!");
        confirmBooking.setDisable(true);  
    }

    @Override
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
    
    @Override
    public void updateAllowance(int hours) {
        allowance = allowance + hours;
        setAllowanceText(Integer.toString(allowance));
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
            password = newpw;
            model.updatePW(username, password);
            message.setText("Password has been changed.");
            this.oldpw.clear();
            this.newpw.clear();
            this.retypepw.clear();
        }
    }

    @Override
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
        nameLabel.setText(fName + " " + lName);
        typeLabel.setText("Account Type: " + userType);
        usernameLabel.setText("Username " + username);
    }
    
    public void setTimes(){        
        for(int i=9, j=0; i<=18; i++){
            String time;
            if(i<10) time = "0" + i + ":00";
            else time = i + ":00";
            
            timeList.add(time);
            j++;
        }
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
    
    public void setBuildings2() throws SQLException {
        try {
            selectedSite = siteBox2.getSelectionModel().getSelectedItem().toString();
            buildingList = model.getBuildings(selectedSite);
            buildingBox2.setItems(buildingList);
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
    
    public void addTimes(Room r){
        Room.Time[] list = r.getTimeArray();
        for(int i=0; i<list.length; i++){
            times.add(list[i]);
        }     
    }
    
    private String addHour(String time){
        String[] array = time.split(":");
        int hour = Integer.parseInt(array[0]);
        
        return ((hour+1) + ":00");    
    }
    
    public void back(){
        bookingPane.setVisible(false);
        heading.setText("Make Booking?");
        confirmBooking.setDisable(false);  
    }
    
    public void showPopup(MouseEvent e){
        String currentRoom = roomBox.getSelectionModel().getSelectedItem().toString();
        int index = resultsTable.getSelectionModel().getSelectedIndex();
        try{ if (resultsTable.getItems().isEmpty()){}
        else if(!times.get(index).isAvailable()){}
        else if(!currentRoom.equals(room.getName())) searchError.setText("Results do not match current room. Please press \"Find Times\" again.");
        else popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, e.getX(), e.getY());}
        catch(ArrayIndexOutOfBoundsException a){
        }
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
    
        public void showPopup2(MouseEvent e){
        popup2.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, e.getX(), e.getY());
    }
    
        private void initPopUp2() {
        JFXButton b = new JFXButton("Make Booking?");
        b.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int index = resultsTable2.getSelectionModel().getSelectedIndex();
            room = rooms.get(index);
            popup2.close();
            setBuilding.setText(buildingBox2.getSelectionModel().getSelectedItem().toString());
            setRoom.setText("Room: " + rooms.get(index).getName());
            setDate.setText(datePicker2.getValue().toString());
            setStart.setText(timeBox.getSelectionModel().getSelectedItem().toString());
            setEnd.setText(addHour(timeBox.getSelectionModel().getSelectedItem().toString()));
            bookingPane.setVisible(true);
        }
    });
        b.setStyle("-fx-background-color: #ffffff; ");
        b.setPadding(new Insets(10));
        popup2.setContent(b);
        popup2.setSource(resultsTable2);
    }
    
    public void makeRepeatBookingRequest() throws SQLException{

        String requestType = "Repeat Booking";
        RoomBooking booking;
        RepeatBookingRequest rbr;
        String startDate;
        String endDate;
         try{
            int selectedIndex = bookingTable.getSelectionModel().getSelectedIndex();
            booking = bookings.get(selectedIndex);
        }catch(Exception a){
            errorLabel.setText("No bookings have been selected.");
            return;
        } 
         try{
             startDate = repeatStartDate.getValue().toString();
             endDate = repeatEndDate.getValue().toString();

        }catch(NullPointerException e){
            errorLabel.setText("Please enter a start and end date.");
            return;     
        }
         
            rbr = new RepeatBookingRequest(booking.getID(),booking,  startDate,  endDate);
            model.makeRepeatBookingRequest(rbr);
            errorLabel.setText("Request made successfully!");
    }
         
    public void makeOverrideRequest() throws SQLException{
        OverrideRequest or;
        RoomBooking roombooking;
        
         try{
            int selectedIndex = resultsTable.getSelectionModel().getSelectedIndex();
            
            if(times.get(selectedIndex).isAvailable()){
                searchError.setText("Room is not booked, override request not needed");
                return;
            }
            String date = datePicker.getValue().toString();
            String chosenRoom = roomBox.getSelectionModel().getSelectedItem().toString();
            String time = times.get(selectedIndex).getTime();
            roombooking = model.getABooking(chosenRoom, date, time, this.username);
            updateAllowance(-1);
            model.updateAllowanceDB(allowance, username);
        
            or = new OverrideRequest( roombooking.getID(), roombooking);
            model.makeOverrideRequest(or);
            searchError.setText("Request made successfully!");
        }catch(Exception a){
            searchError.setText("No bookings have been selected.");      
            
        }     
         
    }     
}

