public class RepeatBookingRequest extends Request{
    
    String startDate;
    String endDate;
    RoomBooking roombooking;

    public RepeatBookingRequest(RoomBooking roombooking, String startDate, String endDate, String status, int ID){
    
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
