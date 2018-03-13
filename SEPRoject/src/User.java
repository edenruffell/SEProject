/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author csja3
 */
public class User {
    private int currency = 10;
    private String name;
    private String password;
    private boolean registered = false;
    
 public User(int i, String n, String p)
    {
        currency = i;
        name = n;
        password = p;
    } 
 
 public boolean Register(){
    registered = true;
    return true;
}

}


