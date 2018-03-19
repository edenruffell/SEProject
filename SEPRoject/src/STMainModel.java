
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AliyahButt1
 */

import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class STMainModel {
    Connection connection;
    
    public STMainModel(){
       connection = SQLiteConnection.Connector();
       if(connection == null){
           System.out.println("Cannot connect to DB.");
       }
    }
    
    public boolean isConnected(){
        try{
            return !connection.isClosed();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public void update(int allowance, String username) throws SQLException {
        PreparedStatement preparedS = null;
        
        String query = "UPDATE USER SET ALLOWANCE = ? "
                        + " WHERE USERNAME = ?";
 
        try {
            preparedS = connection.prepareStatement(query);
 
            // set the corresponding param
            preparedS.setInt(1, allowance);
            preparedS.setString(2, username);
            // update 
            preparedS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            preparedS.close();
        }
    }
    
    public ObservableList<RoomBooking> getBooking(String username) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<RoomBooking> list = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM BOOKING"
                        + " WHERE OWNER = ?";
 
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            
            rs = ps.executeQuery();
            RoomBooking a;
            
            while(rs.next()){
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
        }
        return list;
    }
    
    public void removeBooking(int ID) throws SQLException{
        PreparedStatement preparedS = null;
        
        String query = "DELETE FROM BOOKING"
                        + " WHERE ID = ?";
 
        try {
            preparedS = connection.prepareStatement(query);
 
            // set the corresponding param
            preparedS.setInt(1, ID);
            // update 
            preparedS.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            preparedS.close();
        }
    }
    
 
    
    
    public ObservableList<Room> searchRooms() throws SQLException{
        
       String[] choices = { "Arts One", "Arts two", "Bancroft Road", "Engineering", "Francis Bancroft", "G.E. Fogg","Geography","G.O. Jones","Graduate Centre", "Laws","Lock Keepers","Maths","Peoples Palace","Queens"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose Building: ",
        "Search for Room", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]); // Initial choice
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<Room> list = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM ROOMS"
                        + " WHERE BUILDING = ?";
 
 
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1,input);
            
            rs = ps.executeQuery();
            Room a = new Room();
            
            while(rs.next()){
                a = new Room(rs.getString("SITE"),
                             rs.getString("BUILDING"),
                             rs.getString("ROOM"),
                             rs.getInt("CAPACITY"),
                             rs.getString("COMPUTERS"));
                    list.add(a);
             
            }           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            ps.close();
            rs.close();
        }
        return list;
    }
        
}
