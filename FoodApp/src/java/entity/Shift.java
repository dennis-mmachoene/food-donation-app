package entity;

public class Shift {

    private String email;
    private String dropoff_org;
    private String dropoff_location;
    private String pickup_location;
    private String pickup_date;
    private String pickup_time;
    private boolean isAvailable;
    private int index;
    private boolean completed;

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    

    public Shift(String email, String dropoff_org, String dropoff_location, String pickup_location, String pickup_date, String pickup_time) {
        this.email = email;
        this.pickup_location = pickup_location;
        this.dropoff_org = dropoff_org;
        this.dropoff_location = dropoff_location;
        this.pickup_date = pickup_date;
        this.pickup_time = pickup_time;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Shift(String email, String dropoff_org, String dropoff_location, String pickup_location, String pickup_date, String pickup_time, boolean isAvailable, int index, boolean completed) {
        this.email = email;
        this.dropoff_org = dropoff_org;
        this.dropoff_location = dropoff_location;
        this.pickup_location = pickup_location;
        this.pickup_date = pickup_date;
        this.pickup_time = pickup_time;
        this.isAvailable = isAvailable;
        this.index = index;
        this.completed = completed;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getDropoff_org() {
        return dropoff_org;
    }

    public void setDropoff_org(String dropoff_orf) {
        this.dropoff_org = dropoff_orf;
    }

    public String getDropoff_location() {
        return dropoff_location;
    }

    public void setDropoff_location(String dropoff_location) {
        this.dropoff_location = dropoff_location;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

}
