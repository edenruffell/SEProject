public class PermissionRequest extends Request{

    String 

    public PermissionRequest(RoomBooking roombooking, String startDate, String endDate, String status, int ID){
    
        super(roombooking.getOwner(), "Repeat", ID, status);
        this.startDate = startDate;
        this.endDate = endDate;
        this.roombooking = roombooking;
    }
    
    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }
    
}
}
  
