/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import entity.DonorDetails;
import entity.ProfilePicture;
import entity.Shift;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolunteerAccountManager {

    private Connection connection;

    public VolunteerAccountManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }

    public List<Shift> getShifts() throws SQLException {
        List<Shift> shifts = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM SHIFTS_AVAILABLE");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String email = rs.getString("EMAIL");
            String pickup_location = rs.getString("PICKUP_LOCATION");
            String dropoff_org = rs.getString("DROPOFF_ORG");
            String dropoff_location = rs.getString("DROPOFF_LOCATION");
            String pickup_date = rs.getString("PICKUP_DATE");
            String pickup_time = rs.getString("PICKUP_TIME");
            boolean isAvailable = rs.getBoolean("AVAILABLE");
            int index = rs.getInt("POSITION");
            boolean completed = rs.getBoolean("COMPLETED");
            if (!completed) {
                shifts.add(new Shift(email, dropoff_org, dropoff_location, pickup_location, pickup_date, pickup_time, isAvailable, index, completed));
            }
        }

        return shifts;
    }

    public List<Shift> getSignedShifts(String sEmail) throws SQLException {
        List<Integer> shiftNumbers = new ArrayList<>();
        PreparedStatement getShiftNums = connection.prepareStatement("SELECT SHIFT_NUMBER FROM SIGNED_SHIFTS WHERE EMAIL = ?");
        getShiftNums.setString(1, sEmail);
        ResultSet rs = getShiftNums.executeQuery();

        while (rs.next()) {
            int shiftNumber = rs.getInt("SHIFT_NUMBER");
            shiftNumbers.add(shiftNumber);
        }
        int size = shiftNumbers.size();
        List<Shift> shifts = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM SHIFTS_AVAILABLE WHERE POSITION = ?");
        if (size > 0) {
            for (Integer shiftNumber : shiftNumbers) {
                ps.setInt(1, shiftNumber);
                ResultSet set = ps.executeQuery();
                if (set.next()) {
                    String email = set.getString("EMAIL");
                    String pickup_location = set.getString("PICKUP_LOCATION");
                    String dropoff_org = set.getString("DROPOFF_ORG");
                    String dropoff_location = set.getString("DROPOFF_LOCATION");
                    String pickup_date = set.getString("PICKUP_DATE");
                    String pickup_time = set.getString("PICKUP_TIME");
                    boolean isAvailable = set.getBoolean("AVAILABLE");
                    int index = set.getInt("POSITION");
                    boolean completed = set.getBoolean("COMPLETED");

                    shifts.add(new Shift(email, dropoff_org, dropoff_location, pickup_location, pickup_date, pickup_time, isAvailable, index, completed));

                }
            }
            return shifts;
        } else {
            return null;
        }
    }

    public void markShiftUnAV(int shiftNumber, String email) throws SQLException {
    // Update the shift to unavailable
    PreparedStatement update = connection.prepareStatement("UPDATE SHIFTS_AVAILABLE SET AVAILABLE = ? WHERE POSITION = ?");
    update.setBoolean(1, false);
    update.setInt(2, shiftNumber);
    update.executeUpdate();

    // Insert into SIGNED_SHIFTS
    PreparedStatement ps = connection.prepareStatement("INSERT INTO SIGNED_SHIFTS VALUES (?, ?, ?)");
    ps.setString(1, email);
    ps.setInt(2, shiftNumber);
    ps.setBoolean(3, false);
    ps.executeUpdate();
}


    public void cancelShift(String email, int shiftNumber) throws SQLException {
        PreparedStatement update = connection.prepareStatement("UPDATE SHIFTS_AVAILABLE SET AVAILABLE = ? WHERE POSITION = ?");
        update.setBoolean(1, true);
        update.setInt(2, shiftNumber);
        update.executeUpdate();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM SIGNED_SHIFTS WHERE SHIFT_NUMBER = ?");
        ps.setInt(1, shiftNumber);
        ps.executeUpdate();
    }

    public void completedShift(String email, int shiftNumber) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE SIGNED_SHIFTS SET DONE = ? WHERE SHIFT_Number = ?");
        ps.setBoolean(1, true);
        ps.setInt(2, shiftNumber);
        ps.executeUpdate();

        PreparedStatement update = connection.prepareStatement("UPDATE SHIFTS_AVAILABLE SET COMPLETED = ? WHERE POSITION = ?");
        update.setBoolean(1, true);
        update.setInt(2, shiftNumber);
        update.executeUpdate();
    }

     private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }


    public List<DonorDetails> donors() throws SQLException {
        List<DonorDetails> donors = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONOR_DETAILS");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String names = rs.getString("NAMES");
            String email = rs.getString("EMAIL");
            String phone = rs.getString("PHONE");
            String address = rs.getString("ADDRESS");
            donors.add(new DonorDetails(email, names, phone, address));

        }
        return donors;
    }

    public List<ProfilePicture> pps() throws SQLException {
        List<ProfilePicture> pps = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONOR_PROFILE_TABLE");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            pps.add(new ProfilePicture(rs.getString("EMAIL"), rs.getBytes("PROFILE_PICTURE")));
        }
        return pps;
    }

}
