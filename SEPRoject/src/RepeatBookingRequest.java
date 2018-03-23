public class RepeatBookingRequest extends Request{
    
    private String startDate;
    private String endDate;
    private RoomBooking roombooking;

    public RepeatBookingRequest(int ID, RoomBooking roombooking, String startDate, String endDate){
    
        super(ID, roombooking.getOwner(), "Repeat");
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
