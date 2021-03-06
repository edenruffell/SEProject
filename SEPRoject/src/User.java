import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;

abstract class User {
    
    String username;
    String password;
    String fName;
    String lName;
    String userType;
    int allowance;
    
    abstract void viewBookings() throws SQLException;
    abstract void logout(ActionEvent event) throws IOException;
    abstract void setUserDetails(String[] data);
    abstract void setAllowanceText(String allow);
    abstract void updateAllowance(int hours);
    abstract void cancelBooking() throws SQLException;
    abstract void searchByRooms() throws SQLException;
    abstract void searchByTime() throws SQLException;
    abstract void makeBooking() throws SQLException;
}


