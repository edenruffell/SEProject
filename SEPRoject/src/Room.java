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
    String name;
    String buildingName;
    int capacity;
    boolean computers;
    
    public Room(String name, String building, int cap, boolean comp){
        this.name = name;
        buildingName = building;
        capacity = cap;
        computers = comp;
    }
    
    public String getRoomName(){
        return name;
    }
    
    public void setRoomName(String name){
        this.name = name;
    }
    
    public int getCapacity(){
        return capacity;
    }
    
    public void setCapacity(int cap){
        capacity = cap;
    }
    
    public String getComputers(){
        if(computers) return "Yes";
        else return "No";
    }
    
    public void setComputers(boolean comp){
        computers = comp;
    }
    
    
    public String getBuildingName(){
        return buildingName;
    } 
}
