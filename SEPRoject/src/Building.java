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
public class Building{
    
<<<<<<< HEAD
    HashMap<Room, Boolean> roomList = new HashMap<>();
    private String name;
    int numRooms;
=======
    public Building(int i)
{
    
}
}

>>>>>>> fd010fb307358bb5c539fb1b089da0570744f9de
    
    public Building(String name, int number){
        this.name = name;
        numRooms = number;
        
    }
    
    public void setBuildingName(String name){
        this.name = name;
    }
}