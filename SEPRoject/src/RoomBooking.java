/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
public class RoomBooking {
    
    String owner;
    String room;
    int date;
    int ID;
    int startTime;
    int endTime;
    int hoursBooked; 
    
   
    
    public RoomBooking(User user, String room, int date, int ID, int startTime, int endTime){
        
        hoursBooked = endTime - startTime;
        this.room = room;
        this.date = date;
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        user.name = owner;
        
        
    }
    
    
      public void setOwner(String owner){
    
            this.owner = owner;
          
    }
      
      public void setTime(int sTime, int eTime){
      
            this.startTime = sTime;
            this.endTime = eTime;
      }
      
    public String getRoom(){

            return room;
    }
    
    
    public String getOwner(){
            
            return owner;
    }
   
    
    
    public int getHoursBooked(){
    
            return hoursBooked;
    }
    
    public int getID(){
    
            return ID;
    }
    
    public int getDate(){
    
            return date;
    }
    
    
    
    
}