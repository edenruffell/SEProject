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
    String department;
    
    
    public Teacher(String n, String p, String d, String type){ 
        super(30, n, p, type);
        department = d;
       
    }
    
    
    public void setDepartment(String department){
        this.department = department;
    }
    
    
    
}
