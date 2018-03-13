/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
@author edenruffell
public class Building{
    

    HashMap<Room, Boolean> roomList = new HashMap<>();
    private String name;
    int numRooms;


  
    public Building(String name, int number){
        this.name = name;
        numRooms = number;
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
    
}