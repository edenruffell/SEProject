public class RepeatBookingRequest extends Request{
    
    private String startDate;
    private String endDate;
     RoomBooking roombooking;
    private String status;
    

    public RepeatBookingRequest(int ID, RoomBooking roombooking, String startDate, String endDate){
    
        super(ID, roombooking.getOwner(), "Repeat");
        this.startDate = startDate;
        this.endDate = endDate;
        this.roombooking = roombooking;
        status = "Pending";
    }
    
    public RepeatBookingRequest(int ID, RoomBooking roombooking, String startDate, String endDate, String status){
    
        super(ID, roombooking.getOwner(), "Repeat");
        this.startDate = startDate;
        this.endDate = endDate;
        this.roombooking = roombooking;
        this.status = status;
    }
    
    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }
    
    public RoomBooking getRoomBooking(){
    
        return roombooking;
    }
    
    public String getStatus(){
    
        return status;
    }
    
    public String getName(){
    
        return roombooking.getOwner();
    }
    
    public String getBuilding(){
        return roombooking.getBuilding();
    }
    
    public String getRoom(){
        return roombooking.getRoom();
    }
    
    public String getStartTime(){
        return roombooking.getBuilding();
    }
    
    public String getEndTime(){
        return roombooking.getBuilding();
    }
    
}
