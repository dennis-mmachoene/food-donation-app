package manager;

import entity.Donations;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ListingsManager {

    private Connection connection;

    public ListingsManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }

    public void addDonationList(String email, String to_location, String items,
            String pickup_location, String pickup_date,
            String pickup_time, byte[] image) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO DONATION_LISTING VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, email);
        ps.setString(2, to_location);
        ps.setString(3, items);
        ps.setString(4, pickup_location);
        ps.setString(5, pickup_date);
        ps.setString(6, pickup_time);
        ps.setBytes(7, image);
        ps.setBoolean(8, false);
        ps.setTimestamp(9, Timestamp.from(Instant.now()));
        PreparedStatement pps = connection.prepareStatement("SELECT COUNT(*) AS TOTAL FROM DONATION_LISTING");
        ResultSet rs = pps.executeQuery();
        int count = 0;
        if(rs.next()){
            count = rs.getInt("TOTAL") + 1;
        }
        ps.setInt(10, count);
        ps.setBoolean(11, false);
        int i = ps.executeUpdate();
        if (i > 0) {
            String dropoff_location = new OrganizationsManager().org_address(to_location);
            addShift(email, to_location, dropoff_location, pickup_location, pickup_date, pickup_time);
        }
    }

    public List<Donations> getListings(String email_address) throws SQLException {
        List<Donations> listings = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONATION_LISTING WHERE EMAIL = ?");
        ps.setString(1, email_address);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String email = rs.getString("EMAIL");
            String to_location = rs.getString("TO_LOCATION");
            String items = rs.getString("ITEMS");
            String pickup_location = rs.getString("PICKUP_LOCATION");
            String pickup_date = rs.getString("PICKUP_DATE");
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
    public List<Donations> getDonationsByOrgName(String org_name) throws SQLException{
       List<Donations> listings = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONATION_LISTING WHERE TO_LOCATION = ?");
        ps.setString(1, org_name);
        ResultSet rs = ps.executeQuery();
        System.out.println();
        while (rs.next()) {
            String email = rs.getString("EMAIL");
            String to_location = rs.getString("TO_LOCATION");
            String items = rs.getString("ITEMS");
            String pickup_location = rs.getString("PICKUP_LOCATION");
            String pickup_date = rs.getString("PICKUP_DATE");
            String pickup_time = rs.getString("PICKUP_TIME");
            byte[] image = rs.getBytes("IMAGE");
            boolean collected = rs.getBoolean("COLLECTED");
            Timestamp createDate = rs.getTimestamp("CREATION_DATE");
            int index = rs.getInt("POSITION");
            boolean received = rs.getBoolean("RECEIVED");

            listings.add(new Donations(email, to_location, items, pickup_location, pickup_date, pickup_time, image, collected, createDate, index, received));

        }
        System.out.println(listings.size());
        return listings;
    }

    public void addShift(String email, String dropoff_org,
            String dropoff_location, String pickup_location,
            String pickup_date, String pickup_time) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO SHIFTS_AVAILABLE VALUES(?,?,?,?,?,?,?,?,?)");

        PreparedStatement pps = connection.prepareStatement("SELECT COUNT(*) AS TOTAL FROM SHIFTS_AVAILABLE");
        ResultSet rs = pps.executeQuery();
        int count = 1;
        if(rs.next()){
            count = rs.getInt("TOTAL") + 1;
        }
        ps.setString(1, email);
        ps.setString(2, dropoff_org);
        ps.setString(3, dropoff_location);
        ps.setString(4, pickup_location);
        ps.setString(5, pickup_date);
        ps.setString(6, pickup_time);
        ps.setBoolean(7, true);
        ps.setInt(8, count);
        ps.setBoolean(9, false);
        ps.executeUpdate();

    }

     private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }

    public void update(int index) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("UPDATE DONATION_LISTING SET COLLECTED = ? WHERE POSITION = ?");
        ps.setBoolean(1, true);
        ps.setInt(2, index);
        ps.executeUpdate();
    }
    public void received(int index) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("UPDATE DONATION_LISTING SET RECEIVED = ? WHERE POSITION = ?");
        ps.setBoolean(1, true);
        ps.setInt(2, index);
        ps.executeUpdate();
    }
}
