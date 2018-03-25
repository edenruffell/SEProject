public class Request {
    
    private int ID;
    private String user;
    private String type;
    private String status;

    public Request(int ID, String user, String type){
        this.ID = ID;
        this.user = user;
        this.type = type; 
        status = "Pending";
    }
    
    public Request(int ID, String user, String type, String status){
        this.ID = ID;
        this.user = user;
        this.type = type; 
        this.status = status;
    }

    public String getUser(){
        return user;
    }
        
    public String getType(){
        return type;
    }

    public String getStatus() {
        return status;
    }
    
    public int getID(){
        return ID;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}