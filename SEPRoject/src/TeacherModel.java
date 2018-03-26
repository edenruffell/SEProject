import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AliyahButt1
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TeacherModel extends StudentModel{
    Connection connection;
    
    public TeacherModel(){
       connection = SQLiteConnection.Connector();
       if(connection == null){
           System.out.println("Cannot connect to DB.");
       }
    }
    
    public void makeRepeatBookingRequest(RepeatBookingRequest rbr) throws SQLException{
        PreparedStatement preparedS = null;

         String query = "INSERT INTO REPEAT VALUES(?,?,?,?,?,?,?,?,?)";
   
        try {
            preparedS = connection.prepareStatement(query);

            // set the corresponding param
            preparedS.setInt(1, rbr.roombooking.getID());
            preparedS.setString(2,rbr.roombooking.getOwner());
            preparedS.setString(3, rbr.roombooking.getBuilding());
            preparedS.setString(4, rbr.roombooking.getRoom());
            preparedS.setString(5, rbr.roombooking.getStartTime());
            preparedS.setString(6, rbr.roombooking.getEndTime());
            preparedS.setString(7, rbr.getStartDate());
            preparedS.setString(8, rbr.getEndDate());
            preparedS.setString(9, rbr.getStatus());         
            // update 
            preparedS.executeUpdate();   
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            preparedS.close();   
        }
    }
    
    public void makeOverrideRequest(OverrideRequest or) throws SQLException{
        PreparedStatement ps = null;
        
        String query = "INSERT INTO OVERRIDE VALUES(?,?,?,?,?,?,?,?)";
        
        try {
            ps = connection.prepareStatement(query);
            // set the corresponding param
            ps.setInt(1, or.getRoomBooking().getID());
            ps.setString(2,or.getRoomBooking().getOwner());
            ps.setString(3, or.getRoomBooking().getBuilding());
            ps.setString(4, or.getRoomBooking().getRoom());
            ps.setString(5, or.getRoomBooking().getDate());
            ps.setString(6, or.getRoomBooking().getStartTime());
            ps.setString(7, or.getRoomBooking().getEndTime());
            ps.setString(8, "Pending"); 
            // update 
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps.close();
        }  
    }
    
    public RoomBooking getABooking(String room, String date, String time) throws SQLException{
            PreparedStatement ps = null;
            ResultSet rs = null;
            RoomBooking rb = null;
            
            String query = "SELECT * FROM BOOKING"
                    + " WHERE ROOM = ?"
                    + " AND DATE = ?"
                    + " AND STIME = ?";
            
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, room);
            ps.setString(2, date);
            ps.setString(3, time);
            rs = ps.executeQuery();
            System.out.println("get booking");
            while(rs.next()){
                rb = new RoomBooking(rs.getInt("ID"),
                        rs.getString("OWNER"),
                        rs.getString("BUILDING"),
                        rs.getString("ROOM"),
                        rs.getString("DATE"),
                        rs.getString("STIME"),
                        rs.getString("ETIME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherModel.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ps.close();
            rs.close();
            return rb;
        }
    }
}
