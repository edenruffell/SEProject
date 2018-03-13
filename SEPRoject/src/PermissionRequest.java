/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
public class PermissionRequest extends Request{
    
    
    User user;
    
    
    
    public PermissionRequest(User user, RoomBooking roombooking, int startDate, int endDate, String requestType){
    
        super(user, requestType);
        this.user = user;
        
        
    
    
    }
   
}
  
