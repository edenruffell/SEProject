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
    
    public ObservableList<PermissionRequest> getPermissions() throws SQLException{
        ObservableList<PermissionRequest> list = FXCollections.observableArrayList();
        PermissionRequest pr = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM PERMISSION";
        
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
               pr = new PermissionRequest(rs.getInt("ID"), rs.getString("NAME"),
                    rs.getString("TYPE"), rs.getString("STATUS"));
                System.out.println(rs.getString("STATUS"));
               list.add(pr);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            ps.close();
            rs.close();
            return list;
        }       
    }
    
    public void updatePW(String username, String pw) throws SQLException{
        PreparedStatement preparedS = null;

        String query = "UPDATE USER SET PASSWORD = ? "
                + " WHERE USERNAME = ?";
        try {
            preparedS = connection.prepareStatement(query);

            // set the corresponding param
            preparedS.setString(1, pw);
            preparedS.setString(2, username);
            // update 
            preparedS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            preparedS.close();
        }
    }
}