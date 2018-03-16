
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
public class LoginModel {
    Connection connection;
    
    public LoginModel(){
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
                
                String[] list = new String[5];
                list[0] = resultSet.getObject("USERNAME").toString();
                list[1] = resultSet.getObject("PASSWORD").toString();
                list[2] = resultSet.getObject("FNAME").toString();
                list[3] = resultSet.getObject("LNAME").toString();
                list[4] = resultSet.getObject("UTYPE").toString();
                
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
    
   /* public boolean isLogin(String user, String pass) throws SQLException{
        PreparedStatement preparedS = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
        try{
            preparedS = connection.prepareStatement(query);
            preparedS.setString(1, user);
            preparedS.setString(2, pass);
            
            resultSet = preparedS.executeQuery();
            
            if(resultSet.next()) return true;
            else return false;
        }catch(Exception e){
            System.out.println(e);
            return false;
            
        } finally {
            preparedS.close();
            resultSet.close();
        }
    } */
    
//    public User checkType(String user, String pass) throws SQLException{
//        PreparedStatement preparedS = null;
//        ResultSet resultSet = null;
//        String query = "SELECT * FROM USER WHERE USERNAME = ?";
//        try{
//            preparedS = connection.prepareStatement(query);
//            preparedS.setString(1, user);
//            
//            resultSet = preparedS.executeQuery();
//
//            Object o = resultSet.getObject("UTYPE");
//            String type = o.toString();
//
//           
//            
//            
//        }catch(Exception e){
//            System.out.println(e);
//            return null;
//            
//        } finally {
//            preparedS.close();
//            resultSet.close();
//        }
//    }
}
