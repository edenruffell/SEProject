
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
public class RegistrantModel {
    Connection connection;
    
    public RegistrantModel(){
       connection = SQLiteConnection.Connector();
       if(connection == null){
           System.out.println("Cannot connect to Data Base.");
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
    
    public String[] getData(String user, String pass)throws SQLException{
        PreparedStatement preparedS = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
        try{
            preparedS = connection.prepareStatement(query);
            preparedS.setString(1, user);
            preparedS.setString(2, pass);
            
            resultSet = preparedS.executeQuery();
            
            if(resultSet.next()){
                
                String[] list = new String[6];
                list[0] = resultSet.getString("USERNAME");
                list[1] = resultSet.getString("PASSWORD");
                list[2] = resultSet.getString("FNAME");
                list[3] = resultSet.getString("LNAME");
                list[4] = resultSet.getString("UTYPE");
                list[5] = resultSet.getObject("ALLOWANCE").toString();
                
                return list;
            } else return null;
        }catch(Exception e){
            System.out.println(e);
            return null;
            
        } finally {
            preparedS.close();
            resultSet.close();
        }
    }
    
    public boolean checkUsername(String username) throws SQLException{
        boolean exists = false;
        PreparedStatement preparedS = null;
        ResultSet resultSet = null;
        String query = "SELECT USERNAME FROM USER WHERE USERNAME = ?";
        try{
            preparedS = connection.prepareStatement(query);
            preparedS.setString(1, username);
            
            resultSet = preparedS.executeQuery();
            
            if(resultSet.next()){
                if(username.equals(resultSet.getString("USERNAME"))) exists = true;
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
            
        } finally {
            preparedS.close();
            resultSet.close();
            return exists;
        }
    }

    public void addToDB(String fName, String lName, String user, String pass) throws SQLException{
        PreparedStatement preparedS = null;
        
        String query = "INSERT INTO USER VALUES(?, ?, ?, ?, ?, ?)";
        
        try{
            preparedS = connection.prepareStatement(query);
            preparedS.setString(1, user);
            preparedS.setString(2, pass);
            preparedS.setString(3, fName);
            preparedS.setString(4, lName);
            preparedS.setString(5, "Student");
            preparedS.setInt(6, 15);
            
            preparedS.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            
        } finally {
            preparedS.close();
        }
    }
}