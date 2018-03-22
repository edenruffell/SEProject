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
    int ID;
   
    

        public Request(String owner, String requestType, int ID){

             name = owner;
                this.requestType = requestType;
                this.ID = ID;

        }   
        
        public Request(){
            name = null;
            requestType = null;
        
        }


        public String getName(){
         return name;
        }
        
        public String getRequestType(){
         return requestType;
        }
           
        public int getID(){
         return ID;
        }
}