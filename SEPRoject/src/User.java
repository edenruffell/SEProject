/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author csja3
 */
import java.util.*;
public class User {
     int allowance;
     String name;
     String password;
     boolean registered = false;
     boolean loggedIn = false;
     String type;
     ArrayList<RoomBooking> bookings = new ArrayList<>();
    
 public User(int allowance, String name, String password, String type)
    {
        this.allowance = allowance;
        this.name = name;
        this.password = password;
        this.type = type; 
        
    }
 
 public String getType() {
        return type;
    }
 
 public int getAllowance() {
        return allowance;
    }

    public String getPassword() {
        return password;
    }
 
 public boolean Register(String email, String password){
    this.registered = true;
    return true;
}
 
 public String getName(){ 
     return name;
 }
 
 
 public void setAllowance(int allow){
     allowance = allow;
 }
 
 public void setLogIn(){
     loggedIn = true;     
 }
 
 
 public void changePassword(String newpassword){
 
     this.password = newpassword;
     
 
 }
 
 /*public Room searchBuilding(Building building){
 
     
        return room;
 }*/
 
  /*public Room searchTime(){
 
     
        return room;
 }*/
 
 
}


