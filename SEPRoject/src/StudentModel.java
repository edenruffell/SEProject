
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

    public boolean isConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
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

    public ObservableList<Room> searchRooms(String site, String building, String room, String date) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<Room> list = FXCollections.observableArrayList();

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
            Room a = new Room();

            while (rs.next()) {
                a = new Room(site, building,
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

    public void makeBooking(Room selectedRoom, String selectedDate, User user, String sTime, String eTime) throws SQLException {

        boolean exists = false;
        PreparedStatement preparedS;
        ResultSet rs = null;
        ObservableList<ResultSet> list = FXCollections.observableArrayList();

        String site = selectedRoom.site;
        String building = selectedRoom.buildingName;
        String room = selectedRoom.name;
        String owner = user.fName;
        String date = selectedDate;
        String startTime = sTime;
        String endTime = eTime;

        String[] shourMin = sTime.split(":");
        int shour = Integer.parseInt(shourMin[0]);
        int smins = Integer.parseInt(shourMin[1]);

        String[] ehourMin = eTime.split(":");
        int ehour = Integer.parseInt(ehourMin[0]);
        int emins = Integer.parseInt(ehourMin[1]);

        int ID = 1;

        try {
            String query = "SELECT SITE,BUILDING, ROOM, DATE, STIME, ETIME"
                    + " FROM BOOKINGS"
                    + " WHERE SITE = ?"
                    + "AND BUILDING = ? "
                    + "AND ROOM = ?"
                    + "AND DATE = ?"
                    + "AND STIME = ?"
                    + "AND ETIME = ?";

            preparedS = connection.prepareStatement(query);
            preparedS.setString(1, site);
            preparedS.setString(2, building);
            preparedS.setString(3, room);
            preparedS.setString(4, date);

            rs = preparedS.executeQuery();

            while (rs.next()) {

                String gotSTimes = rs.getString("STIME");
                String gotETimes = rs.getString("ETIME");

                String[] GotEhourMin = gotETimes.split(":");
                int gotehour = Integer.parseInt(GotEhourMin[0]);

                String[] GotShourMin = gotSTimes.split(":");
                int gotshour = Integer.parseInt(GotShourMin[0]);

                if (ehour > gotshour && ehour < gotehour) {

                    exists = true;
                }

                if (shour > gotshour && shour < gotehour) {
                    exists = true;
                }
            }

            if (exists) {
                throw new IllegalArgumentException("There is already a booking made at this time");
            } else {
                RoomBooking a = new RoomBooking();

                a = new RoomBooking(ID, owner, building, room, date, startTime, endTime);

                String query2 = "INSERT INTO USER VALUES(?, ?, ?, ?, ?, ?)";

                try {
                    preparedS = connection.prepareStatement(query2);
                    preparedS.setInt(1, a.getID());
                    preparedS.setString(2, a.getOwner());
                    preparedS.setString(3, a.getBuilding());
                    preparedS.setString(4, a.getRoom());
                    preparedS.setString(5, a.getDate());
                    preparedS.setString(6, a.getStartTime());
                    preparedS.setString(6, a.getEndTime());

                    preparedS.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e);

                } finally {
                    preparedS.close();
                }
            }
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println(e);

        } finally {

            rs.close();

        }

    }
}
