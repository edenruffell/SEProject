/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edenruffell
 */
public class Request {
    
    String name;
    String requestType;
    boolean completed = false;
    

        public Request(User user, String requestType){

             name = user.getName();
            this.requestType = requestType;

        }   

}