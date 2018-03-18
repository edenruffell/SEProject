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
public class Teacher extends User {
  
    
    public Teacher(String name, String password){ 
        super(30, name, password, "Teacher");
       
    }
    
//    public boolean makeBooking(Room room,int date,String startTime, String endTime) {
//
//        
//        if (this.getAllowance()>0) {
//            RoomBooking rm = new RoomBooking(this.getName(), room, date, startTime, endTime ); 
//            return true;
//        }
//        else {
//            System.out.println("Cannot make booking due to unsufficient quota ");
//            return false;
//        }
//    }
    
}
