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
    private int currency;
    private String name;
    private String password;
    private boolean registered;
    
 public User(int i, String n, String p)
    {
        currency = i;
        name = n;
        password = p;
        registered = false;
        
    } 
 
 public boolean Register(){
    registered = true;
    return true;
}

}


