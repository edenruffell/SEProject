import com.jfoenix.controls.*;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author AliyahButt1
 */

public class Registrant implements Initializable {
    
    private String fName;
    private String lName;
    private String username;
    private String password;
    
    protected RegistrantModel loginModel = new RegistrantModel();
    @FXML private Label isConnected;
    @FXML private Label registerLabel;
    @FXML private Label errorLabel;
    @FXML private Label userErr;
    @FXML private Label pwErr;
    @FXML private Label namesLabel;
    @FXML private JFXTextField userTextField;
    @FXML private JFXTextField fNameField;
    @FXML private JFXTextField lNameField;
    @FXML private JFXTextField usernameField;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXPasswordField retypeField;
    @FXML private JFXPasswordField pwTextField;
    @FXML private JFXButton register;
    @FXML private JFXButton completeRegister;
    @FXML private JFXButton login;
    @FXML private JFXButton back;
    @FXML private Pane startPane;
    @FXML private Pane registerPane;
    
    public void login(ActionEvent event){
        try {
                String username = userTextField.getText();
                String password = pwTextField.getText();
                String[] data = loginModel.getData(username, password);
                
            if(data!=null){
            try {
                    if(data[4].equals("Student")){
                    //load new screen
                    FXMLLoader loader = new FXMLLoader();
                    Parent mainMenu = loader.load(getClass().getResource("StudentView.fxml").openStream());
                    //pass on user info
                    Student main = (Student)loader.getController();
                    main.setUserDetails(data);
                    main.setName();
                    main.setAllowanceText(data[5]);
                    Scene mainMenuScene = new Scene(mainMenu);
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(mainMenuScene);
                    window.show();
                    loginModel.connection.close();
                }else if(data[4].equals("Teacher")){
                    //load new screen
                    FXMLLoader loader = new FXMLLoader();
                    Parent mainMenu = loader.load(getClass().getResource("TeacherView.fxml").openStream());
                    //pass on user info
                    Teacher main = (Teacher)loader.getController();
                    main.setUserDetails(data);
                    main.setName();
                    main.setAllowanceText(data[5]);
                    Scene mainMenuScene = new Scene(mainMenu);
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(mainMenuScene);
                    window.show();
                    loginModel.connection.close();
                }
                    
                } catch (IOException ex) {
                Logger.getLogger(Registrant.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(username.equals("")||password.equals("")){
            errorLabel.setText("Username or password cannot be blank.");
        }else errorLabel.setText("Incorrect username or password. Please try again.");
        } catch (SQLException ex) {
            Logger.getLogger(Registrant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showRegisterPane(){
        startPane.setVisible(false);
        registerPane.setVisible(true);
    }
    
    public void clear(){
        pwErr.setText("");
        registerLabel.setText("");
        userErr.setText("");
        namesLabel.setText("");
    }
    
    public void register() throws SQLException{
        fName = fNameField.getText();
        lName = lNameField.getText();
        username = usernameField.getText();
        password = passwordField.getText();
        
        if(fName.isEmpty()||lName.isEmpty()) namesLabel.setText("First name or Surname cannot be empty");
        if(password.isEmpty()) pwErr.setText("Password cannot be empty");
        else if(!password.equals(retypeField.getText())){
            pwErr.setText("Passwords do not match");
            return;
        }
        if(username.isEmpty()){
            userErr.setText("Username cannot be empty");
            return;
        }
        if(!username.contains("@qmul.ac.uk")){
            userErr.setText("You cannot register without your QMUL email address");
            return;
        }
            
        boolean exists = loginModel.checkUsername(username);
        
        if(exists) userErr.setText("Username is already registered to an account, please enter a different one.");
        else{
             fName = fNameField.getText();
             lName = lNameField.getText();
             username = usernameField.getText();
             password = passwordField.getText();            
             loginModel.addToDB(fName, lName, username, password);
             registerLabel.setText("You have registered successfully! Click back and you can log in.");
        }
    }
    
    public void back(){
        registerPane.setVisible(false);
        startPane.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(loginModel.isConnected()) isConnected.setText("Connected");
        else isConnected.setText("Not connected");
        registerPane.setVisible(false);
    }
}
