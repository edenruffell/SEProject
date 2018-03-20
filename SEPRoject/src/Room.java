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
    
    public Room(String site, String building, String name,int cap, String comp){
        this.name = name;
        buildingName = building;
        capacity = cap;
        computers = comp;
        this.site = site;
    }
    
    public Room(){
        this.name = null;
        buildingName = null;
        capacity = 0;
        computers = null;
        site = null;
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
    
    
    public String getBuildingName(){
        return buildingName;
    } 
}
