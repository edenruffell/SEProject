import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AliyahButt1
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class StudentModel {
    Connection connection;
    
    public StudentModel(){
       connection = SQLiteConnection.Connector();
       if(connection == null){
           System.out.println("Cannot connect to DB.");
       }
    }

    public void updateAllowanceDB(int allowance, String username) throws SQLException {
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

    public ObservableList<RoomBooking> getBooking(String username) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<RoomBooking> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM BOOKING"
                + " WHERE OWNER = ? ORDER BY ID ASC";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, username);

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
        }
        return list;
    }

    public void removeBooking(int ID) throws SQLException {
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
    
    

    public ObservableList<String> getSites() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> list = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT SITE FROM ROOMS";

        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("SITE"));
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

    public ObservableList<String> getBuildings(String site) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> list = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT BUILDING FROM ROOMS "
                + "WHERE SITE = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, site);

            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("BUILDING"));
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

    public Room searchRooms(String site, String building, String room, String date) throws SQLException {
        Room r = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT ROOM, CAPACITY, COMPUTERS FROM ROOMS"
                + " WHERE SITE = ?"
                + "AND BUILDING = ? "
                + "AND ROOM = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, site);
            ps.setString(2, building);
            ps.setString(3, room);

            rs = ps.executeQuery();
            

            if(rs.next()) {
                r = new Room(site, building,
                        rs.getString("ROOM"),
                        rs.getInt("CAPACITY"),
                        rs.getString("COMPUTERS"));
            }
            r = setBookedTimes(r, building, room, date);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            ps.close();
            rs.close();
            return r;
        }
    }
    
    public Room setBookedTimes(Room r, String building, String room, String date){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Room.Time[] array = r.getTimeArray();

        String query = "SELECT STIME FROM BOOKING"
                + " WHERE BUILDING = ?"
                + "AND ROOM = ? "
                + "AND DATE = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, building);
            ps.setString(2, room);
            ps.setString(3, date);

            rs = ps.executeQuery();
            

            if(rs.next()) {
                String time = rs.getString("STIME");

                for(int i =0; i<array.length; i++){
                    if(time.equals(array[i].getTime())){
                        array[i].setAvailable(false);
                    }
                }   
            }
            r.setTimeArray(array);
            
            }catch (SQLException e){
                System.out.println(e.getMessage());      
            }
        return r;
    }

    public ObservableList<String> getRooms(String building) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> list = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT ROOM FROM ROOMS "
                + "WHERE BUILDING = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, building);

            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("ROOM"));
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
    
    
    public void saveBooking(Room room, String date, String username, String sTime, String eTime) throws SQLException{
        PreparedStatement ps = null;
        
        String query = "INSERT INTO BOOKING VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        int last = getLastID();
        
        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, last);
            ps.setString(2, username);
            ps.setString(3, room.getBuildingName());
            ps.setString(4, room.getName());
            ps.setString(5, date);
            ps.setString(6, sTime);
            ps.setString(7, eTime);
            // update 
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps.close();
        }  
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

    private int getRequestLastID(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int last = -1;
        try {
            String query = "SELECT * FROM REQUEST"
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
    
    
    int getLastPRequestID(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int last = -1;
        try {
            String query = "SELECT * FROM PREQUEST"
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
    public void makePermissionRequest(PermissionRequest pr) throws SQLException{

         PreparedStatement ps1 = null;
         PreparedStatement ps2 = null;
         
         

             String query1 = "INSERT INTO PREQUEST "
                + " WHERE ID = ?"
                + " WHERE NAME = ?"
                + " WHERE NEWSTATUS"
                + "WHERE REQUESTTYPE = ?";
                
             
             String query2 = "INSERT INTO REQUEST "
                + " WHERE ID = ?"
                + " WHERE REQUESTTYPE = ?";
    
         
             try {
            ps1 = connection.prepareStatement(query1);
            ps2 = connection.prepareStatement(query2);
                    
            ps1.setInt(1, pr.getID());
            ps1.setString(2, pr.name);
            ps1.setString(3, pr.type);
            ps1.setString(4, pr.requestType);
            
            ps2.setInt(1, pr.getID());
            ps2.setString(6, pr.type);
            
            // update 
            ps1.executeUpdate();
            ps2.executeUpdate();
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps1.close();
            ps2.close();
        }  
}
    
}
            
           
