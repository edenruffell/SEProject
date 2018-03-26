import com.jfoenix.controls.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Administrator implements Initializable {
    
    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label usernameLabel;
    @FXML private Label message;
    @FXML private Label requestErr;
    @FXML private Label errorLabel;
    @FXML private JFXPasswordField oldpw; 
    @FXML private JFXPasswordField newpw;
    @FXML private JFXPasswordField retypepw;
    @FXML private JFXTextField siteName;
    @FXML private JFXTextField buildingName;
    @FXML private JFXTextField roomName;
    @FXML private JFXTextField capacity;
    @FXML private JFXTextField computers;
    @FXML private JFXButton viewRequests;
    @FXML private JFXButton viewOverride;
    @FXML private JFXButton viewRepeat;
    @FXML private JFXButton viewPermission;
    @FXML private JFXButton approveRequest;
    @FXML private JFXButton denyRequest;
    @FXML private JFXButton logout;
    @FXML private JFXButton addRoomButton;
    @FXML private JFXButton removeRoomButton;
    @FXML private TableView overrideTable;
    @FXML private TableColumn<OverrideRequest, Integer> oIDCol;
    @FXML private TableColumn<OverrideRequest, String> oUserCol;
    @FXML private TableColumn<OverrideRequest, String> oBuildingCol;
    @FXML private TableColumn<OverrideRequest, String> oRoomCol;
    @FXML private TableColumn<OverrideRequest, String> oDateCol;
    @FXML private TableColumn<OverrideRequest, String> oSTimeCol;
    @FXML private TableColumn<OverrideRequest, String> oStatusCol;
    @FXML private TableView repeatTable;
    @FXML private TableColumn<RepeatBookingRequest, String> rIDCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rUserCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rBuildingCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rRoomCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rSDateCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rEDateCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rSTimeCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rETimeCol;
    @FXML private TableColumn<RepeatBookingRequest, String> rStatusCol;
    @FXML private TableView permissionTable;
    @FXML private TableColumn<PermissionRequest, Integer> pIDCol;
    @FXML private TableColumn<PermissionRequest, String> pUserCol;
    @FXML private TableColumn<PermissionRequest, String> pTypeCol;
    @FXML private TableColumn<PermissionRequest, String> pStatusCol;
    @FXML private TableView modifyTable;
    @FXML private TableColumn<Room, String> siteCol;
    @FXML private TableColumn<Room, String> buildingCol;
    @FXML private TableColumn<Room, String> roomCol;
    @FXML private TableColumn<Room, String> compCol;
    @FXML private TableColumn<Room, String> capCol;
    
    @FXML private Pane detailsPane;
    @FXML private Pane requestsPane;
    @FXML private Pane modifyPane;
    
    private String name;
    private String username;
    private String password;
    private String fName;
    private String lName;
    private String userType;
    
    protected ObservableList<PermissionRequest> permissions = FXCollections.observableArrayList();
    protected ObservableList<RepeatBookingRequest> repeats = FXCollections.observableArrayList();
    protected ObservableList<OverrideRequest> overrides = FXCollections.observableArrayList();
    protected ObservableList<Room> modify = FXCollections.observableArrayList();
    
    private AdminModel model = new AdminModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewDetails();
    }
    
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
    
    public void viewPRequests() throws SQLException{
        overrideTable.setVisible(false);
        repeatTable.setVisible(false);
        permissionTable.setVisible(true);
        permissionTable.getItems().clear();
        permissions = model.getPermissions();
        pIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        pUserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        pTypeCol.setCellValueFactory(new PropertyValueFactory<>("UpgradeType"));
        pStatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        permissionTable.setItems(permissions);
    }
    
    public void viewORequests() throws SQLException{
        overrideTable.setVisible(true);
        repeatTable.setVisible(false);
        permissionTable.setVisible(false);
        overrideTable.getItems().clear();
        overrides = model.getOverrides();
        oIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        oUserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        oBuildingCol.setCellValueFactory(new PropertyValueFactory<>("Building"));
        oRoomCol.setCellValueFactory(new PropertyValueFactory<>("Room"));
        oDateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));        
        oSTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        oStatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        overrideTable.setItems(overrides); 
    }
  
    public void viewRRequests() throws SQLException{
        overrideTable.setVisible(false);
        repeatTable.setVisible(true);
        permissionTable.setVisible(false);
        repeatTable.getItems().clear();
        repeats = model.getRepeats();
        rIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rUserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        rBuildingCol.setCellValueFactory(new PropertyValueFactory<>("Building"));
        rRoomCol.setCellValueFactory(new PropertyValueFactory<>("Room"));
        rSDateCol.setCellValueFactory(new PropertyValueFactory<>("StartDate"));        
        rEDateCol.setCellValueFactory(new PropertyValueFactory<>("EndDate"));        
        rSTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        rETimeCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        rStatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        repeatTable.setItems(repeats); 
    }
    
    public void approveRequest() throws SQLException, ParseException{
        int selectedIndex;
        if(permissionTable.isVisible()){
            selectedIndex = permissionTable.getSelectionModel().getSelectedIndex();
            if(checkRequest(selectedIndex))
                PRequest("Approved", selectedIndex);
            else requestErr.setText("Request has already been resolved");
        }
        else if(repeatTable.isVisible()){
                        selectedIndex = repeatTable.getSelectionModel().getSelectedIndex();
            if(checkRequest(selectedIndex))
            RRequest("Approved", selectedIndex); 
        }
        else if(overrideTable.isVisible()){
            selectedIndex = overrideTable.getSelectionModel().getSelectedIndex();
            if(checkRequest(selectedIndex))
                ORequest("Approved", selectedIndex); 
            else requestErr.setText("Request has already been resolved");
        }   
    }
    
    public void denyRequest() throws SQLException, ParseException{
        int selectedIndex;
        if(permissionTable.isVisible()){
            selectedIndex = permissionTable.getSelectionModel().getSelectedIndex();
            if(checkRequest(selectedIndex))
                PRequest("Denied", selectedIndex); 
            else requestErr.setText("Request has already been resolved");
        }
        else if(repeatTable.isVisible()){
            selectedIndex = repeatTable.getSelectionModel().getSelectedIndex();
            if(checkRequest(selectedIndex))
                RRequest("Denied", selectedIndex); 
            else requestErr.setText("Request has already been resolved");
        }
        else if(overrideTable.isVisible()){
            selectedIndex = overrideTable.getSelectionModel().getSelectedIndex();
            if(checkRequest(selectedIndex))
                ORequest("Denied", selectedIndex); 
            else requestErr.setText("Request has already been resolved");
        }
    }
    
    public void PRequest(String status, int index) throws SQLException{
        requestErr.setText("");
        PermissionRequest pr = permissions.get(index);
        model.removePRequest(pr, status);
        viewPRequests();
    }
    
    public void RRequest(String status, int index) throws SQLException, ParseException{
        requestErr.setText("");
        RepeatBookingRequest rbr = repeats.get(index);
        model.removeRRequest(rbr, status);
        repeatTable.getItems().remove(index);
    }
    
    public void ORequest(String status, int index) throws SQLException{
        requestErr.setText("");
        OverrideRequest or = overrides.get(index);
        model.removeORequest(or, status);
        overrideTable.getItems().remove(index);
        viewORequests();
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
    
    public void setUserDetails(String[] data) {
        username = data[0];
        password = data[1];
        fName = data[2];
        lName = data[3];
        userType = data[4];
        nameLabel.setText(fName + " " + lName);
        typeLabel.setText("Account Type: " + userType);
        usernameLabel.setText("Username " + username);
    }

    public void viewRequestsPane(){
        requestErr.setText("");
        overrideTable.setVisible(false);
        repeatTable.setVisible(false);
        permissionTable.setVisible(true);
        requestsPane.setVisible(true);
        detailsPane.setVisible(false);
    } 
    
    public void viewDetails(){
        requestsPane.setVisible(false);
        detailsPane.setVisible(true);
        modifyPane.setVisible(false);
    }
    
    public void viewModify() throws SQLException{
        modifyTable.getItems().clear();
        requestsPane.setVisible(false);
        detailsPane.setVisible(false);
        modifyPane.setVisible(true);
        modify = model.getRooms();
        siteCol.setCellValueFactory(new PropertyValueFactory<>("Site"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<>("Building"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        compCol.setCellValueFactory(new PropertyValueFactory<>("Computers"));
        capCol.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
        modifyTable.setItems(modify);
    }
    
    public boolean checkRequest(int index){
        if(permissionTable.isVisible())
            if(!permissions.get(index).getStatus().equals("Pending")) return false;
        else if(repeatTable.isVisible())
            if(!repeats.get(index).getStatus().equals("Pending")) return false;
        else if(overrideTable.isVisible())
            if(!overrides.get(index).getStatus().equals("Pending")) return false;
        return true;
    }
   
    public void addRoom() throws SQLException{
        boolean exists = false;
    
        modifyPane.setVisible(true);
        detailsPane.setVisible(false);
        requestsPane.setVisible(false);
        
        String site = siteName.getText();
        String building = buildingName.getText();
        String room = roomName.getText();
        int cap = Integer.parseInt(capacity.getText());
        String comp = computers.getText();

        for(Room r : modify){
        
            if(building.equals(r.getBuilding()) && room.equals(r.getName())){
             exists = true;
             break;
            }
        }
        
        if(!exists){
            Room r = new Room(site, building, room, cap, comp);
            model.addRoom(r);
            errorLabel.setText("Room added successfully!");
            viewModify();
        }
        else errorLabel.setText("Room already exists");
    }
    
    
    public void removeRoom() throws SQLException{
        modifyPane.setVisible(true);
        detailsPane.setVisible(false);
        requestsPane.setVisible(false);
        int selectedIndex = modifyTable.getSelectionModel().getSelectedIndex();
        Room room = modify.get(selectedIndex);  
        model.removeRoom(room);
        viewModify();
    }
    
}
