import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AliyahButt1
 */

public class AdminModel {
    Connection connection;
    
    public AdminModel(){
       connection = SQLiteConnection.Connector();
       if(connection == null){
           System.out.println("Cannot connect to DB.");
       }
    }
    
    
}