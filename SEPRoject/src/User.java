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
    int currency;
    String name;
    String password;
    boolean registered = false;
    boolean loggedIn = false;
    String type;
    ArrayList<RoomBooking> bookings = new ArrayList<>();
    
 public User(int currency, String name, String password, String type)
    {
        this.currency = currency;
        this.name = name;
        this.password = password;
        this.type = type; 
        
    } 
 
 public boolean Register(String email, String password){
    this.registered = true;
    return true;
}

 
 public String getName(){
     
     return name;
 
 }
 
 
 
 
 public boolean LogIn(String username, String password){
     
     
     if(username.equals(this.name) && password.equals(this.password)) loggedIn = true;
     else {
         System.out.println("Incorrect username or password. Please try again");
        
         }
     
      return loggedIn;
 
     
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


