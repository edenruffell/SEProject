public class OverrideRequest extends Request{
    
     RoomBooking roombooking;
     String status;

    public OverrideRequest(int ID, String user, RoomBooking roombooking){
        
        
        super(ID, user, "Override");
        status = "Pending";
        this.roombooking = roombooking;
    } 
    
    public String getStatus(){
    
    return status;
    }
    
    public String getName(){
    
    return roombooking.getOwner();
    }
}
