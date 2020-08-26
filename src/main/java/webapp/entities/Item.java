package webapp.entities;

public class Item {

    private String selectLocation;
  
    private String  selecttime;
    
    public Item() {
        super();
    } 
    
    public Item(String selectLocation, String selecttime) {
        super();
        this.selectLocation = selectLocation;
        this.selecttime = selecttime;
    }
    public String getSelectLocation() {
        return selectLocation;
    }
    public void setSelectlocation(String selectLocation) {
        this.selectLocation = selectLocation;
    }
    public String getSelecttime() {
        return selecttime;
    }
    public void setSelecttime(String selectTime) {
        this.selecttime = selectTime;
    }
}
