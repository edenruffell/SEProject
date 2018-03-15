
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
public class Site {
    
    String name;
    HashMap<Room, Boolean> buildingList = new HashMap<>();
    
    
    public Site(String name){
        this.name = name;
    }
    
    public void setName (String name){
    
        this.name = name;
        
    }   
    
    public String getName (){
    
        return name;
    }
    
    
}
