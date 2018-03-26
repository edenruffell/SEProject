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

            
           
            PreparedStatement preparedS = null;
           

             String query = "INSERT INTO OVERRIDE VALUES(?,?,?,?,?,?,?)";
             
             
                

        try {
            preparedS = connection.prepareStatement(query);
            
           

            // set the corresponding param
            preparedS.setInt(1, or.getRoomBooking().getID());
            preparedS.setString(2,or.getRoomBooking().getOwner());
            preparedS.setString(3, or.getRoomBooking().getBuilding());
            preparedS.setString(4, or.getRoomBooking().getRoom());
            preparedS.setString(5, or.getRoomBooking().getStartTime());
            preparedS.setString(6, or.getRoomBooking().getEndTime());
            preparedS.setString(7, "Pending");
            
          
            
                    
            // update 
            preparedS.executeUpdate();
         
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            preparedS.close();
          
        }
        

    }
}
