/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
public class Administrator extends User {
    
   
    
    
    public Administrator(String name, String password){ 
        super(0, name, password, "Administrator");
       
       
    }
    
    
    public void addRoom(String name, String building, int cap, boolean comp, Building thebuilding){
      
    Room room = new Room (name, building, cap, comp);
    
    thebuilding.addRoom(room);
    
  
   
    }
    
    
    public void removeRoom(String name, Building theBuilding){
     
     theBuilding.removeRoom(name);
        
        
     }
    
    public void addBuilding(String name, int number, Site theSite){
      
    Building building = new Building (name, number);
    
    theSite.addBuilding(building);
    
  
 
    }
    
    public void removeBuilding(String name, Site theSite){
      
    
    
    theSite.removeBuilding(name);
    

    }
    
    
  
   
    
    
    
}
