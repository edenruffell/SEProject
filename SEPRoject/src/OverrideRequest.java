public class OverrideRequest extends Request{
    
    private RoomBooking roombooking;

    public OverrideRequest(int ID, RoomBooking roombooking){
        
        
        super(ID, roombooking.getOwner(), "Override", "Pending");
        this.roombooking = roombooking;
    }
    
    public OverrideRequest(int ID, RoomBooking roombooking, String status){
        
        
        super(ID, roombooking.getOwner(), "Override", status);
        this.roombooking = roombooking;
    }

    public RoomBooking getRoomBooking() {
        return roombooking;
    }
    
    public String getBuilding() {
        return roombooking.getBuilding();
    } 
    
    public String getRoom() {
        return roombooking.getRoom();
    }
    
    public String getDate() {
        return roombooking.getDate();
    }
    
    public String getStartTime() {
        return roombooking.getStartTime();
    }    
}
