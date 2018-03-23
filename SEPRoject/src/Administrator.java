import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


public class Administrator implements Initializable {
    
    private String name;
    String username;
    String password;
    String fName;
    String lName;
    String userType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
//public class Administrator extends User {
//
//    public Administrator(String name, String password){ 
//        super(0, name, password, "Administrator");
//       
//       
//    } 
//    
//    public void addRoom(String site, String building, String name,int cap, String comp, Building roombuilding){
//
//    Room room = new Room (site,building,name, cap, comp);
//    roombuilding.addRoom(room);
//    }
//    
//    public void removeRoom(String name, Building theBuilding){
//     
//     theBuilding.removeRoom(name);
//        
//        
//     }
//    
//    public void addBuilding(String name, int number, Site theSite){
//      
//    Building building = new Building (name, number);
//    
//    theSite.addBuilding(building);
//    
//  
// 
//    }
//    
//    public void removeBuilding(String name, Site theSite){
//      
//    
//    
//    theSite.removeBuilding(name);
//    
//
//    }
//    
//    
//  public void searchBooking(User user){
//  
//      System.out.println(user.bookings.toString());
//      
//     
//  }
//    
//    
//   public void editBooking(RoomBooking roombooking, User user){
//   
//       // roombooking.setOwner(User user);
//       // roombooking.setTime(String sTime, String eTime)
//      
//   
//   }
//   
//   
//   public void overrideBooking(RoomBooking roombooking, RepeatBookingRequest rbq){
//   
//       // roombooking.setOwner(rbq.name);
//       // roombooking.setTime(String sTime, String eTime)
//      
//   
//   }
//   
//   
//    
//}
