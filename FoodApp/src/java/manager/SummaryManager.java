/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import entity.Donations;
import entity.Donor;
import entity.DonorDetails;
import entity.Organization;
import entity.ProfilePicture;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SummaryManager {

    private Connection connection;

    public SummaryManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }

    public int getTotalDonor() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS totalDonors FROM DONOR_LOGIN");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("totalDonors");
        }
        return 0;
    }

    public int getTotalVol() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS VOLS FROM VOLUNTEER_LOGIN");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("VOLS");
        }
        return 0;
    }

    public int getTotalListings() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS LIST FROM DONATION_LISTING");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("LIST");
        }
        return 0;
    }

    public List<Donor> getDonors() throws SQLException {
    
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONOR_LOGIN");
        ResultSet rs = ps.executeQuery();
        List<Donor> donors = new ArrayList<>();
        while(rs.next()){
            donors.add(new Donor(rs.getString("USERNAME"), rs.getString("EMAIL_ADDRESS"), rs.getTimestamp("CREATION_DATE")));
        }
        return donors;
        
    }
    public List<DonorDetails> getDonorDetails() throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONOR_DETAILS");
        ResultSet rs = ps.executeQuery();
        
        List<DonorDetails> details = new ArrayList<>();
        while(rs.next()){
            details.add(new DonorDetails(rs.getString("EMAIL"), rs.getString("NAMES"), rs.getString("PHONE"), rs.getString("ADDRESS")));
        }
        return details;
    }
    public List<ProfilePicture> getPPs() throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONOR_PROFILE_TABLE");
        ResultSet rs = ps.executeQuery();
        List<ProfilePicture> PPs = new ArrayList<>();
        while(rs.next()){
            PPs.add(new ProfilePicture(rs.getString("EMAIL"), rs.getBytes("PROFILE_PICTURE")));
        }
        return PPs;
    }

    public int getTotalOrgs() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS Orgs FROM FOOD_ORGANIZATIONS");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("Orgs");
        }
        return 0;

    }

      private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }

    public List<Donations> listings() throws SQLException{
    List<Donations> listings = new ArrayList<>();
    
    PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONATION_LISTING");
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
     String email = rs.getString("EMAIL");
           String to_location = rs.getString("TO_LOCATION");
           String items = rs.getString("ITEMS");
           String pickup_location = rs.getString("PICKUP_LOCATION");
           String pickup_date =rs.getString("PICKUP_DATE");
           String pickup_time = rs.getString("PICKUP_TIME");
           byte[] image = rs.getBytes("IMAGE");
           boolean collected = rs.getBoolean("COLLECTED");
           Timestamp createDate = rs.getTimestamp("CREATION_DATE");
           int index = rs.getInt("POSITION");
            boolean received = rs.getBoolean("RECEIVED");
           listings.add(new Donations(email, to_location, items, pickup_location, pickup_date, pickup_time, image, collected, createDate, index, received));

    }
    return listings;
    }
    public List<Organization> organizations() throws SQLException{
     List<Organization> orgs = new ArrayList<>();
     
     PreparedStatement ps = connection.prepareStatement("SELECT * FROM FOOD_ORGANIZATIONS");
     ResultSet rs = ps.executeQuery();
     
     while(rs.next()){
         String name = rs.getString("NAME");
         String desc = rs.getString("DESCRIPTION");
         String phone = rs.getString("PHONE");
         String email = rs.getString("EMAIL");
         String address = rs.getString("ADDRESS");
         String link = rs.getString("LINK");
         byte[] img = rs.getBytes("LOGO_IMAGE");
         orgs.add(new Organization(name, desc, phone, email, address, link, img, rs.getTimestamp("CREATION_DATE")));
         
     }
     return orgs;
    }
}
