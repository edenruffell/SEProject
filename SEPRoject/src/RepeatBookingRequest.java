/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
public class RepeatBookingRequest extends Request{
    
    String startDate;
    String endDate;
    RoomBooking roombooking;
      
    
    
    
    public RepeatBookingRequest(RoomBooking roombooking, String startDate, String endDate, String requestType, int ID){
    
        super(roombooking.getOwner(), requestType, ID);
        this.startDate = startDate;
        this.endDate = endDate;
        this.roombooking = roombooking;
      
    
    }
    
     public RepeatBookingRequest(){
         super(null,null, 0);
          this.startDate = null;
           this.endDate = null;
        this.roombooking = null;
      
    
    }
    
    public String getStartDate(){
     return startDate;
    }
    
    
    public String getEndDate(){
     return endDate;
    }
    
}
