/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author edenruffell
 */
public class OverrideRequest extends Request{
    
    
  
   RoomBooking roombooking;
   
  
    
    
    
    public OverrideRequest(String user, RoomBooking roombooking, String requestType, int ID){
        
     
        super(user, requestType, roombooking.getID());
        this.roombooking = roombooking;
        
    
    }
    
    
    public OverrideRequest(){
    
        super(null,null,0);
        roombooking= null;
    }
 
  
}
