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
    
private int ID;
private String owner;
private String building;
private String room;
private String date;
private String startTime;
private String endTime;
private int hoursBooked;
    
   
    
    public RoomBooking(int ID, String owner, String build, String room, String date, String sTime, String eTime){ 
        this.ID = ID;
        this.owner = owner;
        building = build;
        this.room = room;
        this.date = date;
        startTime = sTime;
        endTime = eTime;
        hoursBooked = split(eTime) - split(sTime);  
    }
    
    public RoomBooking(){

        this.ID = 0;
        this.owner = null;
        building = null;
        this.room = null;
        this.date = null;
        startTime = null;
        endTime = null;
        hoursBooked = 0; 

}
    
    

    public int getID() {
        return ID;
    }

    public String getOwner() {
        return owner;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getHoursBooked() {
        return hoursBooked;
    }    
    
    private int split(String time){
        String[] list = time.split(":");
        
        return Integer.parseInt(list[0]);
    }
    
      public void setOwner(String owner){
    
            this.owner = owner;
          
    }
      
      public void setTime(String sTime, String eTime){
      
            this.startTime = sTime;
            this.endTime = eTime;
      }
}