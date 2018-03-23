public class PermissionRequest extends Request{

    String upgradeType;

    public PermissionRequest(int ID, String user, String upgradeType){
    
        super(ID, user, "Permission");
        this.upgradeType = upgradeType;
    }
    
    public PermissionRequest(int ID, String user, String upgradeType, String status){
    
        super(ID, user, "Permission", status);
        this.upgradeType = upgradeType;
    }
    

    public String getUpgradeType() {
        return upgradeType;
    }
}
  
