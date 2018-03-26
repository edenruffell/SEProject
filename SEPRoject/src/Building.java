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
    

    
    String site;
    String name;
    


  
    public Building(String site, String name){
        this.name = name;
        this.site = site;
    
    }

    
    public String getBuildingName(){
        return name;
    }
    
    public void setBuildingName(String name){
        this.name = name;
    }
    
    public String getSite(){
        return site;
    }
    
    
    
}

