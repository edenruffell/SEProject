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
    
    public void removePRequest(PermissionRequest pr, String status) throws SQLException{
        System.out.println("Entering");
        PreparedStatement ps = null;

        String query = "UPDATE PERMISSION SET STATUS = ? "
                + " WHERE NAME = ?";
        String query2 = "UPDATE USER SET UTYPE = ?"
                + " WHERE USERNAME = ?";
        try {
            ps = connection.prepareStatement(query);
            System.out.println("Query1: " + status + " " + pr.getUser());
            // set the corresponding param
            ps.setString(1, status);
            ps.setString(2, pr.getUser());
            // update 
            ps.executeUpdate();
            if(status.equals("Approved")){
                System.out.println("Approved. Update user details");
                ps.close();
                ps = connection.prepareStatement(query2);
                ps.setString(1, pr.getUpgradeType());
                ps.setString(2, pr.getUser());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps.close();
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