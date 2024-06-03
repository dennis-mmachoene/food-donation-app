/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import entity.Requests;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RequestManager {

    Connection connection;

    public RequestManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = getConnection();
    }
    public void doRequest(String name, String email) throws SQLException{
       
       PreparedStatement count = connection.prepareStatement("SELECT COUNT(*) AS TOTAL FROM DONATION_REQUESTS");
       ResultSet set = count.executeQuery();
       int index = 1;
       if(set.next()){
           index = set.getInt("TOTAL") + 1;
       }
       
       PreparedStatement ps = connection.prepareStatement("INSERT INTO DONATION_REQUESTS VALUES(?,?,?,?)");
       ps.setString(1, name);
       ps.setString(2, email);
       ps.setTimestamp(3, Timestamp.from(Instant.now()));
       ps.setInt(4, index);
       ps.executeUpdate();
    }
  
    public List<Requests> getRequests() throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONATION_REQUESTS");
        List<Requests> requests = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            String name = rs.getString("NAME");
            String donor_email = rs.getString("DONOR_EMAIL");
            Timestamp creationDate = rs.getTimestamp("POSTED_AT");
            int index = rs.getInt("POSITION");
            
            Requests req = new Requests(name, donor_email, creationDate, index);
            requests.add(req);
        }
        return requests;
    }
    public List<Requests> getRequests(String email) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM DONATION_REQUESTS WHERE DONOR_EMAIL = ?");
        List<Requests> requests = new ArrayList<>();
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            String name = rs.getString("NAME");
            String donor_email = rs.getString("DONOR_EMAIL");
            Timestamp creationDate = rs.getTimestamp("POSTED_AT");
            int index = rs.getInt("POSITION");
            
            Requests req = new Requests(name, donor_email, creationDate, index);
            requests.add(req);
        }
        return requests;
    }
     private Connection getConnection() throws SQLException{
       return DriverManager.getConnection("jdbc:mysql://192.168.231.179:3306/donnationappassesment5?useSSL=false&&allowPublicKeyRetrieval=true", "app", "123");
 }

}
