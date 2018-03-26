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
    
    public ObservableList<OverrideRequest> getOverrides() throws SQLException{
        ObservableList<OverrideRequest> list = FXCollections.observableArrayList();
        OverrideRequest or = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RoomBooking rb;
        
        String query = "SELECT * FROM OVERRIDE";
        
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                
                int ID = rs.getInt("ID");
                String user = rs.getString("NAME");
                String building = rs.getString("BUILDING");
                String room = rs.getString("ROOM");
                String date = rs.getString("DATE");
                String sTime = rs.getString("STIME");
                String eTime = addHour(sTime);
                
               rb = new RoomBooking(getLastID(), user, building, room, date, sTime, eTime);
               or = new OverrideRequest(ID, rb, rs.getString("STATUS"));
               list.add(or);
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
  
    public ObservableList<RepeatBookingRequest> getRepeats() throws SQLException{
        ObservableList<RepeatBookingRequest> list = FXCollections.observableArrayList();
        RepeatBookingRequest rbr = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RoomBooking rb;
        
        String query = "SELECT * FROM REPEAT";
        
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                
                int ID = rs.getInt("ID");
                String user = rs.getString("NAME");
                String building = rs.getString("BUILDING");
                String room = rs.getString("ROOM");
                String sDate = rs.getString("SDATE");
                String eDate = rs.getString("EDATE");
                String sTime = rs.getString("STIME");
                String eTime = addHour(sTime);
                
               rb = new RoomBooking(getLastID(), user, building, room, sDate, sTime, eTime);
               rbr = new RepeatBookingRequest(ID, rb, sDate, eDate, rs.getString("STATUS"));
               list.add(rbr);
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
    
    public ObservableList<RoomBooking> getBookings() throws SQLException{
            PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<RoomBooking> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM BOOKING"
                + " ORDER BY ID ASC";

        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();
            RoomBooking a;

            while (rs.next()) {
                a = new RoomBooking(rs.getInt("ID"),
                        rs.getString("OWNER"),
                        rs.getString("BUILDING"),
                        rs.getString("ROOM"),
                        rs.getString("DATE"),
                        rs.getString("STIME"),
                        rs.getString("ETIME"));
                list.add(a);
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
            // set the corresponding param
            ps.setString(1, status);
            ps.setString(2, pr.getUser());
            // update 
            ps.executeUpdate();
            if(status.equals("Approved")){
                ps.close();
                ps = connection.prepareStatement(query2);
                ps.setString(1, pr.getUpgradeType());
                ps.setString(2, pr.getUser());
                ps.executeUpdate();
            }
            else if(status.equals("Denied")){
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
    
    public ObservableList<Room> getRooms() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<Room> list = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM ROOMS";
        
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Room r = new Room(rs.getString("SITE"), rs.getString("BUILDING"),
                rs.getString("ROOM"), rs.getInt("CAPACITY"), rs.getString("COMPUTERS"));
                
                list.add(r);                
            }
        }catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ps.close();
            rs.close();
            return list;
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
        PreparedStatement ps = null;

        String query = "UPDATE OVERRIDE SET STATUS = ? "
                + " WHERE ID = ?";
        
       
        String query2 = "INSERT INTO BOOKING VALUES (?, ?, ?, ?, ?, ?, ?)";
                
        try {
            ps = connection.prepareStatement(query);
            
            // set the corresponding param
            ps.setString(1, status);
            ps.setInt(2, or.getID());
            // update 
            ps.executeUpdate();
            if(status.equals("Approved")){
                ps.close();
                ps = connection.prepareStatement(query2);
                ps.setInt(1, getLastID());
                String sTime = or.getRoomBooking().getStartTime();
                ps.setString(2, or.getRoomBooking().getOwner());
                ps.setString(3, or.getRoomBooking().getBuilding());
                ps.setString(4, or.getRoomBooking().getRoom());
                ps.setString(5, or.getRoomBooking().getDate());
                ps.setString(6, sTime);
                ps.setString(7, addHour(sTime));
                ps.executeUpdate();
            }
            else if(status.equals("Denied")){
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
    
    private String addHour(String time){
        String[] array = time.split(":");
        int hour = Integer.parseInt(array[0]);
        
        return ((hour+1) + ":00");    
    }
    
    private int getLastID(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int last = -1;
        try {
            String query = "SELECT * FROM BOOKING"
                    + " ORDER BY ID DESC LIMIT 1;";
            
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                last = rs.getInt("ID") + 1;
            }
            
            ps.close();
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return last;
    }
    
    public void addRoom(Room room){
        PreparedStatement ps = null;
        String query = "INSERT INTO ROOMS VALUES(?,?,?,?,?)";
        
        try {      
            ps = connection.prepareStatement(query);
            
            ps.setString(1,room.getSite());
            ps.setString(2, room.getBuilding());
            ps.setString(3, room.getName());
            ps.setInt(4, room.getCapacity());
            ps.setString(5, room.getComputers());
            ps.executeUpdate();
            ps.close();
 
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
 
    public void removeRoom(Room room) throws SQLException {
        PreparedStatement preparedS = null;

        String query = "DELETE FROM ROOMS"
                + " WHERE BUILDING = ?"
                + "AND ROOM = ?";

        try {
            preparedS = connection.prepareStatement(query);

            // set the corresponding param
            preparedS.setString(1, room.getBuilding() );
            preparedS.setString(2, room.getName());
            // update 
            preparedS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            preparedS.close();
        }
    }
    
    
    
    
    
    
   
    
}