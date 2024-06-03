/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Dennis
 */
public class ProfilePicture {
    private String email;
    private byte[] pp;

    public ProfilePicture(String email, byte[] pp) {
        this.email = email;
        this.pp = pp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPp() {
        return pp;
    }

    public void setPp(byte[] pp) {
        this.pp = pp;
    }
    
    
}
