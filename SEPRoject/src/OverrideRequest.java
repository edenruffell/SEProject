public class OverrideRequest extends Request{
    
    private RoomBooking roombooking;

    public OverrideRequest(int ID, String user, RoomBooking roombooking){
        
     
        super(ID, user, "Override");
        this.roombooking = roombooking;
    } 
}
