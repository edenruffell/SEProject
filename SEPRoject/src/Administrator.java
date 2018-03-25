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
    protected ObservableList<OverrideRequest> override = FXCollections.observableArrayList();
    
    private AdminModel model = new AdminModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewDetails();
    }
    
    public void viewPRequests() throws SQLException{
       // overrideTable.setVisible(false);
       // repeatTable.setVisible(false);
        permissionTable.setVisible(true);
        permissionTable.getItems().clear();
        permissions = model.getPermissions();
        pIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        pUserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
        pTypeCol.setCellValueFactory(new PropertyValueFactory<>("UpgradeType"));
        pStatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        permissionTable.setItems(permissions);
    }
    
    public void approvePRequest() throws SQLException{
        System.out.println("Entering approve method.");
        int selectedIndex = permissionTable.getSelectionModel().getSelectedIndex();
        PermissionRequest pr = permissions.get(selectedIndex);
        model.removePRequest(pr, "Approved");
        permissionTable.getItems().remove(selectedIndex);
    }
    
    public void denyPRequest() throws SQLException{
    
    System.out.println("Entering approve method.");
        int selectedIndex = permissionTable.getSelectionModel().getSelectedIndex();
        PermissionRequest pr = permissions.get(selectedIndex);
        model.removePRequest(pr, "Denied");
        permissionTable.getItems().remove(selectedIndex);
    
    
    }
    
    
    
    public void approveRRequest() throws SQLException, ParseException{
        System.out.println("Entering approve method.");
        int selectedIndex = repeatTable.getSelectionModel().getSelectedIndex();
        RepeatBookingRequest rbr = repeats.get(selectedIndex);
        model.removeRRequest(rbr, "Approved");
        repeatTable.getItems().remove(selectedIndex);
    }
    
    public void denyRRequest() throws SQLException, ParseException{
    
    System.out.println("Entering approve method.");
        int selectedIndex = repeatTable.getSelectionModel().getSelectedIndex();
        RepeatBookingRequest rbr = repeats.get(selectedIndex);
        model.removeRRequest(rbr, "Denied");
        permissionTable.getItems().remove(selectedIndex);
    
    
    }
    
    
    
     public void approveORequest() throws SQLException{
        System.out.println("Entering approve method.");
        int selectedIndex = overrideTable.getSelectionModel().getSelectedIndex();
        OverrideRequest or = override.get(selectedIndex);
        model.removeORequest(or, "Approved");
        overrideTable.getItems().remove(selectedIndex);
    }
    
    public void denyORequest() throws SQLException{
    
    System.out.println("Entering approve method.");
        int selectedIndex = overrideTable.getSelectionModel().getSelectedIndex();
        OverrideRequest or = override.get(selectedIndex);
        model.removeORequest(or, "Denied");
        overrideTable.getItems().remove(selectedIndex);
    
    
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
        requestsPane.setVisible(true);
        detailsPane.setVisible(false);
    } 
    
    public void viewDetails(){
        requestsPane.setVisible(false);
        detailsPane.setVisible(true);
    }
    
    
    
    
    
   
}
