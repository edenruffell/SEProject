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
    boolean registered;
    String type;
    
 public User(int i, String n, String p, String type)
    {
        this.currency = i;
        this.name = n;
        this.password = p;
        this.registered = false;
        this.type = type; 
        
    } 
 
 public boolean Register(){
    registered = true;
    return true;
}

 
 public String getName(){
     
     return name;
 
 }
}


