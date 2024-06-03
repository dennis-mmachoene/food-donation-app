/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author Dennis
 */
public class Donations {
private String email;
    private String to_location;
    
    private String items;
    private String pickup_location;
    private String pickup_date;
    private String pickup_time;
    private byte[] foodImage;
    private String fileName;
    boolean collected;
    private Date creationDate;
    private int index;
    private boolean received;

    public boolean getCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean getReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public Donations(String email, String to_location, String items, String pickup_location, String pickup_date, String pickup_time, byte[] foodImage, boolean collected, Date creationDate, int index, boolean received) {
        this.email = email;
        this.to_location = to_location;
        this.items = items;
        this.pickup_location = pickup_location;
        this.pickup_date = pickup_date;
        this.pickup_time = pickup_time;
        this.foodImage = foodImage;
        this.fileName = fileName;
        this.collected = collected;
        this.creationDate = creationDate;
        this.index = index;
        this.received = received;
    }

    
   

    

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Donations() {
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

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getTo_location() {
        return to_location;
    }

    public void setTo_location(String to_location) {
        this.to_location = to_location;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public byte[] getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(byte[] foodImage) {
        this.foodImage = foodImage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
