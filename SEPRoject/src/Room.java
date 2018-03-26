/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
public class Room {
    String site;
    String name;
    String buildingName;
    int capacity;
    String computers;
    Time[] list = new Time[10];
    
    public Room(String site, String building, String name,int cap, String comp){
        this.name = name;
        buildingName = building;
        capacity = cap;
        computers = comp;
        this.site = site;
        
        for(int i=9, j=0; i<=18; i++){
            String time;
            if(i<10) time = "0" + i + ":00";
            else time = i + ":00";
            
            Time t = new Time(time, true);
            
            list[j] = t;
            j++;
        }
    }

    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getCapacity(){
        return capacity;
    }
    
    public void setCapacity(int cap){
        capacity = cap;
    }
    
    public String getComputers(){
        return computers;
    }
    
    public void setComputers(String comp){
        computers = comp;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    
    public String getSite(){
        return site;
       }
    
    public String getBuilding(){
        return buildingName;
    }
    
    public void setTimeArray(Time[] list){
        this.list = list;
    }
    
    public Time[] getTimeArray(){
        return list;
    }
    
    public class Time {
    
        String time;
        boolean available;
    
        public Time(String time, boolean available){
            this.time = time;
            this.available = available;
        
        }

        public String getTime() {
            return time;
        }

        public boolean isAvailable() {
            return available;
        }
        
        public void setAvailable(boolean available){
            this.available = available;
        }
    }
}
