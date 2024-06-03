/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import entity.DonorDetails;
import java.sql.*;

public class DonorAccountManager {

    private Connection connection;

    public DonorAccountManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }

    public void addDetails(DonorDetails d) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO DONOR_DETAILS VALUES(?,?,?,?)");
        ps.setString(1, d.getEmail());
        ps.setString(2, d.getNames());
        ps.setString(3, d.getPhone());
        ps.setString(4, d.getAddress());
        ps.executeUpdate();
    }

    public void updateDetails(DonorDetails donor) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE DONOR_DETAILS SET NAMES = ?, PHONE = ?, ADDRESS = ? WHERE EMAIL = ?");
        ps.setString(1, donor.getNames());
        ps.setString(2, donor.getPhone());
        ps.setString(3, donor.getAddress());
        ps.setString(4, donor.getEmail());
        ps.executeUpdate();
    }

    public DonorDetails getDetails(String email) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONOR_DETAILS WHERE EMAIL = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        DonorDetails donor = new DonorDetails();
        if (rs.next()) {
            donor.setEmail(rs.getString("EMAIL"));
            donor.setNames(rs.getString("NAMES"));
            donor.setPhone(rs.getString("PHONE"));
            donor.setAddress(rs.getString("address"));
            return donor;
        }
        return null;
    }

  private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }


    public void uploadPP(String email, byte[] image) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONOR_PROFILE_TABLE WHERE EMAIL = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            PreparedStatement update = connection.prepareStatement("UPDATE DONOR_PROFILE_TABLE SET PROFILE_PICTURE = ? WHERE EMAIL = ?");
            update.setBytes(1, image);
            update.setString(2, email);
            update.executeUpdate();
        } else {
            PreparedStatement upload = connection.prepareStatement("INSERT INTO DONOR_PROFILE_TABLE VALUES(?,?)");
            upload.setString(1, email);
            upload.setBytes(2, image);
            upload.executeUpdate();

        }
    }

    public byte[] getDP(String email) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT PROFILE_PICTURE FROM DONOR_PROFILE_TABLE WHERE EMAIL = ?");
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getBytes("PROFILE_PICTURE");
        }
        return null;

    }

    public void deleteDonor(String email) throws SQLException {
        PreparedStatement ps1 = connection.prepareStatement("DELETE FROM SHIFTS_AVAILABLE WHERE EMAIL = ?");
        PreparedStatement ps2 = connection.prepareStatement("DELETE FROM DONATION_LISTING WHERE EMAIL = ?");
        PreparedStatement ps3 = connection.prepareStatement("DELETE FROM DONOR_PROFILE_TABLE WHERE EMAIL = ?");
        PreparedStatement ps4 = connection.prepareStatement("DELETE FROM DONOR_DETAILS WHERE EMAIL = ?");
        PreparedStatement ps5 = connection.prepareStatement("DELETE FROM DONOR_LOGIN WHERE EMAIL_ADDRESS = ?");

        ps1.setString(1, email);
        ps2.setString(1, email);
        ps3.setString(1, email);
        ps4.setString(1, email);
        ps5.setString(1, email);

        ps1.executeUpdate();
        ps2.executeUpdate();
        ps3.executeUpdate();
        ps4.executeUpdate();
        ps5.executeUpdate();
    }
}
