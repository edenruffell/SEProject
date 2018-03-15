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
    
    int startDate;
    int endDate;
    RoomBooking roombooking;
     String requestType = "RepeatRoomBooking";
    
    
    
    public RepeatBookingRequest(User user, RoomBooking roombooking, int startDate, int endDate, String requestType){
    
        super(user, requestType);
        this.startDate = startDate;
        this.endDate = endDate;
        this.roombooking = roombooking;
        
    
    
    }
    
    
  
}

