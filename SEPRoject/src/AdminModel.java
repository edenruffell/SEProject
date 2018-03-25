import static java.nio.file.Files.list;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.Iterator;
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
            else if(status.equals("Denied")){
            System.out.println("Denied.");
                ps.close();
                ps = connection.prepareStatement(query2);
                ps.setString(1,"Student");
                ps.setString(2, pr.getUser());
                ps.executeUpdate();
            
            
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps.close();
        }
    }
    
    
    public void removeRRequest(RepeatBookingRequest rb, String status) throws SQLException, ParseException{
        System.out.println("Entering");
        PreparedStatement ps = null;

        String query = "UPDATE REPEAT SET STATUS = ? "
                + " WHERE NAME = ?";
        try {
            ps = connection.prepareStatement(query);
            
            // set the corresponding param
            ps.setString(1, status);
            ps.setString(2, rb.getName());
            // update 
            ps.executeUpdate();
            if(status.equals("Approved")){
                System.out.println("Approved. ");
                ps.close();
                
                
         Calendar c = Calendar.getInstance();
        ArrayList<String> dates = new ArrayList<>(); 
        
        DateFormat formatter = new SimpleDateFormat("yyyymmdd");
        Date date1 = (Date)formatter.parse(rb.getStartDate());
        Date date2 = (Date)formatter.parse(rb.getEndDate());
        String date;
        c.setTime(date1);
        
       while (c.getTime().before(date2)) {
        // add another week
        c.add(Calendar.WEEK_OF_YEAR, 1);
        date= formatter.format(c.getTime());
        dates.add(date);
        }
      Iterator listIterator = dates.listIterator();
      int counter = 0;
      
       while(listIterator.hasNext()){
           
       
           
       
        String query2 = "INSERT INTO BOOKING VALUES(?,?,?,?,?,?)";
                
        try {
           
        
                ps = connection.prepareStatement(query2);
                ps.setInt(1, rb.getID());
                ps.setString(2, rb.getName());
                ps.setString(3, rb.roombooking.getRoom());
                ps.setString(4, rb.roombooking.getBuilding());
                ps.setString(5,dates.get(counter));
                ps.setString(6, rb.roombooking.getStartTime());
                ps.setString(7, rb.roombooking.getEndTime());
                
                
                
                
           
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps.close();
        }
       }
                
            }
            else if(status.equals("Denied")){
            
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps.close();
        }
        
     
    }
    
    
     public void removeORequest(OverrideRequest or, String status) throws SQLException{
        System.out.println("Entering");
        PreparedStatement ps = null;

        String query = "UPDATE OVERRIDE SET STATUS = ? "
                + " WHERE NAME = ?";
        
       
        String query2 = "UPDATE BOOKING SET USER = ?";
                
        try {
            ps = connection.prepareStatement(query);
            
            // set the corresponding param
            ps.setString(1, status);
            ps.setString(2, or.getName());
            // update 
            ps.executeUpdate();
            if(status.equals("Approved")){
                System.out.println("Approved override request");
                ps.close();
                ps = connection.prepareStatement(query2);
                ps.setString(1, or.getName());
                ps.executeUpdate();
            }
            else if(status.equals("Denied")){
            System.out.println("Denied.");
                ps.close();
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