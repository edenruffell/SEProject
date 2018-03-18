/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
    import java.util.*;

public class Building{
    

    HashMap<String, Room > roomList = new HashMap<>();
     String name;
    int numRooms;


  
    public Building(String name, int number){
        this.name = name;
        this.numRooms = number;
    }

    
    public String getBuildingName(){
        return name;
    }
    
    public void setBuildingName(String name){
        this.name = name;
    }
    
    public int getNumRooms(String name){
        return numRooms;
    }
    
    public void setNumRooms(int number){
        numRooms = number;
    }
    
    public void addRoom(Room room){
        
        roomList.put(room.name,room);
    
    }
    
    public void removeRoom(String roomName){
    
    
        roomList.remove(roomName);
    }
    
}

