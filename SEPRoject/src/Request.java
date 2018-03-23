public class Request {
    
    String name;
    String requestType;
    String status;
    int ID;

    public Request(String owner, String requestType, int ID, String status){
        name = owner;
        this.requestType = requestType;
        this.ID = ID;
        this.status = status;
    }

    public String getName(){
        return name;
    }
        
    public String getRequestType(){
        return requestType;
    }

    public String getStatus() {
        return status;
    }
    
    public int getID(){
        return ID;
    }
}