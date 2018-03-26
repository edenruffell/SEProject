import com.jfoenix.controls.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;


public class Administrator implements Initializable {
    
    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label usernameLabel;
    @FXML private Label message;
    @FXML private Label requestErr;
    @FXML private JFXPasswordField oldpw; 
    @FXML private JFXPasswordField newpw;
    @FXML private JFXPasswordField retypepw;
    @FXML private JFXButton viewRequests;
    @FXML private JFXButton viewOverride;
    @FXML private JFXButton viewRepeat;
    @FXML private JFXButton viewPermission;
    @FXML private JFXButton approveRequest;
    @FXML private JFXButton denyRequest;
    @FXML private TableView overrideTable;
    @FXML private TableColumn<OverrideRequest, Integer> oIDCol;
    @FXML private TableColumn<OverrideRequest, String> oUserCol;
    @FXML private TableColumn<OverrideRequest, String> oBuildingCol;
    @FXML private TableColumn<OverrideRequest, String> oRoomCol;
    @FXML private TableColumn<OverrideRequest, String> oDateCol;
    @FXML private TableColumn<OverrideRequest, String> oSTimeCol;
    @FXML private TableColumn<OverrideRequest, String> oStatusCol;
    @FXML private TableView repeatTable;
    @FXML private TableView permissionTable;
    @FXML private TableColumn<PermissionRequest, Integer> pIDCol;
    @FXML private TableColumn<PermissionRequest, String> pUserCol;
    @FXML private TableColumn<PermissionRequest, String> pTypeCol;
    @FXML private TableColumn<PermissionRequest, String> pStatusCol;
    @FXML private Pane detailsPane;
    @FXML private Pane requestsPane;
    
    private String name;
    private String username;
    private String password;
    private String fName;
    private String lName;
    private String userType;
    
    protected ObservableList<PermissionRequest> permissions = FXCollections.observableArrayList();
    protected ObservableList<RepeatBookingRequest> repeats = FXCollections.observableArrayList();
    protected ObservableList<OverrideRequest> overrides = FXCollections.observableArrayList();
    
    private AdminModel model = new AdminModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewDetails();
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
}
